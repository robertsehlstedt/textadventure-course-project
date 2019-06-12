package game.utils;

import game.score.Highscore;
import game.score.HighscoreList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for input and output.
 */
public final class IOUtilities {

	private final static Logger LOGGER = LoggerToFile.getLogger();
	private final static String PATH_HIGHSCORES = "highscores";
	private final static String PATH_README = "res/readme";

	private IOUtilities(){}

	/**
	 * Saves the current HighscoreList to a local file with the format:
	 * name:score
	 * @param list	The {@link HighscoreList} to save
	 */
	public static void saveHighscoreList(HighscoreList list){

		try(PrintWriter pw = new PrintWriter(new File(PATH_HIGHSCORES))){

			for (Highscore score : list) {
				pw.println(score.getName() + ":" + score.getScore() + ":" + score.getSteps() + ":" + score.getPoints());
			}

		}catch(FileNotFoundException e){
			LOGGER.log(Level.FINE, e + "\n", e);
			LoggerToFile.showErrorMessage("Error while saving highscore!", false);
		}

	}

	/**
	 * Loads the readme-file containing help with commands and such.
	 * @return A String with the readme file.
	 */
	public static String loadReadMe(){

		StringBuilder b = new StringBuilder();

		try (Scanner scanner = new Scanner(getInputStream(PATH_README))){
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				b.append(line)
					.append("\n");
			}
		}
		catch(IOException e){
			LOGGER.log(Level.FINE, e + "\n", e);
			LoggerToFile.showErrorMessage("Could not load ReadMe!", false);
		}
		return b.toString();
	}

	/**
	 * Loads the {@link HighscoreList} from a local file
	 * @return	A {@link List<Highscore> list} containing all of the old highscores.
	 */
	public static List<Highscore> loadHighscoreList(){

		List<Highscore> highscores = new ArrayList<>();

		try (Scanner scanner = new Scanner(new File(PATH_HIGHSCORES))) {

			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				String[] split = line.split(":");
				Highscore score = new Highscore();
				score.setName(split[0]);
				score.addSteps(Integer.parseInt(split[2]));
				score.add(Integer.parseInt(split[3]));
				score.createScore();
				highscores.add(score);
			}

		}catch(FileNotFoundException e){
			LOGGER.log(Level.FINE, e + "\n", e);
			LoggerToFile.showErrorMessage("Error while loading highscore", false);
		}

		return highscores;
	}

	/**
	 * Gets a resource from the project
	 * @param path			The path of resource relative to src
	 * @return				The InputStream
	 * @throws IOException	Throws if the resource could not be opened
	 */
	public static InputStream getInputStream(String path) throws IOException{
		InputStream stream = IOUtilities.class.getClassLoader().getResourceAsStream(path);
		if (stream == null) throw new IOException("Could not open resource: " + path);
		return stream;
	}
}
