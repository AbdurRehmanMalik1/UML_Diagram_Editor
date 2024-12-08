package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Class representing a 'Composition' relationship line in a UML diagram.
 * The Composition line is depicted using a solid line and a diamond at the end to indicate a strong ownership relationship.
 * This class extends the `Line` class and provides specific behavior for drawing a Composition line.
 */
public class Composition extends Line {

    private Polygon diamond = null;

    /**
     * Constructor for the Composition class.
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
    public Composition(double startX, double startY, double endX, double endY,
                       Pane parentPane, AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel, startObject, endObject);
        this.setStroke(Color.BLACK);  // Set line color for Composition
        this.setStrokeWidth(2);       // Set stroke width for the line
        customDraw();
    }

    /**
     * Custom drawing method for the Composition line.
     * Draws a solid line and a diamond shape at the end to represent the Composition relationship.
     */
    @Override
    public void customDraw() {
        if (diamond != null) {
            deleteOld();
        }
        drawDiamond(getEndX(), getEndY(), getStartX(), getStartY(), true);  // 'true' for filled diamond
    }

    /**
     * Helper method to draw a diamond shape at the end of the Composition line.
     *
     * @param x          the x-coordinate of the diamond's tip
     * @param y          the y-coordinate of the diamond's tip
     * @param startX     the x-coordinate of the line start
     * @param startY     the y-coordinate of the line start
     * @param filled     if true, the diamond will be filled with color; otherwise, it will be a transparent outline
     */
    public void drawDiamond(double x, double y, double startX, double startY, boolean filled) {
        double angle = Math.atan2(y - startY, x - startX); // Angle of the line
        double offset = 15;  // Offset to place the diamond after the line's end
        double diamondSize = 10;

        // Adjusted diamond position
        double baseX = x + offset * Math.cos(angle);
        double baseY = y + offset * Math.sin(angle);

        // Points for the diamond polygon
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

        // Create a polygon for the diamond
        diamond = new Polygon();
        for (int i = 0; i < xPoints.length; i++) {
            diamond.getPoints().addAll(xPoints[i], yPoints[i]);
        }

        // Fill or stroke the diamond based on the 'filled' flag
        if (filled) {
            diamond.setFill(Color.BLACK);  // Fill the diamond with black
        } else {
            diamond.setStroke(Color.BLACK);  // Stroke the diamond outline with black
            diamond.setFill(Color.TRANSPARENT);  // Make it transparent inside
        }
        parentPane.getChildren().add(diamond);
    }

    /**
     * Deletes the old diamond shape from the parent pane.
     */
    protected void deleteOld() {
        if (getParent() instanceof Pane parent) {
            parent.getChildren().remove(diamond);
        }
    }

}
