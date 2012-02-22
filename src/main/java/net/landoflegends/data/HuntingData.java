package net.landoflegends.data;

import net.landoflegends.LandOfLegends;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HuntingData extends Data {

    public HuntingData(LandOfLegends plugin) {
        super(plugin, "Hunting");
    }

    @Override
    public void onLevelUp(String player) {
        super.onLevelUp(player);

        Player p = plugin.getServer().getPlayer(player);
        int level = getLevel(player);

        if (level == 5) {
            p.sendMessage(ChatColor.GREEN
                    + "You now gain 0.5 health per fish caught!");
        } else if (level == 10) {
            p.sendMessage(ChatColor.GREEN
                    + "You now gain 1.0 health per fish caught!");
            p.sendMessage(ChatColor.GREEN
                    + "You have the possibility of gaining an extra fish!");
        } else if (level == 20) {
            p.sendMessage(ChatColor.GREEN
                    + "You now gain 1.5 health per fish caught!");
            p.sendMessage(ChatColor.GREEN
                    + "You have the possibility of gaining two extra fish!");
        } else if (level == 30) {
            p.sendMessage(ChatColor.GREEN
                    + "You now gain 2.0 health per fish caught!");
            p.sendMessage(ChatColor.GREEN
                    + "You have the possibility of gaining three extra fish!");
        }
    }
}
