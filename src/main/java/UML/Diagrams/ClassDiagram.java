package UML.Diagrams;

import Models.AssociationModel;
import Models.ClassModel;
import Models.Model;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Class representing a Class Diagram in UML.
 * A Class Diagram is used to represent the static structure of a system by showing classes, their attributes, methods, and the relationships between them.
 * This class extends the `UMLDiagram` class and provides specific behaviors for managing Class Diagrams.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClassDiagram extends UMLDiagram {

    /**
     * Default constructor for creating an empty Class Diagram.
     * Initializes the name of the diagram to "Class Diagram".
     */
    public ClassDiagram() {
        super();
        setName("Class Diagram");
    }

    /**
     * Constructor to create a Class Diagram with specified models and associations.
     *
     * @param models       the list of models (classes) in the diagram
     * @param associations the list of associations (relationships) between the models in the diagram
     */
    public ClassDiagram(List<Model> models, List<AssociationModel> associations) {
        super("Class Diagram", models, associations);
    }

    /**
     * Override of the `saveDiagram` method to include any specific behavior for saving a Class Diagram.
     * Calls the `saveDiagram` method from the superclass (`UMLDiagram`) to handle the basic save logic.
     */
    @Override
    public void saveDiagram() {
        super.saveDiagram();  // Calls the UMLDiagram's save logic
    }

    /**
     * Override of the `loadDiagram` method to include any specific behavior for loading a Class Diagram.
     * Calls the `loadDiagram` method from the superclass (`UMLDiagram`) to handle the basic load logic.
     */
    @Override
    public void loadDiagram() {
        super.loadDiagram();  // Calls the UMLDiagram's load logic
    }
}
