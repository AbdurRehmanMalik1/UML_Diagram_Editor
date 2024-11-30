package UML.Line;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import Models.AssociationModel;
import UML.Objects.UMLObject;

public class Inheritance extends Line {

    private Polygon arrowhead =null;

    // Constructor for Inheritance line that initializes the line and the parent pane
    public Inheritance(double startX, double startY, double endX, double endY,
                       Pane parentPane, AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel, startObject, endObject);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(2);
        parentPane.getChildren().removeAll(startMultiplicityField,endMultiplicityField);
        startMultiplicityField = null;
        endMultiplicityField = null;
        customDraw();
    }

    @Override
    public void customDraw() {
        if(arrowhead!=null)
            deleteOld();
        drawArrowhead(getEndX(), getEndY(), getStartX(), getStartY());
    }

    // Helper method to draw the triangle (arrowhead) using JavaFX Polygon
    public void drawArrowhead(double x, double y, double startX, double startY) {
        double angle = Math.atan2(y - startY, x - startX);  // Angle of the line
        double offset = 10; // Offset to place the arrowhead at a distance from the line's end
        double arrowLength = 15; // Length of the arrowhead

        // Adjusted position for the arrowhead (triangle)
        double baseX = x - offset * Math.cos(angle);  // Adjust the base position
        double baseY = y - offset * Math.sin(angle);

        // Points for the arrowhead triangle
        double[] xPoints = {
                baseX,
                baseX - arrowLength * Math.cos(angle - Math.PI / 6),
                baseX - arrowLength * Math.cos(angle + Math.PI / 6)
        };
        double[] yPoints = {
                baseY,
                baseY - arrowLength * Math.sin(angle - Math.PI / 6),
                baseY - arrowLength * Math.sin(angle + Math.PI / 6)
        };

        // Create the Polygon (arrowhead) with the calculated points
        arrowhead = new Polygon();
        for (int i = 0; i < xPoints.length; i++) {
            arrowhead.getPoints().addAll(xPoints[i], yPoints[i]);
        }

        // Set the stroke and fill color for the arrowhead
        arrowhead.setStroke(Color.BLACK);
        arrowhead.setFill(Color.BLACK);  // Optional: fill the arrowhead if desired

        // Add the arrowhead to the parent pane
        parentPane.getChildren().add(arrowhead);
    }



    protected void deleteOld() {
        if (getParent() instanceof Pane parent) {
            parent.getChildren().remove(arrowhead);
        }
    }
}
