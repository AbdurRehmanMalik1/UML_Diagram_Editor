package Controllers;

import Models.CD.Attribute;
import Models.CD.Method;
import Models.ClassModel;
import UML.Objects.ClassObject;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ClassButtonController extends VBox {
    private ClassObject parentClass;
    private final HBox classNameWrapper;

    private Button addAttributeButton;
    private Button addMethodButton;
    private Button removeAttributeButton;
    private Button removeMethodButton;

    public ClassButtonController(ClassObject parentClass, HBox classNameWrapper) {
        this.parentClass = parentClass;
        this.classNameWrapper = classNameWrapper;

        initComponents();

        addMethodButton.setFocusTraversable(false);
        addAttributeButton.setFocusTraversable(false);
        removeMethodButton.setFocusTraversable(false);
        removeAttributeButton.setFocusTraversable(false);
        Platform.runLater(this::adjustButtonPosition);

        classNameWrapper.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) ->
                Platform.runLater(this::adjustButtonPosition));

        addButtonEvents();
    }

    public void setParentClass(ClassObject parentClass) {
        this.parentClass = parentClass;
    }

    private void initComponents() {
        addAttributeButton = createButton("Add Attribute", "addAttribute_icon.png");
        removeAttributeButton = createButton("Remove Attribute", "removeAttribute_icon.png");
        addMethodButton = createButton("Add Method", "addMethod_icon.png");
        removeMethodButton = createButton("Remove Method", "removeMethod_icon.png");

        getChildren().add(addAttributeButton);
        getChildren().add(removeAttributeButton);
        getChildren().add(addMethodButton);
        getChildren().add(removeMethodButton);
    }

    private Button createButton(String tooltipText, String iconFileName) {
        Button button = new Button();
        ImageView icon = new ImageView(getClass().getResource("/Images/" + iconFileName).toExternalForm());
        icon.setFitHeight(20);
            icon.setFitWidth(20);
        button.setGraphic(icon);

        button.setStyle("-fx-padding: 0; -fx-background-insets: 0;");

        button.setPrefSize(20,20);

        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(button, tooltip);

        tooltip.setShowDelay(javafx.util.Duration.seconds(0.3)); // Adjust delay before showing
        tooltip.setHideDelay(javafx.util.Duration.seconds(1));  // Adjust delay before hiding

        return button;
    }

    private void addButtonEvents() {
        addAttributeButton.setOnMouseClicked(event -> {
            Attribute defaultAttribute = new Attribute("defaultName", "String", "private");
            parentClass.addAttribute(defaultAttribute);
            ClassModel classModel = (ClassModel) parentClass.getModel();
            classModel.addAttribute(defaultAttribute);
            //parentClass.resizeOuterRect();
        });

        removeAttributeButton.setOnMouseClicked(event -> {
            StackPane selectedAttribute = parentClass.getSelectedAttribute();
            if (selectedAttribute != null) {
                parentClass.removeAttribute(selectedAttribute);
                parentClass.resizeOuterRect();
            } else {
                System.out.println("No attribute selected to remove.");
            }
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

        });

        removeMethodButton.setOnMouseClicked(event -> {
            StackPane selectedMethod = parentClass.getSelectedMethod();
            if (selectedMethod != null) {
                parentClass.removeMethod(selectedMethod);
                parentClass.resizeOuterRect();
            } else {
                System.out.println("No method selected to remove.");
            }
        });
    }

    private void adjustButtonPosition() {
        if (classNameWrapper != null && classNameWrapper.getLayoutBounds() != null) {
            double wrapperWidth = classNameWrapper.getLayoutBounds().getWidth();
            setLayoutX(classNameWrapper.getLayoutX() + wrapperWidth + 6);
        }
    }
}
