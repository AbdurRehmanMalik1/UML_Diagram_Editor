package Serializers;

import Models.ClassModel;
import Models.Model;
import UML.Objects.UMLObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JSONSerializer implements Serializer {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void serialize(Model model) {
        try {
            String modelASString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
            writeToFile(modelASString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Model deserialize(String s, Class<? extends Model> clazz) {
        try {
            Model deserializedModel =  objectMapper.readValue(s,clazz);
            return deserializedModel;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void serialize(UMLObject umlObject) {
        try {
            String modelASString =  objectMapper.writeValueAsString(umlObject);
            writeToFile(modelASString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    void writeToFile(String object){
        try(PrintWriter pw = new PrintWriter(new FileWriter("./src/Main/resources/storage/uml.txt",true))){
            pw.println(object);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }

    }
}
