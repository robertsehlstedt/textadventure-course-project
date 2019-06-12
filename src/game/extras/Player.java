package game.extras;

import game.score.Highscore;

/**
 * The player of the game
 */

public class Player {

	private Inventory inventory;
	private Highscore score;
	private boolean hasWon;

	/**
	 * Creates an instance of a {@link Player}.
	 */
	public Player() {
		this.inventory = new Inventory();
		this.score = new Highscore();
		this.hasWon = false;
	}

	public boolean hasWon() {
		return hasWon;
	}

	/**
	 * Adds an item to the player's inventory
	 * @param item	The item to addItem
	 */
	public void addItem(Item item){
		inventory.add(item);
	}

	/**
	 * Grants the player with an amount of points.
	 * @param points	The amount of points to give.
	 */
	public void addPoints(int points){
			score.add(points);
		}

	/**
	 * Returns an item from the inventory, with a string
	 * @param itemName	The name of the item to get
	 * @return		An item of the same name found in the inventory
	 */
	public Item getItem(String itemName){
		return inventory.getItem(itemName);
	}

	/**
	 * Increments the amount of steps the player has taken by 1.
	 */
	public void incSteps(){
		score.addSteps(1);
	}

	public Highscore getScore(){
		return score;
	}

	/**
	 * Whether the player carries a certain item or not.
	 * @param item	The item to look for.
	 * @return	True if the player carries the item, else false.
	 */
	public boolean carries(Item item){
		return inventory.contains(item);
	}

	/**
	 * Updates the player with necessary information, for example if they have met a win condition.
	 */
	public void update(){
		for (Item item : inventory) {
			if(item.isWinCondition()) hasWon = true;
		}
	}
}
