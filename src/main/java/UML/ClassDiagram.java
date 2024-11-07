package UML;

import Controllers.ClassDiagramController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;

public class ClassDiagram extends UMLDiagram {

    private Group groupDiagram;
    private VBox detailsBox;
    private Label className;
    private List<Label> attributes;
    private List<Label> methods;
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
        // Details VBox for class name, attributes, and methods
        detailsBox = new VBox();
        detailsBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        className = new Label("Class Name");
        HBox classNameWrapper = new HBox(className);
        classNameWrapper.setAlignment(Pos.BASELINE_CENTER);
        detailsBox.getChildren().add(classNameWrapper);

        controller = new ClassDiagramController(this, classNameWrapper);

        // Attribute Box
        attributeBox = new VBox();
        attributes = new ArrayList<>();
        addAttribute("Attribute 1");
        addAttribute("Attribute 2");
        attributeBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 1, 0))));

        detailsBox.getChildren().add(attributeBox);

        // Method Box
        methodBox = new VBox();
        methods = new ArrayList<>();
        addMethod("Method():string");
        addMethod("Method():int");
        detailsBox.getChildren().add(methodBox);

        // Add the detailsBox to the Group
        groupDiagram.getChildren().add(detailsBox);
    }

    public void addAttribute(String temp) {
        Label attribute = new Label(temp);
        attributes.add(attribute);
        attributeBox.getChildren().add(attribute);
    }

    public void addMethod(String temp) {
        Label method = new Label(temp);
        methods.add(method);
        methodBox.getChildren().add(method);
    }
}
