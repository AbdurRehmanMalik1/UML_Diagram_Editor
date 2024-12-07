package UML.ObjectFactories;

import UML.Objects.ActorObject;
import UML.Objects.UMLObject;

public class ActorFactory {
    public UMLObject create(){
        return new ActorObject();
    }
}
