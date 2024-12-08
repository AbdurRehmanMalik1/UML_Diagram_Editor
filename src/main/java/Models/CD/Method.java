package Models.CD;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

/**
 * The Method class represents a method in a class diagram (CD).
 * It contains information about the method's return type, text (method signature),
 * whether the method is abstract, and its access modifier.
 * This class implements Serializable to allow for object serialization.
 */
public class Method implements Serializable {

    @JsonInclude()  // Exclude from JSON if the property is null
    private String returnType;  // The return type of the method (e.g., String, void)

    @JsonInclude()  // Exclude from JSON if the property is null
    private String text;  // The text representing the method signature (e.g., "calculateSum()")

    @JsonInclude()  // Exclude from JSON if the property is null
    private boolean isAbstract;  // Indicates whether the method is abstract

    private String accessModifier = "public";  // The access modifier of the method (default is "public")

    /**
     * Default constructor for the Method class.
     * Initializes the method with default values.
     */
    public Method() {
    }

    /**
     * Copy constructor for the Method class.
     * Initializes a new Method object with the values from the provided Method object.
     *
     * @param other The Method object to copy from
     */
    public Method(Method other) {
        this.returnType = other.returnType;
        this.text = other.text;
        this.isAbstract = other.isAbstract;
        this.accessModifier = other.accessModifier;
    }

    /**
     * Constructor for the Method class with return type and method signature.
     * Initializes the method with the specified return type and method signature.
     * The access modifier is set to the default value "public".
     *
     * @param returnType The return type of the method
     * @param text The method signature (name and parameters)
     */
    public Method(String returnType, String text) {
        this.returnType = returnType;
        this.text = text;
    }

    /**
     * Constructor for the Method class with return type, method signature, and access modifier.
     * Initializes the method with the specified return type, method signature, and access modifier.
     *
     * @param returnType The return type of the method
     * @param text The method signature (name and parameters)
     * @param accessModifier The access modifier of the method (e.g., "public", "private", "protected")
     */
    public Method(String returnType, String text, String accessModifier) {
        this.returnType = returnType;
        this.text = text;
        this.accessModifier = accessModifier;
    }

    /**
     * Gets the return type of the method.
     *
     * @return The return type of the method
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * Sets the return type of the method.
     *
     * @param returnType The new return type of the method
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * Gets the text (method signature) of the method.
     *
     * @return The method signature (e.g., "calculateSum()")
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text (method signature) of the method.
     *
     * @param text The new method signature
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Checks whether the method is abstract.
     *
     * @return True if the method is abstract, otherwise false
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * Sets whether the method is abstract.
     *
     * @param isAbstract The new abstract status of the method
     */
    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    /**
     * Gets the access modifier of the method.
     *
     * @return The access modifier of the method (e.g., "public", "private", "protected")
     */
    public String getAccessModifier() {
        return accessModifier;
    }

    /**
     * Sets the access modifier of the method.
     *
     * @param accessModifier The new access modifier of the method
     */
    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    /**
     * Returns a string representation of the method in the format:
     * "[access modifier] [method signature] : [return type]".
     * The access modifier is represented by symbols:
     * + for public, - for private, # for protected, and defaults to + for unknown access modifiers.
     *
     * @return A string representation of the method
     */
    @Override
    public String toString() {
        // Determine the access symbol based on the access modifier
        String prefix = switch (accessModifier) {
            case "public" -> "+";  // For public methods
            case "protected" -> "#";  // For protected methods
            case "private" -> "-";  // For private methods
            default -> "+";  // Default to public if no valid modifier is set
        };
        return prefix + " " + text + " : " + returnType;
    }
}
