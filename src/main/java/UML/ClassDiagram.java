package UML;

import Controllers.ClassDiagramController;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

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

    public void unfocusSelf(){
        setFocused(false);
    }
    public ClassDiagram() {
        super();
        groupDiagram = new Group();
        initComponents();

        groupDiagram.getChildren().add(controller);


        //Finally add the group to the ClassDiagram Object
        getChildren().add(groupDiagram);

        groupDiagram.getChildren().addFirst(outerRect);
        outerRect.setVisible(false);

        this.setOnMouseClicked(event->{
            if(event.getClickCount()>=1)
                requestFocus();
        });
        this.layoutBoundsProperty().addListener((observable, oldValue, newValue) ->{
            Platform.runLater(this::resizeOuterRect);
        });
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
               outerRect.setVisible(true);
            }
            else{
                outerRect.setVisible(false);
            }
        });
    }

    public void resizeOuterRect() {
        Bounds boundsInScene  = detailsBox.localToScene(detailsBox.getBoundsInLocal());
        Bounds boundsInGroup = groupDiagram.sceneToLocal(boundsInScene);
        outerRect.setX(boundsInGroup.getMinX() - 2);
        outerRect.setY(boundsInGroup.getMinY() - 2);
        outerRect.setWidth(boundsInGroup.getWidth() + 4);
        outerRect.setHeight(boundsInGroup.getHeight() + 4);
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
        attributeBox.setPadding(new Insets(5,0,5,0));
        attributes = new ArrayList<>();
        addAttribute("Attribute 1");
        addAttribute("Attribute 2");

        attributeBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 1, 0))));

        detailsBox.getChildren().add(attributeBox);

        methodBox = new VBox();
        methodBox.setPadding(new Insets(5,0,5,0));
        methods = new ArrayList<>();
        addMethod("Method():string");
        addMethod("Method():int");
        detailsBox.getChildren().add(methodBox);

        groupDiagram.getChildren().add(detailsBox);
    }

    public void addAttribute(String temp) {
        StackPane attribute = new EditableField(temp);
        attribute.setFocusTraversable(true);
        attributes.add(attribute);
        attributeBox.getChildren().add(attribute);
    }

    public void addMethod(String temp) {
        StackPane method = new EditableField(temp);
        methods.add(method);
        methodBox.getChildren().add(method);
    }



    private static class EditableField extends StackPane {

        private final Label label;
        private final TextField textField;

        public EditableField(String s) {
            setAlignment(Pos.BASELINE_LEFT);
            setPadding(new Insets(0,5,0,5));
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
                if(!newValue)
                    commitEdit();
            });
        }
        private void commitEdit() {
            if (getChildren().contains(textField)) {
                getChildren().remove(textField);
                label.setText(textField.getText()); // Set label text to TextField content
                getChildren().add(label);
            }
        }
    }

}
