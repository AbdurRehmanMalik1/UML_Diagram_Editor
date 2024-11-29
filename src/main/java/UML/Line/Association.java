package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

public class Association extends Line {

    // Constructor for Association that initializes the line
    public Association(double startX, double startY, double endX, double endY,
                       Pane parentPane, AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel,startObject,endObject);
        this.setStroke(Color.BLACK);  // Set line color
        this.setStrokeWidth(2);       // Set line width
    }

    // Implement the abstract draw method (can be used for custom behaviors if needed)
    @Override
    public void customDraw() {
        this.setStroke(Color.BLACK);
    }

    @Override
    protected void deleteOld() {
    }
}
