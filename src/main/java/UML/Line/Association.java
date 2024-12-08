package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

/**
 * Class representing an 'Association' relationship line in a UML diagram.
 * The Association line is typically depicted as a solid line connecting two UML objects.
 * This class extends the `Line` class and provides specific behavior for drawing an Association line.
 */
public class Association extends Line {

    /**
     * Constructor for the Association class.
     *
     * @param startX           the starting x-coordinate of the line
     * @param startY           the starting y-coordinate of the line
     * @param endX             the ending x-coordinate of the line
     * @param endY             the ending y-coordinate of the line
     * @param parentPane       the parent pane where the line and its components are added
     * @param associationModel the association model associated with this line
     * @param startObject      the starting UML object connected by this line
     * @param endObject        the ending UML object connected by this line
     */
    public Association(double startX, double startY, double endX, double endY,
                       Pane parentPane, AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel, startObject, endObject);
        this.setStroke(Color.BLACK);  // Set line color
        this.setStrokeWidth(2);       // Set line width
    }

    /**
     * Implement the abstract draw method from the `Line` class.
     * This can be used for custom behaviors if needed. In this case, it simply sets the stroke color to black.
     */
    @Override
    public void customDraw() {
        this.setStroke(Color.BLACK);  // Ensure the line is black
    }

    /**
     * Override the `deleteOld` method from the `Line` class.
     * This method is currently a no-op for the Association line, as no cleanup is needed.
     */
    @Override
    protected void deleteOld() {
        // No cleanup needed for the Association line
    }
}
