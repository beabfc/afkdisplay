package io.github.beabfc.afkdisplay.util;

import static io.github.beabfc.afkdisplay.data.ModData.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AfkDisplayLogger {
    private static Logger LOGGER;
    private static boolean log;

    public static void initLogger(String ModID) {
        LOGGER = LogManager.getLogger(ModID);
        log = true;
        LOGGER.info("[{}] {}-{}-{}", AFK_MOD_ID, AFK_MOD_NAME, AFK_MC_VERSION, AFK_MOD_VERSION);
        LOGGER.info("[{}] Author: {}", AFK_MOD_ID, AFK_MOD_AUTHO_STRING);
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
