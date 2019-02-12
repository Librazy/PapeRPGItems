package org.librazy.paperpgitem.power.impl;

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.librazy.paperpgitem.power.PowerKnockback;
import think.rpgitems.I18n;
import think.rpgitems.power.PowerMeta;
import think.rpgitems.power.PowerResult;
import think.rpgitems.power.Property;

import static think.rpgitems.power.Utils.checkCooldown;

@PowerMeta(immutableTrigger = true)
public class PowerNewtonsThird extends BasePower implements PowerKnockback {

    /**
     * Cooldown time of this power
     */
    @Property(order = 0)
    public long cooldown = 20;
    /**
     * Cost of this power
     */
    @Property
    public int cost = 0;

    @Property
    public double selfFactor = 0.5;

    @Property
    public double factor = 0.5;

    @Override
    public PowerResult<Void> knockback(Player player, ItemStack stack, EntityKnockbackByEntityEvent event) {
        if (!checkCooldown(this, player, cooldown, false, true)) return PowerResult.cd();
        if (!getItem().consumeDurability(stack, cost)) return PowerResult.cost();

        Entity hitBy = event.getHitBy();
        if (hitBy instanceof Projectile) return PowerResult.noop();
        Vector acceleration = event.getAcceleration();
        hitBy.setVelocity(hitBy.getVelocity().subtract(acceleration.clone().multiply(factor)));
        acceleration.multiply(selfFactor);
        return PowerResult.ok();
    }

    @Override
    public String getName() {
        return "newtonsthird";
    }

    @Override
    public String displayText() {
        return I18n.format("power.newtonsthird");
    }

}
