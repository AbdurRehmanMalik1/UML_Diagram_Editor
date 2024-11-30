package UML.ObjectFactories;

import Models.ClassModel;
import Models.InterfaceModel;
import Models.Model;
import UML.Objects.ClassObject;
import UML.Objects.InterfaceObject;
import UML.Objects.UMLObject;

public class ObjectFactory {
    static InterfaceFactory interfaceFactory ;
    static ClassFactory classFactory;
    public ObjectFactory(){
        interfaceFactory = new InterfaceFactory();
        classFactory = new ClassFactory();
    }
    public UMLObject createUMLObject(Model model){
        UMLObject umlObject = null;
        if(model instanceof ClassModel) {
            umlObject = classFactory.create();
            umlObject.setModel(model);
        }
        else if(model instanceof InterfaceModel){
            umlObject = interfaceFactory.create();
            umlObject.setModel(model);
        }
        return umlObject;
    }
    public UMLObject createClassObject(){
        return new ClassObject();
    }
    public UMLObject createInterfaceObject(){
        return new InterfaceObject();
    }
}
