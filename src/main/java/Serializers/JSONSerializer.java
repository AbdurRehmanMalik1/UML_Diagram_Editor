package Serializers;

import Models.Model;
import UML.Objects.UMLObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is responsible for serializing and deserializing UML objects (both general Models and UML-specific objects).
 * It uses the Jackson ObjectMapper to convert objects to JSON strings and write them to a file,
 * and also to read JSON strings back into objects.
 */
public class JSONSerializer implements Serializer {

    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper for JSON processing

    /**
     * Serializes a general Model object to a JSON string and writes it to a file.
     *
     * @param model The Model object to serialize.
     */
    @Override
    public void serialize(Model model) {
        try {
            // Convert the Model object to a pretty-printed JSON string
            String modelASString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
            // Write the JSON string to a file
            writeToFile(modelASString);
        } catch (JsonProcessingException e) {
            // Handle serialization errors
            throw new RuntimeException("Error serializing model: " + e.getMessage(), e);
        }
    }

    /**
     * Deserializes a JSON string into a Model object of the specified class type.
     *
     * @param s The JSON string to deserialize.
     * @param clazz The class type of the Model to deserialize into.
     * @return The deserialized Model object.
     */
    @Override
    public Model deserialize(String s, Class<? extends Model> clazz) {
        try {
            // Convert the JSON string back into a Model object
            return objectMapper.readValue(s, clazz);
        } catch (JsonProcessingException e) {
            // Handle deserialization errors
            throw new RuntimeException("Error deserializing model: " + e.getMessage(), e);
        }
    }

    /**
     * Serializes a UMLObject to a JSON string and writes it to a file.
     *
     * @param umlObject The UMLObject to serialize.
     */
    @Override
    public void serialize(UMLObject umlObject) {
        try {
            // Convert the UMLObject to a JSON string
            String modelASString = objectMapper.writeValueAsString(umlObject);
            // Write the JSON string to a file
            writeToFile(modelASString);
        } catch (JsonProcessingException e) {
            // Handle serialization errors
            throw new RuntimeException("Error serializing UMLObject: " + e.getMessage(), e);
        }
    }

    /**
     * Writes the serialized object (as a JSON string) to a file.
     *
     * @param object The serialized object as a JSON string.
     */
    void writeToFile(String object) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("./src/Main/resources/storage/uml.txt", true))) {
            // Append the JSON string to the file
            pw.println(object);
        } catch (IOException e) {
            // Handle file writing errors
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
}
