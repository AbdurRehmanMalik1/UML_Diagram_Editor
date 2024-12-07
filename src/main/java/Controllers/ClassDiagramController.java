package Controllers;

import Models.CD.Attribute;
import Models.CD.Method;
import Models.ClassModel;
import UML.Objects.ClassObject;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClassDiagramController extends VBox {
    private ClassObject parentClass;
    private final HBox classNameWrapper;

    private Button addAttributeButton;
    private Button addMethodButton;
    public ClassDiagramController(ClassObject parentClass, HBox classNameWrapper) {
        this.parentClass = parentClass;
        this.classNameWrapper = classNameWrapper;


        initComponents();

        addMethodButton.setFocusTraversable(false);
        addAttributeButton.setFocusTraversable(false);
        Platform.runLater(this::adjustButtonPosition);

        classNameWrapper.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) ->
                Platform.runLater(this::adjustButtonPosition));

        addButtonEvents();
    }
    public void setParentClass(ClassObject parentClass){
        this.parentClass = parentClass;
    }

    private void initComponents() {
        addAttributeButton = new Button("Add Attribute");
        addMethodButton = new Button("Add Method");

        getChildren().add(addAttributeButton);
        getChildren().add(addMethodButton);
    }

    private void addButtonEvents() {
        addAttributeButton.setOnMouseClicked(event -> {
            Attribute defaultAttribute = new Attribute("defaultName", "String", "private");
            parentClass.addAttribute(defaultAttribute);
            ClassModel classModel = (ClassModel) parentClass.getModel();
            classModel.addAttribute(defaultAttribute);
            //parentClass.resizeOuterRect();
        });
        addMethodButton.setOnMouseClicked(event -> {
            // Define default values for a new method
            String defaultReturnType = "void";
            String defaultMethodName = "newMethod";
            String defaultParameters = "()";

            // Create a new Method instance with default values
            Method method = new Method(defaultReturnType, defaultMethodName, defaultParameters);

            // Add the method to the parent class
            parentClass.addMethod(method);
            //parentClass.resizeOuterRect(); // Uncomment if needed to resize the outer rectangle
        });
    }

    private void adjustButtonPosition() {
        if (classNameWrapper != null && classNameWrapper.getLayoutBounds() != null) {
            double wrapperWidth = classNameWrapper.getLayoutBounds().getWidth();
            setLayoutX(classNameWrapper.getLayoutX() + wrapperWidth);
        }
    }
}
