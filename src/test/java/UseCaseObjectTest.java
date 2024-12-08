import UML.Objects.UseCaseObject;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UseCaseObjectTest {


    @BeforeAll
    static void setUpJavaFX() {
        Platform.startup(() -> {}); // Start the JavaFX application thread
    }

    @Test
    void testInitialization() {
        UseCaseObject useCase = new UseCaseObject("Test Use Case");

        // Check that the name is set correctly
        assertNull(useCase.downcastModel().getUseCaseName());
        assertEquals("Use Case", useCase.getUseCaseName());

        // Check that the dimensions are set correctly
        assertEquals(140.0, useCase.getWidth(), 0.1);
        assertEquals(100.0, useCase.getHeight(), 0.1);
    }

    @Test
    void testEditableField() {
        UseCaseObject useCase = new UseCaseObject("Editable Test");

        // Check initial display as label
        assertInstanceOf(Label.class, useCase.lookup(".label"), "Failed Label Lookup");

        // Switch to text field and check
        useCase.lookup(".label").fireEvent(new javafx.scene.input.MouseEvent(
                MouseEvent.MOUSE_CLICKED, // Type of the event
                0, 0, // X, Y coordinates of the mouse
                0, 0, // X, Y coordinates relative to the node
                MouseButton.PRIMARY, // Primary mouse button
                2, // Number of clicks
                true, // Whether shift is pressed
                true, // Whether control is pressed
                true, // Whether alt is pressed
                true, // Whether meta (command key on macOS) is pressed
                true, true, true, true, true, true, null // Whether the mouse is pressed, released, etc.
        ));
        assertInstanceOf(TextField.class, useCase.lookup(".text-field"), "Failed Text field Lookup");

        // Edit the text field and commit changes
        TextField textField = (TextField) useCase.lookup(".text-field");
        textField.setText("Updated Test Use Case");
        textField.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));
        assertEquals("Updated Test Use Case", useCase.downcastModel().getUseCaseName());
    }

    @Test
    void testModelUpdates() {
        UseCaseObject useCase = new UseCaseObject("Initial Test");

        // Modify the name via the editable field
        useCase.downcastModel().setUseCaseName("Updated Name");

        // Verify if the model updates correctly
        assertEquals("Updated Name", useCase.downcastModel().getUseCaseName());
    }
}
