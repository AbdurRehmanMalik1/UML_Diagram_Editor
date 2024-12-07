package UML.Diagrams;

import Models.AssociationModel;
import Models.Model;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UseCaseDiagram extends UMLDiagram {

    public UseCaseDiagram() {
        super();
        setName("Use Case Diagram");
    }

    public UseCaseDiagram(List<Model> models, List<AssociationModel> associations) {
        super("Use Case Diagram" ,models, associations);
    }

    // Override saveDiagram if needed (specific to UseCaseDiagram)
    @Override
    public void saveDiagram() {
        super.saveDiagram();  // Calls the UMLDiagram's save logic
    }

    // Override loadDiagram if needed (specific to UseCaseDiagram)
    @Override
    public void loadDiagram() {
        super.loadDiagram();  // Calls the UMLDiagram's load logic
    }
}
