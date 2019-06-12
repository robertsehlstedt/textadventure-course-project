package game.utils;

import game.extras.Game;
import game.extras.Item;
import game.extras.Player;
import game.locations.Direction;
import game.locations.Location;
import game.score.HighscoreList;
import gui.InventoryComponent;
import gui.ListViewComponent;
import gui.StatusComponent;
import javafx.scene.text.Text;

/**
 * Used to interpret all of the input by the user, and forward a response and/or action.
 * Takes the different graphical components it will interact with, as well as the current game instance.
 */
public final class Interpreter {

	private final static String ERROR_MESSAGE = "That is not a command I understand =(";

	private final static int OPEN_SCORE = 200;
	private final static int TAKE_SCORE = 100;

	private InventoryComponent invComp;
	private ListViewComponent listComp;
	private StatusComponent statusComp;
	private Game game;
	private HighscoreList highscores;

	/**
	 * @param inv		InventoryComponent
	 * @param list		ListViewComponent
	 * @param statusComp	StatusComponent
	 * @param game		Game instance
	 */
	public Interpreter(InventoryComponent inv, ListViewComponent list, StatusComponent statusComp, Game game) {
		this.invComp = inv;
		this.listComp = list;
		this.statusComp = statusComp;
		this.game = game;
		this.highscores = HighscoreList.getHighscoreList();
		sendMessage("You wake up in a room in the B-House. You feel disoriented \nand hungry.");
	}

	/**
	 * Interprets a message and forwards the information to the game.
	 * The message string must consist of two words separated by blankspace,
	 * or it will send the error message {@value #ERROR_MESSAGE} to the {@link ListViewComponent}
	 * if it didn't understand.
	 * @param msg		Message from user, must consist of exactly two words in order to be evaluated
	 */
	public void interpret(String msg) {

		String[] splitMessage = msg.split("\\s");
		String verb;
		String itemString;

		// Controls if the message has correct format

		if (splitMessage.length != 2) {
			sendMessage(ERROR_MESSAGE);
			return;
		}

		verb = splitMessage[0];
		itemString = splitMessage[1];

		Location loc = game.getLocation();
		Player player = game.getPlayer();
		Item item = loc.getItem(itemString);

		if (item == null) {
			item = player.getItem(itemString);
		}

		// Different commands (primitive version)

		switch (verb) {
			case "take":

				if (takeItem(item)){
					player.incSteps();
					player.addPoints(TAKE_SCORE);
				}else
					sendMessage(ERROR_MESSAGE);

				break;
			case "go":

				if(move(itemString)) player.incSteps();

				break;
			case "show":

				switch (itemString) {
					case "info":
						sendMessage(loc.getInformation());
						break;
					case "scores":
						highscores.sendHighscores(listComp);
						break;
					default:
						sendMessage(ERROR_MESSAGE);
						break;
				}

				break;
			case "open":

				if (loc.openBarrier(player, itemString)) {
					sendMessage("Opened " + itemString);
					player.incSteps();
					player.addPoints(OPEN_SCORE);
				}else sendMessage("You cannot open that!");

				break;
			case "help":
				sendMessage(IOUtilities.loadReadMe());
				break;
			default:
				sendMessage(ERROR_MESSAGE);
				break;
		}
		player.update();
		statusComp.update(game.getLocation(), player);
	}

	/**
	 * Moves the player in a {@link Direction}.
	 * @param itemString	A string representing a {@link Direction}
	 * @return	True if moved, else false.
	 */
	private boolean move(final String itemString) {
		Direction dir = Direction.getDirection(itemString);
		if (dir != null && game.move(dir)) {
			Location newLoc = game.getLocation();
			sendMessage(newLoc.getInformation());
			return true;
		}
		sendMessage("That is not a valid direction!");
		return false;
	}

	/**
	 * Sends a server message from the interpreter to the user.
	 * @param msg	The message to send.
	 */
	public void sendMessage(String msg){
		Text text = new Text(msg);
		text.setStyle("-fx-font-weight:bold;");
		listComp.addMessage(text);
	}

	/**
	 * Picks up an item from the current location.
	 * @param item	The item to pick up.
	 * @return	True if an item was taken, else false.
	 */
	private boolean takeItem(Item item){
		Player player = game.getPlayer();
		Location loc = game.getLocation();
		if (loc.removeItem(item)){
			sendMessage("Taken.");
			player.addItem(item);
			invComp.add(item.getName());
			return true;
		}
		sendMessage("I cannot do that");
		return false;
	}
}
