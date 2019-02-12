package org.librazy.paperpgitem.power;

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.power.Power;
import think.rpgitems.power.PowerResult;

import javax.annotation.CheckReturnValue;

public interface PowerKnockback extends Power {
    /**
     * Calls when {@code player} using {@code stack} knockbacked
     *
     * @param player Player
     * @param stack  ItemStack of this RPGItem
     * @param event  Event that triggered this power
     * @return PowerResult
     */
    @CheckReturnValue
    PowerResult<Void> knockback(Player player, ItemStack stack, EntityKnockbackByEntityEvent event);
}
