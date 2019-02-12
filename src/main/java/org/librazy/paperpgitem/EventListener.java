package org.librazy.paperpgitem;

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent;
import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.RPGItem;

import java.util.Optional;

public class EventListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerJump(PlayerJumpEvent e) {
        Player player = e.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        Optional<RPGItem> rItem = ItemManager.toRPGItem(stack);
        if (!rItem.isPresent()) {
            stack = player.getInventory().getItemInOffHand();
            rItem = ItemManager.toRPGItem(stack);
            if (!rItem.isPresent()) {
                return;
            }
        }
        rItem.get().power(player, stack, e, PaperTriggers.PAPER_JUMP);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerKnockback(EntityKnockbackByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
        ItemStack stack = player.getInventory().getItemInMainHand();
        Optional<RPGItem> rItem = ItemManager.toRPGItem(stack);
        if (!rItem.isPresent()) {
            stack = player.getInventory().getItemInOffHand();
            rItem = ItemManager.toRPGItem(stack);
            if (!rItem.isPresent()) {
                return;
            }
        }
        rItem.get().power(player, stack, e, PaperTriggers.PAPER_KNOCKBACK);
    }


    @EventHandler(ignoreCancelled = true)
    public void onProjectileCollide(ProjectileCollideEvent e) {
        if (!(e.getCollidedWith() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getCollidedWith();
        ItemStack stack = player.getInventory().getItemInMainHand();
        Optional<RPGItem> rItem = ItemManager.toRPGItem(stack);
        if (!rItem.isPresent()) {
            stack = player.getInventory().getItemInOffHand();
            rItem = ItemManager.toRPGItem(stack);
            if (!rItem.isPresent()) {
                return;
            }
        }
        rItem.get().power(player, stack, e, PaperTriggers.PAPER_PROJECTILECOLLIDED);
    }
}
