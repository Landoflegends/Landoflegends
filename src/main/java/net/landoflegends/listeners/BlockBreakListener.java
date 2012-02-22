package net.landoflegends.listeners;

import net.landoflegends.LandOfLegends;
import net.landoflegends.professions.Mining;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @SuppressWarnings("unused")
    private final LandOfLegends plugin;
    private final Mining mining;

    public BlockBreakListener(LandOfLegends plugin) {
        this.plugin = plugin;
        this.mining = (Mining) plugin.getProfessionsManager().getProfession(
                "Mining");
    }

    @EventHandler
    public void handle(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!(mining.canUse(player))) {
            return;
        }
        mining.dropExtra(player.getLocation(), player);
    }
}
