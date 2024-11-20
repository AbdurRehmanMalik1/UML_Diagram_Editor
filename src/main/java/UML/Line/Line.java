package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.scene.layout.Pane;

public abstract class Line extends javafx.scene.shape.Line {
    public Pane parentPane;

    // AssociationModel to represent the connection between objects
    private AssociationModel associationModel;

    // UMLObject representing the start and end points of the line
    private UMLObject startObject;
    private UMLObject endObject;

    public Line(double startX, double startY, double endX, double endY, Pane parentPane,
                AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY);
        this.parentPane = parentPane;
        this.associationModel = associationModel;
        this.startObject = startObject;
        this.endObject = endObject;

        customDraw();  // Call to abstract method for custom drawing logic
    }

    // Abstract method for custom drawing logic specific to each subclass
    public abstract void customDraw();

    // Getter for the AssociationModel
    public AssociationModel getAssociationModel() {
        return associationModel;
    }

    // Setter for the AssociationModel
    public void setAssociationModel(AssociationModel associationModel) {
        this.associationModel = associationModel;
    }

    // Getter for the start object
    public UMLObject getStartObject() {
        return startObject;
    }

    // Setter for the start object
    public void setStartObject(UMLObject startObject) {
        this.startObject = startObject;
    }

    // Getter for the end object
    public UMLObject getEndObject() {
        return endObject;
    }

    // Setter for the end object
    public void setEndObject(UMLObject endObject) {
        this.endObject = endObject;
    }
}
