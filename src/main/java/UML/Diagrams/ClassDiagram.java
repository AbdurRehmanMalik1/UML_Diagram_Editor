package UML.Diagrams;

import Models.AssociationModel;
import Models.ClassModel;
import Models.Model;
import Serializers.ClassDiagramSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.*;
import java.util.List;


public class ClassDiagram extends UMLDiagram{
    @JsonInclude()
    List<AssociationModel> associationList;
    @JsonInclude()
    List<Model> models;

    public ClassDiagram(){
    }
    public ClassDiagram(List<Model> m,List<AssociationModel> a){
        associationList = a;
        models = m;
    }
    public void setAssociationList(List<AssociationModel> associationModelList){
        this.associationList = associationModelList;
    }
    public void setModelList(List<Model> modelList){
        this.models = modelList;
    }
    public List<Model> getModels() {
        return models;
    }

    public List<AssociationModel> getAssociationList() {
        return associationList;
    }

    public void saveClassDiagram() {
        ClassDiagramSerializer classDiagramSerializer = new ClassDiagramSerializer();
        classDiagramSerializer.serialize(this);
    }
    public void loadClassDiagram(){
        associationList.clear();
        models.clear();
        ClassDiagramSerializer classDiagramSerializer = new ClassDiagramSerializer();
        String content =  readClassDiagramFile();
        UMLDiagram deserializedClassDiagram;
        if(!content.isEmpty()) {
            deserializedClassDiagram = classDiagramSerializer.deserialize(readClassDiagramFile(), ClassDiagram.class);
            ClassDiagram classDiagram = (ClassDiagram) deserializedClassDiagram;
            this.setAssociationList(classDiagram.getAssociationList());
            this.setModelList(classDiagram.getModels());
        }
//        ClassModel classModel = (ClassModel)models.getFirst();
//        System.out.println("Class Model is abstract = " + classModel.isAbstract());
        //System.out.println(readClassDiagramFile());
    }
    public static String readClassDiagramFile() {
        // Path to the classDiagram.json file
        String filePath = "./src/Main/resources/storage/classDiagram.json";

        // StringBuilder to hold the file contents
        StringBuilder content = new StringBuilder();

        // Use BufferedReader to read the file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Append each line to the StringBuilder
                content.append(line).append(System.lineSeparator());  // Appends line break after each line
            }
        } catch (IOException e) {
            // Handle any IOException that might occur (e.g., file not found)
            System.out.println("Could not read JSON file");
        }

        // Return the content as a string
        return content.toString();
    }
}
