package UML.UI_Components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class EditableField extends StackPane {
    private final Label label; // The label that displays the text
    private final TextField textField; // The text field for editing the label text
    private final Runnable onCommitCallback; // Callback to run when the text is committed
    boolean isAbstract = false; // Indicates whether the field is abstract

    /**
     * Constructs an `EditableField` with a specified label.
     *
     * @param s the initial text for the label and text field.
     */
    public EditableField(String s) {
        this(s, null);
    }

    /**
     * Constructs an `EditableField` with a specified label and a callback to run when the text is committed.
     *
     * @param s the initial text for the label and text field.
     * @param onCommitCallback the callback to run when the text is committed.
     */
    public EditableField(String s, Runnable onCommitCallback) {
        this.onCommitCallback = onCommitCallback; // Initialize the commit callback
        setFocusTraversable(false); // Prevent the component from taking focus

        setAlignment(Pos.BASELINE_LEFT); // Align text left
        setPadding(new Insets(0, 5, 0, 5)); // Set padding around the component
        label = new Label(s); // Create the label with the initial text
        textField = new TextField(s); // Create the text field with the initial text
        getChildren().add(label); // Add the label to the stack pane

        setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && getChildren().contains(label)) {
                getChildren().remove(label); // Remove the label
                getChildren().add(textField); // Add the text field for editing
            }
        });

        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) { // Check if the Enter key was pressed
                commitEdit();
            }
        });

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                commitEdit(); // Commit the edit if the text field loses focus
            }
        });
    }

    /**
     * Commits the current edit by replacing the label with the text field's content.
     * Calls the commit callback if it exists.
     */
    private void commitEdit() {
        if (getChildren().contains(textField)) {
            getChildren().remove(textField); // Remove the text field
            label.setText(textField.getText()); // Set the label text to the text field's content
            getChildren().add(label); // Add the label back

            if (onCommitCallback != null) {
                onCommitCallback.run(); // Run the commit callback
            }
        }
    }

    /**
     * Gets the current text in the editable field.
     *
     * @return the current text, either from the label or the text field.
     */
    public String getText() {
        if (getChildren().contains(label)) {
            return label.getText(); // Return label text if it's the current display
        } else {
            return textField.getText(); // Return text field content if it's currently displayed
        }
    }

    /**
     * Sets the text of the editable field.
     *
     * @param text the new text to set.
     */
    public void setText(String text) {
        label.setText(text);
        textField.setText(text);
    }

    /**
     * Gets whether the field is abstract.
     *
     * @return `true` if the field is abstract, `false` otherwise.
     */
    public boolean getIsAbstract() {
        return isAbstract;
    }

    /**
     * Sets whether the field is abstract.
     *
     * @param s `true` to set the field as abstract, `false` otherwise.
     */
    public void setIsAbstract(boolean s) {
        isAbstract = s;
        updateFontStyle(); // Update the font style based on the abstract state
    }

    /**
     * Toggles the italic font style.
     * Changes the field's abstract state and updates the font style accordingly.
     */
    public void toggleItalic() {
        isAbstract = !isAbstract;
        updateFontStyle(); // Update the font style when toggled
    }

    /**
     * Updates the font style based on whether the field is abstract.
     * Sets the font style to italic if the field is abstract, and normal otherwise.
     */
    private void updateFontStyle() {
        if (isAbstract) {
            textField.setStyle("-fx-font-style: italic;");
            label.setStyle("-fx-font-style: italic;");
        } else {
            textField.setStyle("-fx-font-style: normal;");
            label.setStyle("-fx-font-style: normal;");
        }
    }

    /**
     * Sets whether the text field is editable.
     *
     * @param editable `true` to make the text field editable, `false` otherwise.
     */
    public void setEditable(boolean editable) {
        textField.setEditable(editable);
        textField.setStyle("-fx-background-color: transparent;"); // Transparent background when not editable
    }

    /**
     * Checks if the text field is currently focused.
     *
     * @return `true` if the text field is focused, `false` otherwise.
     */
    public boolean isTextFieldFocused() {
        return textField.isFocused();
    }
}
