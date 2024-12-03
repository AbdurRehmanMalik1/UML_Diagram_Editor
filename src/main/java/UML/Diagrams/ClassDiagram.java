package UML.Diagrams;

import Models.AssociationModel;
import Models.ClassModel;
import Models.Model;

import java.util.List;

public class ClassDiagram extends UMLDiagram {

    public ClassDiagram() {
    }

    public ClassDiagram(List<Model> models, List<AssociationModel> associations) {
        super(models, associations);
    }

    // Override saveDiagram if needed (specific to ClassDiagram)
    @Override
    public void saveDiagram() {
        super.saveDiagram();  // Calls the UMLDiagram's save logic
    }

    // Override loadDiagram if needed (specific to ClassDiagram)
    @Override
    public void loadDiagram() {
        super.loadDiagram();  // Calls the UMLDiagram's load logic
    }
}
