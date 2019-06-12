package game.locations;

/**
 * A position on the map in the game
 */
public class MapPosition {

	private final static int BITS_TO_SHIFT_LEFT = 16;

	private int x, y;

	/**
	 * @param x		x-coordinate
	 * @param y		y-coordinate
	 */
	public MapPosition(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "x=" + x + ", y=" + y;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || !getClass().equals(o.getClass())) return false;
		final MapPosition that = (MapPosition) o;
		return x == that.x && y == that.y;
	}

	@Override
	public int hashCode() {
		return ((x << BITS_TO_SHIFT_LEFT) & 0xFFFF0000) | (y & 0x0000FFFF);
	}
}
