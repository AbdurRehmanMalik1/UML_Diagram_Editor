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

/**
 * The main entry point for the UML Editor application.
 * This class extends JavaFX's Application class and is responsible for starting the application,
 * setting up the primary stage, and initializing the necessary services.
 */
public class HelloApplication extends Application {

    // The primary stage of the JavaFX application
    private static Stage primaryStage;

    // The ProjectService instance used throughout the application
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
     * The entry point for the JavaFX application.
     * This method is invoked when the application is launched.
     *
     * @param stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        try {
            // Load the OpeningWindow.fxml file into the FXMLLoader
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/OpeningWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());  // Create a new Scene with the loaded FXML

            // Set the title of the primary stage and add the scene to it
            stage.setTitle("UML Editor");
            stage.setScene(scene);
            stage.show();  // Display the primary stage

            // Initialize the ProjectService using a ProjectDAOJSON implementation for data handling
            projectService = new ProjectService(new ProjectDAOJSON(null));

        } catch (IOException e) {
            // Handle the case where the OpeningWindow.fxml file could not be loaded
            System.err.println("Failed to load OpeningWindow.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * The main entry point for launching the JavaFX application.
     *
     * @param args command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        try {
            // Launch the JavaFX application
            launch();
        } finally {
            // Print a message when the application is shut down
            System.out.println("Shutting down resources...");
        }
    }
}
