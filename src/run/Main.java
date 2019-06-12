package run;

import gui.ApplicationFrame;
import javafx.application.Application;

/**
 * Runs the game
 */
final class Main {

	private Main() {
	}

	/**
	 * RUN
	 */
	public static void main(String[] args) {
		Application.launch(ApplicationFrame.class, args);
	}
}
