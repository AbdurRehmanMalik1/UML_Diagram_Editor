package Services;

import DAO.ProjectDAO;
import Main.ClassController;
import Main.HelloApplication;
import Main.UseCaseController;
import UML.Diagrams.UMLDiagram;
import UML.Diagrams.ClassDiagram;
import UML.Diagrams.UseCaseDiagram;
import UML.Project;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ProjectService {
    private final ProjectDAO projectDAO;

    public ProjectService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    /**
     * Updates the current project.
     *
     * @param project the new project to initialize.
     */
    public void initialize(Project project) {
        projectDAO.initialize(project);  // Delegate to the DAO
    }

    /**
     * Retrieves the current project.
     *
     * @return the current project.
     */
    public Project getCurrentProject() {
        return projectDAO.getCurrentProject();  // Retrieve project from the DAO
    }

    /**
     * Saves the current project.
     */
    public void saveProject() {
        Project currentProject = getCurrentProject();  // Get current project using DAO
        if (currentProject != null) {
            projectDAO.saveProject();  // Delegate saving to DAO
        } else {
            System.err.println("No project is loaded to save.");
        }
    }

    /**
     * Adds a new diagram to the project.
     *
     * @param diagram the diagram to add.
     */
    public void addDiagram(UMLDiagram diagram) {
        Project currentProject = getCurrentProject();
        if (currentProject != null) {
            currentProject.addUmlDiagram(diagram);
            saveProject();
        } else {
            System.err.println("No project is loaded to add diagrams.");
        }
    }

    /**
     * Renames a diagram and saves the project.
     *
     * @param diagram the diagram to rename.
     * @param newName the new name for the diagram.
     */
    public void renameDiagram(UMLDiagram diagram, String newName) {
        if (diagram != null && newName != null && !newName.trim().isEmpty()) {
            diagram.setName(newName);
            saveProject();
        } else {
            System.err.println("Invalid diagram or new name provided.");
        }
    }

    /**
     * Retrieves a diagram by its ID.
     *
     * @param id the ID of the diagram.
     * @return the diagram, or null if not found.
     */
    public UMLDiagram getDiagramById(int id) {
        Project currentProject = getCurrentProject();
        if (currentProject != null) {
            return currentProject.getUmlDiagramList()
                    .stream()
                    .filter(diagram -> diagram.getId() == id)
                    .findFirst()
                    .orElse(null);
        } else {
            System.err.println("No project is loaded to retrieve diagrams.");
            return null;
        }
    }

    /**
     * Opens the first diagram view if any diagrams exist in the project.
     */
    public void openFirstDiagramView() {
        Project currentProject = getCurrentProject();
        if (currentProject != null) {
            if (currentProject.getUmlDiagramList().isEmpty()) {
                UMLDiagram defaultDiagram = new ClassDiagram();
                currentProject.addUmlDiagram(defaultDiagram);
                openDiagramView(defaultDiagram);
            } else {
                Optional<UMLDiagram> firstDiagram = currentProject.getUmlDiagramList().stream().findFirst();
                firstDiagram.ifPresent(this::openDiagramView);
            }
        } else {
            System.err.println("No project available.");
        }
    }

    public void openDiagramById(int diagramId) {
        UMLDiagram selectedDiagram = getDiagramById(diagramId);
        if (selectedDiagram != null) {
            openDiagramView(selectedDiagram);  // Reusing the openDiagramView method
        } else {
            System.err.println("Diagram with ID " + diagramId + " not found.");
        }
    }

    public void openDiagramView(UMLDiagram diagram) {
        if (diagram == null) {
            System.err.println("No diagram provided to open.");
            return;
        }

        String fxmlFile = null;
        FXMLLoader loader = null;

        // Determine the correct FXML and controller based on diagram type
        if (diagram instanceof ClassDiagram) {
            fxmlFile = "/views/ClassDiagram-view.fxml";
            loader = new FXMLLoader(getClass().getResource(fxmlFile));
        } else if (diagram instanceof UseCaseDiagram) {
            fxmlFile = "/views/UseCaseDiagram-view.fxml";
            loader = new FXMLLoader(getClass().getResource(fxmlFile));
        }

        if (fxmlFile == null || loader == null) {
            System.err.println("Unsupported diagram type for opening a view.");
            return;
        }

        try {
            BorderPane pane = loader.load();

            // Pass the necessary data to the appropriate controller
            if (diagram instanceof ClassDiagram) {
                ClassController classController = loader.getController();
                classController.initialize(
                        diagram.getAssociationList(),
                        diagram.getModels(),
                        getCurrentProject(),
                        (ClassDiagram) diagram
                );
            } else {
                UseCaseController useCaseController = loader.getController();
                useCaseController.initialize(
                        diagram.getAssociationList(),
                        diagram.getModels(),
                        getCurrentProject(),
                        (UseCaseDiagram) diagram
                );
            }

            Scene scene = new Scene(pane);
            HelloApplication.getPrimaryStage().setScene(scene);

            // Set maximum size for the window
            Stage stage = HelloApplication.getPrimaryStage();
//            stage.setWidth(1200);  // Set a fixed width (adjust as needed)
//            stage.setHeight(800);  // Set a fixed height (adjust as needed)
//            stage.setMaxWidth(1200);  // Optionally limit the max width
//            stage.setMaxHeight(800);  // Optionally limit the max height
        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + fxmlFile);
            e.printStackTrace();
        }
    }
}
