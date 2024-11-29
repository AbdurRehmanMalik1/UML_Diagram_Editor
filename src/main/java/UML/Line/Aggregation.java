package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.layout.Pane;

public class Aggregation extends Line {

    private Polygon diamond=null;

    // Constructor to initialize line properties
    public Aggregation(double startX, double startY, double endX, double endY,
                       Pane parentPane, AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel, startObject, endObject); // Call the superclass (Line) constructor
        this.setStroke(Color.BLACK);      // Set the line color
        this.setStrokeWidth(3);           // Set the line thickness
        customDraw();                     // Call customDraw to draw the line and the diamond shape
    }

    @Override
    public void customDraw() {
        if(diamond!=null)
            deleteOld();
        drawDiamond(getEndX(), getEndY(), getStartX(), getStartY(), false); // "false" for transparent diamond outline
    }

    // Custom method to draw a diamond shape at the end of the line
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

    // Method to add the Aggregation line to the parent pane (if needed)
    public void addToPane() {
        parentPane.getChildren().add(this);  // Add the Aggregation line to the parent pane
    }

    protected void deleteOld() {
        if (getParent() instanceof Pane parent) {
            parent.getChildren().remove(diamond);
        }
    }
}
