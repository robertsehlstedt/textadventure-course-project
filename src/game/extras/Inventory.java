package game.extras;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An inventory which can contain items.
 */
public class Inventory implements Iterable<Item> {

	private List<Item> inventory;

	public Inventory() {
		inventory = new ArrayList<>();
	}

	/**
	 * Adds an item to the inventory
	 * @param item The item to addItem
	 */
	public void add(Item item) {
		inventory.add(item);
	}

	/**
	 * Removes an item from the inventory
	 * @param item The item to removeItem
	 * @return true if removed item, else false
	 */
	public boolean remove(Item item) {
		return inventory.remove(item);
	}

	/**
	 * Gets an item from the inventory based on its name.
	 * @param name Name of the item we want to find
	 * @return The item if it exists, else null
	 */
	public Item getItem(String name) {
		for (Item item : inventory) {
			if (name.equals(item.getName())) return item;
		}
		return null;
	}

	/**
	 * @return A {@link String} with the information of all the items in the inventory.
	 */
	public String getInfo() {

		StringBuilder b = new StringBuilder();

		for (Item item : inventory) {
			b.append(item.getInformation());
		}
		return b.toString();
	}

	@Override public Iterator<Item> iterator() {
		return inventory.iterator();
	}

	/**
	 * @param item	An {@link Item}.
	 * @return True if the inventory contains the {@link Item}, else false.
	 */
	public boolean contains(Item item) {
		return inventory.contains(item);
	}

	/**
	 * Adds all {@link Item items} in a {@link List<Item> list} to the inventory.
	 * @param itemList	The list of items to add.
	 */
	public void addAll(List<Item> itemList) {
		inventory.addAll(itemList);
	}
}
