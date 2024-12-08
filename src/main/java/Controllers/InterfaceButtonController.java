package Controllers;

import Models.CD.Method;
import UML.Objects.InterfaceObject;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * InterfaceButtonController is a controller class responsible for managing the interface buttons
 * (Add Method and Remove Method) for an InterfaceObject. It adjusts the position of the buttons
 * based on the class name wrapper and handles user interactions.
 */
public class InterfaceButtonController extends VBox {
    private final InterfaceObject parentClass;  // The InterfaceObject that the buttons are associated with
    private final HBox classNameWrapper;       // Wrapper around the class name for layout purposes

    private Button addMethodButton;            // Button for adding a new method
    private Button removeMethodButton;         // Button for removing an existing method

    /**
     * Constructs an InterfaceButtonController.
     *
     * @param parentClass    the InterfaceObject that this controller is managing
     * @param classNameWrapper the HBox wrapper containing the class name
     */
    public InterfaceButtonController(InterfaceObject parentClass, HBox classNameWrapper) {
        this.parentClass = parentClass;
        this.classNameWrapper = classNameWrapper;

        initComponents();  // Initialize UI components
        addMethodButton.setFocusTraversable(false);
        removeMethodButton.setFocusTraversable(false);
        Platform.runLater(this::adjustButtonPosition);  // Adjust button position on the layout

        // Listen for changes in the layout bounds to adjust button position
        classNameWrapper.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) ->
                Platform.runLater(this::adjustButtonPosition));

        addButtonEvents();  // Set up event listeners for button actions
    }

    /**
     * Initializes UI components including the Add Method and Remove Method buttons.
     */
    private void initComponents() {
        addMethodButton = createButton("Add Method", "addMethod_icon.png");
        removeMethodButton = createButton("Remove Method", "removeMethod_icon.png");
        getChildren().add(addMethodButton);
        getChildren().add(removeMethodButton);
    }

    /**
     * Creates a styled button with an icon and a tooltip.
     *
     * @param tooltipText  the text to be displayed in the tooltip
     * @param iconFileName the file name of the icon to be displayed on the button
     * @return a styled Button instance
     */
    private Button createButton(String tooltipText, String iconFileName) {
        Button button = new Button();
        ImageView icon = new ImageView(getClass().getResource("/Images/" + iconFileName).toExternalForm());
        icon.setFitHeight(20);
        icon.setFitWidth(20);
        button.setGraphic(icon);

        // Style the button
        button.setStyle("-fx-padding: 0; -fx-background-insets: 0;");
        button.setPrefSize(20, 20);

        // Attach a tooltip to the button
        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(button, tooltip);

        // Customize tooltip display delay
        tooltip.setShowDelay(javafx.util.Duration.seconds(0.3));
        tooltip.setHideDelay(javafx.util.Duration.seconds(1));

        return button;
    }

    /**
     * Adds event listeners to the Add Method and Remove Method buttons.
     */
    private void addButtonEvents() {
        // Event listener for adding a new method
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

        // Event listener for removing an existing method
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

    /**
     * Adjusts the position of the buttons based on the layout bounds of the class name wrapper.
     */
    private void adjustButtonPosition() {
        if (classNameWrapper != null && classNameWrapper.getLayoutBounds() != null) {
            double wrapperWidth = classNameWrapper.getLayoutBounds().getWidth();
            setLayoutX(classNameWrapper.getLayoutX() + wrapperWidth + 6);
        }
    }
}
