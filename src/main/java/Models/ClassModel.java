package Models;

import Models.CD.Attribute;
import Models.CD.Method;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * The ClassModel class represents a class in a UML diagram, including its name, attributes, methods,
 * and whether it is abstract. It extends the Model class and provides functionality to add attributes
 * and methods, and to retrieve the class details.
 */
public class ClassModel extends Model {

    @JsonInclude() // Jackson annotation to include this field in JSON serialization
    private String className; // The name of the class

    @JsonInclude() // Jackson annotation to include this field in JSON serialization
    private List<Attribute> attributes; // List of attributes (fields) of the class

    @JsonInclude() // Jackson annotation to include this field in JSON serialization
    private List<Method> methods; // List of methods of the class

    @JsonInclude() // Jackson annotation to include this field in JSON serialization
    @JsonProperty("abstract") // Jackson annotation to map the field to "abstract" in JSON
    private boolean isAbstract; // Whether the class is abstract

    /**
     * Default constructor that initializes the class with empty lists for attributes and methods.
     */
    public ClassModel(){
        super(); // Calls the constructor of the parent Model class
        attributes = new ArrayList<>(); // Initializes an empty list for attributes
        methods = new ArrayList<>(); // Initializes an empty list for methods
    }

    /**
     * Copy constructor that creates a new ClassModel by copying data from another instance.
     *
     * @param other The ClassModel to copy data from.
     */
    public ClassModel(ClassModel other) {
        super(other); // Calls the parent class copy constructor
        this.className = other.className; // Copies the class name
        this.attributes = new ArrayList<>(other.attributes); // Copies the attributes list
        this.methods = new ArrayList<>(); // Initializes a new list for methods
        // Copies each method from the other ClassModel's methods list
        for (Method method : other.methods) {
            this.methods.add(new Method(method));
        }
        this.isAbstract = other.isAbstract; // Copies the abstract flag
    }

    // Getter and Setter methods for each field

    /**
     * Gets the name of the class.
     *
     * @return The class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the name of the class.
     *
     * @param className The new class name.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets the list of attributes of the class.
     *
     * @return The list of attributes.
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * Sets the list of attributes for the class.
     *
     * @param attributes The new list of attributes.
     */
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * Gets the list of methods of the class.
     *
     * @return The list of methods.
     */
    public List<Method> getMethods() {
        return methods;
    }

    /**
     * Sets the list of methods for the class.
     *
     * @param methods The new list of methods.
     */
    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    /**
     * Adds an attribute to the class.
     *
     * @param attribute The attribute to add.
     */
    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
    }

    /**
     * Adds a method to the class.
     *
     * @param method The method to add.
     */
    public void addMethod(Method method){
        methods.add(method);
    }

    /**
     * Gets whether the class is abstract.
     *
     * @return True if the class is abstract, otherwise false.
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * Sets whether the class is abstract.
     *
     * @param anAbstract The new abstract flag (true for abstract, false for not).
     */
    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }
}
