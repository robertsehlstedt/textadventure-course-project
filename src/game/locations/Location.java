package game.locations;

import game.barriers.Barrier;
import game.extras.Inventory;
import game.extras.Item;
import game.extras.Player;
import game.barriers.BarrierHandler;

import java.util.List;

/**
 * A location in the game, which contains different items and has a position on the map.
 */
public class Location {

	private String name;
	private String information;
	private MapPosition position;
	private Inventory items;
	private BarrierHandler barriers;

	/**
	 * @param locName		Name of the location
	 * @param information	Location description in game
	 * @param x				The x-position of the location
	 * @param y				The y-position of the location
	 */
	public Location(String locName, String information, int x, int y) {
		this.name = locName;
		this.information = information;
		this.items = new Inventory();
		this.position = new MapPosition(x, y);
		this.barriers = new BarrierHandler();
	}

	public String getName() {
		return name;
	}

	public MapPosition getPosition() {
		return position;
	}

	/**
	 * Gets a string containing formatted information about the current location. This includes the basic description
	 * of the location, and additional information about items which are still present at the location.
	 * @return		A formatted string with the information
	 */
	public String getInformation() {
		return information + items.getInfo() + barriers.getInfo();
	}

	/**
	 * Finds an item if it is present at the location.
	 * @param itemName	The name of the desired item.
	 * @return			The {@link Item} if it exists, else null.
	 */
	public Item getItem(String itemName) {
		return items.getItem(itemName);
	}

	/**
	 * Removes an item from the location
	 * @param item returns
	 * @return asd
	 */
	public boolean removeItem(Item item) {
		return items.remove(item);
	}

	/**
	 * Tries to open a barrier with the given information.
	 * @param player	The {@link Player} of the game.
	 * @param message	The {@link String message} from the player.
	 * @return	True if a barrier was opened, else false.
	 */
	public boolean openBarrier(Player player, String message){
		return barriers.openBarrier(player, message, this);
	}

	/**
	 * Adds all items in a list to the location.
	 * @param list	The list of items to add.
	 */
	public void addAll(List<Item> list){
		items.addAll(list);
	}

	/**
	 * Adds all items from an {@link Inventory} to the location.
	 * @param inv The inventory to add
	 */
	public void addAll(Inventory inv) {
		for (Item item : inv) {
			items.add(item);
		}
	}

	/**
	 * Adds all barriers in a list to the location.
	 * @param bList	THe list of barriers to add.
	 */
	public void addAllBarriers(List<Barrier> bList) {
		barriers.addAll(bList);
	}
}

