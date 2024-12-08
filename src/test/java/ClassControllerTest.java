import static org.mockito.Mockito.*;

import Main.ClassController;
import Main.DiagramController;
import UML.ObjectFactories.ObjectFactory;
import UML.Objects.UMLObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ClassController.class) // Prepare the ClassController for partial mocking
public class ClassControllerTest {

    @Spy
    private ClassController classController;

    @Mock
    private DiagramController diagramController; // The parent controller

    @Mock
    private ToggleButton classButton;

    @Mock
    private ToggleButton interfaceButton;

    @Mock
    private Canvas canvas;

    @Mock
    private ToggleGroup buttonToggleGroup;

    @Mock
    private UMLObject classDiagram;

    @Mock
    private UMLObject interfaceDiagram;

    @Mock
    private ObjectFactory objectFactory;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(ToggleButton.class); // Mock static methods of ToggleButton class
        MockitoAnnotations.openMocks(this);

        when(classButton.getToggleGroup()).thenReturn(buttonToggleGroup);
        when(interfaceButton.getToggleGroup()).thenReturn(buttonToggleGroup);
        when(objectFactory.createClassObject()).thenReturn(classDiagram);
        when(objectFactory.createInterfaceObject()).thenReturn(interfaceDiagram);

        // Spy the classController instance
        classController = PowerMockito.spy(classController);

        // Inject the mock DiagramController through @Spy
        // No need for direct assignment, as @Spy handles this
    }

    @Test
    public void testSetButtonsToggle() {
        classController.setButtonsToggle();

        verify(classButton).setToggleGroup(buttonToggleGroup);
        verify(interfaceButton).setToggleGroup(buttonToggleGroup);

        // Simulate a click on the canvas
        when(canvas.getWidth()).thenReturn(500.0);
        when(canvas.getHeight()).thenReturn(500.0);

        classController.setButtonsToggle(); // Ensure canvas setup

        // Simulate a primary mouse click
        classController.canvas.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                ToggleButton selectedButton = (ToggleButton) buttonToggleGroup.getSelectedToggle();
                if (selectedButton != null && classController.drawObjectFunc != null) {
                    double x = 250.0;
                    double y = 250.0;
                    classController.drawObjectFunc.accept(x, y);
                }
            }
        });

        verify(classController.drawObjectFunc).accept(250.0, 250.0);
    }

    @Test
    public void testDrawClass() {
        classController.onAddClassDiagramClick();

        classController.drawClass(100.0, 100.0);

        verify(objectFactory).createClassObject();
        verify(classController).addToCanvas(classDiagram, 100.0, 100.0);
    }

    @Test
    public void testDrawInterface() {
        classController.onAddInterfaceDiagramClick();

        classController.drawInterface(150.0, 150.0);

        verify(objectFactory).createInterfaceObject();
        verify(classController).addToCanvas(interfaceDiagram, 150.0, 150.0);
    }

    @Test
    public void testOnDrawAssociationClick() {
        classController.onDrawAssociationClick();
        verify(classController).onDrawClick("Association");
    }

    @Test
    public void testOnDrawInheritanceClick() {
        classController.onDrawInheritanceClick();
        verify(classController).onDrawClick("Inheritance");
    }

    @Test
    public void testOnDrawAggregationClick() {
        classController.onDrawAggregationClick();
        verify(classController).onDrawClick("Aggregation");
    }

    @Test
    public void testOnDrawCompositionClick() {
        classController.onDrawCompositionClick();
        verify(classController).onDrawClick("Composition");
    }
}
