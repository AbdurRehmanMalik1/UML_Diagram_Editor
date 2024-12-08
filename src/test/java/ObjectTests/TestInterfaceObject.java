package ObjectTests;

import Models.InterfaceModel;
import UML.Objects.InterfaceObject;
import Models.CD.Method;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestInterfaceObject extends JavaFxTestBase {

    @Test
    void testInterfaceObjectSetup() {
        // Create an InterfaceObject instance
        InterfaceObject interfaceObject = new InterfaceObject();

        // Set a model for the InterfaceObject
        InterfaceModel model = new InterfaceModel();
        model.setInterfaceName("IPrintable");
        model.addMethod(new Method("void", "print()", "public"));

        interfaceObject.setModel(model);

        InterfaceModel interfaceModel = (InterfaceModel) interfaceObject.getModel();
        // Validate that the class model was set correctly
        assertEquals("IPrintable", interfaceModel.getInterfaceName(), "Expected interface name to be 'IPrintable'");
        assertEquals(1, interfaceModel.getMethods().size(), "Expected 1 method in the interface");
        assertEquals("void", interfaceModel.getMethods().getFirst().getReturnType(), "Expected method return type to be 'void'");
        assertEquals("print()", interfaceModel.getMethods().getFirst().getText(), "Expected method text to be 'print()'");
        assertEquals("public", interfaceModel.getMethods().getFirst().getAccessModifier(), "Expected method access modifier to be 'public'");
    }

    @Test
    void testInterfaceObjectWithMultipleMethods() {
        // Create an InterfaceObject instance
        InterfaceObject interfaceObject = new InterfaceObject();

        // Set a different model for the InterfaceObject
        InterfaceModel model = new InterfaceModel();
        model.setInterfaceName("ICalculable");
        model.addMethod(new Method("double", "calculateArea()", "public"));
        model.addMethod(new Method("double", "calculateVolume()", "public"));

        interfaceObject.setModel(model);

        InterfaceModel interfaceModel = (InterfaceModel) interfaceObject.getModel();
        // Validate that the class model was set correctly
        assertEquals("ICalculable", interfaceModel.getInterfaceName(), "Expected interface name to be 'ICalculable'");
        assertEquals(2, interfaceModel.getMethods().size(), "Expected 2 methods in the interface");
        assertEquals("double", interfaceModel.getMethods().getFirst().getReturnType(), "Expected method return type to be 'double'");
        assertEquals("calculateArea()", interfaceModel.getMethods().getFirst().getText(), "Expected method text to be 'calculateArea()'");
        assertEquals("public", interfaceModel.getMethods().getFirst().getAccessModifier(), "Expected method access modifier to be 'public'");
        assertEquals("double", interfaceModel.getMethods().getLast().getReturnType(), "Expected method return type to be 'double'");
        assertEquals("calculateVolume()", interfaceModel.getMethods().getLast().getText(), "Expected method text to be 'calculateVolume()'");
        assertEquals("public", interfaceModel.getMethods().getLast().getAccessModifier(), "Expected method access modifier to be 'public'");
    }

    @Test
    void testMethodStackPanes() {
        // Create an InterfaceObject instance
        InterfaceObject interfaceObject = new InterfaceObject();

        // Set a model for the InterfaceObject with methods
        InterfaceModel model = new InterfaceModel();
        model.setInterfaceName("IVehicle");
        model.addMethod(new Method("void", "startEngine()", "public"));
        model.addMethod(new Method("void", "stopEngine()", "public"));

        interfaceObject.setModel(model);

        // Retrieve StackPanes from the InterfaceObject
        List<StackPane> methodStackPanes = interfaceObject.getMethods();

        assertEquals(model.getMethods().size(), methodStackPanes.size(), "Expected 2 method StackPanes");
    }

    @Test
    void testRemoveMethod() {
        // Create an InterfaceObject instance
        InterfaceObject interfaceObject = new InterfaceObject();

        // Set a model for the InterfaceObject with methods
        InterfaceModel model = new InterfaceModel();
        model.setInterfaceName("ITransport");
        model.addMethod(new Method("void", "accelerate()", "public"));
        model.addMethod(new Method("void", "brake()", "public"));

        interfaceObject.setModel(model);

        // Remove a method
        StackPane methodToRemove = interfaceObject.getMethods().getFirst();

        interfaceObject.removeMethod(methodToRemove);

        // Assert that the method is removed
        assertEquals(1, model.getMethods().size(), "Expected no methods in the model");
        assertEquals(1, interfaceObject.getMethods().size(), "Expected no methods in the InterfaceObject");
    }
    @Test
    void copyConstructorInterface(){
        InterfaceModel interfaceModel = new InterfaceModel();
        interfaceModel.setInterfaceName("Manager");
        interfaceModel.setMethods(List.of(new Method()));
        InterfaceModel interfaceModel1 = new InterfaceModel(interfaceModel);

        assertEquals(interfaceModel1.getInterfaceName(),interfaceModel.getInterfaceName(),"Interface Model Copy Constructor not working");
    }
}
