package Serializers;

import Models.AssociationModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is responsible for serializing and deserializing an AssociationModel.
 * It uses Jackson's ObjectMapper to convert AssociationModel objects to JSON and
 * write them to a file, as well as reading the JSON back into an AssociationModel object.
 */
public class AssociationModelSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper for JSON processing

    /**
     * Serializes an AssociationModel object to a JSON string and writes it to a file.
     *
     * @param associationModel The AssociationModel object to serialize.
     */
    public void serialize(AssociationModel associationModel) {
        try {
            // Convert the AssociationModel object to a pretty-printed JSON string
            String associationModelAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(associationModel);

            // Write the JSON string to a file
            writeToFile(associationModelAsString);

        } catch (JsonProcessingException e) {
            // Handle any errors that occur during the serialization process
            throw new RuntimeException("Error serializing AssociationModel", e);
        }
    }

    /**
     * Writes the serialized JSON string to a file.
     *
     * @param object The serialized JSON string to write to a file.
     */
    private void writeToFile(String object) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("./src/Main/resources/storage/uml.txt", true))) {
            // Write the object to the file, appending the content
            pw.println(object);
        } catch (IOException e) {
            // Handle any errors that occur while writing to the file
            throw new RuntimeException("Error writing to file", e);
        }
    }

    /**
     * Deserializes a JSON string back into an AssociationModel object.
     *
     * @param jsonString The JSON string to deserialize.
     * @return The deserialized AssociationModel object.
     */
    public AssociationModel deserialize(String jsonString) {
        try {
            // Convert the JSON string back into an AssociationModel object
            return objectMapper.readValue(jsonString, AssociationModel.class);
        } catch (JsonProcessingException e) {
            // Handle any errors that occur during the deserialization process
            throw new RuntimeException("Error deserializing AssociationModel", e);
        }
    }
}
