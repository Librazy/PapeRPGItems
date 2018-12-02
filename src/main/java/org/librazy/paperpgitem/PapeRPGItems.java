package org.librazy.paperpgitem;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.librazy.nclangchecker.LangKey;
import org.librazy.paperpgitem.power.PowerJump;
import think.rpgitems.power.*;

import java.util.logging.Logger;

import static org.librazy.paperpgitem.PaperTriggers.PAPER_JUMP;

public final class PapeRPGItems extends JavaPlugin {

    public static PapeRPGItems plugin;

    private static Logger logger;
    private static boolean supported = true;
    private EventListener eventlistener;

    @Override
    public void onLoad() {
        plugin = this;
        logger = plugin.getLogger();
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

        PowerManager.registAdapter(PowerPlain.class, PowerJump.class, PlainJumpDelegatePower::new);

        PowerManager.registerPowers(this, "org.librazy.paperpgitem.power");
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
        new I18n(this, "en_US");
        getServer().getPluginManager().registerEvents(eventlistener = new EventListener(), this);
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(plugin);
        HandlerList.unregisterAll(eventlistener);
        plugin = null;
        logger = null;
    }

    class PlainJumpDelegatePower extends DelegatePower<PowerPlain> implements PowerJump {
        PlainJumpDelegatePower(PowerPlain instance) {
            super(instance);
        }

        @Override
        public PowerResult<Void> jump(Player player, ItemStack stack, PlayerJumpEvent event) {
            return getInstance().fire(player, stack);
        }
    }
}
