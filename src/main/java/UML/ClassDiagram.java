package UML;

import Controllers.ClassDiagramControllers.ClassDController;
import Controllers.ClassDiagramControllers.ClassDiagramController;
import Models.ClassModel;
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


public class ClassDiagram extends UMLDiagram {
    private transient final Group groupDiagram;
    private transient  VBox detailsBox;
    private transient EditableField className;
    private transient List<StackPane> attributes;
    private transient List<StackPane> methods;
    private transient VBox attributeBox;
    private transient VBox methodBox;
    private transient ClassDController controller;
    private ClassModel classModel;
    public void unfocusSelf(){
        setFocused(false);
    }
    public ClassDiagram() {
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

        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            outerRect.setVisibility(newValue);
        });
    }
    public ClassModel getClassModel(){
        return classModel;
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
    public void reloadClassModel(){
        ClassModel reloadedModel = new ClassModel();
        reloadedModel.setClassName(className.getText());
        for(StackPane attributeStackPane : attributes) {
            EditableField editableField = (EditableField)attributeStackPane;
            reloadedModel.addAttribute(editableField.getText());
        }
        for(StackPane methodStackPane : methods) {
            EditableField editableField = (EditableField) methodStackPane;
            reloadedModel.addMethod(editableField.getText());
        }
        classModel = reloadedModel;
    }
}
