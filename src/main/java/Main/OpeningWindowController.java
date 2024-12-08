package Main;

import Main.HelloApplication;
import Services.ProjectService;
import UML.Project;
import Util.Dialogs;
import Util.FileChooserUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Controller for the Opening Window of the UML Editor.
 * This controller handles actions related to starting a new project or opening an existing project.
 */
public class OpeningWindowController {

    // Button to start a new project
    @FXML
    private Button startProjectButton;

    // Button to open an existing project
    @FXML
    private Button openProjectButton;

    /**
     * This method is called when the "Start New Project" button is clicked.
     * It allows the user to save a new project and initialize the diagram view.
     */
    @FXML
    private void onStartNewProjectClick() {
        // Create a FileChooser to let the user select where to save the new project
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save New Project");

        // Set file extension filter for JSON files
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        // Show the save dialog and get the selected file
        File selectedFile = fileChooser.showSaveDialog(HelloApplication.getPrimaryStage());

        // If the user selects a file, create a new project and save it
        if (selectedFile != null) {
            // Create a new Project object
            Project newProject = new Project();
            // Set the project's file path to the selected file
            newProject.setProjectFilePath(selectedFile.getAbsolutePath());
            // Save the project to the specified file
            newProject.saveProject();

            // Initialize the ProjectService with the new project and open the diagram view
            HelloApplication.getProjectService().initialize(newProject);
            HelloApplication.getProjectService().openFirstDiagramView();
        }
    }

    /**
     * This method is called when the "Open Existing Project" button is clicked.
     * It allows the user to open an existing project and initialize the diagram view.
     */
    @FXML
    private void onOpenExistingProjectClick() {
        // Create a FileChooser to let the user select an existing project file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Existing Project");

        // Set file extension filter for JSON files
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        // Show the open dialog and get the selected file
        File selectedFile = fileChooser.showOpenDialog(HelloApplication.getPrimaryStage());

        // If the user selects a file, load the existing project
        if (selectedFile != null) {
            // Load the project from the selected file
            Project loadedProject = Project.loadProject(selectedFile.getAbsolutePath());

            // If the project is successfully loaded, initialize the ProjectService and open the diagram view
            if (loadedProject != null) {
                HelloApplication.getProjectService().initialize(loadedProject);
                HelloApplication.getProjectService().openFirstDiagramView();
            }
        }
    }
}
