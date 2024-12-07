package UML.ObjectFactories;
import UML.Objects.UMLObject;
import UML.Objects.UseCaseObject;

public class UseCaseFactory {
    public UMLObject create(){
        return new UseCaseObject();
    }
}
