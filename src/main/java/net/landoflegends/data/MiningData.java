package net.landoflegends.data;

import net.landoflegends.LandOfLegends;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MiningData extends Data {

    public MiningData(LandOfLegends plugin) {
        super(plugin, "Mining");
    }

    @Override
    public void onLevelUp(String player) {
        super.onLevelUp(player);

        Player p = plugin.getServer().getPlayer(player);
        int level = getLevel(player);

        if (level == 8) {
            p.sendMessage(ChatColor.GREEN + "You unlocked the Stone pickaxe!");
        } else if (level == 15) {
            p.sendMessage(ChatColor.GREEN + "You unlocked the Iron pickaxe!");
        } else if (level == 25) {
            p.sendMessage(ChatColor.GREEN + "You unlocked the Diamond pickaxe!");
        } else if (level == 50) {
            p.sendMessage(ChatColor.GREEN + "You unlocked the Gold pickaxe!");
        }
    }
}
