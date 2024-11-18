package Controllers;

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

        Platform.runLater(this::adjustButtonPosition);

        classNameWrapper.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            Platform.runLater(this::adjustButtonPosition);
        });

        addButtonEvents();
    }

    private void initComponents() {
        addMethodButton = new Button("Add Method");
        getChildren().add(addMethodButton);
    }

    private void addButtonEvents() {
        addMethodButton.setOnMouseClicked(event -> {
            parentClass.addMethod("New Method3111111111111111111111111");
            //parentClass.resizeOuterRect();
        });
    }

    private void adjustButtonPosition() {
        if (classNameWrapper != null && classNameWrapper.getLayoutBounds() != null) {
            double wrapperWidth = classNameWrapper.getLayoutBounds().getWidth();
            setLayoutX(classNameWrapper.getLayoutX() + wrapperWidth);
        }
    }
}
