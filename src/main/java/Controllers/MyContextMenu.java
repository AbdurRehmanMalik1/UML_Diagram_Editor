package Controllers;

import UML.Objects.UMLObject;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MyContextMenu {

    public static void createContextMenu(Pane pane, Runnable onCopy, Runnable onPaste, Runnable onDelete, Runnable onCut) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setOnAction(event -> onCopy.run());

        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(event -> onPaste.run());

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> onDelete.run());

        MenuItem cutItem = new MenuItem("Cut");
        cutItem.setOnAction(event -> onCut.run());

        contextMenu.getItems().addAll(cutItem, copyItem, pasteItem, deleteItem);

        pane.setOnMousePressed((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                boolean isObjectFocused = false;

                for (Node node : pane.getChildren()) {
                    if (node instanceof UMLObject && (node).isFocused()) {
                        isObjectFocused = true;
                        break;
                    } else if (node instanceof UML.Line.Line && (node).isFocused()) {
                        isObjectFocused = true;
                        break;
                    }
                }

                copyItem.setDisable(!isObjectFocused);
                pasteItem.setDisable(false); // Paste logic would be based on your context, leaving as always enabled here
                deleteItem.setDisable(!isObjectFocused);
                cutItem.setDisable(!isObjectFocused);

                contextMenu.show(pane, event.getScreenX(), event.getScreenY());
            }
        });
    }
}
