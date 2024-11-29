package Main;

import Util.OrmUtil.HibernateUtil;
import Util.OrmUtil.JPAUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

public class HelloApplication extends Application {
    public static void settingUpLogging(){
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (Exception e) {
            System.err.println("Could not load logging properties file");
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        try {
            settingUpLogging();
            launch();
        }
        finally {
//            JPAUtil.shutdown();
//            HibernateUtil.shutdown();
            System.out.println("Entity Manager Factories Shutting Down");
        }
    }
}