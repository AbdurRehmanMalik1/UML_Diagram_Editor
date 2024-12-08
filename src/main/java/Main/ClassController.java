package Main;

import UML.Objects.UMLObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;

/**
 * Controller class for managing user interactions in the UML class diagram view.
 * Handles the creation of class and interface diagrams, as well as associations and relationships.
 */
public class ClassController extends DiagramController {

    // Toggle buttons for selecting class and interface diagram types
    @FXML
    protected ToggleButton classButton;

    @FXML
    protected ToggleButton interfaceButton;

    /**
     * Sets up the toggle buttons for selecting diagram types (Class or Interface).
     * The user can only select one diagram type at a time.
     * Also handles mouse click events on the canvas to draw objects.
     */
    public void setButtonsToggle(){
        buttonToggleGroup = new ToggleGroup();
        classButton.setToggleGroup(buttonToggleGroup);
        interfaceButton.setToggleGroup(buttonToggleGroup);

        // Register mouse click event on the canvas to create the selected object
        canvas.setOnMouseClicked(event -> {
            // Only proceed if the left mouse button is clicked
            if (event.getButton() == MouseButton.PRIMARY) {
                // Get the selected toggle button (either class or interface)
                ToggleButton button =(ToggleButton) buttonToggleGroup.getSelectedToggle();

                // Check if a valid button is selected and if there is a drawing function assigned
                if (button != null && drawObjectFunc != null) {
                    double x = event.getX();  // Get the X coordinate of the mouse click
                    double y = event.getY();  // Get the Y coordinate of the mouse click

                    // Ensure the click is within the canvas boundaries
                    if (x >= 0 && x <= canvas.getWidth() && y >= 0 && y <= canvas.getHeight()) {
                        // Call the drawing function with the coordinates
                        drawObjectFunc.accept(x, y);
                    } else {
                        System.out.println("Can't add an object here");  // Log if click is outside the canvas
                    }

                    // Reset the drawing function after use
                    drawObjectFunc = null;
                    button.setSelected(false);  // Deselect the toggle button after use
                }
            }
        });
    }

    /**
     * Triggered when the user clicks on the 'Add Class Diagram' button.
     * Sets the drawing function to create class diagrams.
     */
    @FXML
    public void onAddClassDiagramClick() {
        // Assign the drawClass method to drawObjectFunc
        drawObjectFunc = this::drawClass;
    }

    /**
     * Creates a new Class diagram object at the specified coordinates and adds it to the canvas.
     *
     * @param x The X coordinate where the object should be placed.
     * @param y The Y coordinate where the object should be placed.
     */
    public void drawClass(double x, double y){
        // Create a new UML Class object
        UMLObject classDiagram = objectFactory.createClassObject();
        // Add the class diagram object to the canvas at the specified coordinates
        addToCanvas(classDiagram, x, y);
    }

    /**
     * Triggered when the user clicks on the 'Add Interface Diagram' button.
     * Sets the drawing function to create interface diagrams.
     */
    @FXML
    public void onAddInterfaceDiagramClick() {
        // Assign the drawInterface method to drawObjectFunc
        drawObjectFunc = this::drawInterface;
    }

    /**
     * Creates a new Interface diagram object at the specified coordinates and adds it to the canvas.
     *
     * @param x The X coordinate where the object should be placed.
     * @param y The Y coordinate where the object should be placed.
     */
    public void drawInterface(double x, double y){
        // Create a new UML Interface object
        UMLObject interfaceDiagram = objectFactory.createInterfaceObject();
        // Add the interface diagram object to the canvas at the specified coordinates
        addToCanvas(interfaceDiagram, x, y);
    }

    /**
     * Triggered when the user clicks on the 'Draw Association' button.
     * Initiates the creation of an association between UML objects.
     */
    @FXML
    public void onDrawAssociationClick() {
        // Call the onDrawClick method with "Association" as the type
        onDrawClick("Association");
    }

    /**
     * Triggered when the user clicks on the 'Draw Inheritance' button.
     * Initiates the creation of an inheritance relationship between UML objects.
     */
    @FXML
    public void onDrawInheritanceClick() {
        // Call the onDrawClick method with "Inheritance" as the type
        onDrawClick("Inheritance");
    }

    /**
     * Triggered when the user clicks on the 'Draw Aggregation' button.
     * Initiates the creation of an aggregation relationship between UML objects.
     */
    @FXML
    public void onDrawAggregationClick() {
        // Call the onDrawClick method with "Aggregation" as the type
        onDrawClick("Aggregation");
    }

    /**
     * Triggered when the user clicks on the 'Draw Composition' button.
     * Initiates the creation of a composition relationship between UML objects.
     */
    @FXML
    public void onDrawCompositionClick() {
        // Call the onDrawClick method with "Composition" as the type
        onDrawClick("Composition");
    }
}
