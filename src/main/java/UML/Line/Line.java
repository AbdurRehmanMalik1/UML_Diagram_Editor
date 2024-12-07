package UML.Line;

import Models.AssociationModel;
import UML.Objects.UMLObject;
import Util.DistanceCalc;
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
    protected EditableField associationNameField;  // New field for the association name

    public Line(double startX, double startY, double endX, double endY, Pane parentPane,
                AssociationModel associationModel, UMLObject startObject, UMLObject endObject) {
        super(startX, startY, endX, endY);
        this.parentPane = parentPane;
        this.associationModel = associationModel;
        this.startObject = startObject;
        this.endObject = endObject;

        // Initialize the EditableFields for multiplicity
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

        // Initialize the EditableField for the association name
        associationNameField = new EditableField(
                associationModel.getAssociationName() != null ? associationModel.getAssociationName() : "Association Name",
                this::reloadModel
        );

        // Add the EditableFields to the parent pane
        parentPane.getChildren().addAll(startMultiplicityField, endMultiplicityField, associationNameField);

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
        updateMultiplicityPosition(false);  // Update end multiplicity field position too
        updateAssociationNamePosition();  // Position the association name field
        reloadModel();
    }

    public void updateLinePosition(UMLObject object, boolean isStart) {
        Platform.runLater(() -> {
            // Calculate the shortest distance points between object1 and object2
            DistanceCalc.ResultPoint resultPoint = DistanceCalc.getShortestDistance(startObject, endObject);
            double startX = resultPoint.point1.x;
            double startY = resultPoint.point1.y;
            double endX = resultPoint.point2.x;
            double endY = resultPoint.point2.y;

            // Update line's start or end position based on isStart
            if (isStart) {
                this.setStartX(startX);
                this.setStartY(startY);
                updateMultiplicityPosition(true);
            } else {
                this.setEndX(endX);
                this.setEndY(endY);
                updateMultiplicityPosition(false);
            }

            // Update the association name and redraw the line
            updateAssociationNamePosition();
            customDraw();
        });
    }
    private void updateMultiplicityPosition(boolean isStart) {
        if(startMultiplicityField != null) {
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

    private void updateAssociationNamePosition() {
        if (associationNameField != null) {
            double startX = this.getStartX();
            double startY = this.getStartY();
            double endX = this.getEndX();
            double endY = this.getEndY();

            // Calculate the midpoint for the association name
            double midX = (startX + endX) / 2;
            double midY = (startY + endY) / 2;

            // Position the association name text field at the midpoint
            associationNameField.setLayoutX(midX);
            associationNameField.setLayoutY(midY);
        }
    }

    public abstract void customDraw();

    public AssociationModel getAssociationModel() {
        return associationModel;
    }

    public void reloadModel() {
        if (startMultiplicityField != null && endMultiplicityField != null && associationNameField != null) {
            String start = startMultiplicityField.getText().isEmpty() ? "1" : startMultiplicityField.getText();
            String end = endMultiplicityField.getText().isEmpty() ? "1" : endMultiplicityField.getText();
            String associationName = associationNameField.getText().isEmpty() ? "" : associationNameField.getText();

            associationModel.setStartMultiplicity(start);
            associationModel.setEndMultiplicity(end);
            associationModel.setAssociationName(associationName);
        }
    }

    public void setAssociationModel(AssociationModel associationModel) {
        this.associationModel = associationModel;
        if (associationModel != null) {
            startMultiplicityField.setText(associationModel.getStartMultiplicity() != null ? associationModel.getStartMultiplicity() : "1");
            endMultiplicityField.setText(associationModel.getEndMultiplicity() != null ? associationModel.getEndMultiplicity() : "1");
            associationNameField.setText(associationModel.getAssociationName() != null ? associationModel.getAssociationName() : "");
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
            parentPane.getChildren().remove(associationNameField);  // Remove the association name field
        }
    }

    // Set multiplicity values (optional)
    public void setMultiplicity(String startMultiplicity, String endMultiplicity) {
        startMultiplicityField.setText(startMultiplicity);
        endMultiplicityField.setText(endMultiplicity);
    }
}
