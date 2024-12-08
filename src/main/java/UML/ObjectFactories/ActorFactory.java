package UML.ObjectFactories;

import UML.Objects.ActorObject;
import UML.Objects.UMLObject;


/**
 * Factory class responsible for creating instances of `ActorObject`.
 * This class abstracts the creation of actor objects within a UML diagram.
 */
public class ActorFactory {


    /**
     * Creates a new `ActorObject`.
     *
     * @return A new instance of `ActorObject`.
     */
    public UMLObject create(){
        return new ActorObject();
    }
}
