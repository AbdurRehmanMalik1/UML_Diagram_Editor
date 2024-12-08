package UML.ObjectFactories;
import UML.Objects.UMLObject;
import UML.Objects.UseCaseObject;


/**
 * Factory class responsible for creating instances of `UseCaseObject`.
 * This class abstracts the creation of use case objects within a UML diagram.
 */
public class UseCaseFactory {

    /**
     * Creates a new `UseCaseObject`.
     *
     * @return A new instance of `UseCaseObject`.
     */
    public UMLObject create(){
        return new UseCaseObject();
    }
}
