package UML.ObjectFactories;

import UML.Objects.ClassObject;
import UML.Objects.UMLObject;

public class ClassFactory implements ObjectFactory{

    @Override
    public UMLObject create(){
        return new ClassObject();
    }

}
