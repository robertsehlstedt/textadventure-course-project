package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * A graphical component to display the inventory
 */
public class InventoryComponent extends ListView<String> {

	private static final int WIDTH = 300;
	private static final int HEIGHT = 10;

	public InventoryComponent(){
	 ObservableList<String> items = FXCollections.observableArrayList();
	 this.setItems(items);
	 this.setEditable(false);
	 this.setPrefSize(WIDTH, HEIGHT);
	}

	/**
	* Adds an item to the component.
	* @param itemName   The name of the item to addItem to the component
	*/
	public void add(String itemName) {
	 ObservableList<String> items = this.getItems();
	 items.add(itemName);
	}
}
