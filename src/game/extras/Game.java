package game.extras;

import game.locations.Direction;
import game.locations.Location;
import game.locations.LocationManager;

import java.util.List;

/**
 * Creates a game of Text Adventure, this class keeps track of our current location, player and other useful information.
 */
public class Game {

	private Location location;
	private Player player;
	private LocationManager locManager;

	/**
	 * Creates a game
	 * @param list	A {@link List<Location> List} containing {@link Location}
	 */
	public Game(List<Location> list) {
		this.player = new Player();
		this.locManager = new LocationManager();
		locManager.addAll(list);
		location = locManager.getLocAtPosition(0,0);
	}

	public Location getLocation() {
		return location;
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * Move the player in a direction, if there is a location in that direction
	 * @param dir	Desired direction to move
	 * @return		True if player was moved, else false
	 */
	public boolean move(Direction dir){
		if (locManager.hasLocInDirection(location, dir)){
			this.location = locManager.getLocInDirection(location, dir);
			return true;
		}
		return false;
	}
}
