package UML.Objects;

import Controllers.ClassDiagramControllers.ClassDController;
import Controllers.ClassDiagramControllers.ClassDiagramController;
import Models.ClassModel;
import Models.Model;
import UML.UI_Components.EditableField;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;


public class ClassObject extends UMLObject {
    private final Group groupDiagram;
    private VBox detailsBox;
    private EditableField className;
    private List<StackPane> attributes;
    private List<StackPane> methods;
    private VBox attributeBox;
    private VBox methodBox;
    private ClassDController controller;
    private ClassModel classModel;
    public void unfocusSelf(){
        setFocused(false);
    }
    public ClassObject() {
        super();
        classModel = new ClassModel();
        groupDiagram = new Group();

        initComponents();

        groupDiagram.getChildren().add((Node) controller);

        groupDiagram.getChildren().addFirst(outerRect);
        outerRect.setVisibility(false);

        getChildren().add(groupDiagram);

        this.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 1)
                requestFocus();
        });

        this.layoutBoundsProperty().addListener((observable, oldValue, newValue)->
                Platform.runLater(this::resizeOuterRect)
        );

        this.focusedProperty().addListener((observable, oldValue, newValue) ->
            outerRect.setVisibility(newValue));
    }

    @Override
    public Model getModel(){
        return classModel;
    }
    @Override
    public double getWidth() {
        return className.getWidth();
    }

    @Override
    public double getHeight() {
        return detailsBox.getHeight();
    }

    public ClassModel getClassModel(){
        return classModel;
    }
    public void setClassModel(ClassModel model) {
        this.classModel = model;

        if (model.getClassName() != null && !model.getClassName().isEmpty()) {
            className.setText(model.getClassName());
        }

        for (String attribute : model.getAttributes()) {
            addAttribute(attribute);
        }

        for (String method : model.getMethods()) {
            addMethod(method);
        }
        this.setLayoutX(model.getX());
        this.setLayoutY(model.getY());
    }
    public void resizeOuterRect() {
        Bounds boundsInScene  = detailsBox.localToScene(detailsBox.getBoundsInLocal());
        Bounds boundsInGroup = groupDiagram.sceneToLocal(boundsInScene);
        outerRect.setLocation(boundsInGroup.getMinX()-2,boundsInGroup.getMinY()-2);
        outerRect.setSize(boundsInGroup.getWidth() + 4,boundsInGroup.getHeight() + 4);
    }

    private void initComponents() {
        detailsBox = new VBox();
        detailsBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        EditableField classNameField = new EditableField("Class Name",this::reloadClassModel);
        classNameField.setAlignment(Pos.BASELINE_CENTER);
        className = classNameField;
        HBox classNameWrapper = new HBox(className);
        classNameWrapper.setAlignment(Pos.BASELINE_CENTER);
        detailsBox.getChildren().add(classNameWrapper);

        controller = new ClassDiagramController(this, classNameWrapper);

        attributeBox = new VBox();
        attributeBox.setPadding(new Insets(5,0,5,0));
        attributes = new ArrayList<>();

        attributeBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 1, 0))));

        detailsBox.getChildren().add(attributeBox);

        methodBox = new VBox();
        methodBox.setPadding(new Insets(5,0,5,0));
        methods = new ArrayList<>();
        detailsBox.getChildren().add(methodBox);

        groupDiagram.getChildren().add(detailsBox);
    }

    public void addAttribute(String temp) {
        StackPane attribute = new EditableField(temp , this::reloadClassModel);
        attribute.setFocusTraversable(true);
        attributes.add(attribute);
        attributeBox.getChildren().add(attribute);
    }

    public void addMethod(String temp) {
        StackPane method = new EditableField(temp,this::reloadClassModel);
        methods.add(method);
        methodBox.getChildren().add(method);
    }
    public void reloadClassModel() {
        // Update the class name directly
        classModel.setClassName(className.getText());

        // Clear the existing attributes and replace them with the updated values
        if (classModel.getAttributes() != null) {
            classModel.getAttributes().clear();
        }
        for (StackPane attributeStackPane : attributes) {
            // Ensure the element is an instance of EditableField
            if (attributeStackPane instanceof EditableField) {
                EditableField editableField = (EditableField) attributeStackPane;
                classModel.addAttribute(editableField.getText());
            }
        }

        // Clear the existing methods and add the new ones
        if (classModel.getMethods() != null) {
            classModel.getMethods().clear();
        }
        for (StackPane methodStackPane : methods) {
            // Ensure the element is an instance of EditableField
            if (methodStackPane instanceof EditableField) {
                EditableField editableField = (EditableField) methodStackPane;
                classModel.addMethod(editableField.getText());
            }
        }
    }

}
