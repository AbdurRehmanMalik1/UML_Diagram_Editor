package Main;

import DAO.ProjectDAO;
import DAO.ProjectDAOJSON;
import Services.ProjectService;
import Util.Dialogs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    private static ProjectService projectService;

    /**
     * Returns the primary stage of the application.
     *
     * @return the primary stage.
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the instance of the project service.
     *
     * @return the project service.
     */
    public static ProjectService getProjectService() {
        return projectService;
    }

    /**
     * Entry point for JavaFX application.
     *
     * @param stage the primary stage.
     */
    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        try {
            // Load the OpeningWindow.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/OpeningWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("UML Editor");
            stage.setScene(scene);
            stage.show();

            // Initialize the ProjectService
            projectService = new ProjectService(new ProjectDAOJSON(null));

        } catch (IOException e) {
            System.err.println("Failed to load OpeningWindow.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main entry point for the application.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        try {
            launch();
        } finally {
            System.out.println("Shutting down resources...");
        }
    }
}
