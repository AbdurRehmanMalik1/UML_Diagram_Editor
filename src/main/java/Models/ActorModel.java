package Models;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The ActorModel class represents an actor in the context of a model.
 * It inherits from the Model class and includes an additional field for the actor's name.
 * The class provides constructors for creating an actor object, a getter and setter for the actor's name,
 * and serialization support using the Jackson library.
 */
public class ActorModel extends Model {

    @JsonInclude()  // Exclude from JSON if the property is null
    private String actorName;  // The name of the actor

    /**
     * Default constructor for the ActorModel class.
     * Initializes the object using the parent class's constructor.
     */
    public ActorModel() {
        super();
    }

    /**
     * Copy constructor for the ActorModel class.
     * Initializes a new ActorModel object with values from the provided ActorModel object.
     *
     * @param other The ActorModel object to copy from
     */
    public ActorModel(ActorModel other) {
        super(other);  // Calls the constructor of the parent class (Model) to copy shared fields
        this.actorName = other.actorName;  // Copies the actor name
    }

    /**
     * Gets the name of the actor.
     *
     * @return The name of the actor
     */
    public String getActorName() {
        return actorName;
    }

    /**
     * Sets the name of the actor.
     *
     * @param actorName The new name of the actor
     */
    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
}
