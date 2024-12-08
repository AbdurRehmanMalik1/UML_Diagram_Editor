package ObjectTests;
import UML.Objects.ClassObject;
import Models.CD.Attribute;
import Models.CD.Method;
import Models.ClassModel;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestClassObject extends JavaFxTestBase {


    @Test
    void testClassObjectSetup() {
        // Create a ClassObject instance
        ClassObject classObject = new ClassObject();

        // Set a model for the ClassObject
        ClassModel model = new ClassModel();
        model.setClassName("Person");
        model.setAbstract(false);
        model.addAttribute(new Attribute("name", "String", "private"));
        model.addAttribute(new Attribute("age", "int", "private"));
        model.addMethod(new Method("void", "setName(s : String)", "public"));
        model.addMethod(new Method("void", "setAge()", "public"));

        classObject.setModel(model);

        ClassModel classModel = (ClassModel) classObject.getModel();
        // Validate that the class model was set correctly
        assertEquals("Person", classModel.getClassName(), "Expected class name to be 'Person'");
        assertFalse(classModel.isAbstract(), "Expected isAbstract to be false");
        assertEquals(2, classModel.getAttributes().size(), "Expected 2 attributes");
        assertEquals("name", classModel.getAttributes().getFirst().getName(), "Expected attribute name to be 'name'");
        assertEquals("String", classModel.getAttributes().getFirst().getDataType(), "Expected attribute data type to be 'String'");
        assertEquals("private", classModel.getAttributes().getFirst().getAccessModifier(), "Expected attribute access modifier to be 'private'");
        assertEquals(2, classModel.getMethods().size(), "Expected 2 methods");
        assertEquals("void", classModel.getMethods().getFirst().getReturnType(), "Expected method return type to be 'void'");
        assertEquals("setName(s : String)", classModel.getMethods().getFirst().getText(), "Expected method text to be 'setName(s : String)'");
        assertEquals("public", classModel.getMethods().getFirst().getAccessModifier(), "Expected method access modifier to be 'public'");
    }

    @Test
    void testClassObjectWithDifferentAttributes() {
        // Create a ClassObject instance
        ClassObject classObject = new ClassObject();

        // Set a different model for the ClassObject
        ClassModel model = new ClassModel();
        model.setClassName("Employee");
        model.setAbstract(false);
        model.addAttribute(new Attribute("employeeID", "int", "private"));
        model.addAttribute(new Attribute("position", "String", "protected"));
        model.addMethod(new Method("void", "setEmployeeID(id : int)", "public"));
        model.addMethod(new Method("String", "getPosition()", "public"));

        classObject.setModel(model);

        ClassModel classModel = (ClassModel) classObject.getModel();
        // Validate that the class model was set correctly
        assertEquals("Employee", classModel.getClassName(), "Expected class name to be 'Employee'");
        assertFalse(classModel.isAbstract(), "Expected isAbstract to be false");
        assertEquals(2, classModel.getAttributes().size(), "Expected 2 attributes");
        assertEquals("employeeID", classModel.getAttributes().getFirst().getName(), "Expected attribute name to be 'employeeID'");
        assertEquals("int", classModel.getAttributes().getFirst().getDataType(), "Expected attribute data type to be 'int'");
        assertEquals("private", classModel.getAttributes().getFirst().getAccessModifier(), "Expected attribute access modifier to be 'private'");
        assertEquals("position", classModel.getAttributes().getLast().getName(), "Expected attribute name to be 'position'");
        assertEquals("String", classModel.getAttributes().getLast().getDataType(), "Expected attribute data type to be 'String'");
        assertEquals("protected", classModel.getAttributes().getLast().getAccessModifier(), "Expected attribute access modifier to be 'protected'");
        assertEquals(2, classModel.getMethods().size(), "Expected 2 methods");
        assertEquals("void", classModel.getMethods().getFirst().getReturnType(), "Expected method return type to be 'void'");
        assertEquals("setEmployeeID(id : int)", classModel.getMethods().getFirst().getText(), "Expected method text to be 'setEmployeeID(id : int)'");
        assertEquals("public", classModel.getMethods().getFirst().getAccessModifier(), "Expected method access modifier to be 'public'");
        assertEquals("String", classModel.getMethods().getLast().getReturnType(), "Expected method return type to be 'String'");
        assertEquals("getPosition()", classModel.getMethods().getLast().getText(), "Expected method text to be 'getPosition()'");
        assertEquals("public", classModel.getMethods().getLast().getAccessModifier(), "Expected method access modifier to be 'public'");
    }
    @Test
    void testAttributeAndMethodStackPanes() {
        // Create a ClassObject instance
        ClassObject classObject = new ClassObject();

        // Set a model for the ClassObject with attributes and methods
        ClassModel model = new ClassModel();
        model.setClassName("Student");
        model.setAbstract(false);
        model.addAttribute(new Attribute("studentID", "int", "private"));
        model.addAttribute(new Attribute("course", "String", "protected"));
        model.addMethod(new Method("void", "setStudentID(id : int)", "public"));
        model.addMethod(new Method("String", "getCourse()", "public"));

        classObject.setModel(model);

        // Retrieve StackPanes from the ClassObject
        List<StackPane> attributesStackPanes = classObject.getAttributes();
        List<StackPane> methodStackPanes = classObject.getAttributes();

        assertEquals(model.getAttributes().size(), attributesStackPanes.size(), "Expected 2 attributes StackPanes");
        assertEquals(model.getMethods().size(),methodStackPanes.size() ,"Expected 2 method StackPanes");
    }
    @Test
    void testRemoveAttributeAndMethod() {
        // Create a ClassObject instance
        ClassObject classObject = new ClassObject();

        // Set a model for the ClassObject with attributes and methods
        ClassModel model = new ClassModel();
        model.setClassName("Student");
        model.setAbstract(false);
        model.addAttribute(new Attribute("studentID", "int", "private"));
        model.addAttribute(new Attribute("course", "String", "protected"));
        model.addMethod(new Method("void", "setStudentID(id : int)", "public"));
        model.addMethod(new Method("String", "getCourse()", "public"));

        classObject.setModel(model);

        // Remove an attribute and a method
        StackPane attributeToRemove = classObject.getAttributes().getFirst();
        StackPane methodToRemove = classObject.getMethods().getFirst();

        classObject.removeAttribute(attributeToRemove);
        classObject.removeMethod(methodToRemove);

        // Assert that all attributes and methods are removed
        assertEquals(1, model.getAttributes().size(), "Expected no attributes in the model");
        assertEquals(1, model.getMethods().size(), "Expected no methods in the model");
        assertEquals(1, classObject.getAttributes().size(), "Expected no attributes in the ClassObject");
        assertEquals(1, classObject.getMethods().size(), "Expected no methods in the ClassObject");
    }
    @Test
    void copyConstructorClass(){
        ClassModel classModel = new ClassModel();
        classModel.setClassName("Manager");
        classModel.setAttributes(List.of(new Attribute()));
        classModel.setMethods(List.of(new Method()));
        ClassModel classModel1 = new ClassModel(classModel);

        assertEquals(classModel1.getClassName(),classModel.getClassName(),"Class Model Copy Constructor not working");
    }
}
