package Main;

import UML.Project;
import Util.Dialogs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

public class HelloApplication extends Application {
    private static Stage primaryStage;

    public static void settingUpLogging() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (Exception e) {
            System.err.println("Could not load logging properties file");
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage stage) {
        try {
            settingUpLogging();
            primaryStage = stage; // Set the primary stage

            Project project = promptUserForProject(stage);

            if (project != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/Main.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 600);
                MainController controller = fxmlLoader.getController();
                controller.initialize(project);

                stage.setTitle("UML Project Manager");
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("No project selected. Exiting application.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Entity Manager Factories Shutting Down");
        }
    }

    private Project promptUserForProject(Stage stage) {
        boolean createNew = Dialogs.showConfirmDialog(
                "New or Load Project",
                "Do you want to create a new project?",
                "New",
                "Load"
        );

        if (createNew) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save New Project");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File selectedFile = fileChooser.showSaveDialog(stage);

            if (selectedFile != null) {
                Project newProject = new Project();
                newProject.setProjectFilePath(selectedFile.getAbsolutePath());
                newProject.saveProject();
                return newProject;
            }
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Existing Project");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                return Project.loadProject(selectedFile.getAbsolutePath());
            }
        }

        return null; // No project selected
    }

    public static void main(String[] args) {
        try {
            launch();
        } finally {
            System.out.println("Entity Manager Factories Shutting Down");
        }
    }
}
