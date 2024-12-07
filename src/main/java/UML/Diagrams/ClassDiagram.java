package UML.Diagrams;

import Models.AssociationModel;
import Models.ClassModel;
import Models.Model;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClassDiagram extends UMLDiagram {

    public ClassDiagram() {
        super();
        setName("Class Diagram");
    }

    public ClassDiagram(List<Model> models, List<AssociationModel> associations) {
        super("Class Diagram",models, associations);
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
