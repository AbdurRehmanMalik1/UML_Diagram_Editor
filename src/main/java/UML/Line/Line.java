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
        customDraw();
    }

    public void updateLineStart() {
        // Request layout update to ensure the bounds are recalculated
        startObject.requestLayout();

        Platform.runLater(() -> {
            // Get the position relative to the parent (canvas or pane)
            double startX = startObject.localToParent(startObject.getBoundsInLocal()).getMinX();
            double startY = startObject.localToParent(startObject.getBoundsInLocal()).getMinY();

            // Debugging output to verify bounds
            //System.out.println("Start X: " + startX + ", Start Y: " + startY);

            // Update the line start position
            this.setStartX(startX);
            this.setStartY(startY);
            customDraw();
        });
    }

    public void updateLineEnd() {
        // Request layout update to ensure the bounds are recalculated
        endObject.requestLayout();

        Platform.runLater(() -> {
            // Get the position relative to the parent (canvas or pane)
            double endX = endObject.localToParent(endObject.getBoundsInLocal()).getMinX();
            double endY = endObject.localToParent(endObject.getBoundsInLocal()).getMinY();

            // Debugging output to verify bounds
            System.out.println("End X: " + endX + ", End Y: " + endY);

            // Update the line end position
            this.setEndX(endX);
            this.setEndY(endY);
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
        updateLineStart();
    }

    public UMLObject getEndObject() {
        return endObject;
    }

    public void setEndObject(UMLObject endObject) {
        this.endObject = endObject;
        updateLineEnd();
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
