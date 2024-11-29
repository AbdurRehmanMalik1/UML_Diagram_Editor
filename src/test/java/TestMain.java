import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import UML.Objects.ClassObject;
import Main.HelloController;

public class TestMain {

    private HelloController helloController;

    @BeforeEach
    public void setUp() {
        helloController = new HelloController();
    }

    @Test
    public void testAddClassDiagram() {
        // Test logic directly without UI interaction
        helloController.onAddClassDiagramClick();  // This method should perform logic to add the diagram
        // Verify that the class diagram was added to the canvas
        ClassObject classDiagram = (ClassObject) helloController.canvas.getChildren().getFirst();
        String originalModel = classDiagram.getModel().toString();
        assertEquals("dawd", originalModel);
    }
}
