package DAO;

import UML.Project;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

/**
 * This class is responsible for saving a project as an XML file.
 * It extends the ProjectDAO class and implements the saveProject method
 * for saving the project data in XML format using the Jackson XML mapper.
 */
public class ProjectDAOXML extends ProjectDAO {

    // Instance variable to hold the current project to be saved
    private Project currentProject;

    /**
     * Constructor that initializes the current project.
     *
     * @param project the project instance to be managed.
     */
    public ProjectDAOXML(Project project) {
        super(project); // Pass the project to the superclass constructor
    }

    /**
     * This method saves the current project as an XML file.
     * It uses Jackson's XmlMapper to convert the project object to XML.
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

        // Create an XmlMapper instance for XML serialization
        XmlMapper xmlMapper = new XmlMapper();

        try {
            // Write the current project to the specified file in XML format
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(projectFilePath), currentProject);
            System.out.println("Project saved as XML successfully.");
        } catch (IOException e) {
            // If an error occurs during the saving process, log the error
            System.out.println("Could not save project as XML: " + e.getMessage());
        }
    }
}
