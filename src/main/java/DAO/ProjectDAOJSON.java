package DAO;

import UML.Project;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ProjectDAOJSON extends ProjectDAO {
    public ProjectDAOJSON(Project project) {
        super(project);
    }

    @Override
    public void saveProject() {
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
}
