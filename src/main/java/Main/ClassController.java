package Main;

import UML.Objects.UMLObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;

public class ClassController extends DiagramController {
    @FXML
    protected ToggleButton classButton;
    @FXML
    protected ToggleButton interfaceButton;
    @FXML
    protected Button associationButton;
    @FXML
    protected Button aggregationButton;
    @FXML
    protected Button compositionButton;
    @FXML
    protected Button inheritanceButton;


    public void setButtonsToggle(){
        buttonToggleGroup = new ToggleGroup();
        classButton.setToggleGroup(buttonToggleGroup);
        interfaceButton.setToggleGroup(buttonToggleGroup);
        canvas.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                ToggleButton button =(ToggleButton) buttonToggleGroup.getSelectedToggle();
                if (button!=null && drawObjectFunc!=null) {
                    double x = event.getX();
                    double y = event.getY();
                    if (x >= 0 && x <= canvas.getWidth() && y >= 0 && y <= canvas.getHeight()) {
                        drawObjectFunc.accept(x,y);
                    } else {
                        System.out.println("Can't add an object here");
                    }
                    drawObjectFunc = null;
                    button.setSelected(false);
                }
                //contextMenu.hideContextMenu();
            }
        });
    }
    @FXML
    public void onAddClassDiagramClick() {
        drawObjectFunc = this::drawClass;
    }
    public void drawClass(double x , double y){
        UMLObject classDiagram = objectFactory.createClassObject();
        addToCanvas(classDiagram, x, y);
    }
    @FXML
    public void onAddInterfaceDiagramClick() {
        drawObjectFunc = this::drawInterface;
    }
    public void drawInterface(double x , double y){
        UMLObject interfaceDiagram = objectFactory.createInterfaceObject();
        addToCanvas(interfaceDiagram, x, y);
    }
    @FXML
    public void onDrawAssociationClick() {
        unselectToggleButton();
        handleLineDrawing("Association");
    }
    @FXML
    public void onDrawInheritanceClick() {
        unselectToggleButton();
        handleLineDrawing("Inheritance");
    }
    @FXML
    public void onDrawAggregationClick() {
        unselectToggleButton();
        handleLineDrawing("Aggregation");
    }
    @FXML
    public void onDrawCompositionClick() {
        unselectToggleButton();
        handleLineDrawing("Composition");
    }


}
