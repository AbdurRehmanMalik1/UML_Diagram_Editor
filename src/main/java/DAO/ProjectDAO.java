package DAO;
import UML.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class ProjectDAO {
    protected  Project currentProject;

    // Constructor to initialize the DAO with a project
    public ProjectDAO(Project project) {
        this.currentProject = project;
    }

    /**
     * Saves the current project.
     *
     *
     */
    public void saveProject() {
        if (currentProject != null) {
            currentProject.saveProject();
        } else {
            System.err.println("No project is available to save.");
        }
    }

    /**
     * Initializes the project in the DAO.
     *
     * @param project the new project to initialize
     */
    public void initialize(Project project) {
        this.currentProject = project;
    }

    /**
     * Retrieves the current project.
     *
     * @return the current project
     */
    public Project getCurrentProject() {
        return currentProject;
    }

    public void saveProjectAsJSON() {
        String projectFilePath = currentProject.getProjectFilePath();
        if (projectFilePath == null || projectFilePath.isEmpty()) {
            System.out.println("Project file path is not set.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(projectFilePath), currentProject);
            System.out.println("Project saved as JSON successfully.");
        } catch (IOException e) {
            System.out.println("Could not save project as JSON: " + e.getMessage());
        }
    }

    // Method to save project using ObjectStream (serialization)
    public void saveProjectAsObjectStream() {
        String projectFilePath = currentProject.getProjectFilePath();
        if (projectFilePath == null || projectFilePath.isEmpty()) {
            System.out.println("Project file path is not set.");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(projectFilePath))) {
            oos.writeObject(currentProject);
            System.out.println("Project saved as ObjectStream successfully.");
        } catch (IOException e) {
            System.out.println("Could not save project as ObjectStream: " + e.getMessage());
        }
    }

    public void saveProjectAsXML() {
        String projectFilePath = currentProject.getProjectFilePath();
        if (projectFilePath == null || projectFilePath.isEmpty()) {
            System.out.println("Project file path is not set.");
            return;
        }

        XmlMapper xmlMapper = new XmlMapper();  // Jackson XML mapper
        try {
            // Serialize the project object to XML and save it to the file
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(projectFilePath), currentProject);
            System.out.println("Project saved as XML successfully.");
        } catch (IOException e) {
            System.out.println("Could not save project as XML: " + e.getMessage());
        }
    }
}