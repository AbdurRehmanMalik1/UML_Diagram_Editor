package Serializers;

import Models.ClassModel;
import Models.Model;
import UML.UMLObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JSONSerializer implements Serializer {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void serialize(Model model) {
        try {
            String modelASString =  objectMapper.writeValueAsString(model);
            writeToFile(modelASString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deserialize(String s, Class<ClassModel> clazz) {
        try {
            Model modelASString =  objectMapper.readValue(s,clazz);
            System.out.println(modelASString);
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
        try(PrintWriter pw = new PrintWriter(new FileWriter("./storage/uml.txt",true))){
            pw.println(object);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }

    }
}
