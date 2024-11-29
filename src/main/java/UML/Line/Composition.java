package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Composition extends Line {

    Polygon diamond=null;
    // Constructor for Composition line that initializes the line and the parent pane
    public Composition(double startX, double startY, double endX, double endY,
                       Pane parentPane, AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel, startObject, endObject);
        this.setStroke(Color.BLACK);  // Set line color for Composition
        this.setStrokeWidth(2);       // Set stroke width for the line
        customDraw();  // Call customDraw to draw the diamond
    }

    // Override the customDraw method to draw the composition line with a filled diamond
    @Override
    public void customDraw() {
        if(diamond!=null)
            deleteOld();
        drawDiamond(getEndX(), getEndY(), getStartX(), getStartY(), true);  // 'true' for filled diamond
    }

    // Helper method to draw the diamond shape using JavaFX Polygon
    public void drawDiamond(double x, double y, double startX, double startY, boolean filled) {
        double angle = Math.atan2(y - startY, x - startX);
        double offset = 15;  // Offset to place the diamond after the line's end
        double diamondSize = 10;

        // Adjusted diamond position
        double baseX = x + offset * Math.cos(angle);
        double baseY = y + offset * Math.sin(angle);

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

        if (filled) {
            diamond.setFill(Color.BLACK);  // Fill the diamond with black
        } else {
            diamond.setStroke(Color.BLACK);  // Stroke the diamond outline with black
            diamond.setFill(Color.TRANSPARENT);  // Make it transparent inside
        }
        parentPane.getChildren().add(diamond);
        System.out.println("added diamond");
    }

    // Method to add the Composition line to the parent pane (if needed)
    public void addToPane() {
        parentPane.getChildren().add(this);  // Add the Composition line to the parent pane
    }

    protected void deleteOld() {
        if (getParent() instanceof Pane parent) {
            parent.getChildren().remove(diamond);
        }
    }

}
