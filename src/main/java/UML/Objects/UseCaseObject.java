package UML.Objects;

import Models.Model;
import Models.UseCaseModel;
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

/**
 * Represents a use case object in a UML diagram.
 */
public class UseCaseObject extends UMLObject {
    private final Ellipse ellipse;
    private double radiusX = 70; // Default width of the use case ellipse
    private double radiusY = 50; // Default height of the use case ellipse
    private EditableField field; // Editable field for the use case name

    /**
     * Constructor to create a UseCaseObject with an initial text.
     *
     * @param initialText The initial text for the use case name.
     */
    public UseCaseObject(String initialText) {
        super();

        if(model == null) {
            model = new UseCaseModel(); // Initialize model if not already set
        }

        StackPane box = new StackPane();
        ellipse = new Ellipse();

        // Determine the use case name based on model state or default value
        String useCaseName = (downcastModel().getUseCaseName() == null || downcastModel().getUseCaseName().trim().isEmpty() || downcastModel().getUseCaseName().equals("Use Case"))
                ? "Use Case"
                : initialText;

        field = new EditableField(useCaseName, this::reloadModel); // Create an editable field for the use case name
        ellipse.setFill(Color.TRANSPARENT); // Set fill to transparent
        ellipse.setFill(Color.rgb(231, 227, 227)); // Set the fill color
        ellipse.setStroke(Color.BLACK); // Set the stroke color
        ellipse.setStrokeWidth(1); // Set the stroke width
        setRadii(radiusX, radiusY); // Set the initial radii

        box.getChildren().addAll(ellipse, field);
        box.setAlignment(Pos.CENTER);

        getChildren().add(box);

        outerRect.setSize(ellipse.getRadiusX() * 2 + 4, ellipse.getRadiusY() * 2 + 4);
        outerRect.setLocation(box.getLayoutX() - 1, box.getLayoutY() - 1);
        outerRect.setVisibility(false);
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            outerRect.setVisibility(newValue);
        });
        this.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            outerRect.setSize(ellipse.getRadiusX() * 2 + 4, ellipse.getRadiusY() * 2 + 4);
        });
        box.setOnMouseClicked(event -> {
            this.requestFocus();  // Request focus when the object is clicked
        });
    }

    /**
     * Default constructor that creates a UseCaseObject with the default name "Use Case".
     */
    public UseCaseObject() {
        this("Use Case");
    }

    /**
     * Gets the use case name.
     *
     * @return The name of the use case.
     */
    public String getUseCaseName() {
        return field.getText();
    }

    @Override
    public double getWidth() {
        return ellipse.getRadiusX() * 2;
    }

    @Override
    public double getHeight() {
        return ellipse.getRadiusY() * 2;
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public void setModel(Model model) {
        if (model != null) {
            this.model = model;
            setModel(downcastModel());
            reloadModel();
        }
    }

    private void setModel(UseCaseModel model) {
        field.setText(model.getUseCaseName());
        this.setLayoutX(model.getX());
        this.setLayoutY(model.getY());
    }

    @Override
    public void reloadModel() {
        super.reloadModel();
        UseCaseModel useCaseModel = downcastModel();
        useCaseModel.setUseCaseName(field.getText());
        model.setCoordinate(this.getLayoutX(), this.getLayoutY());
    }

    /**
     * Downcasts the model to `UseCaseModel`.
     *
     * @return The use case model.
     */
    public UseCaseModel downcastModel() {
        return (UseCaseModel) model;
    }

    /**
     * Sets the radii for the use case ellipse.
     *
     * @param newRadiusX The new radius X value.
     * @param newRadiusY The new radius Y value.
     */
    public void setRadii(double newRadiusX, double newRadiusY) {
        this.radiusX = newRadiusX;
        this.radiusY = newRadiusY;
        ellipse.setRadiusX(newRadiusX);
        ellipse.setRadiusY(newRadiusY);
        requestLayout();
    }

    /**
     * Gets the current radius X of the ellipse.
     *
     * @return The current radius X.
     */
    public double getRadiusX() {
        return radiusX;
    }

    /**
     * Gets the current radius Y of the ellipse.
     *
     * @return The current radius Y.
     */
    public double getRadiusY() {
        return radiusY;
    }

    /**
     * Sets the position of the use case object.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    public void setPosition(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
    }

    /**
     * Inner class that represents an editable field for the use case name.
     */
    private class EditableField extends StackPane {
        private final Label label;
        private final TextField textField;
        private ChangeListener<String> labelListener;
        private ChangeListener<String> textFieldListener;
        Runnable reloadModel;

        /**
         * Constructor to create an editable field with an initial text and a callback to reload the model.
         *
         * @param text      The initial text.
         * @param runnable  The callback to reload the model.
         */
        public EditableField(String text, Runnable runnable) {
            reloadModel = runnable;
            setAlignment(Pos.CENTER); // Center the label and text field
            setPadding(new Insets(5));
            label = new Label(text);
            textField = new TextField(text);
            getChildren().add(label);

            initializeListeners();

            label.textProperty().addListener(labelListener);

            setOnMouseClicked(event -> {
                requestFocus();
                if (event.getClickCount() == 2 && getChildren().contains(label)) {
                    switchToTextField();
                    event.consume();  // Stop event propagation to parent
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

        /**
         * Initializes listeners for label and text field changes.
         */
        private void initializeListeners() {
            labelListener = (ObservableValue<? extends String> obs, String oldText, String newText) -> {
                double newWidth = label.getFont().getSize() * newText.length() * 0.3;
                setRadii(Math.max(30, newWidth + 10), radiusY);
            };

            textFieldListener = (ObservableValue<? extends String> obs, String oldText, String newText) -> {
                double newWidth = textField.getFont().getSize() * newText.length() * 0.3;
                setRadii(Math.max(30, newWidth + 10), radiusY);
            };
        }

        /**
         * Switches from the text field back to the label view.
         */
        private void switchToLabel() {
            textField.textProperty().removeListener(textFieldListener);
            label.textProperty().addListener(labelListener);
        }

        /**
         * Switches from the label view to the text field for editing.
         */
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

        /**
         * Commits the edit when the text field loses focus or the Enter key is pressed.
         */
        private void commitEdit() {
            if (getChildren().contains(textField)) {
                getChildren().remove(textField);
                label.setText(textField.getText());
                getChildren().add(label);
                switchToLabel();
                reloadModel.run();
            }
        }

        /**
         * Gets the current text from the editable field.
         *
         * @return The current text.
         */
        public String getText() {
            if (getChildren().contains(textField)) {
                return textField.getText();
            } else {
                return label.getText();
            }
        }

        /**
         * Sets the text of the editable field.
         *
         * @param text The new text.
         */
        public void setText(String text) {
            label.setText(text);
            textField.setText(text);
        }
    }
}
