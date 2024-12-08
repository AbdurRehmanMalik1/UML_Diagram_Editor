package UML.ObjectFactories;

import Models.*;
import UML.Objects.*;

/**
 * Factory class responsible for creating UML objects based on the provided models.
 * This class delegates the creation process to specialized factories such as `InterfaceFactory`, `ClassFactory`, `UseCaseFactory`, and `ActorFactory`.
 */
public class ObjectFactory {
    static InterfaceFactory interfaceFactory;
    static ClassFactory classFactory;
    static UseCaseFactory useCaseFactory;
    static ActorFactory actorFactory;

    /**
     * Constructor initializes the specific UML object factories.
     */
    public ObjectFactory() {
        interfaceFactory = new InterfaceFactory();
        classFactory = new ClassFactory();
        useCaseFactory = new UseCaseFactory();
        actorFactory = new ActorFactory();
    }

    /**
     * Creates a UMLObject based on the provided model type.
     *
     * @param model The model representing a UML element (e.g., class, interface, use case, actor).
     * @return A corresponding `UMLObject` or `null` if the model type is not recognized.
     */
    public UMLObject createUMLObject(Model model) {
        UMLObject umlObject = null;
        if (model != null) {
            if (model instanceof ClassModel) {
                umlObject = classFactory.create();
            } else if (model instanceof InterfaceModel) {
                umlObject = interfaceFactory.create();
            } else if (model instanceof UseCaseModel) {
                umlObject = useCaseFactory.create();
            } else if (model instanceof ActorModel) {
                umlObject = actorFactory.create();
            }
            assert umlObject != null;
            umlObject.setModel(model);
        }
        return umlObject;
    }

    /**
     * Creates a default UML class object.
     *
     * @return A new instance of `ClassObject`.
     */
    public UMLObject createClassObject() {
        return new ClassObject();
    }

    /**
     * Creates a default UML interface object.
     *
     * @return A new instance of `InterfaceObject`.
     */
    public UMLObject createInterfaceObject() {
        return new InterfaceObject();
    }

    /**
     * Creates a default UML use case object.
     *
     * @return A new instance of `UseCaseObject`.
     */
    public UMLObject createUseCaseObject() {
        return new UseCaseObject();
    }

    /**
     * Creates a default UML actor object.
     *
     * @return A new instance of `ActorObject`.
     */
    public UMLObject createActorObject() {
        return new ActorObject();
    }

    /**
     * Creates a copy of an existing UML object based on the provided model.
     *
     * @param model The model representing the UML element to be copied.
     * @return A new `UMLObject` that is a copy of the provided model or `null` if the model type is not recognized.
     */
    public UMLObject copyUMLObject(Model model) {
        UMLObject copiedUMLObject = null;
        if (model != null) {
            Model copiedModel = null;
            if (model instanceof ClassModel classModel) {
                copiedModel = new ClassModel(classModel);
            } else if (model instanceof InterfaceModel interfaceModel) {
                copiedModel = new InterfaceModel(interfaceModel);
            } else if (model instanceof UseCaseModel useCaseModel) {
                copiedModel = new UseCaseModel(useCaseModel);
            } else if (model instanceof ActorModel actorModel) {
                copiedModel = new ActorModel(actorModel);
            }
            if (copiedModel != null) {
                copiedUMLObject = createUMLObject(copiedModel);
            }
        }
        return copiedUMLObject;
    }
}
