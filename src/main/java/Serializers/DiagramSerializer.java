package Serializers;

import UML.Diagrams.UMLDiagram;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is responsible for serializing and deserializing UMLDiagram objects.
 * It uses Jackson's ObjectMapper to convert UMLDiagram objects to JSON and write them to a file,
 * as well as reading the JSON back into UMLDiagram objects.
 */
public class DiagramSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper for JSON processing

    /**
     * Serializes a UMLDiagram object to a JSON string and writes it to a file.
     *
     * @param umlDiagram The UMLDiagram object to serialize.
     */
    public void serialize(UMLDiagram umlDiagram) {
        try {
            // Convert the UMLDiagram object to a pretty-printed JSON string
            String classDiagramAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(umlDiagram);

            // Write the JSON string to a file
            writeToFile(classDiagramAsString);
        } catch (JsonProcessingException e) {
            // Handle any errors that occur during the serialization process
            throw new RuntimeException("Error serializing ClassDiagram: " + e.getMessage(), e);
        }
    }

    /**
     * Deserializes a JSON string back into a UMLDiagram object of the specified type.
     *
     * @param s The JSON string to deserialize.
     * @param clazz The class type of the UMLDiagram to deserialize into.
     * @return The deserialized UMLDiagram object.
     */
    public UMLDiagram deserialize(String s, Class<? extends UMLDiagram> clazz) {
        try {
            // Convert the JSON string back into a UMLDiagram object
            UMLDiagram diagram = objectMapper.readValue(s, clazz);

            // Debugging: Print the class name of the deserialized object
            System.out.println("Deserialized diagram: " + diagram.getClass().getSimpleName());

            return diagram;
        } catch (JsonProcessingException e) {
            // Handle any errors that occur during the deserialization process
            throw new RuntimeException("Error deserializing UMLDiagram: " + e.getMessage(), e);
        }
    }

    /**
     * Writes the serialized JSON string to a file.
     *
     * @param object The serialized JSON string to write to a file.
     */
    private void writeToFile(String object) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("./src/Main/resources/storage/diagram.json"))) {
            // Write the object to the file
            pw.println(object);
        } catch (IOException e) {
            // Handle any errors that occur while writing to the file
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
}
