package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import UML.UI_Components.EditableField;

public abstract class Line extends javafx.scene.shape.Line {
    public Pane parentPane;

    private AssociationModel associationModel;
    private UMLObject startObject;
    private UMLObject endObject;

    protected EditableField startMultiplicityField;
    protected EditableField endMultiplicityField;

    public Line(double startX, double startY, double endX, double endY, Pane parentPane,
                AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY);
        this.parentPane = parentPane;
        this.associationModel = associationModel;
        this.startObject = startObject;
        this.endObject = endObject;

        // Initialize the EditableFields

        startMultiplicityField = new EditableField(
                associationModel.getStartMultiplicity() != null && !associationModel.getStartMultiplicity().isEmpty()
                        ? associationModel.getStartMultiplicity()
                        : "1",
                this::reloadModel
        );

        endMultiplicityField = new EditableField(
                associationModel.getEndMultiplicity() != null && !associationModel.getEndMultiplicity().isEmpty()
                        ? associationModel.getEndMultiplicity()
                        : "1",
                this::reloadModel
        );

        // Add the EditableFields to the parent pane
        parentPane.getChildren().addAll(startMultiplicityField, endMultiplicityField);

        // Set focus behavior on the line
        this.setFocusTraversable(true);
        this.setOnMouseClicked(event -> this.requestFocus());
        this.focusedProperty().addListener((value, oldValue, newValue) -> {
            if (newValue) {
                this.setStroke(Color.BLUE);
            } else {
                this.setStroke(Color.BLACK);
            }
        });
        updateMultiplicityPosition(true);
        updateMultiplicityPosition(true);
        reloadModel();
    }

    public void updateLinePosition(UMLObject object, boolean isStart) {
        Platform.runLater(() -> {
            double posX = object.localToParent(object.getBoundsInLocal()).getMinX();
            double posY = object.localToParent(object.getBoundsInLocal()).getMinY();

            if (isStart) {
                this.setStartX(posX);
                this.setStartY(posY);
                updateMultiplicityPosition(true);
            } else {
                this.setEndX(posX);
                this.setEndY(posY);
                updateMultiplicityPosition(false);
            }
            customDraw();
        });
    }
    private void updateMultiplicityPosition(boolean isStart) {
        if(startMultiplicityField!=null) {
            double startX = this.getStartX();
            double startY = this.getStartY();
            double endX = this.getEndX();
            double endY = this.getEndY();

            double distanceFromLine = 0.2;
            double startOffsetX = startX + distanceFromLine * (endX - startX);
            double startOffsetY = startY + distanceFromLine * (endY - startY);

            double endOffsetX = endX - distanceFromLine * (endX - startX);
            double endOffsetY = endY - distanceFromLine * (endY - startY);

            if (isStart) {
                startMultiplicityField.setLayoutX(startOffsetX);
                startMultiplicityField.setLayoutY(startOffsetY);
            } else {
                endMultiplicityField.setLayoutX(endOffsetX);
                endMultiplicityField.setLayoutY(endOffsetY);
            }
        }
    }

    public abstract void customDraw();

    public AssociationModel getAssociationModel() {
        return associationModel;
    }

    public void reloadModel() {
        if(startMultiplicityField!=null) {
            String start = startMultiplicityField.getText().isEmpty() ? "1" : startMultiplicityField.getText();
            String end = endMultiplicityField.getText().isEmpty() ? "1" : endMultiplicityField.getText();

            associationModel.setStartMultiplicity(start);
            associationModel.setEndMultiplicity(end);
        }
    }


    public void setAssociationModel(AssociationModel associationModel) {
        this.associationModel = associationModel;
        if(associationModel!=null && associationModel.getStartMultiplicity()!=null && associationModel.getEndMultiplicity()!=null){
            startMultiplicityField.setText(String.valueOf(associationModel.getStartMultiplicity()));
            startMultiplicityField.setText(String.valueOf(associationModel.getEndMultiplicity()));
        }
    }

    public UMLObject getStartObject() {
        return startObject;
    }

    public void setStartObject(UMLObject startObject) {
        this.startObject = startObject;
    }

    public UMLObject getEndObject() {
        return endObject;
    }

    public void setEndObject(UMLObject endObject) {
        this.endObject = endObject;
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
        if (parentPane != null) {
            this.deleteOld();
            parentPane.getChildren().remove(this);
            parentPane.getChildren().remove(startMultiplicityField);
            parentPane.getChildren().remove(endMultiplicityField);
        }
    }

    // Set multiplicity values (optional)
    public void setMultiplicity(String startMultiplicity, String endMultiplicity) {
        startMultiplicityField.setText(startMultiplicity);
        endMultiplicityField.setText(endMultiplicity);
    }
}
