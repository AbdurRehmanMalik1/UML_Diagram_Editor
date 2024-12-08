package DAO;

import UML.Project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class is responsible for saving a project as an Object Stream file.
 * It extends the ProjectDAO class and implements the saveProject method
 * for saving the project data in a serialized Object Stream format.
 */
public class ProjectDAOObjectStream extends ProjectDAO {

    /**
     * Constructor that initializes the current project.
     *
     * @param project the project instance to be managed.
     */
    public ProjectDAOObjectStream(Project project) {
        super(project);
    }

    /**
     * This method saves the current project as an Object Stream file.
     * It uses Java's ObjectOutputStream for serializing the project object.
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

        // Create an ObjectOutputStream to serialize the project object
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(projectFilePath))) {
            // Serialize the current project and write it to the file
            oos.writeObject(currentProject);
            System.out.println("Project saved as ObjectStream successfully.");
        } catch (IOException e) {
            // If an error occurs during the saving process, log the error
            System.out.println("Could not save project as ObjectStream: " + e.getMessage());
        }
    }
}
