package game.utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Utility class which initializes the logger
 */
public final class LoggerToFile{

	private final static String PATH_TO_LOG = "log.txt";
	private final static Logger LOGGER = Logger.getLogger(LoggerToFile.class.getName());

	private LoggerToFile(){}

	/**
	 * Init logger with desired settings
	 * @throws IOException	If the FileHandler can't be created.
	 */
	public static void init() throws IOException {

		// Configure logger level
		LOGGER.setLevel(Level.ALL);

		// Configures filehandler
		FileHandler fileHandler = new FileHandler(PATH_TO_LOG, true);
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		LOGGER.addHandler(fileHandler);
	}

	public static Logger getLogger(){
		return LOGGER;
	}

	public static void showErrorMessage(String msg, boolean exit) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText("Something went wrong");
		alert.setContentText(msg);
		alert.showAndWait();

		if (exit) Platform.exit();
	}
}
