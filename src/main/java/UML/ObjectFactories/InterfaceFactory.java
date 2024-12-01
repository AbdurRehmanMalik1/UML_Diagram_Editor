package UML.ObjectFactories;

import UML.Objects.InterfaceObject;
import UML.Objects.UMLObject;

public class InterfaceFactory {

    public UMLObject create(){
        return new InterfaceObject();
    }
}
