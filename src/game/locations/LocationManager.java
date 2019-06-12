package game.locations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Keeps track of all locations in a game of text adventure
 */
public class LocationManager {

	private Map<MapPosition, Location> locations;

	/**
	 * Creates an instance of a LocationManager.
	 */
	public LocationManager(){
		this.locations = new HashMap<>();
	}


	/**
	 * Takes a location and returns the location in a direction
	 * @param loc	The current location
	 * @param dir	The direction in which to find a new location
	 * @return		The location in that direction if it exists, else null
	 */
	public Location getLocInDirection(Location loc, Direction dir){
		MapPosition currentPos = loc.getPosition();
		MapPosition newPos = new MapPosition(currentPos.getX() + dir.dx, currentPos.getY() + dir.dy);
		return locations.get(newPos);
	}

	/**
	 * Finds a location at a specific position (x,y).
	 * @param x	The x-position.
	 * @param y	The y-position.
	 * @return	The {@link Location} at the desired position if it exists, else null.
	 */
	public Location getLocAtPosition(int x, int y){
		MapPosition pos = new MapPosition(x, y);
		return locations.get(pos);
	}

	/**
	 * Adds a new location to the location manager
	 * @param loc	Location to addItem
	 */
	public void add(Location loc){
		this.locations.put(loc.getPosition(), loc);
	}

	/**
	 * Adds all locations in a list of locations to the location manager
	 * @param list	A list of locations
	 */
	public void addAll(List<Location> list){
		for (Location loc : list) {
			add(loc);
		}
	}

	/**
	 * Returns whether a location has another location in a direction
	 * @param loc	The origin location
	 * @param dir	The direction to look in
	 * @return		Whether the location in that direction exists
	 */
	public boolean hasLocInDirection(Location loc, Direction dir){
		return getLocInDirection(loc, dir) != null;
	}
}
