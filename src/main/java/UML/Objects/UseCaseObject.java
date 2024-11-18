package UML.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class UseCaseObject extends UMLObject {
    private final StackPane box;
    private final Ellipse ellipse;
    private final EditableField field;
    private double radiusX = 70;
    private double radiusY = 50;

    public UseCaseObject(String initialText) {
        super();
        box = new StackPane();
        ellipse = new Ellipse();
        field = new EditableField(initialText);

        // Configure Ellipse
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(1);
        setRadii(radiusX, radiusY);

        // Add elements to StackPane
        box.getChildren().addAll(ellipse, field);
        box.setAlignment(Pos.CENTER);

        getChildren().add(box);

        outerRect.setSize(box.getLayoutBounds().getWidth()+10,box.getLayoutBounds().getHeight()+10);
        outerRect.setLocation(box.getLayoutX()-5,box.getLayoutY()-5);
    }

    public UseCaseObject() {
        this("Use Case");
    }

    public void setRadii(double newRadiusX, double newRadiusY) {
        this.radiusX = newRadiusX;
        this.radiusY = newRadiusY;
        ellipse.setRadiusX(newRadiusX);
        ellipse.setRadiusY(newRadiusY);
        requestLayout();
    }

    public double getRadiusX() {
        return radiusX;
    }

    public double getRadiusY() {
        return radiusY;
    }

    public void setPosition(double x, double y) {
        setLayoutX(x);
        setLayoutY(y); // Update position of the entire UseCase object
    }

    // Inner class for editable field
    private class EditableField extends StackPane {
        private final Label label;
        private final TextField textField;
        private ChangeListener<String> labelListener;
        private ChangeListener<String> textFieldListener;

        public EditableField(String text) {
            setAlignment(Pos.CENTER); // Center the label and text field
            setPadding(new Insets(5));
            label = new Label(text);
            textField = new TextField(text);
            getChildren().add(label);

            initializeListeners();

            label.textProperty().addListener(labelListener);

            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && getChildren().contains(label)) {
                    switchToTextField();
                }
            });

            textField.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    commitEdit();
                }
            });

            textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) commitEdit();
            });
        }

        private void initializeListeners() {
            labelListener = (ObservableValue<? extends String> obs, String oldText, String newText) -> {
                double newWidth = label.getFont().getSize() * newText.length();
                setRadii(Math.max(30, newWidth + 10), radiusY);
            };

            textFieldListener = (ObservableValue<? extends String> obs, String oldText, String newText) -> {
                double newWidth = textField.getFont().getSize() * newText.length();
                setRadii(Math.max(30, newWidth + 10), radiusY);
            };
        }

        private void switchToLabel() {
            textField.textProperty().removeListener(textFieldListener);
            label.textProperty().addListener(labelListener);
        }

        private void switchToTextField() {
            if (getChildren().contains(label)) {
                getChildren().remove(label);
                getChildren().add(textField);
                textField.requestFocus();
                textField.selectAll();

                label.textProperty().removeListener(labelListener);
                textField.textProperty().addListener(textFieldListener);
            }
        }

        private void commitEdit() {
            if (getChildren().contains(textField)) {
                getChildren().remove(textField);
                label.setText(textField.getText());
                getChildren().add(label);
                switchToLabel();
            }
        }
    }
}