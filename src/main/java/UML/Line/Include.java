package UML.Line;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Line;
import Models.AssociationModel;
import UML.Objects.UMLObject;

public class Include extends UML.Line.Line {

    private Polygon arrowhead;

    public Include(double startX, double startY, double endX, double endY,
                       Pane parentPane, AssociationModel associationModel,
                       UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel,startObject,endObject);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);

        this.getStrokeDashArray().addAll(10d, 5d);

        parentPane.getChildren().removeAll(startMultiplicityField, endMultiplicityField);
        startMultiplicityField = null;
        endMultiplicityField = null;
        associationNameField.setText("<<include>>");
        associationNameField.setEditable(false);
        customDraw();
    }

    @Override
    public void customDraw() {
        if (arrowhead != null) {
            deleteOld();
        }
        drawArrowhead(getEndX(), getEndY(), getStartX(), getStartY());
    }

    // Draw the arrowhead for the Include line
    public void drawArrowhead(double x, double y, double startX, double startY) {
        double angle = Math.atan2(y - startY, x - startX);  // Angle of the line
        double offset = 10; // Offset to place the arrowhead at a distance from the line's end
        double arrowLength = 10; // Length of the arrowhead

        double baseX = x - offset * Math.cos(angle);
        double baseY = y - offset * Math.sin(angle);

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

        arrowhead = new Polygon();
        for (int i = 0; i < xPoints.length; i++) {
            arrowhead.getPoints().addAll(xPoints[i], yPoints[i]);
        }

        arrowhead.setStroke(Color.BLACK);
        arrowhead.setFill(Color.BLACK);  // Optional: fill the arrowhead if desired
        parentPane.getChildren().add(arrowhead);
    }

    @Override
    protected void deleteOld() {
        if (getParent() instanceof Pane parent) {
            parent.getChildren().remove(arrowhead);
        }
    }
}
