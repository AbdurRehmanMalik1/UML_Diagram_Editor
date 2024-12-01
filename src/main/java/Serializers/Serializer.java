package Serializers;

import Models.Model;
import UML.Objects.UMLObject;

public interface Serializer {

    public void serialize(UMLObject umlObject);
    public void serialize(Model model);
    public Model deserialize(String s , Class<? extends Model> clazz);
}
