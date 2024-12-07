package Main;

import UML.Objects.UMLObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;

public class UseCaseController extends DiagramController {
    @FXML
    private ToggleButton useCaseButton;
    @FXML
    private ToggleButton actorButton;

    @Override
    public void setButtonsToggle(){
        buttonToggleGroup = new ToggleGroup();
        useCaseButton.setToggleGroup(buttonToggleGroup);
        actorButton.setToggleGroup(buttonToggleGroup);
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
            }
        });
    }
    @FXML
    public void onAddUseCaseClick() {
        drawObjectFunc = this::drawUseCase;
    }
    public void drawUseCase(double x , double y){
        UMLObject useCaseObject = objectFactory.createUseCaseObject();
        addToCanvas(useCaseObject, x, y);
    }
    @FXML
    public void onAddActorClick() {
        drawObjectFunc = this::drawActor;
    }

    public void drawActor(double x , double y){
        UMLObject actorObject = objectFactory.createActorObject();
        addToCanvas(actorObject, x, y);
    }
    public void onDrawClick(String type) {
        unselectToggleButton();
        handleLineDrawing(type);
    }

    @FXML
    public void onDrawUsesClick() {
        onDrawClick("Uses");
    }

    @FXML
    public void onDrawIncludeClick() {
        onDrawClick("Includes");
    }

    @FXML
    public void onDrawExtendsClick() {
        onDrawClick("Extends");
    }


}
