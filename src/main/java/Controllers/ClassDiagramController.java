package Controllers;

import UML.ClassDiagram;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClassDiagramController extends VBox {
    private final ClassDiagram parentClass;
    private final HBox classNameWrapper;

    private Button addAttributeButton;
    private Button addMethodButton;

    public ClassDiagramController(ClassDiagram parentClass, HBox classNameWrapper) {
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

        getChildren().add(addAttributeButton);
        getChildren().add(addMethodButton);
    }

    private void addButtonEvents() {
        addAttributeButton.setOnMouseClicked(event -> {
            parentClass.addAttribute("New Attribute1313131");
            parentClass.resizeOuterRect();
        });
        addMethodButton.setOnMouseClicked(event -> {
            parentClass.addMethod("New Method3111111111111111111111111");
            parentClass.resizeOuterRect();
        });
    }

    private void adjustButtonPosition() {
        if (classNameWrapper != null && classNameWrapper.getLayoutBounds() != null) {
            double wrapperWidth = classNameWrapper.getLayoutBounds().getWidth();
            setLayoutX(classNameWrapper.getLayoutX() + wrapperWidth);
        }
    }
}
