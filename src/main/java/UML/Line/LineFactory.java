package UML.Line;

import Models.AssociationModel;
import UML.ObjectFactories.ObjectFactory;
import UML.Objects.UMLObject;
import javafx.scene.layout.Pane;

/**
 * Factory class responsible for creating different types of UML lines.
 * It uses an `ObjectFactory` to create UML objects associated with each line.
 */
public class LineFactory {
    final ObjectFactory objectFactory;

    /**
     * Constructs a new `LineFactory` with an instance of `ObjectFactory`.
     */
    public LineFactory() {
        objectFactory = new ObjectFactory();
    }

    /**
     * Creates a UML line with associated objects based on the provided `AssociationModel`.
     *
     * @param model  The association model that contains information about the line type and objects.
     * @param canvas The pane where the line will be drawn.
     * @return A new instance of a specific UML line type.
     */
    public UML.Line.Line createLineWithObjects(AssociationModel model, Pane canvas) {
        String type = model.getType();
        double startX = model.getStartX();
        double startY = model.getStartY();
        double endX = model.getEndX();
        double endY = model.getEndY();
        UMLObject startObject = objectFactory.createUMLObject(model.getStartModel());
        UMLObject endObject = objectFactory.createUMLObject(model.getEndModel());
        return this.createLine(type, startX, startY, endX, endY, canvas, model, startObject, endObject);
    }

    /**
     * Creates a UML line based on the provided parameters.
     *
     * @param lineType       The type of UML line (e.g., "Association", "Aggregation", "Composition", etc.).
     * @param startX         The x-coordinate of the start point of the line.
     * @param startY         The y-coordinate of the start point of the line.
     * @param endX           The x-coordinate of the end point of the line.
     * @param endY           The y-coordinate of the end point of the line.
     * @param canvas         The pane where the line will be drawn.
     * @param associationModel The association model containing additional information for the line.
     * @param object1        The starting UML object associated with the line.
     * @param object2        The ending UML object associated with the line.
     * @return A new instance of a specific UML line type.
     */
    public UML.Line.Line createLine(String lineType, double startX, double startY, double endX, double endY, Pane canvas, AssociationModel associationModel, UMLObject object1, UMLObject object2) {
        UML.Line.Line line = null;
        switch (lineType) {
            case "Association":
                line = new Association(startX, startY, endX, endY, canvas, associationModel, object1, object2);
                break;
            case "Aggregation":
                line = new Aggregation(startX, startY, endX, endY, canvas, associationModel, object1, object2);
                break;
            case "Composition":
                line = new Composition(startX, startY, endX, endY, canvas, associationModel, object1, object2);
                break;
            case "Inheritance":
                line = new Inheritance(startX, startY, endX, endY, canvas, associationModel, object1, object2);
                break;
            case "Uses":
                line = new Uses(startX, startY, endX, endY, canvas, associationModel, object1, object2);
                break;
            case "Includes":
                line = new Include(startX, startY, endX, endY, canvas, associationModel, object1, object2);
                break;
            case "Extends":
                line = new Extend(startX, startY, endX, endY, canvas, associationModel, object1, object2);
                break;
            default:
                System.out.println("Invalid line type");
        }
        return line;
    }
}
