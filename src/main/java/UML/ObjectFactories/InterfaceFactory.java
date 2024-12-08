package UML.ObjectFactories;

import UML.Objects.InterfaceObject;
import UML.Objects.UMLObject;

/**
 * Factory class responsible for creating instances of `InterfaceObject`.
 * This class abstracts the creation of interface objects within a UML diagram.
 */
public class InterfaceFactory {

    /**
     * Creates a new `InterfaceObject`.
     *
     * @return A new instance of `InterfaceObject`.
     */
    public UMLObject create() {
        return new InterfaceObject();
    }
}
