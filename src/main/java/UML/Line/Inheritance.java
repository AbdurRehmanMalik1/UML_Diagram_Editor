package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Inheritance extends Line {

    // Constructor for Inheritance line that initializes the line and the parent pane
    public Inheritance(double startX, double startY, double endX, double endY,
                       Pane parentPane, AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel,startObject,endObject);
        this.setStroke(Color.BLACK);  // Set line color for Inheritance
        this.setStrokeWidth(2);       // Set stroke width for the line
    }

    // Override the customDraw method to draw the inheritance line with an arrowhead
    @Override
    public void customDraw() {
        // Draw the line itself (this is automatically drawn as part of the Line class)
        this.setStartX(getStartX());
        this.setStartY(getStartY());
        this.setEndX(getEndX());
        this.setEndY(getEndY());

        // Draw the arrowhead (triangle) at the end of the inheritance line
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
        Polygon arrowhead = new Polygon();
        for (int i = 0; i < xPoints.length; i++) {
            arrowhead.getPoints().addAll(xPoints[i], yPoints[i]);
        }

        // Set the stroke and fill color for the arrowhead
        arrowhead.setStroke(Color.BLACK);
        arrowhead.setFill(Color.BLACK);  // Optional: fill the arrowhead if desired

        // Add the arrowhead to the parent pane
        parentPane.getChildren().add(arrowhead);
    }

    // Method to add the Inheritance line to the parent pane
    //@Override
    public void addToPane() {
        parentPane.getChildren().add(this);  // Add the Inheritance line to the parent pane
    }
}
