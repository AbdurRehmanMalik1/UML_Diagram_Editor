package Models;

import Models.CD.Method;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

/**
 * The InterfaceModel class represents an interface in a UML diagram, including its name and the methods
 * that are part of the interface. It extends the Model class and provides functionality to manage the
 * methods of the interface.
 */
public class InterfaceModel extends Model {

    @JsonInclude() // Jackson annotation to include this field in JSON serialization
    private String interfaceName; // The name of the interface

    @JsonInclude() // Jackson annotation to include this field in JSON serialization
    private List<Method> methods; // List of methods in the interface

    /**
     * Default constructor that initializes the methods list as an empty list.
     */
    public InterfaceModel() {
        super(); // Calls the constructor of the parent Model class
        methods = new ArrayList<>(); // Initializes an empty list for methods
    }

    /**
     * Copy constructor that creates a new InterfaceModel by copying data from another instance.
     *
     * @param other The InterfaceModel to copy data from.
     */
    public InterfaceModel(InterfaceModel other) {
        super(other); // Calls the parent class copy constructor
        this.interfaceName = other.interfaceName; // Copies the interface name
        this.methods = new ArrayList<>(other.methods); // Copies the methods list
    }

    // Getter and Setter methods for each field

    /**
     * Gets the name of the interface.
     *
     * @return The name of the interface.
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * Sets the name of the interface.
     *
     * @param interfaceName The new interface name.
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * Gets the list of methods in the interface.
     *
     * @return The list of methods.
     */
    public List<Method> getMethods() {
        return methods;
    }

    /**
     * Sets the list of methods for the interface.
     *
     * @param methods The new list of methods.
     */
    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    /**
     * Adds a method to the interface.
     *
     * @param method The method to add.
     */
    public void addMethod(Method method) {
        methods.add(method);
    }

    // Uncomment and modify toString method if needed for printing Interface details
    // @Override
    // public String toString() {
    //     StringBuilder sb = new StringBuilder();
    //     sb.append("Interface Name: ").append(interfaceName).append("\n");
    //     sb.append("Methods: \n");
    //     for (Method method : methods) {
    //         sb.append("  ").append(method).append("\n");
    //     }
    //     return sb.toString();
    // }
}
