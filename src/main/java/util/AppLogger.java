package main.java.util;

import java.io.IOException;
import java.util.logging.*;

/**
 * Centralized Logging Utility for the application.
 * Uses a Singleton-like pattern to provide a global Logger instance.
 * It is configured to handle two output streams:
 * 1. Console: Shows high-level information (INFO and above) to the user.
 * 2. File: Records detailed diagnostic data (ALL levels) in 'app.log' for debugging.
 */
public class AppLogger {
    private static final Logger logger = Logger.getLogger("FundManagerLog");

    static {
        try {
            LogManager.getLogManager().reset();
            logger.setLevel(Level.ALL);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO); // Only show INFO and above to user
            logger.addHandler(consoleHandler);

            FileHandler fileHandler = new FileHandler("app.log", true);
            fileHandler.setLevel(Level.ALL); // Log EVERYTHING to file
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

        } catch (IOException e) {
            System.err.println("Failed to setup logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
