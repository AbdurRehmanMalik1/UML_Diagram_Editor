package Models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

/**
 * The UseCaseModel class represents a use case in the UML diagram.
 * It extends the Model class and contains a use case name.
 * This class is used to model use cases in a system's design.
 */
public class UseCaseModel extends Model {

    @JsonInclude() // This field will be included in the JSON serialization
    private String useCaseName; // The name of the use case

    /**
     * Default constructor to initialize the use case model.
     * Calls the parent constructor to generate a unique ID for the use case model.
     */
    public UseCaseModel() {
        super(); // Initializes the model with a unique ID and default properties
    }

    /**
     * Copy constructor to initialize a new use case model by copying data from another use case model.
     *
     * @param other The use case model to copy data from.
     */
    public UseCaseModel(UseCaseModel other) {
        super(other); // Calls the parent class copy constructor to handle shared properties
        this.useCaseName = other.useCaseName; // Copies the use case name
    }

    /**
     * Gets the name of the use case.
     *
     * @return The name of the use case.
     */
    public String getUseCaseName() {
        return useCaseName;
    }

    /**
     * Sets the name of the use case.
     *
     * @param useCaseName The name to set for the use case.
     */
    public void setUseCaseName(String useCaseName) {
        this.useCaseName = useCaseName; // Sets the use case name
    }
}
