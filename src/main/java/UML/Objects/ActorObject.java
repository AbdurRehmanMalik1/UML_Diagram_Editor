package UML.Objects;

import Models.ActorModel;
import Models.Model;
import UML.UI_Components.EditableField;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class ActorObject extends UMLObject {
    private final SVGPath actorSVG;
    private final EditableField field;
    private final VBox container;

    public ActorObject(String initialText) {
        super();

        if(model==null)
            model = new ActorModel();
        actorSVG = new SVGPath();
        this.setOnMouseClicked(e->{
            requestFocus();
        });
        String actorName = downcastModel().getActorName();
//        System.out.println("Downcast Actor name = " + actorName);
        if (actorName == null || actorName.isEmpty() || actorName.equals("Actor")) {

            downcastModel().setActorName("Actor");
        }

        actorSVG.setContent("M75 25a20 20 0 1 0 0.01 0 M75 65v60 M50 80h50 M75 125l-25 40 M75 125l25 40");
        actorSVG.setFill(Color.TRANSPARENT);
        actorSVG.setStroke(Color.BLACK);
        actorSVG.setStrokeWidth(1);

        field = new EditableField(initialText, this::reloadModel);


        container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(actorSVG, field);

        getChildren().add(container);

        outerRect.setSize(getWidth(), getHeight());
        outerRect.setVisibility(false);
        this.focusedProperty().addListener((observable, oldValue, newValue) ->{
            outerRect.setVisibility(newValue);
        });
    }

    public ActorModel downcastModel(){
        return (ActorModel) model;
    }
    public ActorObject() {
        this("Actor");
    }

    @Override
    public double getWidth() {
        return Math.max(actorSVG.getLayoutBounds().getWidth(), field.getWidth());
    }

    @Override
    public double getHeight() {
        return actorSVG.getLayoutBounds().getHeight() + field.getHeight() + 5; // 5px spacing
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
        field.setText(downcastModel().getActorName());
        this.setLayoutX(model.getX());
        this.setLayoutY(model.getY());
        reloadModel();
    }

    @Override
    public void reloadModel() {
        if (model != null) {
            downcastModel().setActorName(field.getText());
            model.setCoordinate(this.getLayoutX(),this.getLayoutY());

        }
    }

    public void setPosition(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
    }

    public void setActorColor(Color color) {
        actorSVG.setFill(color);
    }

    public String getActorName() {
        return field.getText();
    }
}
