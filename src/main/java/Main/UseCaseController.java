package Main;

import UML.Objects.UMLObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;

/**
 * Controller for the Use Case Diagram.
 * This controller handles interactions for adding use case and actor objects,
 * as well as drawing relationships between them (Uses, Includes, Extends).
 */
public class UseCaseController extends DiagramController {

    // Toggle buttons for Use Case and Actor
    @FXML
    private ToggleButton useCaseButton;
    @FXML
    private ToggleButton actorButton;

    /**
     * This method is used to set up the toggle buttons for the Use Case and Actor.
     * It assigns a ToggleGroup to the buttons and sets up a mouse click event to handle drawing objects.
     */
    @Override
    public void setButtonsToggle() {
        buttonToggleGroup = new ToggleGroup();
        useCaseButton.setToggleGroup(buttonToggleGroup);
        actorButton.setToggleGroup(buttonToggleGroup);

        // Set up a mouse click event handler on the canvas
        canvas.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Get the selected button and the drawing function
                ToggleButton button = (ToggleButton) buttonToggleGroup.getSelectedToggle();
                if (button != null && drawObjectFunc != null) {
                    double x = event.getX();
                    double y = event.getY();

                    // Check if the mouse click is within the canvas bounds
                    if (x >= 0 && x <= canvas.getWidth() && y >= 0 && y <= canvas.getHeight()) {
                        drawObjectFunc.accept(x, y);
                    } else {
                        System.out.println("Can't add an object here");
                    }

                    // Reset the drawing function and unselect the button
                    drawObjectFunc = null;
                    button.setSelected(false);
                }
            }
        });
    }

    /**
     * This method is called when the "Add Use Case" button is clicked.
     * It sets the drawing function to create a Use Case object.
     */
    @FXML
    public void onAddUseCaseClick() {
        drawObjectFunc = this::drawUseCase;
    }

    /**
     * This method draws a Use Case object at the specified coordinates on the canvas.
     *
     * @param x the x-coordinate where the object should be placed
     * @param y the y-coordinate where the object should be placed
     */
    public void drawUseCase(double x, double y) {
        UMLObject useCaseObject = objectFactory.createUseCaseObject();
        addToCanvas(useCaseObject, x, y);
    }

    /**
     * This method is called when the "Add Actor" button is clicked.
     * It sets the drawing function to create an Actor object.
     */
    @FXML
    public void onAddActorClick() {
        drawObjectFunc = this::drawActor;
    }

    /**
     * This method draws an Actor object at the specified coordinates on the canvas.
     *
     * @param x the x-coordinate where the object should be placed
     * @param y the y-coordinate where the object should be placed
     */
    public void drawActor(double x, double y) {
        UMLObject actorObject = objectFactory.createActorObject();
        addToCanvas(actorObject, x, y);
    }

    /**
     * This method handles drawing lines between objects on the canvas for different relationship types.
     *
     * @param type the type of relationship to be drawn (e.g., "Uses", "Includes", "Extends")
     */
    public void onDrawClick(String type) {
        unselectToggleButton();  // Deselect any selected toggle button
        handleLineDrawing(type); // Handle the drawing of the specified relationship type
    }

    /**
     * This method is called when the "Draw Uses" button is clicked.
     * It triggers drawing of a "Uses" relationship between objects.
     */
    @FXML
    public void onDrawUsesClick() {
        onDrawClick("Uses");
    }

    /**
     * This method is called when the "Draw Include" button is clicked.
     * It triggers drawing of an "Includes" relationship between objects.
     */
    @FXML
    public void onDrawIncludeClick() {
        onDrawClick("Includes");
    }

    /**
     * This method is called when the "Draw Extends" button is clicked.
     * It triggers drawing of an "Extends" relationship between objects.
     */
    @FXML
    public void onDrawExtendsClick() {
        onDrawClick("Extends");
    }
}
