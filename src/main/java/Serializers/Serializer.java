package Serializers;

import Models.Model;
import UML.Objects.UMLObject;

/**
 * This interface defines the contract for serializing and deserializing objects.
 * Implementing classes must provide methods for serializing both UML objects and general model objects,
 * as well as for deserializing JSON strings into Model objects.
 */
public interface Serializer {

    /**
     * Serializes a UMLObject into a JSON string and writes it to a file.
     *
     * @param umlObject The UMLObject to serialize.
     */
    void serialize(UMLObject umlObject);

    /**
     * Serializes a general Model object into a JSON string and writes it to a file.
     *
     * @param model The Model object to serialize.
     */
    void serialize(Model model);

    /**
     * Deserializes a JSON string into a Model object of the specified class type.
     *
     * @param s The JSON string to deserialize.
     * @param clazz The class type of the Model to deserialize into.
     * @return The deserialized Model object.
     */
    Model deserialize(String s, Class<? extends Model> clazz);
}
