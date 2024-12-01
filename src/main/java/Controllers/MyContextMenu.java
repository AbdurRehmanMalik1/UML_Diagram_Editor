package Controllers;

import UML.Objects.UMLObject;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MyContextMenu {

    public static void createContextMenu(Pane pane, Runnable onCopy, Runnable onPaste, Runnable onDelete, Runnable onCut) {
        ContextMenu contextMenu = new ContextMenu();

        pane.getStylesheets().add(MyContextMenu.class.getResource("/Styling/CanvasContextMenuStyling.css").toExternalForm());

        MenuItem copyItem = createStyledMenuItem("Copy", "Ctrl+C", onCopy);
        MenuItem pasteItem = createStyledMenuItem("Paste", "Ctrl+V", onPaste);
        MenuItem deleteItem = createStyledMenuItem("Delete", "Del", onDelete);
        MenuItem cutItem = createStyledMenuItem("Cut", "Ctrl+X", onCut);

        contextMenu.getItems().addAll(cutItem, copyItem, pasteItem, deleteItem);

        // Show ContextMenu on right-click
        pane.setOnMousePressed((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                boolean isObjectFocused = false;

                for (Node node : pane.getChildren()) {
                    if (node instanceof UMLObject && node.isFocused()) {
                        isObjectFocused = true;
                        break;
                    } else if (node instanceof UML.Line.Line && node.isFocused()) {
                        isObjectFocused = true;
                        break;
                    }
                }

                copyItem.setDisable(!isObjectFocused);
                pasteItem.setDisable(false);
                deleteItem.setDisable(!isObjectFocused);
                cutItem.setDisable(!isObjectFocused);

                contextMenu.show(pane, event.getScreenX(), event.getScreenY());
            } else if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.hide();
            }
        });

        contextMenu.setOnHidden(event -> {
        });
    }

    private static MenuItem createStyledMenuItem(String text, String shortcut, Runnable action) {
        MenuItem menuItem = new MenuItem();
        HBox hBox = new HBox();

        Label textLabel = new Label(text);
        textLabel.getStyleClass().add("menu-text");
        textLabel.setMinWidth(80);
        textLabel.setAlignment(Pos.CENTER_LEFT);
        Label shortcutLabel = new Label(shortcut);
        shortcutLabel.getStyleClass().add("menu-shortcut");

        hBox.getChildren().addAll(textLabel, shortcutLabel);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_LEFT);


        menuItem.setGraphic(hBox);
        menuItem.setOnAction(event -> action.run());

        return menuItem;
    }
}

