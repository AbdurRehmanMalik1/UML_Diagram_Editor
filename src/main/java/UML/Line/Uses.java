package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.scene.layout.Pane;

public class Uses extends Association{
    public Uses(double startX, double startY, double endX, double endY, Pane parentPane, AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY, parentPane, associationModel, startObject, endObject);

        parentPane.getChildren().removeAll(startMultiplicityField,endMultiplicityField,associationNameField);
        startMultiplicityField = null;
        endMultiplicityField = null;
        associationNameField = null;
    }
}
