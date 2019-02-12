package org.librazy.paperpgitem.power.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.I18n;
import think.rpgitems.power.PowerMeta;
import think.rpgitems.power.PowerProjectileLaunch;
import think.rpgitems.power.PowerResult;

@PowerMeta(immutableTrigger = true)
public class PowerNoBallisticDrop extends BasePower implements PowerProjectileLaunch {

    @Override
    public PowerResult<Void> projectileLaunch(Player player, ItemStack stack, ProjectileLaunchEvent event) {
        event.getEntity().setGravity(false);
        return PowerResult.ok();
    }

    @Override
    public String getName() {
        return "noballisticdrop";
    }

    @Override
    public String displayText() {
        return I18n.format("power.noballisticdrop");
    }
}
