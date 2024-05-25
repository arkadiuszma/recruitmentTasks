package logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringJoiner;

public class Log {
    private static final String SEPARATOR = " | ";
    private static final String INFO = "[INFO] ";
    private static final String ERROR = "[ERROR] ";
    private static final Logger log = LoggerFactory.getLogger(Log.class);

    public static void info(String message) {
        logMessage(INFO, message);
    }

    public static void error(String message) {
        logMessage(ERROR, message);
    }

    private static void logMessage(String prefix, String message) {
        log.info(new StringJoiner(SEPARATOR)
                .add(prefix)
                .add(message)
                .toString());
    }
}
