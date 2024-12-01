package Serializers;

import Models.AssociationModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AssociationModelSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void serialize(AssociationModel associationModel) {
        try {
            String associationModelAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(associationModel);

            writeToFile(associationModelAsString);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing AssociationModel", e);
        }
    }

    private void writeToFile(String object) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("./src/Main/resources/storage/uml.txt", true))) {
            pw.println(object);  // Write the serialized object to the file
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file", e);
        }
    }

    public AssociationModel deserialize(String jsonString) {
        try {
            // Deserialize the JSON string back into an AssociationModel object
            return objectMapper.readValue(jsonString, AssociationModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing AssociationModel", e);
        }
    }
}