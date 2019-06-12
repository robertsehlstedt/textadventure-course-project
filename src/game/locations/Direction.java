package game.locations;

/**
 * Represents a direction between two Locations. These are used to represent a change in a 2D space.
 */
public enum Direction {
	/**
	 * Move north
	 */
	NORTH(0,1),

	/**
	 * Move east
	 */
	EAST(1,0),

	/**
	 * move south
	 */
	SOUTH(0,-1),

	/**
	 * move west
	 */
	WEST(-1,0);

	/**
	 * delta x
	 */
	public final int dx;

	/**
	 * delta y
	 */
	public final int dy;

	Direction(int x, int y){
		this.dx = x;
		this.dy = y;
	}

	/**
	 * Takes a string and returns its corresponding Direction
	 * @param str	String name of the direction we want the value of
	 * @return		The direction if it exists, else null
	 */
	public static Direction getDirection(String str){
		for (Direction dir : values()) {
			if (dir.toString().equals(str.toUpperCase())) return dir;
		}
		return null;
	}
}
