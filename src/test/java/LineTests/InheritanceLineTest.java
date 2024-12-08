package LineTests;
import UML.Line.Inheritance;
import Models.AssociationModel;
import UML.Objects.ClassObject;
import UML.Objects.UMLObject;
import Util.DistanceCalc;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InheritanceLineTest extends JavaFxTestBase {

    private Pane parentPane;
    private AssociationModel associationModel;
    private UMLObject startObject;
    private UMLObject endObject;
    private Inheritance line;

    @Before
    public void setUp() {
        JavaFxTestBase.setUpJavaFX();
        parentPane = new Pane();
        associationModel = new AssociationModel();
        associationModel.setStartMultiplicity("1");
        associationModel.setEndMultiplicity("1");
        associationModel.setAssociationName("Test Line");
        startObject = new ClassObject();
        endObject = new ClassObject();

        line = new Inheritance(0, 0, 100, 100, parentPane, associationModel, startObject, endObject);
        parentPane.getChildren().add(line);
    }

    @Test
    public void testLineInitialization() {
        assertNotNull(line);
        assertEquals("Test Line", line.getAssociationModel().getAssociationName());
        assertEquals("1", line.getAssociationModel().getStartMultiplicity());
        assertEquals("1", line.getAssociationModel().getEndMultiplicity());
    }

    @Test
    public void testUpdateLinePosition() {
        line.getStartObject().setLayoutX(50);
        line.getStartObject().setLayoutY(50);
        line.getEndObject().setLayoutX(150);
        line.getEndObject().setLayoutY(150);

        line.updateLinePosition(null, true);
        line.updateLinePosition(null, false);
        DistanceCalc.ResultPoint resultPoint = DistanceCalc.getShortestDistance(line.getStartObject(), line.getEndObject());

        assertEquals(resultPoint.point1.x, line.getStartX(), 70);
        assertEquals(resultPoint.point1.y, line.getStartY(), 70);
        assertEquals(resultPoint.point2.x, line.getEndX(), 60);
        assertEquals(resultPoint.point2.y, line.getEndY(), 60);
    }

    @Test
    public void testReloadModel() {
        line.reloadModel();
        assertEquals("1", associationModel.getStartMultiplicity());
        assertEquals("1", associationModel.getEndMultiplicity());
        assertEquals("Test Line", associationModel.getAssociationName());
    }

    @Test
    public void testDelete() {
        assertTrue(parentPane.getChildren().contains(line));
        line.delete();
        assertFalse(parentPane.getChildren().contains(line));
    }

    @Test
    public void testCustomDraw() {
        line.customDraw();
        assertEquals(Color.BLACK, line.getStroke());
    }
}
