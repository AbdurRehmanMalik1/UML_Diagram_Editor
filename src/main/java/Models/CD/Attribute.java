package Models.CD;

import java.io.Serializable;

/**
 * The Attribute class represents an attribute in a class diagram (CD).
 * It contains details about the attribute's name, data type, and access modifier.
 * This class implements Serializable to allow for object serialization.
 */
public class Attribute implements Serializable {

    private String name;             // The name of the attribute
    private String dataType;         // The data type of the attribute (e.g., String, int)
    private String accessModifier = "private";  // The access modifier of the attribute (default is "private")

    /**
     * Default constructor for the Attribute class.
     * Initializes the attribute with default values.
     */
    public Attribute() {
    }

    /**
     * Constructor for the Attribute class with parameters.
     * Initializes the attribute with the provided name, data type, and access modifier.
     *
     * @param name The name of the attribute
     * @param type The data type of the attribute
     * @param accessModifier The access modifier of the attribute (e.g., "private", "public", "protected")
     */
    public Attribute(String name, String type, String accessModifier) {
        this.name = name;
        this.dataType = type;
        this.accessModifier = accessModifier;
    }

    /**
     * Gets the name of the attribute.
     *
     * @return The name of the attribute
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the attribute.
     *
     * @param name The new name of the attribute
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the data type of the attribute.
     *
     * @return The data type of the attribute
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets the data type of the attribute.
     *
     * @param dataType The new data type of the attribute
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * Gets the access modifier of the attribute.
     *
     * @return The access modifier of the attribute (e.g., "private", "public", "protected")
     */
    public String getAccessModifier() {
        return accessModifier;
    }

    /**
     * Sets the access modifier of the attribute.
     *
     * @param accessModifier The new access modifier of the attribute
     */
    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    /**
     * Returns a string representation of the attribute in the format:
     * "[access modifier] [name] : [data type]"
     * The access modifier is represented by symbols:
     * + for public, - for private, # for protected, ~ for default.
     *
     * @return A string representation of the attribute
     */
    @Override
    public String toString() {
        // Determine the access symbol based on the access modifier
        String accessSymbol = switch (accessModifier.toLowerCase()) {
            case "public" -> "+";
            case "private" -> "-";
            case "protected" -> "#";
            default -> "~";  // Default access modifier
        };
        return accessSymbol + " " + name + " : " + dataType;
    }
}
