package UML.Line;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import Models.AssociationModel;
import UML.Objects.UMLObject;

/**
 * Class representing an 'Extend' line in a UML diagram.
 * This class extends the `Line` class to provide specific behavior for the 'Extend' relationship.
 * It includes methods for drawing the extend line and its arrowhead.
 */
public class Extend extends UML.Line.Line {

    private Polygon arrowhead;

    /**
     * Constructor for the Extend class.
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
    public Extend(double startX, double startY, double endX, double endY,
                  Pane parentPane, AssociationModel associationModel,
                  UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel, startObject, endObject);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);

        // Correct way to set dashed line pattern
        this.getStrokeDashArray().addAll(10d, 5d); // Dash pattern: 10 pixels on, 5 pixels off

        // Remove multiplicity fields and set the association name
        parentPane.getChildren().removeAll(startMultiplicityField, endMultiplicityField);
        startMultiplicityField = null;
        endMultiplicityField = null;
        associationNameField.setText("<<extend>>");
        associationNameField.setEditable(false);

        // Draw the custom extend line
        customDraw();
    }

    /**
     * Custom drawing method for the extend line and its arrowhead.
     */
    @Override
    public void customDraw() {
        if (arrowhead != null) {
            deleteOld();
        }
        drawArrowhead(getEndX(), getEndY(), getStartX(), getStartY());
    }

    /**
     * Draw the arrowhead for the 'Extend' line.
     *
     * @param x       the x-coordinate of the arrowhead tip
     * @param y       the y-coordinate of the arrowhead tip
     * @param startX  the x-coordinate of the line start
     * @param startY  the y-coordinate of the line start
     */
    public void drawArrowhead(double x, double y, double startX, double startY) {
        double angle = Math.atan2(y - startY, x - startX); // Angle of the line
        double offset = 10; // Offset to place the arrowhead at a distance from the line's end
        double arrowLength = 10; // Length of the arrowhead

        // Adjusted position for the arrowhead (triangle)
        double baseX = x - offset * Math.cos(angle);  // Adjust the base position
        double baseY = y - offset * Math.sin(angle);

        // Points for the arrowhead triangle
        double[] xPoints = {
                baseX,
                baseX - arrowLength * Math.cos(angle - Math.PI / 8),
                baseX - arrowLength * Math.cos(angle + Math.PI / 8)
        };
        double[] yPoints = {
                baseY,
                baseY - arrowLength * Math.sin(angle - Math.PI / 8),
                baseY - arrowLength * Math.sin(angle + Math.PI / 8)
        };

        // Create the Polygon (arrowhead) with the calculated points
        arrowhead = new Polygon();
        for (int i = 0; i < xPoints.length; i++) {
            arrowhead.getPoints().addAll(xPoints[i], yPoints[i]);
        }

        // Set the stroke and fill color for the arrowhead
        arrowhead.setStroke(Color.BLACK);
        arrowhead.setFill(Color.BLACK);

        // Add the arrowhead to the parent pane
        parentPane.getChildren().add(arrowhead);
    }

    /**
     * Deletes the old arrowhead from the parent pane.
     */
    @Override
    protected void deleteOld() {
        if (getParent() instanceof Pane parent) {
            parent.getChildren().remove(arrowhead);
        }
    }
}
