package UML;

import UML.Diagrams.UMLDiagram;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Project implements Serializable {
    private static final String PROJECT_FILE = "./src/Main/resources/storage/project.json";

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UMLDiagram> umlDiagramList = new ArrayList<>(); // Initialize the list

    // Getter and Setter for UML diagrams
    public List<UMLDiagram> getUmlDiagramList() {
        return umlDiagramList;
    }

    public void setUmlDiagramList(List<UMLDiagram> umlDiagramList) {
        this.umlDiagramList = umlDiagramList;
    }

    public void addUmlDiagram(UMLDiagram umlDiagram) {
        umlDiagramList.add(umlDiagram);
    }

    public void removeUmlDiagram(UMLDiagram umlDiagram) {
        umlDiagramList.remove(umlDiagram);
    }

    // Save the project to a file
    public void saveProject() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(PROJECT_FILE), this);
        } catch (IOException e) {
            System.out.println("Could not save project: " + e.getMessage());
        }
    }

    // Load the project from a file
    public static Project loadProject() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(PROJECT_FILE), Project.class);
        } catch (IOException e) {
            System.out.println("Could not load project: " + e.getMessage());
            return new Project(); // Return a new project if loading fails
        }
    }
}
