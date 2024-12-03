package UML.Diagrams;

import Models.AssociationModel;
import Models.Model;
import Serializers.DiagramSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.*;
import java.util.List;

public abstract class UMLDiagram {
    @JsonInclude()
    protected List<AssociationModel> associationList;
    @JsonInclude()
    protected List<Model> models;

    public UMLDiagram() {
        // Default constructor
    }

    public UMLDiagram(List<Model> models, List<AssociationModel> associationList) {
        this.models = models;
        this.associationList = associationList;
    }

    public void setAssociationList(List<AssociationModel> associationModelList) {
        this.associationList = associationModelList;
    }

    public void setModelList(List<Model> modelList) {
        this.models = modelList;
    }

    public List<Model> getModels() {
        return models;
    }

    public List<AssociationModel> getAssociationList() {
        return associationList;
    }

    // Method to save the diagram (will be overridden by subclass if needed)
    public void saveDiagram() {
        DiagramSerializer diagramSerializer = new DiagramSerializer();
        diagramSerializer.serialize(this);
    }

    // Method to load the diagram
    public void loadDiagram() {
        associationList.clear();
        models.clear();
        DiagramSerializer diagramSerializer = new DiagramSerializer();
        String content = readDiagramFile();
        if (!content.isEmpty()) {
            UMLDiagram deserializedDiagram = diagramSerializer.deserialize(content, this.getClass());
            if (deserializedDiagram instanceof UMLDiagram) {
                UMLDiagram diagram = (UMLDiagram) deserializedDiagram;
                this.setAssociationList(diagram.getAssociationList());
                this.setModelList(diagram.getModels());
            }
        }
    }

    public static String readDiagramFile() {
        // Path to the diagram JSON file
        String filePath = "./src/Main/resources/storage/diagram.json";
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Could not read JSON file");
        }

        return content.toString();
    }
}
