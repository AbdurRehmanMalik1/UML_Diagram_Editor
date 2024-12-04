package Main;

import Services.ProjectService;
import Util.Dialogs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    private static ProjectService projectService;

    /**
     * Sets up logging configuration.
     */
    private static void setupLogging() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (IOException e) {
            System.err.println("Failed to load logging properties: " + e.getMessage());
        }
    }

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
        setupLogging();
        primaryStage = stage;

        try {
            // Load the OpeningWindow.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/OpeningWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("UML Project Manager");
            stage.setScene(scene);
            stage.show();

            // Initialize the ProjectService
            projectService = new ProjectService(null);

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
