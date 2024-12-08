package UML;

import UML.Diagrams.UMLDiagram;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a UML project containing multiple diagrams.
 * This class is responsible for managing the UML diagrams, saving, and loading projects.
 */
public class Project implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UMLDiagram> umlDiagramList = new ArrayList<>(); // Initialize the list of UML diagrams
    private String projectName;
    private String projectFilePath; // File path for saving/loading the project

    /**
     * Default constructor initializing the project with a default name.
     */
    public Project() {
        projectName = "Project";
    }

    /**
     * Constructor to create a project with a specified file path.
     *
     * @param projectFilePath the file path to load/save the project.
     */
    public Project(String projectFilePath) {
        this.projectFilePath = projectFilePath;
    }

    /**
     * Gets the file path of the project.
     *
     * @return the file path.
     */
    public String getProjectFilePath() {
        return projectFilePath;
    }

    /**
     * Sets the file path of the project.
     *
     * @param projectFilePath the file path to set.
     */
    public void setProjectFilePath(String projectFilePath) {
        this.projectFilePath = projectFilePath;
    }

    /**
     * Gets the list of UML diagrams in the project.
     *
     * @return the list of UML diagrams.
     */
    public List<UMLDiagram> getUmlDiagramList() {
        return umlDiagramList;
    }

    /**
     * Sets the list of UML diagrams in the project.
     *
     * @param umlDiagramList the list of UML diagrams to set.
     */
    public void setUmlDiagramList(List<UMLDiagram> umlDiagramList) {
        this.umlDiagramList = umlDiagramList;
    }

    /**
     * Adds a UML diagram to the project.
     *
     * @param umlDiagram the UML diagram to add.
     */
    public void addUmlDiagram(UMLDiagram umlDiagram) {
        umlDiagramList.add(umlDiagram);
    }

    /**
     * Removes a UML diagram from the project.
     *
     * @param umlDiagram the UML diagram to remove.
     */
    public void removeUmlDiagram(UMLDiagram umlDiagram) {
        umlDiagramList.remove(umlDiagram);
    }

    /**
     * Saves the project to the specified file path.
     * If the file path is not set, a warning message is printed.
     */
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

    /**
     * Static method to load a project from a specified file path.
     * If loading fails, a new project is created with the specified file path.
     *
     * @param filePath the file path from which to load the project.
     * @return the loaded project, or a new project if loading fails.
     */
    public static Project loadProject(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Project project = mapper.readValue(new File(filePath), Project.class);
            return project;
        } catch (IOException e) {
            System.out.println("Could not load project: " + e.getMessage());
            return new Project(filePath); // Return a new project if loading fails
        }
    }

    /**
     * Gets the name of the project.
     *
     * @return the project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the name of the project.
     *
     * @param projectName the project name to set.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
