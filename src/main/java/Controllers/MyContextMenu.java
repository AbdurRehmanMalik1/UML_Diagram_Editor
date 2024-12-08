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

/**
 * MyContextMenu is a utility class that provides a context menu for handling different actions
 * such as copy, paste, delete, and cut on UML objects within a given Pane.
 */
public class MyContextMenu {

    /**
     * Creates a context menu on the specified Pane with given actions.
     *
     * @param pane   the Pane on which the context menu is to be created
     * @param onCopy the action to be executed when the "Copy" menu item is selected
     * @param onPaste the action to be executed when the "Paste" menu item is selected
     * @param onDelete the action to be executed when the "Delete" menu item is selected
     * @param onCut the action to be executed when the "Cut" menu item is selected
     */
    public static void createContextMenu(Pane pane, Runnable onCopy, Runnable onPaste, Runnable onDelete, Runnable onCut) {
        ContextMenu contextMenu = new ContextMenu();

        // Apply custom stylesheet for the context menu
        pane.getStylesheets().add(MyContextMenu.class.getResource("/Styling/CanvasContextMenuStyling.css").toExternalForm());

        // Create menu items
        MenuItem copyItem = createStyledMenuItem("Copy", "Ctrl+C", onCopy);
        MenuItem pasteItem = createStyledMenuItem("Paste", "Ctrl+V", onPaste);
        MenuItem deleteItem = createStyledMenuItem("Delete", "Del", onDelete);
        MenuItem cutItem = createStyledMenuItem("Cut", "Ctrl+X", onCut);

        // Add menu items to the context menu
        contextMenu.getItems().addAll(cutItem, copyItem, pasteItem, deleteItem);

        // Show ContextMenu on right-click
        pane.setOnMousePressed((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                boolean isObjectFocused = false;

                // Check if any UML object or line is focused
                for (Node node : pane.getChildren()) {
                    if (node instanceof UMLObject && node.isFocused()) {
                        isObjectFocused = true;
                        break;
                    } else if (node instanceof UML.Line.Line && node.isFocused()) {
                        isObjectFocused = true;
                        break;
                    }
                }

                // Enable/disable menu items based on the focus state
                copyItem.setDisable(!isObjectFocused);
                pasteItem.setDisable(false);
                deleteItem.setDisable(!isObjectFocused);
                cutItem.setDisable(!isObjectFocused);

                // Show the context menu at the cursor position
                contextMenu.show(pane, event.getScreenX(), event.getScreenY());
            } else if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.hide();
            }
        });

        contextMenu.setOnHidden(event -> {
            // Optional callback when the context menu is hidden
        });
    }

    /**
     * Creates a styled menu item with a label and a keyboard shortcut.
     *
     * @param text     the text to be displayed on the menu item
     * @param shortcut the keyboard shortcut associated with the menu item
     * @param action   the action to be executed when the menu item is selected
     * @return a styled MenuItem with a graphical representation
     */
    private static MenuItem createStyledMenuItem(String text, String shortcut, Runnable action) {
        MenuItem menuItem = new MenuItem();
        HBox hBox = new HBox();

        // Create text label for the menu item
        Label textLabel = new Label(text);
        textLabel.getStyleClass().add("menu-text");
        textLabel.setMinWidth(80);
        textLabel.setAlignment(Pos.CENTER_LEFT);

        // Create shortcut label for the menu item
        Label shortcutLabel = new Label(shortcut);
        shortcutLabel.getStyleClass().add("menu-shortcut");

        // Arrange the labels horizontally
        hBox.getChildren().addAll(textLabel, shortcutLabel);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_LEFT);

        // Set graphical content and action for the menu item
        menuItem.setGraphic(hBox);
        menuItem.setOnAction(event -> action.run());

        return menuItem;
    }
}
