package Models;

import java.io.Serial;
import java.io.Serializable;

/**
 * The AssociationModel class represents an association between two models in the context of a diagram.
 * This class is serializable and includes details like the type of association, multiplicity, coordinates,
 * and the models being connected. The start and end models are transient to avoid serialization of large objects.
 */
public class AssociationModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // Serial version UID for serialization

    private int id;  // Unique identifier for the association
    private String associationName;  // Name of the association
    private String type;  // Type of the association (e.g., aggregation, composition, etc.)
    private double startX;  // X-coordinate of the start of the association
    private double startY;  // Y-coordinate of the start of the association
    private double endX;  // X-coordinate of the end of the association
    private double endY;  // Y-coordinate of the end of the association
    private String startMultiplicity;  // Multiplicity at the start of the association (e.g., 1, *, etc.)
    private String endMultiplicity;  // Multiplicity at the end of the association (e.g., 1, *, etc.)

    private transient Model startModel;  // The model at the start of the association (transient to avoid serialization)
    private transient Model endModel;  // The model at the end of the association (transient to avoid serialization)

    /**
     * Default constructor for the AssociationModel class.
     * Initializes the object with default values.
     */
    public AssociationModel() {}

    /**
     * Constructor that initializes the AssociationModel with a specific association type.
     *
     * @param type The type of the association (e.g., aggregation, composition)
     */
    public AssociationModel(String type) {
        this.type = type;
    }

    // Getter and Setter methods for each field

    /**
     * Gets the type of the association.
     *
     * @return The type of the association
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the association.
     *
     * @param type The new type of the association
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the multiplicity at the start of the association.
     *
     * @return The multiplicity at the start
     */
    public String getStartMultiplicity() {
        return startMultiplicity;
    }

    /**
     * Sets the multiplicity at the start of the association.
     *
     * @param startMultiplicity The new start multiplicity
     */
    public void setStartMultiplicity(String startMultiplicity) {
        this.startMultiplicity = startMultiplicity;
    }

    /**
     * Gets the multiplicity at the end of the association.
     *
     * @return The multiplicity at the end
     */
    public String getEndMultiplicity() {
        return endMultiplicity;
    }

    /**
     * Sets the multiplicity at the end of the association.
     *
     * @param endMultiplicity The new end multiplicity
     */
    public void setEndMultiplicity(String endMultiplicity) {
        this.endMultiplicity = endMultiplicity;
    }

    /**
     * Gets the Y-coordinate of the end of the association.
     *
     * @return The Y-coordinate of the end
     */
    public double getEndY() {
        return endY;
    }

    /**
     * Sets the Y-coordinate of the end of the association.
     *
     * @param endY The new Y-coordinate of the end
     */
    public void setEndY(double endY) {
        this.endY = endY;
    }

    /**
     * Gets the X-coordinate of the end of the association.
     *
     * @return The X-coordinate of the end
     */
    public double getEndX() {
        return endX;
    }

    /**
     * Sets the X-coordinate of the end of the association.
     *
     * @param endX The new X-coordinate of the end
     */
    public void setEndX(double endX) {
        this.endX = endX;
    }

    /**
     * Gets the Y-coordinate of the start of the association.
     *
     * @return The Y-coordinate of the start
     */
    public double getStartY() {
        return startY;
    }

    /**
     * Sets the Y-coordinate of the start of the association.
     *
     * @param startY The new Y-coordinate of the start
     */
    public void setStartY(double startY) {
        this.startY = startY;
    }

    /**
     * Gets the X-coordinate of the start of the association.
     *
     * @return The X-coordinate of the start
     */
    public double getStartX() {
        return startX;
    }

    /**
     * Sets the X-coordinate of the start of the association.
     *
     * @param startX The new X-coordinate of the start
     */
    public void setStartX(double startX) {
        this.startX = startX;
    }

    /**
     * Gets the model at the start of the association.
     *
     * @return The model at the start
     */
    public Model getStartModel() {
        return startModel;
    }

    /**
     * Sets the model at the start of the association.
     *
     * @param startModel The new model at the start
     */
    public void setStartModel(Model startModel) {
        this.startModel = startModel;
    }

    /**
     * Gets the model at the end of the association.
     *
     * @return The model at the end
     */
    public Model getEndModel() {
        return endModel;
    }

    /**
     * Sets the model at the end of the association.
     *
     * @param endModel The new model at the end
     */
    public void setEndModel(Model endModel) {
        this.endModel = endModel;
    }

    /**
     * Gets the unique identifier of the association.
     *
     * @return The ID of the association
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the association.
     *
     * @param id The new ID of the association
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the association.
     *
     * @return The name of the association
     */
    public String getAssociationName() {
        return associationName;
    }

    /**
     * Sets the name of the association.
     *
     * @param associationName The new name of the association
     */
    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }
}
