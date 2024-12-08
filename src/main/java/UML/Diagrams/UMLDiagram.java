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

/**
 * Abstract class representing a UML Diagram.
 * This class serves as a blueprint for different types of UML Diagrams such as Class Diagrams and Use Case Diagrams.
 * It holds the basic structure including models, associations, and an ID for identification.
 */
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

    /**
     * Default constructor that initializes the UML Diagram with a unique ID.
     * The ID is automatically assigned a value, incremented sequentially.
     */
    public UMLDiagram() {
        this.id = ++idCounter; // Automatically assign a unique ID
    }

    /**
     * Constructor to initialize a UML Diagram with a name, models, and associations.
     *
     * @param name             the name of the diagram
     * @param models           the list of models (such as classes or use cases) in the diagram
     * @param associationList  the list of associations (relationships) between the models
     */
    public UMLDiagram(String name, List<Model> models, List<AssociationModel> associationList) {
        this.id = ++idCounter;
        this.name = name;
        this.models = models;
        this.associationList = associationList;
    }

    // Getters and setters
    /**
     * Gets the unique ID of the diagram.
     *
     * @return the unique ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the diagram.
     *
     * @return the name of the diagram
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the diagram.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of models in the diagram.
     *
     * @return the list of models
     */
    public List<Model> getModels() {
        return models;
    }

    /**
     * Sets the list of models in the diagram.
     *
     * @param modelList the list of models to set
     */
    public void setModelList(List<Model> modelList) {
        this.models = modelList;
    }

    /**
     * Gets the list of associations in the diagram.
     *
     * @return the list of associations
     */
    public List<AssociationModel> getAssociationList() {
        return associationList;
    }

    /**
     * Sets the list of associations in the diagram.
     *
     * @param associationModelList the list of associations to set
     */
    public void setAssociationList(List<AssociationModel> associationModelList) {
        this.associationList = associationModelList;
    }

    /**
     * Saves the diagram to a JSON file.
     * Utilizes the `DiagramSerializer` class to handle serialization.
     */
    public void saveDiagram() {
        DiagramSerializer diagramSerializer = new DiagramSerializer();
        diagramSerializer.serialize(this);
    }

    /**
     * Loads the diagram from a JSON file.
     * Clears the existing models and associations and loads them from the JSON file.
     * Utilizes the `DiagramSerializer` class to handle deserialization.
     */
    public void loadDiagram() {
        associationList.clear();
        models.clear();
        DiagramSerializer diagramSerializer = new DiagramSerializer();
        String content = readDiagramFile();
        if (!content.isEmpty()) {
            UMLDiagram deserializedDiagram = diagramSerializer.deserialize(content, this.getClass());
            if (deserializedDiagram != null) {
                this.setAssociationList(deserializedDiagram.getAssociationList());
                this.setModelList(deserializedDiagram.getModels());
                this.setName(deserializedDiagram.getName());
            }
        }
    }

    /**
     * Reads the content of the diagram file.
     * The file is expected to be in the path `./src/Main/resources/storage/diagram.json`.
     *
     * @return the content of the file as a String
     */
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
