package gui;

import game.extras.Game;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import game.utils.LoggerToFile;
import game.utils.MapReader;

import java.io.IOException;

/**
 * Starts a graphical component with a new game of Text Adventure
 */
public class ApplicationFrame extends Application {

	private final static String MAP_PATH = "res/maps/B-House";
	private final static int FRAME_WIDTH = 1200;
	private final static int FRAME_HEIGHT = 500;
	private final static int HGAP = 100;
	private final static int VGAP = 10;

	private Game game;

	public ApplicationFrame(){
		try{
			LoggerToFile.init();
		}catch(IOException e){
			e.printStackTrace();
			LoggerToFile.showErrorMessage("Could not initialize error log", true);
		}
		this.game = MapReader.getGame(MAP_PATH);
	}

	@Override
	public void start(Stage primaryStage) {
		// Sets a window title and creates an aligned grid
		primaryStage.setTitle("Text Adventure");
		primaryStage.setResizable(false);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setGridLinesVisible(false);
		grid.setHgap(HGAP);
		grid.setVgap(VGAP);

		// Creates and adds ListViewComponent to the grid
		ListViewComponent listViewComponent = new ListViewComponent();
		grid.add(listViewComponent, 0, 0);

		//Creates and adds a ListViewComponent to represent an inventory
		InventoryComponent inventoryComponent = new InventoryComponent();
		grid.add(inventoryComponent,1,0);

		// Creates and adds StatusComponent to the grid
		StatusComponent locComponent = new StatusComponent(game);
		grid.add(locComponent, 1, 1);

		// Creates and adds TextInputComponent to the grid
		TextInputComponent textComponent = new TextInputComponent(listViewComponent, inventoryComponent, locComponent,  game);
		grid.add(textComponent, 0, 1);

		// Creates a Scene with the grid and adds it to the primary stage
		// Also makes sure that the TextInputComponent is focused
		Scene scene = new Scene(grid, FRAME_WIDTH, FRAME_HEIGHT);
		textComponent.requestFocus();
		listViewComponent.setFocusTraversable(false);
		inventoryComponent.setFocusTraversable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
