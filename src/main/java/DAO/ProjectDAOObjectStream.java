package DAO;

import UML.Project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ProjectDAOObjectStream extends ProjectDAO {
    public ProjectDAOObjectStream(Project project) {
        super(project);
    }

    @Override
    public void saveProject() {
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
}
