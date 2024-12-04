package UML;

import UML.Diagrams.UMLDiagram;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Project implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UMLDiagram> umlDiagramList = new ArrayList<>(); // Initialize the list

    private String projectFilePath; // File path for saving/loading the project

    // Constructor
    public Project() {
        // Default constructor
    }

    public Project(String projectFilePath) {
        this.projectFilePath = projectFilePath;
    }

    // Getter and Setter for projectFilePath
    public String getProjectFilePath() {
        return projectFilePath;
    }

    public void setProjectFilePath(String projectFilePath) {
        this.projectFilePath = projectFilePath;
    }

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

    // Save the project to the specified file path
    public void saveProject() {
        if (projectFilePath == null || projectFilePath.isEmpty()) {
            System.out.println("Project file path is not set.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(projectFilePath), this);
        } catch (IOException e) {
            System.out.println("Could not save project: " + e.getMessage());
        }
    }

    // Static method to load a project from a specified file path
    public static Project loadProject(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Project project = mapper.readValue(new File(filePath), Project.class);

//            System.out.println("Deserialize Project size = " + project.getUmlDiagramList());
//            System.out.println("Association list : " + project.getUmlDiagramList().getLast().getAssociationList());
//            System.out.println("Association list : " + project.getUmlDiagramList().getLast().getAssociationList().getFirst().getStartX());

            return  project;
        } catch (IOException e) {
            System.out.println("Could not load project: " + e.getMessage());
            return new Project(filePath); // Return a new project if loading fails
        }
    }
}
