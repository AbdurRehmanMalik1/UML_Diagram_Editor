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

public class OpeningWindowController {
    @FXML
    private Button startProjectButton;

    @FXML
    private Button openProjectButton;

    @FXML
    private void onStartNewProjectClick() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save New Project");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showSaveDialog(HelloApplication.getPrimaryStage());

        if (selectedFile != null) {
            Project newProject = new Project();
            newProject.setProjectFilePath(selectedFile.getAbsolutePath());
            newProject.saveProject();

            // Initialize the ProjectService and open the diagram view
            HelloApplication.getProjectService().initialize(newProject);
            HelloApplication.getProjectService().openFirstDiagramView();
        }
    }

    @FXML
    private void onOpenExistingProjectClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Existing Project");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(HelloApplication.getPrimaryStage());

        if (selectedFile != null) {
            Project loadedProject = Project.loadProject(selectedFile.getAbsolutePath());

            if (loadedProject != null) {
                // Initialize the ProjectService and open the diagram view
                HelloApplication.getProjectService().initialize(loadedProject);
                HelloApplication.getProjectService().openFirstDiagramView();
            }
        }
    }
}
