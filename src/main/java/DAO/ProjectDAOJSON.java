package DAO;

import UML.Project;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * This class is responsible for saving a project to a JSON file.
 * It extends the ProjectDAO class and implements the saveProject method
 * for saving the project data in a JSON format.
 */
public class ProjectDAOJSON extends ProjectDAO {

    /**
     * Constructor that initializes the current project.
     *
     * @param project the project instance to be managed.
     */
    public ProjectDAOJSON(Project project) {
        super(project);
    }

    /**
     * This method saves the current project as a JSON file.
     * It uses Jackson's ObjectMapper to serialize the project data.
     */
    @Override
    public void saveProject() {
        // Retrieve the file path where the project should be saved
        String projectFilePath = currentProject.getProjectFilePath();

        // Check if the file path is set, otherwise log an error and return
        if (projectFilePath == null || projectFilePath.isEmpty()) {
            System.out.println("Project file path is not set.");
            return;
        }

        // Create an ObjectMapper instance to handle JSON serialization
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Serialize the current project to the specified file path
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(projectFilePath), currentProject);
            System.out.println("Project saved as JSON successfully.");
        } catch (IOException e) {
            // If an error occurs during the saving process, log the error
            System.out.println("Could not save project as JSON: " + e.getMessage());
        }
    }
}
