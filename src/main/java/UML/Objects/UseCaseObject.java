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

public class UseCaseObject extends UMLObject {
    private final Ellipse ellipse;
    private double radiusX = 70;
    private double radiusY = 50;
    EditableField field;
    public UseCaseObject(String initialText) {
        super();
        model = new UseCaseModel();
        StackPane box = new StackPane();
        ellipse = new Ellipse();
        field = new EditableField(initialText , this::reloadModel);

        // Configure Ellipse
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(1);
        setRadii(radiusX, radiusY);

        // Add elements to StackPane
        box.getChildren().addAll(ellipse, field);
        box.setAlignment(Pos.CENTER);

        getChildren().add(box);

        outerRect.setSize(ellipse.getRadiusX()*2+4,ellipse.getRadiusY()*2+4);
        outerRect.setLocation(box.getLayoutX()-1, box.getLayoutY()-1);
    }

    public UseCaseObject() {
        this("Use Case");
    }


    @Override
    public double getWidth() {
        return ellipse.getRadiusX()*2;
    }

    @Override
    public double getHeight() {
        return ellipse.getRadiusY()*2;
    }

    @Override
    public Model getModel() {
        return null;
    }

    @Override
    public void setModel(Model model) {
        if (model!=null){
            this.model = model;

        }

    }

    @Override
    public void reloadModel() {
        super.reloadModel();
        UseCaseModel useCaseModel = downcastModel();
        useCaseModel.setUseCaseName(field.getText());

    }

    public UseCaseModel downcastModel(){
        return (UseCaseModel) model;
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
        setLayoutY(y);
    }

    private class EditableField extends StackPane {
        private final Label label;
        private final TextField textField;
        private ChangeListener<String> labelListener;
        private ChangeListener<String> textFieldListener;
        Runnable reloadModel;
        public EditableField(String text , Runnable runnable) {
            reloadModel = runnable;
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
        public String getText(){
            if(getChildren().contains(textField))
                return textField.getText();
            else
                return label.getText();
        }
    }
}
