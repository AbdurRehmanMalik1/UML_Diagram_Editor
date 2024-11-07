package UML;

import Controllers.ClassDiagramController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassDiagram extends UMLDiagram {

    private final Group groupDiagram;
    private VBox detailsBox;
    private EditableField className;
    private List<StackPane> attributes;
    private List<StackPane> methods;
    private VBox attributeBox;
    private VBox methodBox;
    private ClassDiagramController controller;

    public ClassDiagram() {
        super();
        groupDiagram = new Group();
        initComponents();

        groupDiagram.getChildren().add(controller);


        //Finally add the group to the ClassDiagram Object
        getChildren().add(groupDiagram);
    }

    private void initComponents() {
        detailsBox = new VBox();
        detailsBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        EditableField classNameField = new EditableField("Class Name");
        classNameField.setAlignment(Pos.BASELINE_CENTER);
        className = classNameField;
        HBox classNameWrapper = new HBox(className);
        classNameWrapper.setAlignment(Pos.BASELINE_CENTER);
        detailsBox.getChildren().add(classNameWrapper);

        controller = new ClassDiagramController(this, classNameWrapper);

        attributeBox = new VBox();
        attributes = new ArrayList<>();
        addAttribute("Attribute 1");
        addAttribute("Attribute 2");
        attributeBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 1, 0))));

        detailsBox.getChildren().add(attributeBox);

        methodBox = new VBox();
        methods = new ArrayList<>();
        addMethod("Method():string");
        addMethod("Method():int");
        detailsBox.getChildren().add(methodBox);

        groupDiagram.getChildren().add(detailsBox);
    }

    public void addAttribute(String temp) {
        StackPane attribute = new EditableField(temp);
        attributes.add(attribute);
        attributeBox.getChildren().add(attribute);
    }

    public void addMethod(String temp) {
        StackPane method = new EditableField(temp);
        methods.add(method);
        methodBox.getChildren().add(method);
    }



    private static class EditableField extends StackPane {

        private Label label;
        private TextField textField;

        public EditableField(String s) {
            setAlignment(Pos.BASELINE_LEFT);
            label = new Label(s);
            textField = new TextField(s);
            getChildren().add(label);

            addEditEvent();

            textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) { // Checks if the TextField lost focus
                    // Actions to take when focus is lost, e.g., replace TextField with Label
                    getChildren().remove(textField);
                    label.setText(textField.getText()); // Update the label with the TextField content
                    if(getChildren().isEmpty())
                        getChildren().add(label); // Replace TextField with the Label
                }
            });
        }

        private void addEditEvent() {
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    if (getChildren().contains(label)) {
                        getChildren().remove(label);
                        getChildren().add(textField);
                    }
                }
            });
            textField.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ENTER) { // Check if the Enter key was pressed
                    if (getChildren().contains(textField)) {
                        getChildren().remove(textField);
                        label.setText(textField.getText()); // Set label text to text field content
                        getChildren().add(label);
                    }
                }
            });
        }
    }
}
