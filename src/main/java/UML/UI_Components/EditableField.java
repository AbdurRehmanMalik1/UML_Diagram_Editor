package UML.UI_Components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
public class EditableField extends StackPane {
    private final Label label;
    private final TextField textField;
    private Runnable onCommitCallback;
    public EditableField(String s) {
        this(s,null);
    }
    public EditableField(String s, Runnable onCommitCallback) {
        this.onCommitCallback = onCommitCallback;  // Set the callback

        setAlignment(Pos.BASELINE_LEFT);
        setPadding(new Insets(0, 5, 0, 5));
        label = new Label(s);
        textField = new TextField(s);
        getChildren().add(label);

        setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && getChildren().contains(label)) {
                getChildren().remove(label);
                getChildren().add(textField);
            }
        });
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) { // Check if the Enter key was pressed
                commitEdit();
            }
        });
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                commitEdit();
        });
    }

    private void commitEdit() {
        if (getChildren().contains(textField)) {
            getChildren().remove(textField);
            label.setText(textField.getText()); // Set label text to TextField content
            getChildren().add(label);

            if (onCommitCallback != null) {
                onCommitCallback.run(); // Call the parent callback when commit happens
            }
        }
    }

    public String getText(){
        if(getChildren().contains(label))
            return label.getText();
        else
            return textField.getText();
    }
}
