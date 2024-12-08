package UML.ObjectFactories;

import UML.Objects.ClassObject;
import UML.Objects.UMLObject;


/**
 * Factory class responsible for creating instances of `ClassObject`.
 * This class abstracts the creation of class objects within a UML diagram.
 */
public class ClassFactory {

    /**
     * Creates a new `ClassObject`.
     *
     * @return A new instance of `ClassObject`.
     */
    public UMLObject create(){
        return new ClassObject();
    }

}
