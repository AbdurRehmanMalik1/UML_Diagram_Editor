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

/**
 * ClassButtonController handles the functionality of buttons related to adding/removing
 * attributes and methods for a ClassObject in a UML diagram editor.
 * This class also ensures the proper positioning and behavior of the buttons.
 */
public class ClassButtonController extends VBox {
    private ClassObject parentClass;  // The ClassObject that these buttons will interact with
    private final HBox classNameWrapper;  // Wrapper for the class name, used for positioning buttons

    // Buttons for adding/removing attributes and methods
    private Button addAttributeButton;
    private Button addMethodButton;
    private Button removeAttributeButton;
    private Button removeMethodButton;

    /**
     * Constructor for initializing the ClassButtonController.
     * Sets up the layout, initializes the components, and adds events for buttons.
     *
     * @param parentClass The ClassObject this controller will manage.
     * @param classNameWrapper The HBox that contains the class name.
     */
    public ClassButtonController(ClassObject parentClass, HBox classNameWrapper) {
        this.parentClass = parentClass;
        this.classNameWrapper = classNameWrapper;

        initComponents();  // Initialize buttons and add them to the layout

        // Disable focus traversal for these buttons
        addMethodButton.setFocusTraversable(false);
        addAttributeButton.setFocusTraversable(false);
        removeMethodButton.setFocusTraversable(false);
        removeAttributeButton.setFocusTraversable(false);

        // Adjust button position after layout is updated
        Platform.runLater(this::adjustButtonPosition);

        // Listener for layout changes, repositions buttons if necessary
        classNameWrapper.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) ->
                Platform.runLater(this::adjustButtonPosition));

        addButtonEvents();  // Add event handlers for the buttons
    }

    /**
     * Sets the parent ClassObject for the controller.
     *
     * @param parentClass The ClassObject to set.
     */
    public void setParentClass(ClassObject parentClass) {
        this.parentClass = parentClass;
    }

    /**
     * Initializes the components: creates buttons for adding/removing attributes and methods.
     */
    private void initComponents() {
        // Create buttons with icons and tooltips
        addAttributeButton = createButton("Add Attribute", "addAttribute_icon.png");
        removeAttributeButton = createButton("Remove Attribute", "removeAttribute_icon.png");
        addMethodButton = createButton("Add Method", "addMethod_icon.png");
        removeMethodButton = createButton("Remove Method", "removeMethod_icon.png");

        // Add buttons to the layout (VBox)
        getChildren().addAll(addAttributeButton, removeAttributeButton, addMethodButton, removeMethodButton);
    }

    /**
     * Creates a button with a tooltip and an icon.
     *
     * @param tooltipText The text displayed in the tooltip.
     * @param iconFileName The filename of the icon image.
     * @return The created button.
     */
    private Button createButton(String tooltipText, String iconFileName) {
        Button button = new Button();
        // Load and set the icon for the button
        ImageView icon = new ImageView(getClass().getResource("/Images/" + iconFileName).toExternalForm());
        icon.setFitHeight(20);
        icon.setFitWidth(20);
        button.setGraphic(icon);

        // Set button style (no padding, no background insets)
        button.setStyle("-fx-padding: 0; -fx-background-insets: 0;");
        button.setPrefSize(20, 20);

        // Create and install tooltip
        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(button, tooltip);

        // Adjust tooltip delay timings
        tooltip.setShowDelay(javafx.util.Duration.seconds(0.3));
        tooltip.setHideDelay(javafx.util.Duration.seconds(1));

        return button;
    }

    /**
     * Adds event handlers for the buttons to perform actions like adding/removing attributes and methods.
     */
    private void addButtonEvents() {
        // Event for adding an attribute
        addAttributeButton.setOnMouseClicked(event -> {
            // Create a default attribute and add it to the class
            Attribute defaultAttribute = new Attribute("defaultName", "String", "private");
            parentClass.addAttribute(defaultAttribute);

            // Add the attribute to the class model
            ClassModel classModel = (ClassModel) parentClass.getModel();
            classModel.addAttribute(defaultAttribute);
        });

        // Event for removing an attribute
        removeAttributeButton.setOnMouseClicked(event -> {
            StackPane selectedAttribute = parentClass.getSelectedAttribute();
            if (selectedAttribute != null) {
                // Remove selected attribute
                parentClass.removeAttribute(selectedAttribute);
                parentClass.resizeOuterRect();  // Resize the class rectangle after removal
            } else {
                System.out.println("No attribute selected to remove.");
            }
        });

        // Event for adding a method
        addMethodButton.setOnMouseClicked(event -> {
            // Define default values for a new method
            String defaultReturnType = "void";
            String defaultMethodName = "newMethod";
            String defaultParameters = "()";

            // Create a new Method instance and add it to the class
            Method method = new Method(defaultReturnType, defaultMethodName, defaultParameters);
            parentClass.addMethod(method);
        });

        // Event for removing a method
        removeMethodButton.setOnMouseClicked(event -> {
            StackPane selectedMethod = parentClass.getSelectedMethod();
            if (selectedMethod != null) {
                // Remove selected method
                parentClass.removeMethod(selectedMethod);
                parentClass.resizeOuterRect();  // Resize the class rectangle after removal
            } else {
                System.out.println("No method selected to remove.");
            }
        });
    }

    /**
     * Adjusts the position of the buttons relative to the class name wrapper.
     * Ensures buttons are positioned correctly when the layout changes.
     */
    private void adjustButtonPosition() {
        if (classNameWrapper != null && classNameWrapper.getLayoutBounds() != null) {
            // Calculate the new position based on the class name wrapper's width
            double wrapperWidth = classNameWrapper.getLayoutBounds().getWidth();
            setLayoutX(classNameWrapper.getLayoutX() + wrapperWidth + 6);  // Position the buttons accordingly
        }
    }
}
