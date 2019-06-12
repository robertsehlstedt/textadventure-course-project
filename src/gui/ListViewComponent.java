package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * Extends a JavaFX ListView which will contain some application specific functionality.
 * The component is non-editable.
 */
public class ListViewComponent extends ListView<Text> {

    private final static int WIDTH = 800;
    private final static int HEIGHT = 450;

    public ListViewComponent(){

        ObservableList<Text> messages = FXCollections.observableArrayList();
        this.setItems(messages);
        this.setEditable(false);
        this.setPrefSize(WIDTH, HEIGHT);
    }

    /**
     * Adds a message to the bottom of the component and scrolls it down to the bottom.
     * @param msg   The message to addItem to the component
     */
    public void addMessage(Text msg) {
        ObservableList<Text> items = this.getItems();
        items.add(msg);
        this.scrollTo(items.size() - 1);
    }
}