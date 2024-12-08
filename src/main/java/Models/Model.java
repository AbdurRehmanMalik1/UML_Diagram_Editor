package Models;

import com.fasterxml.jackson.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Model class is an abstract base class for all model types in the UML diagram.
 * It contains common properties and methods for handling associations, coordinates,
 * and the unique identification of each model.
 * This class is extended by specific model types like ClassModel, InterfaceModel, etc.
 */
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"  // Using 'id' as the unique identifier for serialization
)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassModel.class, name = "Class"),
        @JsonSubTypes.Type(value = InterfaceModel.class, name = "Interface"),
        @JsonSubTypes.Type(value = UseCaseModel.class, name = "Use Case"),
        @JsonSubTypes.Type(value = ActorModel.class, name = "Actor")
})
public abstract class Model implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static int modelIdCounter = 1; // Static counter to generate unique model IDs

    public int id; // Unique identifier for each model instance

    @JsonInclude() // This field will be included in the JSON serialization
    private double x = 0; // X-coordinate for the model (default: 0)

    @JsonInclude() // This field will be included in the JSON serialization
    private double y = 0; // Y-coordinate for the model (default: 0)

    @JsonIgnore // This field will be ignored in JSON serialization
    transient private List<AssociationModel> incomingAssociations = new ArrayList<>(); // List of incoming associations

    @JsonIgnore // This field will be ignored in JSON serialization
    transient private List<AssociationModel> outgoingAssociations = new ArrayList<>(); // List of outgoing associations

    /**
     * Default constructor to initialize the model with a unique ID.
     */
    protected Model() {
        id = generateUniqueId(); // Assigns a unique ID to this model instance
    }

    /**
     * Copy constructor to initialize a new model instance by copying values from another.
     *
     * @param other The model instance to copy data from.
     */
    protected Model(Model other) {
        id = generateUniqueId(); // Generates a new unique ID for the copied model
        this.x = 0; // Default X-coordinate
        this.y = 0; // Default Y-coordinate
        this.incomingAssociations = new ArrayList<>(); // Initializes an empty list for incoming associations
        this.outgoingAssociations = new ArrayList<>(); // Initializes an empty list for outgoing associations
    }

    /**
     * Generates a unique ID for each model instance.
     * This method is synchronized to avoid issues with concurrent access.
     *
     * @return The unique model ID.
     */
    private synchronized int generateUniqueId() {
        return modelIdCounter++; // Increment and return the next available ID
    }

    // Getter and Setter methods for the model fields

    /**
     * Gets the unique ID of this model.
     *
     * @return The model ID.
     */
    public int getModelId() {
        return id;
    }

    /**
     * Sets the unique ID for this model.
     *
     * @param id The new model ID.
     */
    public void setModelId(int id) {
        this.id = id;
    }

    /**
     * Gets the X-coordinate of the model.
     *
     * @return The X-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the X-coordinate of the model.
     *
     * @param x The new X-coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the Y-coordinate of the model.
     *
     * @return The Y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the Y-coordinate of the model.
     *
     * @param y The new Y-coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Sets both the X and Y coordinates of the model.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    public void setCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the list of incoming associations to this model.
     *
     * @return The list of incoming associations.
     */
    public List<AssociationModel> getIncomingAssociations() {
        return incomingAssociations;
    }

    /**
     * Sets the list of incoming associations for this model.
     *
     * @param incomingAssociations The list of incoming associations.
     */
    public void setIncomingAssociations(List<AssociationModel> incomingAssociations) {
        this.incomingAssociations = incomingAssociations;
    }

    /**
     * Gets the list of outgoing associations from this model.
     *
     * @return The list of outgoing associations.
     */
    public List<AssociationModel> getOutgoingAssociations() {
        return outgoingAssociations;
    }

    /**
     * Sets the list of outgoing associations for this model.
     *
     * @param outgoingAssociations The list of outgoing associations.
     */
    public void setOutgoingAssociations(List<AssociationModel> outgoingAssociations) {
        this.outgoingAssociations = outgoingAssociations;
    }

    /**
     * Adds an outgoing (start) association to this model.
     *
     * @param association The association to add.
     */
    public void addStartAssociation(AssociationModel association) {
        outgoingAssociations.add(association);  // Adds the association to the outgoing list
        association.setStartModel(this);         // Sets this model as the start of the association
    }

    /**
     * Removes an outgoing (start) association from this model.
     *
     * @param association The association to remove.
     */
    public void removeStartAssociation(AssociationModel association) {
        outgoingAssociations.remove(association);
        association.setStartModel(null); // Removes this model as the start of the association
    }

    /**
     * Adds an incoming (end) association to this model.
     *
     * @param association The association to add.
     */
    public void addEndAssociation(AssociationModel association) {
        incomingAssociations.add(association);  // Adds the association to the incoming list
        association.setEndModel(this);           // Sets this model as the end of the association
    }

    /**
     * Removes an incoming (end) association from this model.
     *
     * @param association The association to remove.
     */
    public void removeEndAssociation(AssociationModel association) {
        incomingAssociations.remove(association);
        association.setEndModel(null); // Removes this model as the end of the association
    }
}
