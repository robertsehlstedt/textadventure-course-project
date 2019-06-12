package game.score;

import game.utils.IOUtilities;
import gui.ListViewComponent;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.List;

import static java.lang.Integer.min;

/**
 * A list containing all of the local {@link Highscore Highscores} in the game.
 */
public final class HighscoreList implements Iterable<Highscore> {

	private final static int NUMBER_OF_SCORES = 10;
	private final static String SCOREBOARD_HEADER = "Name: Score / Steps / Points";

	public static HighscoreList getHighscoreList() {
			return highscoreList;
		}
	private static HighscoreList highscoreList = new HighscoreList();
	private List<Highscore> highscores;

	private HighscoreList() {
		highscores = IOUtilities.loadHighscoreList();
	}

	/**
	 * Adds a {@link Highscore} to the {@link HighscoreList} and sorts it by score.
	 * @param score	The {@link Highscore} to addItem
	 */
	public void add(Highscore score){
		highscores.add(score);
		highscores.sort((h1, h2) -> (int) Math.signum((float) h2.getScore() - h1.getScore()));
	}

	/**
	 * Sends the first {@value NUMBER_OF_SCORES} scores from the {@link HighscoreList} to the player.
	 * First sends {@value SCOREBOARD_HEADER} and then the scores in descending order.
	 * @param listComp	The ListViewComponent to send the scoreboard to.
	 */
	public void sendHighscores(ListViewComponent listComp){
		listComp.addMessage(new Text(SCOREBOARD_HEADER));
		listComp.addMessage(new Text("------------------------------"));
		int length = highscores.size();
		for (int i = 0; i < min(length, NUMBER_OF_SCORES); i++) {
			Highscore score = highscores.get(i);
			Text text = new Text(i+1 + ". " + score);
			listComp.addMessage(text);
		}
	}

	@Override public Iterator<Highscore> iterator() {
		return highscores.iterator();
	}
}
