package Controllers;

import UML.Objects.UMLObject;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MyContextMenu {

    // Create the context menu with Runnable actions
    public static void createContextMenu(Pane pane, Runnable onCopy, Runnable onPaste, Runnable onDelete) {
        // Create the context menu
        javafx.scene.control.ContextMenu contextMenu = new javafx.scene.control.ContextMenu();

        // Create menu items


        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setOnAction(event -> {
            onCopy.run(); // Run the passed Runnable for the "Copy" action
        });

        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(event -> {
            onPaste.run(); // Run the passed Runnable for the "Paste" action
        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> {
            onDelete.run(); // Run the passed Runnable for the "Delete" action
        });

        // Add items to the context menu
        contextMenu.getItems().addAll(copyItem, pasteItem, deleteItem);
        pane.setOnMousePressed((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                boolean isObjectFocused = false;

                for (Node node : pane.getChildren()) {
                    if (node instanceof UMLObject && (node).isFocused()) {
                        isObjectFocused = true;
                        break;
                    }else if (node instanceof  UML.Line.Line && (node).isFocused()){
                        isObjectFocused = true;
                        break;
                    }
                }
                copyItem.setDisable(!isObjectFocused);
                pasteItem.setDisable(!isObjectFocused);
                deleteItem.setDisable(!isObjectFocused);
                contextMenu.show(pane, event.getScreenX(), event.getScreenY());
            }
        });
    }
}
