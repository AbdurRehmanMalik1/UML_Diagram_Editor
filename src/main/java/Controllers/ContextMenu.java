package Controllers;

import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ContextMenu {

    public static void createContextMenu(Pane pane) {
        // Create the context menu
        javafx.scene.control.ContextMenu contextMenu = new javafx.scene.control.ContextMenu();

        // Create menu items
        MenuItem selectItem = new MenuItem("Select");
        selectItem.setOnAction(event -> {
            System.out.println("Select option clicked");
            // Add logic for selecting here
        });

        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setOnAction(event -> {
            System.out.println("Copy option clicked");
            // Add logic for copying here
        });

        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(event -> {
            System.out.println("Paste option clicked");
            // Add logic for pasting here
        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> {
            System.out.println("Delete option clicked");
            // Add logic for deleting here
        });

        // Add items to the context menu
        contextMenu.getItems().addAll(selectItem, copyItem, pasteItem, deleteItem);

        // Set up right-click event to open the context menu
        pane.setOnMousePressed((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY){
                contextMenu.show(pane, event.getScreenX(), event.getScreenY());
            }
        });
    }
}
