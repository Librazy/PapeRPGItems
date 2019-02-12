package org.librazy.paperpgitem.power;

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.power.Power;
import think.rpgitems.power.PowerResult;

import javax.annotation.CheckReturnValue;

public interface PowerProjectileCollided extends Power {
    /**
     * Calls when {@code player} using {@code stack} being hit by projectile
     *
     * @param player Player
     * @param stack  ItemStack of this RPGItem
     * @param event  Event that triggered this power
     * @return PowerResult
     */
    @CheckReturnValue
    PowerResult<Void> projectileCollided(Player player, ItemStack stack, ProjectileCollideEvent event);
}
