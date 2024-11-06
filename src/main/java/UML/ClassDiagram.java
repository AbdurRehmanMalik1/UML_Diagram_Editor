package UML;

import Controllers.ClassDiagramController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;
public class ClassDiagram extends Group {
    private VBox detailsBox;           // VBox for class details (name, attributes, methods)
    private Label className;
    private VBox attributeBox;
    private List<Label> attributes;
    private List<Label> methods;
    private VBox methodBox;
    private CustomPoint point;         // Used for moving
    private ClassDiagramController controller;

    public ClassDiagram() {
        point = new CustomPoint(getLayoutX(), getLayoutY());

        // Initialize and add components
        initComponents();

        addMouseEvents();

        // Pass className to the controller when creating it

        getChildren().add(controller);
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
        controller = new ClassDiagramController(this,classNameWrapper);
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
        getChildren().add(detailsBox);
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

    private void addMouseEvents() {
        setOnMousePressed(event -> {
            point.setLocation(event.getSceneX(), event.getSceneY());
        });
        setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - point.getX();
            double deltaY = event.getSceneY() - point.getY();

            setLayoutX(getLayoutX() + deltaX);
            setLayoutY(getLayoutY() + deltaY);

            point.setLocation(event.getSceneX(), event.getSceneY());
        });
    }
}
