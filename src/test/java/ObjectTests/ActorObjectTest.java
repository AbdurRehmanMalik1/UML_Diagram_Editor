package ObjectTests;

import Models.ActorModel;
import UML.Objects.ActorObject;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActorObjectTest  extends JavaFxTestBase {

//    @BeforeAll
//    static void setUpJavaFX() {
//        if(!Platform.isFxApplicationThread())
//            Platform.startup(() -> {}); // Start the JavaFX application thread
//    }

    @Test
    void testInitialization() {
        ActorObject actor = new ActorObject();

        // Check that the name is set to "Actor" by default
        assertEquals("Actor", actor.downcastModel().getActorName());

        // Check that the dimensions are set correctly
        boolean r1 = false;
        boolean r2 = false;
        if(actor.getWidth()>40)
            r1 = true;
        if(actor.getHeight()>50)
            r2 = true;
        assertTrue(r1, "Actor Width is wrong");
        assertTrue(r2, "Actor Height is wrong");

        // Check that the actor SVG path is correctly initialized
        SVGPath svgPath = actor.getActorSvg();
        assertNotNull(svgPath);
        assertEquals("M75 25a20 20 0 1 0 0.01 0 M75 65v60 M50 80h50 M75 125l-25 40 M75 125l25 40", svgPath.getContent());
    }

    @Test
    void testSetName() {
        ActorObject actor = new ActorObject("Custom Name");

        // Check that the name is correctly set
        actor.reloadModel();
        assertEquals("Custom Name", actor.downcastModel().getActorName());
    }

    @Test
    void testEditableField() {
        ActorObject actor = new ActorObject("Initial Name");

        // Initial display should be as a label
        assertInstanceOf(Label.class, actor.lookup(".label"), "Failed Label Lookup");

        // Simulate a double-click to edit the text
        actor.lookup(".label").fireEvent(new javafx.scene.input.MouseEvent(
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

    }



    @Test
    void testSetPosition() {
        ActorObject actor = new ActorObject();
        actor.setPosition(50.0, 100.0);

        // Check that the position is set correctly
        assertEquals(50.0, actor.getLayoutX());
        assertEquals(100.0, actor.getLayoutY());
    }

    @Test
    void testSetActorColor() {
        ActorObject actor = new ActorObject();
        actor.setActorColor(Color.BLUE);

        // Check that the color is set correctly
        actor.getColor();
        assertEquals(Color.BLUE,  actor.getColor());
    }
    @Test
    void copyConstructorActor(){
        ActorModel actorModel = new ActorModel();
        actorModel.setActorName("Manager");
        ActorModel actorModel1 = new ActorModel(actorModel);

        assertEquals(actorModel1.getActorName(),actorModel.getActorName(),"Actor Model Copy Constructor not working");
    }
}
