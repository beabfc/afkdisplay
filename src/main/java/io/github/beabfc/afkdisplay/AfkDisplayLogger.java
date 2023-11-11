package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.AfkDisplay.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AfkDisplayLogger {
    private static Logger LOGGER;
    private static boolean log;

    public static void initLogger(String ModID) {
        LOGGER = LogManager.getLogger(ModID);
        log = true;
        LOGGER.info("[{}] {}-{}", MOD_ID, MOD_NAME, MOD_VERSION);
        LOGGER.info("[{}] Author: {}", MOD_ID, MOD_AUTHO_STRING);
    }

    public static void info(String msg) {
        if (log)
            LOGGER.info("[{}] " + msg, MOD_ID);
    }

    public static void warn(String msg) {
        if (log)
            LOGGER.warn("[{}] " + msg, MOD_ID);
    }

    public static void error(String msg) {
        if (log)
            LOGGER.error("[{}] " + msg, MOD_ID);
    }

    public static void fatal(String msg) {
        if (log)
            LOGGER.fatal("[{}] " + msg, MOD_ID);
    }
}
