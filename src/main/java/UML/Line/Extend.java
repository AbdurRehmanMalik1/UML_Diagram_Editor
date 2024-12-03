package UML.Line;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import Models.AssociationModel;
import UML.Objects.UMLObject;

public class Extend extends UML.Line.Line {

    private Polygon arrowhead;

    public Extend(double startX, double startY, double endX, double endY,
                  Pane parentPane, AssociationModel associationModel,
                  UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel, startObject, endObject);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);

        // Correct way to set dashed line pattern
        this.getStrokeDashArray().addAll(10d, 5d); // Dash pattern: 10 pixels on, 5 pixels off

        parentPane.getChildren().removeAll(startMultiplicityField, endMultiplicityField);
        startMultiplicityField = null;
        endMultiplicityField = null;
        associationNameField.setText("<<extend>>");
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

    public void drawArrowhead(double x, double y, double startX, double startY) {
        double angle = Math.atan2(y - startY, x - startX);
        double offset = 10;
        double arrowLength = 10;

        double baseX = x - offset * Math.cos(angle);
        double baseY = y - offset * Math.sin(angle);

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

        arrowhead = new Polygon();
        for (int i = 0; i < xPoints.length; i++) {
            arrowhead.getPoints().addAll(xPoints[i], yPoints[i]);
        }

        arrowhead.setStroke(Color.BLACK);
        arrowhead.setFill(Color.BLACK);
        parentPane.getChildren().add(arrowhead);
    }

    @Override
    protected void deleteOld() {
        if (getParent() instanceof Pane parent) {
            parent.getChildren().remove(arrowhead);
        }
    }
}
