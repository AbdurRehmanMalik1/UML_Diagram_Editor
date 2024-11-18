package Controllers.ClassDiagramControllers;

import Models.ClassModel;
import UML.ClassDiagram;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClassDiagramTestController extends VBox implements ClassDController{
    private final ClassDiagram parentClass;
    private final HBox classNameWrapper;

    private Button addAttributeButton;
    private Button addMethodButton;
    private Button printModelButton;  // New button

    public ClassDiagramTestController(ClassDiagram parentClass, HBox classNameWrapper) {
        this.parentClass = parentClass;
        this.classNameWrapper = classNameWrapper;

        initComponents();

        Platform.runLater(this::adjustButtonPosition);

        classNameWrapper.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            Platform.runLater(this::adjustButtonPosition);
        });

        addButtonEvents();
    }

    private void initComponents() {
        addAttributeButton = new Button("Add Attribute");
        addMethodButton = new Button("Add Method");
        printModelButton = new Button("Print Models");  // Initialize the new button

        getChildren().add(addAttributeButton);
        getChildren().add(addMethodButton);
        getChildren().add(printModelButton);  // Add the new button to the layout
    }

    private void addButtonEvents() {
        addAttributeButton.setOnMouseClicked(event -> {
            String attribute = "New Attribute";
            parentClass.addAttribute(attribute);
            parentClass.getClassModel().addAttribute(attribute);
            //parentClass.resizeOuterRect();
        });
        addMethodButton.setOnMouseClicked(event -> {
            String method = "New Method";
            parentClass.addMethod(method);
            //parentClass.resizeOuterRect();
        });

        printModelButton.setOnMouseClicked(event -> {
            ClassModel classModel = parentClass.getClassModel();
            System.out.println("class model : " + classModel);
            // Empty body for printModel button event handler
        });
    }

    private void adjustButtonPosition() {
        if (classNameWrapper != null && classNameWrapper.getLayoutBounds() != null) {
            double wrapperWidth = classNameWrapper.getLayoutBounds().getWidth();
            setLayoutX(classNameWrapper.getLayoutX() + wrapperWidth);
        }
    }
}
