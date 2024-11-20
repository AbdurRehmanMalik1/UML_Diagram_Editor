package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

public class Association extends Line {

    // Constructor for Association that initializes the line
    public Association(double startX, double startY, double endX, double endY,
                       Pane parentPane, AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel,startObject,endObject);
        this.setStroke(Color.BLACK);  // Set line color
        this.setStrokeWidth(2);       // Set line width
    }

    // Implement the abstract draw method (can be used for custom behaviors if needed)
    @Override
    public void customDraw() {
        // For the Association line, we are simply drawing the line from start to end.
        // You could add extra behavior if you need to, such as drawing arrows, labels, etc.

        // The actual drawing of the line is already handled by JavaFX Line class.
        // If you need to do more (e.g., adding arrows, custom markers), you can override this method.

        // Example of a possible custom behavior:
        // Draw the line (super.draw() will invoke the default JavaFX Line drawing)
        this.setStroke(Color.BLACK); // Ensuring the stroke color is black for the association line
    }

    // Add this line to a parent container like a Pane or StackPane
    public void addToPane(Pane parentPane) {
        // Add the line to the parent container, such as Pane or StackPane
        parentPane.getChildren().add(this);
    }
}
