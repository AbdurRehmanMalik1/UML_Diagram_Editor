package Serializers;

import Models.ClassModel;
import Models.Model;
import UML.UMLObject;

public interface Serializer {

    public void serialize(UMLObject umlObject);
    public void serialize(Model model);
    public void deserialize(String s , Class<ClassModel> clazz);
}
