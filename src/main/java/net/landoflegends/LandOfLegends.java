package net.landoflegends;

import java.util.Random;
import java.util.logging.Level;

import net.landoflegends.listeners.BlockBreakListener;
import net.landoflegends.professions.ProfessionsManager;
import net.landoflegends.utils.LOLLogging;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LandOfLegends extends JavaPlugin {

    public static final String NAME = "LandOfLegends";
    public static final String PREFIX = "[LOL]";
    public static final double VERSION = 1.0;
    public static final int SUB_VERSION = 0;
    public static final Random RANDOM = new Random();

    private LOLLogging logger;

    private ProfessionsManager pManager;

    // Listener
    private BlockBreakListener blockBreakL;

    public void onLoad() {
        logger = new LOLLogging();

        // TODO
        logger.setFile(null);
        logger.setDebugFile(null);
        logger.setDebug(false);

        logger.start();

        pManager = new ProfessionsManager(this);

        blockBreakL = new BlockBreakListener(this);
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(blockBreakL, this);

        logger.log(Level.INFO, "Enabled!");
    }

    public void onDisable() {
        logger.log(Level.INFO, "Disabled!");

        logger.shutdown();
    }

    public LOLLogging getLOLLogger() {
        return logger;
    }

    public ProfessionsManager getProfessionsManager() {
        return pManager;
    }
}