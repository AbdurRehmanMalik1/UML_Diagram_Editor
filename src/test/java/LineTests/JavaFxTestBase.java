package LineTests;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

public class JavaFxTestBase {

    private static boolean javaFxInitialized = false;

    @BeforeAll
    protected  static void setUpJavaFX() {
        if (!javaFxInitialized) {
            Platform.startup(() -> {});
            javaFxInitialized = true; // Ensure it doesn't run again
        }
    }
}
