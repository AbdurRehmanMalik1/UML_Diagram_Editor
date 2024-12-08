package UML.Diagrams;

import Models.AssociationModel;
import Models.Model;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Class representing a Use Case Diagram in UML.
 * A Use Case Diagram is used to depict the functional requirements of a system using use cases, actors, and their relationships.
 * This class extends the `UMLDiagram` class and provides specific behaviors for managing Use Case Diagrams.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UseCaseDiagram extends UMLDiagram {

    /**
     * Default constructor for creating an empty Use Case Diagram.
     * Initializes the name of the diagram to "Use Case Diagram".
     */
    public UseCaseDiagram() {
        super();
        setName("Use Case Diagram");
    }

    /**
     * Constructor to create a Use Case Diagram with specified models and associations.
     *
     * @param models       the list of models (use cases, actors) in the diagram
     * @param associations the list of associations (relationships) between the models in the diagram
     */
    public UseCaseDiagram(List<Model> models, List<AssociationModel> associations) {
        super("Use Case Diagram", models, associations);
    }

    /**
     * Override of the `saveDiagram` method to include any specific behavior for saving a Use Case Diagram.
     * Calls the `saveDiagram` method from the superclass (`UMLDiagram`) to handle the basic save logic.
     */
    @Override
    public void saveDiagram() {
        super.saveDiagram();  // Calls the UMLDiagram's save logic
    }

    /**
     * Override of the `loadDiagram` method to include any specific behavior for loading a Use Case Diagram.
     * Calls the `loadDiagram` method from the superclass (`UMLDiagram`) to handle the basic load logic.
     */
    @Override
    public void loadDiagram() {
        super.loadDiagram();  // Calls the UMLDiagram's load logic
    }
}
