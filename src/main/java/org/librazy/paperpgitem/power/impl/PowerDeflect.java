package org.librazy.paperpgitem.power.impl;

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.librazy.paperpgitem.I18n;
import org.librazy.paperpgitem.PapeRPGItems;
import org.librazy.paperpgitem.PaperTriggers;
import org.librazy.paperpgitem.power.PowerProjectileCollided;
import think.rpgitems.power.PowerMeta;
import think.rpgitems.power.PowerResult;
import think.rpgitems.power.Trigger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static think.rpgitems.power.Utils.checkCooldown;

/**
 * Power deflect.
 * <p>
 * Deflect arrows or fireballs towards player within {@link #facing} when
 * 1. manual triggered when some of initiative trigger are enabled with a cooldown of {@link #cooldown} and duration {@link #duration}
 * 2. auto triggered when {@link Trigger#HIT_TAKEN} is enabled with a chance of {@link #chance} and a cooldown of {@link #cooldownpassive}
 * </p>
 */
@PowerMeta(defaultTrigger = "RIGHT_CLICK")
public class PowerDeflect extends think.rpgitems.power.impl.PowerDeflect implements PowerProjectileCollided {

    @Override
    public NamespacedKey getNamespacedKey() {
        return new NamespacedKey(PapeRPGItems.plugin, getName());
    }

    @Override
    public PowerResult<Double> takeHit(Player target, ItemStack stack, double damage, EntityDamageEvent event) {
        throw new IllegalStateException();
    }

    @Override
    public PowerResult<Void> projectileCollided(Player player, ItemStack stack, ProjectileCollideEvent event) {
        boolean activated = System.currentTimeMillis() / 50 < time.getOrDefault(player.getUniqueId(), 0L);

        if (!activated) {
            if (!triggers.contains(Trigger.HIT_TAKEN)
                        || ThreadLocalRandom.current().nextInt(0, 100) >= chance)
                return PowerResult.noop();
            if (!checkCooldown(this, player, cooldownpassive, false, true)) return PowerResult.cd();
        }

        if (!getItem().consumeDurability(stack, cost)) return PowerResult.cost();

        event.setCancelled(true);
        Projectile p = event.getEntity();
        p.setShooter(player);
        p.setVelocity(player.getEyeLocation().getDirection().multiply(p.getVelocity().length()));
        if (p instanceof Fireball) {
            ((Fireball) p).setDirection(player.getEyeLocation().getDirection());
        }
        return PowerResult.ok();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Set<Trigger> getTriggers() {
        HashSet<Trigger> triggers = new HashSet<>(super.getTriggers());
        triggers.remove(Trigger.HIT_TAKEN);
        triggers.add(PaperTriggers.PAPER_PROJECTILECOLLIDED);
        return triggers;
    }
}
