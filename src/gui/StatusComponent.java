package gui;

import game.extras.Game;
import game.extras.Player;
import game.locations.Location;
import game.score.Highscore;
import javafx.scene.control.Label;

/**
 * A graphical component to display more useful information
 */
public class StatusComponent extends Label{

	/**
	 * @param game	The game to look at
	 */
	public StatusComponent(Game game){
		Location loc = game.getLocation();
		Player player = game.getPlayer();
		update(loc, player);
	}

	/**
	 * Changes the text of the component to display a new location
	 * @param loc		The location to display the name of
	 * @param player	The player of the game
	 */
	public void update(Location loc, Player player){
		Highscore score = player.getScore();
		String text = " Loc: " + loc.getName() + ", Steps: " + score.getSteps() + ", Points: " + score.getPoints();
		this.setText(text);
	}

}
