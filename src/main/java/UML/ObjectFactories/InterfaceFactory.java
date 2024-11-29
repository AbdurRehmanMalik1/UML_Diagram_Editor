package UML.ObjectFactories;

import UML.Objects.ClassObject;
import UML.Objects.InterfaceObject;
import UML.Objects.UMLObject;

public class InterfaceFactory implements ObjectFactory{

    @Override
    public UMLObject create(){
        return new InterfaceObject();
    }
}
