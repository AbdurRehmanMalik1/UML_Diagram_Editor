package Controllers;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MyContextMenu {
    private ContextMenu contextMenu;

    public MyContextMenu(Pane pane, Runnable onCopy, Runnable onPaste, Runnable onDelete, Runnable onCut) {
        contextMenu = new ContextMenu();

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
                boolean isObjectFocused = pane.getChildren().stream().anyMatch(node -> node.isFocused());

                copyItem.setDisable(!isObjectFocused);
                deleteItem.setDisable(!isObjectFocused);
                cutItem.setDisable(!isObjectFocused);
                pasteItem.setDisable(false);

                contextMenu.show(pane, event.getScreenX(), event.getScreenY());
            }
        });
    }

    public void hideContextMenu() {
        if (contextMenu != null) {
            contextMenu.hide();
        }
    }
}
