package org.librazy.paperpgitem;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.librazy.nclangchecker.LangKey;
import org.librazy.paperpgitem.power.PowerJump;
import think.rpgitems.RPGItems;
import think.rpgitems.power.PowerManager;
import think.rpgitems.power.PowerPlain;
import think.rpgitems.power.Trigger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

import static org.librazy.paperpgitem.PaperTriggers.PAPER_JUMP;
import static org.librazy.paperpgitem.PaperTriggers.PAPER_KNOCKBACK;

public final class PapeRPGItems extends JavaPlugin {

    public static PapeRPGItems plugin;

    private static Logger logger;
    private static boolean supported = true;
    private EventListener eventlistener;

    @Override
    public void onLoad() {
        plugin = this;
        logger = plugin.getLogger();
        new I18n(this, "en_US");
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
        } catch (ClassNotFoundException e) {
            logger.severe("====================================================");
            logger.severe(" " + plugin.getDescription().getName() + " REQUIRES Paper ");
            logger.severe(" as your server software. ");
            logger.severe("====================================================");
            supported = false;
            this.setEnabled(false);
            throw new UnknownDependencyException("PaperSpigot");
        }
        super.onLoad();
        Trigger.register(PAPER_JUMP);
        Trigger.register(PAPER_KNOCKBACK);

        PowerManager.registerAdapter(PowerPlain.class, PowerJump.class, p -> getWrapper(p, PowerJump.class));

        PowerManager.registerPowers(this, "org.librazy.paperpgitem.power");
        PowerManager.registerOverride(new NamespacedKey(RPGItems.plugin, "deflect"), new NamespacedKey(PapeRPGItems.plugin, "deflect"));
        PowerManager.addDescriptionResolver(this, (power, property) -> {
            if (property == null) {
                @LangKey(skipCheck = true) String powerKey = "power.properties." + power.getKey() + ".main_description";
                return I18n.format(powerKey);
            }
            @LangKey(skipCheck = true) String key = "power.properties." + power.getKey() + "." + property;
            if (I18n.instance.hasKey(key)) {
                return I18n.format(key);
            }
            @LangKey(skipCheck = true) String baseKey = "power.properties.base." + property;
            if (I18n.instance.hasKey(baseKey)) {
                return I18n.format(baseKey);
            }
            return null;
        });
    }

    @Override
    public void onEnable() {
        plugin = this;
        logger = plugin.getLogger();
        if (!supported) {
            logger.severe("====================================================");
            logger.severe(" " + plugin.getDescription().getName() + " REQUIRES Paper ");
            logger.severe(" as your server software. ");
            logger.severe("====================================================");
            this.setEnabled(false);
            return;
        }
        getServer().getPluginManager().registerEvents(eventlistener = new EventListener(), this);
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(plugin);
        HandlerList.unregisterAll(eventlistener);
        plugin = null;
        logger = null;
    }

    public static <T> T getWrapper(final PowerPlain obj, final Class<T> intface) {
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            if (!method.getName().equals("jump")) {
                return obj.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(obj, args);
            } else {
                return obj.getClass().getDeclaredMethod("fire", Player.class, ItemStack.class).invoke(obj, args[0], args[1]);
            }
        };
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[]{intface}, invocationHandler);
    }
}
