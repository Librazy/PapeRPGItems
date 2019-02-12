package org.librazy.paperpgitem;

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent;
import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.librazy.paperpgitem.power.PowerJump;
import org.librazy.paperpgitem.power.PowerKnockback;
import org.librazy.paperpgitem.power.PowerProjectileCollided;
import think.rpgitems.power.PowerResult;
import think.rpgitems.power.Trigger;

public class PaperTriggers {
    public static final Trigger<PlayerJumpEvent, PowerJump, Void, Void> PAPER_JUMP = new Trigger<PlayerJumpEvent, PowerJump, Void, Void>("PAPER_JUMP", PlayerJumpEvent.class, PowerJump.class, Void.class, Void.class) {
        @Override
        public PowerResult<Void> run(PowerJump power, Player player, ItemStack i, PlayerJumpEvent event) {
            return power.jump(player, i, event);
        }
    };

    public static final Trigger<EntityKnockbackByEntityEvent, PowerKnockback, Void, Void> PAPER_KNOCKBACK = new Trigger<EntityKnockbackByEntityEvent, PowerKnockback, Void, Void>("PAPER_KNOCKBACK", EntityKnockbackByEntityEvent.class, PowerKnockback.class, Void.class, Void.class) {
        @Override
        public PowerResult<Void> run(PowerKnockback power, Player player, ItemStack i, EntityKnockbackByEntityEvent event) {
            return power.knockback(player, i, event);
        }
    };

    public static final Trigger<ProjectileCollideEvent, PowerProjectileCollided, Void, Void> PAPER_PROJECTILECOLLIDED = new Trigger<ProjectileCollideEvent, PowerProjectileCollided, Void, Void>("PAPER_PROJECTILECOLLIDED", ProjectileCollideEvent.class, PowerProjectileCollided.class, Void.class, Void.class) {
        @Override
        public PowerResult<Void> run(PowerProjectileCollided power, Player player, ItemStack i, ProjectileCollideEvent event) {
            return power.projectileCollided(player, i, event);
        }
    };
}
