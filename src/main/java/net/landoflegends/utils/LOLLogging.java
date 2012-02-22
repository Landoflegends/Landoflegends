/*
 * This file is part of CallOfMinecraft.
 *
 * CallOfMinecraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CallOfMinecraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Tips48 also gives full credit to thehutch for his awesomeness and skills which
 * are way over tipsy head :D , he is also willing to donate $100 to everyone who 
 * reads this. Mainly because he is that awesome :P
 */

package net.landoflegends.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.landoflegends.LandOfLegends;

public class LOLLogging extends Thread {

    private final Logger logger = Logger.getLogger("Minecraft");

    private BufferedWriter writer;
    private Queue<String> toRun;

    private BufferedWriter debugWriter;
    private boolean debug;
    private Queue<String> debugToRun;

    /**
     * Date Format used by the plugin to format to file logging
     */
    public static final DateFormat df = new SimpleDateFormat("HH:mm:ss");

    /**
     * Creates a new LOLLogging Should only be called by the main plugin on
     * enable
     */
    public LOLLogging() {
        super(LandOfLegends.NAME + " Logging Thread");
        toRun = new ConcurrentLinkedQueue<String>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        while (!this.isInterrupted()) {
            if (writer != null) {
                while (!toRun.isEmpty()) {
                    String string = toRun.poll();
                    try {
                        writer.write(df.format(System.currentTimeMillis())
                                + string);
                        writer.newLine();
                        writer.flush();
                    } catch (Exception e) {
                        this.log(e, "Could not write " + string
                                + " to the log file!");
                    }
                }
            }
            if (debugWriter != null) {
                while (!debugToRun.isEmpty()) {
                    String string = debugToRun.poll();
                    if (debugWriter != null) {
                        try {
                            debugWriter.write(df.format(System
                                    .currentTimeMillis()) + string);
                            debugWriter.newLine();
                            debugWriter.flush();
                        } catch (Exception e) {
                            log(e, "Could not write " + string
                                    + " to the debug file!");
                        }
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    this.log(e, "Logging thread was interupted!");
                }
            }
        }
    }

    /**
     * Shut's the logger down
     */
    public void shutdown() {
        try {
            this.interrupt();
            if (writer != null) {
                writer.flush();
                writer.close();
                writer = null;
            }
            if (debugWriter != null) {
                debugWriter.flush();
                debugWriter.close();
                debugWriter = null;
            }
            toRun.clear();
            toRun = null;
            debugToRun.clear();
            debugToRun = null;
            this.join(5000);
        } catch (Exception e) {
        }
    }

    /**
     * Sets the name of the file the logger should debug to, null if none
     * 
     * @param name
     *            New name of the file
     */
    public void setFile(String name) {
        if (writer != null) {
            try {
                writer.close();
            } catch (Exception e) {
                this.log(e, "Failed to close the writer!");
            }
        }
        if (name == null) {
            writer = null;
            return;
        }
        FileWriter fw;
        try {
            fw = new FileWriter(name, true);
        } catch (Exception e) {
            this.log(e, "Failed to find the file " + name + "!");
            return;
        }
        writer = new BufferedWriter(fw);
    }

    /**
     * Logs a message
     * 
     * @param level
     *            Level to log the message
     * @param message
     *            Message to log
     */
    public void log(Level level, String message) {
        logger.log(level, LandOfLegends.PREFIX + " " + message);
        toRun.add(level.getName() + " " + message);
    }

    /**
     * Logs an exception
     * 
     * @param e
     *            Exception to log
     * @param reason
     *            Reason for the exception
     */
    public void log(Exception e, String reason) {
        String message = e.getMessage();
        this.log(Level.SEVERE, reason);
        this.log(Level.SEVERE, "ERROR MESSAGE:");
        this.log(Level.SEVERE, message);
    }

    /**
     * Gets if the logger is in debug mode
     * 
     * @return true if the logger is in debug mode
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Sets if the logger is in debug mode
     * 
     * @param debug
     *            New debug
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
        if (debugToRun == null) {
            debugToRun = new ConcurrentLinkedQueue<String>();
        }
    }

    /**
     * Logs a debug message
     * 
     * @param level
     *            Level to log the message
     * @param message
     *            Message to log
     */
    public void logDebug(Level level, String message) {
        if (isDebug()) {
            logger.log(level, LandOfLegends.PREFIX + "[DEBUG] " + message);
            debugToRun.add(level.getName() + " " + message);
        }
    }

    /**
     * Sets the file the debug log's to
     * 
     * @param name
     *            New name of the debug file to download to
     */
    public void setDebugFile(String name) {
        if (debugWriter != null) {
            try {
                debugWriter.close();
            } catch (Exception e) {
                this.log(e, "Failed to close the writer!");
            }
        }
        if (name == null) {
            debugWriter = null;
            return;
        }
        FileWriter fw;
        try {
            fw = new FileWriter(name, true);
        } catch (Exception e) {
            this.log(e, "Failed to find the file " + name + "!");
            return;
        }
        debugWriter = new BufferedWriter(fw);
    }

}
