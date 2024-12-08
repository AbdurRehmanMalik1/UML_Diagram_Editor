package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.layout.Pane;

/**
 * Class representing an 'Aggregation' relationship line in a UML diagram.
 * Aggregation is a type of association that indicates a strong ownership relationship.
 * This class extends the `Line` class and provides specific behavior for drawing an Aggregation line with a diamond shape.
 */
public class Aggregation extends Line {

    private Polygon diamond; // Polygon to represent the diamond shape at the end of the line

    /**
     * Constructor to initialize the Aggregation line with its properties.
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
    public Aggregation(double startX, double startY, double endX, double endY,
                       Pane parentPane, AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel, startObject, endObject); // Call the superclass (Line) constructor
        this.setStroke(Color.BLACK);      // Set the line color
        this.setStrokeWidth(3);           // Set the line thickness
        customDraw();                     // Call customDraw to draw the line and the diamond shape
    }

    /**
     * Custom draw method to render the Aggregation line and diamond shape.
     * If a diamond already exists, it will be removed before drawing a new one.
     */
    @Override
    public void customDraw() {
        if (diamond != null) {
            deleteOld();
        }
        drawDiamond(getEndX(), getEndY(), getStartX(), getStartY(), false); // Draw the diamond without filling it
    }

    /**
     * Helper method to draw a diamond shape at the end of the Aggregation line.
     *
     * @param x         the x-coordinate of the line's end
     * @param y         the y-coordinate of the line's end
     * @param startX    the x-coordinate of the line's start
     * @param startY    the y-coordinate of the line's start
     * @param filled    whether the diamond should be filled or just outlined
     */
    public void drawDiamond(double x, double y, double startX, double startY, boolean filled) {
        double angle = Math.atan2(y - startY, x - startX);
        double offset = 20; // Offset to place diamond after the line's end
        double diamondSize = 15; // Size of the diamond

        // Adjusted position for the diamond shape based on the angle
        double baseX = x + offset * Math.cos(angle);
        double baseY = y + offset * Math.sin(angle);

        // Coordinates of the diamond
        double[] xPoints = {
                baseX,
                baseX - diamondSize * Math.cos(angle - Math.PI / 4),
                baseX - 2 * diamondSize * Math.cos(angle),
                baseX - diamondSize * Math.cos(angle + Math.PI / 4)
        };
        double[] yPoints = {
                baseY,
                baseY - diamondSize * Math.sin(angle - Math.PI / 4),
                baseY - 2 * diamondSize * Math.sin(angle),
                baseY - diamondSize * Math.sin(angle + Math.PI / 4)
        };

        diamond = new Polygon(xPoints[0], yPoints[0], xPoints[1], yPoints[1],
                xPoints[2], yPoints[2], xPoints[3], yPoints[3]);

        // Set the appearance of the diamond
        if (filled) {
            diamond.setFill(Color.BLACK);  // Fill the diamond shape
            diamond.setStroke(Color.BLACK);  // Ensure stroke color matches fill color
        } else {
            diamond.setFill(Color.TRANSPARENT);  // Make the interior transparent (not filled)
            diamond.setStroke(Color.BLACK);  // Draw the diamond outline
        }

        // Add the diamond to the parent container (Pane or StackPane)
        parentPane.getChildren().add(diamond);
    }

    /**
     * Method to remove the old diamond shape from the parent container.
     */
    protected void deleteOld() {
        if (getParent() instanceof Pane parent) {
            parent.getChildren().remove(diamond);
        }
    }
}
