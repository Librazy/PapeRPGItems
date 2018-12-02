package org.librazy.paperpgitem;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.RPGItem;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJump(PlayerJumpEvent e) {
        Player player = e.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        RPGItem rItem = ItemManager.toRPGItem(stack);
        if (rItem == null) {
            stack = player.getInventory().getItemInOffHand();
            rItem = ItemManager.toRPGItem(stack);
            if (rItem == null) {
                return;
            }
        }
        rItem.power(player, stack, e, PaperTriggers.PAPER_JUMP);
    }
}
