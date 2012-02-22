package net.landoflegends.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.landoflegends.LandOfLegends;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Data {

    protected LandOfLegends plugin;

    private final Map<String, Integer> levels;
    private final Map<String, Integer> xps;
    private boolean unlocked;

    private static final int[] needed;

    private final File players;

    private final String name;

    static {
        needed = new int[51];
        needed[0] = 0;
        for (int i = 1; i <= 50; i++) {
            // needed[i] = needed[i - 1] + i^3 - 6 * i^2 + 17 * i
            // needed[3] = needed[2] + 27 - 6 * 9 + 17 * 3;
            // needed[3] = needed[2] + 618
            needed[i] = needed[i - 1]
                    + ((int) (Math.pow(i, 3) - 6 * Math.pow(i, 2) + 17 * i));
        }
        needed[51] = 1337000; // Big number fail?
    }

    public Data(LandOfLegends plugin, String name) {
        this.plugin = plugin;
        this.name = name;

        this.players = new File(plugin.getDataFolder(), "players");
        if (!players.exists()) {
            players.mkdir();
        }
        Validate.isTrue(players.isDirectory(), "Players must be a directory!");

        this.levels = new HashMap<String, Integer>();
        this.xps = new HashMap<String, Integer>();
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public int getLevel(Player player) {
        Validate.notNull(player, "Player cannot be null!");
        return getLevel(player.getName());
    }

    public int getLevel(String player) {
        // TODO event
        Validate.notNull(player, "Player cannot be null!");
        if (!isUnlocked()) {
            return -1;
        }
        return levels.get(player);
    }

    public void setLevel(Player player, int level) {
        Validate.notNull(player, "Player cannot be null!");
        setLevel(player.getName(), level);
    }

    public void setLevel(String player, int level) {
        Validate.notNull(player, "Player cannot be null!");
        Validate.isTrue(level >= 1, "Level cannot be less than 1!");
        // TODO event
        if (!isUnlocked()) {
            return;
        }
        levels.put(player, level);
    }

    public void incrementLevel(Player player) {
        Validate.notNull(player, "Player cannot be null!");
        incrementLevel(player.getName());
    }

    public void incrementLevel(String player) {
        Validate.notNull(player, "Player cannot be null!");
        int level = getLevel(player);
        setLevel(player, level + 1);
    }

    public int getXP(Player player) {
        Validate.notNull(player, "Player cannot be null!");
        return getXP(player.getName());
    }

    public int getXP(String player) {
        Validate.notNull(player, "Player cannot be null!");
        if (!isUnlocked()) {
            return -1;
        }
        return xps.get(player);
    }

    public void setXP(Player player, int xp) {
        Validate.notNull(player, "Player cannot be null!");
        setXP(player.getName(), xp);
    }

    public void setXP(String player, int xp) {
        Validate.notNull(player, "Player cannot be null!");
        Validate.isTrue(xp >= 0, "XP cannot be less than 0!");
        if (!isUnlocked()) {
            return;
        }
        xps.put(player, xp);
    }

    public void load(Player player) {
        Validate.notNull(player, "Player cannot be null!");
        load(player.getName());
    }

    public void load(String player) {
        Validate.notNull(player, "Player cannot be null!");

        LoadThread thread = new LoadThread(player);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int[] result = thread.getResult();

        levels.put(player, result[0]);
        xps.put(player, result[1]);
        this.unlocked = result[2] == 0;
    }

    public void save(Player player) {
        Validate.notNull(player, "Player cannot be null!");
        save(player.getName());
    }

    public void save(String player) {
        Validate.notNull(player, "Player cannot be null!");

        SaveThread thread = new SaveThread(player);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public final void loadAll() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            load(player);
        }
    }

    public final void saveAll() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            save(player);
        }
    }

    public static int getMaximumXP(int level) {
        Validate.isTrue(level > 0, "Level cannot be less than 0!");
        return needed[level];
    }

    public int getXPTillNext(Player player, int boost) {
        Validate.notNull(player, "Player cannot be null!");
        return getXPTillNext(player.getName(), boost);
    }

    public int getXPTillNext(String player, int boost) {
        Validate.notNull(player, "Player cannot be null!");
        if (!isUnlocked()) {
            return -1;
        }
        int level = getLevel(player);
        int xp = getXP(player);
        return Data.getMaximumXP(level + 1)
                - ((Data.getMaximumXP(level) - xp) * boost);
    }

    public void onLevelUp(Player player) {
        Validate.notNull(player, "Player cannot be null!");
        onLevelUp(player.getName());
    }

    public void onLevelUp(String player) {
        Validate.notNull(player, "Player cannot be null!");
        Player p = Bukkit.getPlayer(player);
        incrementLevel(player);
        int level = getLevel(player);
        if (p == null) {
            return;
        }
        p.sendMessage(ChatColor.GREEN + name + " leveled to level " + level
                + "!");
        if (level >= 20) {
            p.getServer().broadcastMessage(
                    ChatColor.GREEN + player + " just leveled " + name
                            + " to level " + level + "!");
        }
    }

    private class LoadThread extends Thread {
        private int[] result;
        private String player;

        public LoadThread(String player) {
            this.player = player;

            result = new int[3]; // TODO 2?
        }

        public void run() {
            File file = new File(players, player);
            YamlConfiguration config = YamlConfiguration
                    .loadConfiguration(file);

            result[0] = config.getInt(player + ".xp", 0);
            result[1] = config.getInt(player + ".level", 1);
            result[2] = config.getBoolean(player + ".unlocked", false) ? 0 : 1;
        }

        public int[] getResult() {
            return result;
        }
    }

    private class SaveThread extends Thread {
        private String player;

        public SaveThread(String player) {
            this.player = player;
        }

        public void run() {
            File file = new File(players, player);
            YamlConfiguration config = YamlConfiguration
                    .loadConfiguration(file);

            config.set(player + ".xp", getXP(player));
            config.set(player + ".level", getLevel(player));
            config.set(player + ".unlocked", false);
        }
    }
}
