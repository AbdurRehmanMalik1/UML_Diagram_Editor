package Controllers;

import Models.CD.Method;
import UML.Objects.InterfaceObject;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InterfaceDiagramController extends VBox {
    private final InterfaceObject parentClass;
    private final HBox classNameWrapper;

    private Button addMethodButton;

    public InterfaceDiagramController(InterfaceObject parentClass, HBox classNameWrapper) {
        this.parentClass = parentClass;
        this.classNameWrapper = classNameWrapper;

        initComponents();
        addMethodButton.setFocusTraversable(false);
        Platform.runLater(this::adjustButtonPosition);

        classNameWrapper.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) ->
                Platform.runLater(this::adjustButtonPosition));

        addButtonEvents();
    }

    private void initComponents() {
        addMethodButton = new Button("Add Method");
        getChildren().add(addMethodButton);
    }

    private void addButtonEvents() {
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
