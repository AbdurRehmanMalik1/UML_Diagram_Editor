package Serializers;

import UML.Diagrams.UMLDiagram;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DiagramSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void serialize(UMLDiagram umlDiagram) {
        try {
            String classDiagramAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(umlDiagram);
            writeToFile(classDiagramAsString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing ClassDiagram: " + e.getMessage(), e);
        }
    }

    public UMLDiagram deserialize(String s, Class<? extends UMLDiagram> clazz) {
        try {
            UMLDiagram diagram = objectMapper.readValue(s, clazz);
            // Debugging: Check if deserialization is successful
            System.out.println("Deserialized diagram: " + diagram.getClass().getSimpleName());
            return diagram;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing UMLDiagram: " + e.getMessage(), e);
        }
    }
    private void writeToFile(String object) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("./src/Main/resources/storage/diagram.json"))) {
            pw.println(object);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
}