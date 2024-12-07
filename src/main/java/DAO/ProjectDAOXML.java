package DAO;

import UML.Project;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class ProjectDAOXML extends ProjectDAO {

    private Project currentProject;

    public ProjectDAOXML(Project project) {
        super(project);
    }

    @Override
    public void saveProject() {
        String projectFilePath = currentProject.getProjectFilePath();
        if (projectFilePath == null || projectFilePath.isEmpty()) {
            System.out.println("Project file path is not set.");
            return;
        }

        XmlMapper xmlMapper = new XmlMapper();  // Jackson XML mapper
        try {
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(projectFilePath), currentProject);
            System.out.println("Project saved as XML successfully.");
        } catch (IOException e) {
            System.out.println("Could not save project as XML: " + e.getMessage());
        }
    }
}
