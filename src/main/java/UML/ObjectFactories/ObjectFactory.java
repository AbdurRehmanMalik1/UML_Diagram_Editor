package UML.ObjectFactories;

import Models.ClassModel;
import Models.InterfaceModel;
import Models.Model;
import UML.Objects.*;

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
    public UMLObject createUseCaseObject(){return  new UseCaseObject();}
    public UMLObject createActorObject(){return  new ActorObject();}

    public UMLObject copyUMLObject(Model model) {
        UMLObject copiedUMLObject = null;
        if (model != null) {
            Model copiedModel = null;
            if (model instanceof ClassModel classModel) {
                copiedModel = new ClassModel(classModel);
            } else if (model instanceof InterfaceModel interfaceModel) {
                copiedModel = new InterfaceModel(interfaceModel);
            }

            if (copiedModel != null) {
                copiedUMLObject = createUMLObject(copiedModel);
            }
        }
        return copiedUMLObject;
    }
}
