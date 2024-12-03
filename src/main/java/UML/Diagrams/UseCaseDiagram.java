package UML.Diagrams;

import Models.AssociationModel;
import Models.Model;

import java.util.List;

public class UseCaseDiagram extends UMLDiagram {

    public UseCaseDiagram() {
    }

    public UseCaseDiagram(List<Model> models, List<AssociationModel> associations) {
        super(models, associations);
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
