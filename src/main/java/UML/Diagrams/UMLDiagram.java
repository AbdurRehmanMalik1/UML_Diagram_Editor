package UML.Diagrams;

import Models.AssociationModel;
import Models.Model;
import Serializers.DiagramSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,  // Use type name for identification
        property = "type"  // The property name that holds the type info
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassDiagram.class, name = "ClassDiagram"),
        @JsonSubTypes.Type(value = UseCaseDiagram.class, name = "UseCaseDiagram")
})
public abstract class UMLDiagram implements Serializable {
    // Static variable for generating unique IDs
    private static int idCounter = 0;

    // Primary key for the diagram
    private final int id;

    // Name of the diagram
    private String name;

    @JsonInclude()
    protected List<AssociationModel> associationList = new ArrayList<>();
    @JsonInclude()
    protected List<Model> models = new ArrayList<>();

    public UMLDiagram() {
        this.id = ++idCounter; // Automatically assign a unique ID
    }

    public UMLDiagram(String name, List<Model> models, List<AssociationModel> associationList) {
        this.id = ++idCounter;
        this.name = name;
        this.models = models;
        this.associationList = associationList;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModelList(List<Model> modelList) {
        this.models = modelList;
    }

    public List<AssociationModel> getAssociationList() {
        return associationList;
    }

    public void setAssociationList(List<AssociationModel> associationModelList) {
        this.associationList = associationModelList;
    }

    // Save diagram
    public void saveDiagram() {
        DiagramSerializer diagramSerializer = new DiagramSerializer();
        diagramSerializer.serialize(this);
    }

    // Load diagram
    public void loadDiagram() {
        associationList.clear();
        models.clear();
        DiagramSerializer diagramSerializer = new DiagramSerializer();
        String content = readDiagramFile();
        if (!content.isEmpty()) {
            UMLDiagram deserializedDiagram = diagramSerializer.deserialize(content, this.getClass());
            if (deserializedDiagram instanceof UMLDiagram) {
                UMLDiagram diagram = deserializedDiagram;
                this.setAssociationList(diagram.getAssociationList());
                this.setModelList(diagram.getModels());
                this.setName(diagram.getName());
            }
        }
    }

    // Read diagram file
    public static String readDiagramFile() {
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
