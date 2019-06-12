package game.utils;

import game.barriers.*;
import game.extras.Game;
import game.extras.Item;
import game.locations.Location;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class which is used to read games from a text file in JSON format.
 *
 * Example
 * --------------------------------------------------------------------------------------------------------
 * {
 *     Location:
 *     {
 *         name:	"ExampleLocation",
 *         x:		"0",
 *         y:		"0",
 *         info:	"This is the description of Location \n",
 *         items: [
 *         			{
 *         			 name:	"ExampleItem1",
 *         			 info:	"This is the description of Item 1 \n",
 *         			 win:	"true/false"
 *         			},
 *         			{
 *         			 name:	"ExampleItem2",
 *         			 info"	"This is the description of Item 2 \n",
 *         			 win:	"true/false"
 *         			}
 *         ],
 *         barriers: [
 *         			{
 *         			 name:	"ExampleBarrier1",
 *         			 info:	"This is the description of Barrier1",
 *         			 key:	{
 *         			     	name:	"ExampleKey(ITEM)"
 *
 *         			 		},
*         			 items: [
 *         			 		{
 *         			 		 name:	"ExampleItemBehindBarrier1",
 *         			 		 info:	"This is the description of an item behind an ItemBarrier",
 *         			 		 win:	"true/false"
 *         			 		}
 *         			 ]
 *         			},
 *         			{
 *         			 name:	"ExampleBarrier2",
 *         			 info:	"This is the description of Barrier2",
 *         			 items: [
 *         			 		{
 *         			 		 name:	"ExampleItemBehindBarrier2",
 *         			 		 info:	"This is the description of an item behind a KeylessBarrier",
 *         			 		 win:	"true/false"
 *         			 		}
 *         			 ]
 *         			}
 *         ]
 *     }
 * }
 * --------------------------------------------------------------------------------------------------------
 */
public final class MapReader {

	private final static Logger LOGGER = LoggerToFile.getLogger();
	private static final String NAME = "name";
	private static final String INFO = "info";
	private static final String _X_ = "x";
	private static final String _Y_ = "y";
	private static final String ITEMS = "items";
	private static final String BARRIERS = "barriers";
	private static final String KEY = "key";
	private static final String WIN_CONDITION = "win";

	private MapReader() {
	}

	/**
	 * Takes the filepath to a {@link JSONObject map} and returns a Text adventure
	 * @param path	A filepath to a {@link JSONObject map}.
	 * @return		A {@link Game Text Adventure}.
	 */
	public static Game getGame(String path){

		JSONObject map = getJsonObjectFromFile(path);
		int length = map.length();	// map will never be null, the game has closed before that
		List<Location> locations = new ArrayList<>();

		for (int i = 0; i < length; i++) {

			try{
				String name = map.names().getString(i);
				JSONObject loc = map.getJSONObject(name);
				Location newLoc = getLocation(loc);
				newLoc.addAll(getItems(loc)); // newLoc will never be null, the game has closed before that
				newLoc.addAllBarriers(getBarriers(loc));
				locations.add(newLoc);
			}catch(JSONException e){
				LOGGER.log(Level.WARNING, e + "\n", e);
				LoggerToFile.showErrorMessage("Error in setup, closing", true);
			}
		}
		return new Game(locations);
	}

	/**
	 * Uses a {@link JSONObject} representing a location to create
	 * an instance of {@link Location}
	 * @param loc	{@link JSONObject} representing a location, contains name, x, y, info
	 * @return		Instance of {@link Location} with information in loc
	 */
	private static Location getLocation(final JSONObject loc) {
		try{
			String locName;
			String info;
			int x, y;
			locName = loc.getString(NAME);
			info = loc.getString(INFO);
			x = Integer.parseInt(loc.getString(_X_));
			y = Integer.parseInt(loc.getString(_Y_));
			return new Location(locName, info, x, y);
		}catch(JSONException e){
			LOGGER.log(Level.WARNING, e + "\n at" + loc, e);
			LoggerToFile.showErrorMessage("Error in setup, closing", true);
		}
		// Won't reach this point
		return null;
	}

	/**
	 * Returns a {@link List<Item> list} which contains all of the
	 * items in an {@link JSONObject obj}.
	 * @param obj	A {@link JSONObject}, containing a {@link JSONArray} named items,
	 *                 containing {@link Item items}
	 * @return		A {@link List<Item>} with all items in the location
	 */
	private static List<Item> getItems(final JSONObject obj){
		List<Item> itemList = new ArrayList<>();
		try{
			JSONArray items = obj.getJSONArray(ITEMS);
			int itemsLength = items.length();
			for (int i = 0; i < itemsLength; i++) {
				JSONObject item = items.getJSONObject(i);
				String itemName = item.getString(NAME);
				String itemInfo = item.getString(INFO);
				String itemCond = item.getString(WIN_CONDITION);
				boolean winCond = Boolean.parseBoolean(itemCond);
				itemList.add(new Item(itemName, itemInfo, winCond));
			}
		}catch(JSONException e){
			LOGGER.log(Level.FINE, e + "\n at" + obj, e);
			LoggerToFile.showErrorMessage("Error in setuo, closing", true);
		}
		return itemList;
	}

	/**
	 * Returns a {@link List<Barrier> list} which contains all of the
	 * items in an {@link JSONObject obj}.
	 * @param loc	An {@link JSONObject obj} containing an {@link JSONArray array} named
	 * @return	A {@link List<Barrier> list} of all {@link Barrier barriers} in this location
	 */
	private static List<Barrier> getBarriers(final JSONObject loc){
		List<Barrier> barrierList = new ArrayList<>();
		try{
			JSONArray barriers = loc.getJSONArray(BARRIERS);
			int barriersLength = barriers.length();
			for (int i = 0; i < barriersLength; i++) {

				JSONObject jsonBarrier = barriers.getJSONObject(i);
				String barrierName = jsonBarrier.getString(NAME);
				String barrierInfo = jsonBarrier.getString(INFO);
				List<Item> barrierItems = getItems(jsonBarrier);

				Barrier barrier;

				if(jsonBarrier.has(KEY)){
					String keyName = jsonBarrier.getJSONObject(KEY).getString(NAME);
					Item barrierKey = new Item(keyName, KEY, false);
					barrier = new GenericBarrier(barrierName, barrierInfo, barrierKey, barrierItems);
				}else{
					barrier = new GenericBarrier(barrierName, barrierInfo, null, barrierItems);
				}
				barrierList.add(barrier);
			}
		}catch(JSONException e){
			LOGGER.log(Level.FINE, e + "\n at" + loc, e);
			LoggerToFile.showErrorMessage("Error in setup, closing", true);
		}
		return barrierList;
	}

	/**
	 * Takes a filepath and converts it into a {@link JSONObject map}.
	 * Will never return null
	 * @param path	A filepath to a map in JSON format
	 * @return		A {@link JSONObject map} containing {@link JSONObject locations}
	 */
	private static JSONObject getJsonObjectFromFile(String path) {
		StringBuilder sb = new StringBuilder();
		try (Scanner sc = new Scanner(IOUtilities.getInputStream(path))){
			while (sc.hasNextLine()){
				sb.append(sc.nextLine()).append('\n');
			}
			String file = sb.toString();
			return new JSONObject(file);
		}catch(IOException | JSONException e){
			LOGGER.log(Level.SEVERE, "Failed setup", e);
			LoggerToFile.showErrorMessage("Error in setup, closing", true);
		}
		// Won't reach this point
		return null;
	}
}
