package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Line extends javafx.scene.shape.Line {
    public Pane parentPane;

    private AssociationModel associationModel;
    private UMLObject startObject;
    private UMLObject endObject;

    public Line(double startX, double startY, double endX, double endY, Pane parentPane,
                AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY);
        this.parentPane = parentPane;
        this.associationModel = associationModel;
        this.startObject = startObject;
        this.endObject = endObject;
        this.setFocusTraversable(true);
        this.setOnMouseClicked(event->{
            this.requestFocus();
        });
        this.focusedProperty().addListener((value,oldValue,newValue)->{
            if(newValue){
                this.setStroke(Color.BLUE);
            }else
                this.setStroke(Color.BLACK);
        });
    }

    public void updateLinePosition(UMLObject object, boolean isStart) {
        //object.requestLayout();
        Platform.runLater(() -> {
            double posX = object.localToParent(object.getBoundsInLocal()).getMinX();
            double posY = object.localToParent(object.getBoundsInLocal()).getMinY();

            if (isStart) {
                this.setStartX(posX);
                this.setStartY(posY);
            } else {
                this.setEndX(posX);
                this.setEndY(posY);
            }
            customDraw();
        });
    }

    public abstract void customDraw();

    public AssociationModel getAssociationModel() {
        return associationModel;
    }

    public void setAssociationModel(AssociationModel associationModel) {
        this.associationModel = associationModel;
    }

    public UMLObject getStartObject() {
        return startObject;
    }

    public void setStartObject(UMLObject startObject) {
        this.startObject = startObject;
        //updateLinePosition(this.startObject,true);
        //updateLineStart();
    }

    public UMLObject getEndObject() {
        return endObject;
    }

    public void setEndObject(UMLObject endObject) {
        this.endObject = endObject;
       // updateLinePosition(this.startObject,false);
        //updateLineEnd();
    }
    protected abstract void deleteOld();

    public void delete() {
        if (startObject != null && startObject.getModel() != null && startObject.getAssociatedLines() != null) {
            startObject.getModel().removeStartAssociation(this.associationModel);
            startObject.getAssociatedLines().remove(this);
        }

        if (endObject != null && endObject.getModel() != null && endObject.getAssociatedLines() != null) {
            endObject.getModel().removeEndAssociation(this.associationModel);
            endObject.getAssociatedLines().remove(this);
        }
        if(parentPane!=null){
            this.deleteOld();
            parentPane.getChildren().remove(this);
        }
    }
}
