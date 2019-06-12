package gui;

import game.extras.Game;
import game.score.Highscore;
import game.score.HighscoreList;
import game.utils.IOUtilities;
import game.utils.Interpreter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;

/**
 * Extends a JavaFX TextField which will contain some application specific functionality.
 * Once the user presses ENTER, the component will send the message forward to the
 * ListView component for interpretation, and then clear the TextField.
 */
public class TextInputComponent extends TextField {

    private ListViewComponent listComp;
    private TextInputComponent comp = this;
    private Interpreter interp;
    private Game game;

    /**
     * @param listViewComponent     The connected ListViewComponent
     * @param inventoryComponent    The connected InventoryComponent
     * @param statusComponent     The connected StatusComponent
     * @param game                  The game instance
     */
    public TextInputComponent(ListViewComponent listViewComponent,
                              InventoryComponent inventoryComponent,
                              StatusComponent statusComponent,
                              Game game){
    	this.listComp = listViewComponent;
        this.interp = new Interpreter(inventoryComponent, listViewComponent, statusComponent, game);
        this.game = game;

        // Determines what will happen when we click ENTER in the textField
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String msg = comp.getCharacters().toString().toLowerCase();

                runPlaying(msg);
                comp.clear();
            }
        });
    }

	/**
	 * Saves the highscore to the database where highscores are stored
	 */
	private void saveHighscore() {
		String name = requestPlayerName();
		Highscore score = game.getPlayer().getScore();
		score.setName(name);
		score.createScore();
		HighscoreList highscores = HighscoreList.getHighscoreList();
		highscores.add(score);
		IOUtilities.saveHighscoreList(highscores);
	}

	/**
	 * Requests a player's name with a popup.
	 * @return	The player name, or 'Player' if none was entered
	 */
	private String requestPlayerName() {
		TextInputDialog request = new TextInputDialog();
		request.setHeaderText("Congratulations!");
		request.setTitle("Win");
		request.setContentText("Name:");
		request.showAndWait();
		if(request.getResult().isEmpty()){
			request.setResult("Player");
		}
		return request.getResult();
	}

	/**
	 * Runs the game with the message.
	 * @param msg	Message from the user
	 */
	private void runPlaying(String msg){
    	if(msg.isEmpty()) return;
    	sendUserMessage(msg);
    	interp.interpret(msg);
    	if(game.getPlayer().hasWon()) runWon();
	}

	/**
	 * Run the winning sequence
	 */
	private void runWon(){
    	sendServerMessage("You have won!");
    	saveHighscore();
    	Platform.exit();
	}

	/**
	 * Sends the user's message to the listview
	 * @param msg	The user's message
	 */
	private void sendUserMessage(String msg){
    	Text text = new Text(msg);
    	listComp.addMessage(text);
	}

	/**
	 * Sends a server message to the user
	 * @param msg	The message to send.
	 */
	private void sendServerMessage(String msg){
    	interp.sendMessage(msg);
	}
}