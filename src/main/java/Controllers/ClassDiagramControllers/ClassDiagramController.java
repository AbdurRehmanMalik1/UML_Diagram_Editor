package Controllers.ClassDiagramControllers;

import Models.CD.Method;
import Models.ClassModel;
import UML.Objects.ClassObject;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClassDiagramController extends VBox {
    private ClassObject parentClass;
    private final HBox classNameWrapper;

    private Button addAttributeButton;
    private Button addMethodButton;
    public ClassDiagramController(ClassObject parentClass, HBox classNameWrapper) {
        this.parentClass = parentClass;
        this.classNameWrapper = classNameWrapper;


        initComponents();

        addMethodButton.setFocusTraversable(false);
        addAttributeButton.setFocusTraversable(false);
        Platform.runLater(this::adjustButtonPosition);

        classNameWrapper.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) ->
                Platform.runLater(this::adjustButtonPosition));

        addButtonEvents();
    }
    public void setParentClass(ClassObject parentClass){
        this.parentClass = parentClass;
    }

    private void initComponents() {
        addAttributeButton = new Button("Add Attribute");
        addMethodButton = new Button("Add Method");

        getChildren().add(addAttributeButton);
        getChildren().add(addMethodButton);
    }

    private void addButtonEvents() {
        addAttributeButton.setOnMouseClicked(event -> {
            String attribute = "New Attribute";
            parentClass.addAttribute(attribute);
            ClassModel classModel = (ClassModel) parentClass.getModel();
            classModel.addAttribute(attribute);
            //parentClass.resizeOuterRect();
        });
        addMethodButton.setOnMouseClicked(event -> {
            String m = "New Method";
            Method method = new Method(m);
            parentClass.addMethod(method);
            //parentClass.resizeOuterRect();
        });
    }

    private void adjustButtonPosition() {
        if (classNameWrapper != null && classNameWrapper.getLayoutBounds() != null) {
            double wrapperWidth = classNameWrapper.getLayoutBounds().getWidth();
            setLayoutX(classNameWrapper.getLayoutX() + wrapperWidth);
        }
    }
}
