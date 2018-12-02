package org.librazy.paperpgitem;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.librazy.paperpgitem.power.PowerJump;
import think.rpgitems.power.PowerResult;
import think.rpgitems.power.Trigger;

public class PaperTriggers {
    public static final Trigger<PlayerJumpEvent, PowerJump, Void, Void> PAPER_JUMP = new Trigger<PlayerJumpEvent, PowerJump, Void, Void>("PAPER_JUMP", PlayerJumpEvent.class, PowerJump.class, Void.class, Void.class) {
        @Override
        public PowerResult<Void> run(PowerJump power, Player player, ItemStack i, PlayerJumpEvent event) {
            return power.jump(player, i, event);
        }
    };
}
