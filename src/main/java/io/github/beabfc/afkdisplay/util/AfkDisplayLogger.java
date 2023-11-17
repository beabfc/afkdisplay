package io.github.beabfc.afkdisplay.util;

import static io.github.beabfc.afkdisplay.data.ModData.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AfkDisplayLogger {
    private static Logger LOGGER;
    private static boolean log;

    public static void initLogger() {
        LOGGER = LogManager.getLogger(AFK_MOD_ID);
        log = true;
        LOGGER.debug("[{}] Logger initalized.", AFK_MOD_ID);
    }

    public static void debug(String msg) {
        if (log)
            LOGGER.debug("[{}] " + msg, AFK_MOD_ID);
    }

    public static void info(String msg) {
        if (log)
            LOGGER.info("[{}] " + msg, AFK_MOD_ID);
    }

    public static void warn(String msg) {
        if (log)
            LOGGER.warn("[{}] " + msg, AFK_MOD_ID);
    }

    public static void error(String msg) {
        if (log)
            LOGGER.error("[{}] " + msg, AFK_MOD_ID);
    }

    public static void fatal(String msg) {
        if (log)
            LOGGER.fatal("[{}] " + msg, AFK_MOD_ID);
    }
}
