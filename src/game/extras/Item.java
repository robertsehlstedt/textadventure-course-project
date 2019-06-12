package game.extras;

import java.util.Objects;

/**
 * Items present in the game.
 */
public class Item {

	private String name;
	private String information;
	private boolean winCondition;

	/**
	 * @param name			The desired name of the item.
	 * @param information	The desired item description.
	 * @param winCondition	Whether the item is a win condition or not.
	 */
	public Item(String name, String information, boolean winCondition){
		this.name = name;
		this.information = information;
		this.winCondition = winCondition;
	}

	public String getName() {
		return name;
	}

	public String getInformation() {
		return information;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean isWinCondition(){
		return winCondition;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || !getClass().equals(o.getClass())) return false;
		final Item item = (Item) o;
		return Objects.equals(name, item.name);
	}
}

