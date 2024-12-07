package UML.Objects;

import Controllers.InterfaceDiagramController;
import Models.InterfaceModel;
import Models.Model;
import UML.UI_Components.EditableField;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class InterfaceObject extends UMLObject {
    private final Group groupDiagram;
    private VBox detailsBox;
    private EditableField className;
    private List<StackPane> methods;
    private VBox methodBox;
    private InterfaceDiagramController controller;

    public InterfaceObject() {
        super();
        groupDiagram = new Group();
        model = new InterfaceModel();

        initComponents();

        groupDiagram.getChildren().add(controller);

        groupDiagram.getChildren().addFirst(outerRect);
        outerRect.setVisibility(false);

        getChildren().add(groupDiagram);

        this.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 1)
                requestFocus();
        });

        this.layoutBoundsProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(this::resizeOuterRect));


        this.focusedProperty().addListener((observable, oldValue, newValue) ->{
            outerRect.setVisibility(newValue);
            if (!newValue) {
                hideController();
            } else {
                showController();
            }
        });
    }
    private void hideController() {
        // This will hide the controller when the InterfaceObject loses focus
        groupDiagram.getChildren().remove(controller);
    }

    private void showController() {
        // This will show the controller when the InterfaceObject gains focus
        if (!groupDiagram.getChildren().contains( controller)) {
            groupDiagram.getChildren().add( controller);
        }
    }
    @Override
    public Model getModel(){
        return model;
    }
    @Override
    public double getWidth() {
        return detailsBox.getWidth();
    }

    @Override
    public double getHeight() {
        return detailsBox.getHeight();
    }

    public void resizeOuterRect() {
        Bounds boundsInScene  = detailsBox.localToScene(detailsBox.getBoundsInLocal());
        Bounds boundsInGroup = groupDiagram.sceneToLocal(boundsInScene);
        outerRect.setLocation(boundsInGroup.getMinX()-2,boundsInGroup.getMinY()-2);
        outerRect.setSize(boundsInGroup.getWidth() + 4,boundsInGroup.getHeight() + 4);
    }

    private void initComponents() {
        detailsBox = new VBox();
        detailsBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        VBox topBox = new VBox();
        Label interfaceLabel =  new Label("<<interface>>");
        interfaceLabel.setAlignment(Pos.BASELINE_CENTER);
        HBox interfaceLabelWrapper = new HBox(interfaceLabel);
        interfaceLabelWrapper.setAlignment(Pos.BASELINE_CENTER);
        className = new EditableField("Interface Name");
        HBox classNameWrapper = new HBox(className);
        classNameWrapper.setAlignment(Pos.BASELINE_CENTER);

        topBox.getChildren().addAll(interfaceLabelWrapper,classNameWrapper);
        detailsBox.getChildren().add(topBox);

        controller = new InterfaceDiagramController(this, interfaceLabelWrapper);

        methodBox = new VBox();
        methodBox.setPadding(new Insets(5,0,5,0));
        methods = new ArrayList<>();
        methodBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 0, 0))));

        detailsBox.getChildren().add(methodBox);

        groupDiagram.getChildren().add(detailsBox);
    }



    public void addMethod(String temp) {
        StackPane method = new EditableField(temp);
        methods.add(method);
        methodBox.getChildren().add(method);
    }

    @Override
    public void setModel(Model model){
        InterfaceModel interfaceModel = (InterfaceModel) model;
        this.setModel(interfaceModel);
    }
    public void setModel(InterfaceModel model) {
        this.model = model;

        methods.clear();

        if (model.getInterfaceName() != null && !model.getInterfaceName().isEmpty()) {
            className.setText(model.getInterfaceName());
        }

        for (String method : model.getMethods()) {
            addMethod(method);
        }
        this.setLayoutX(model.getX());
        this.setLayoutY(model.getY());
    }
    public void reloadModel() {
        super.reloadModel();

        InterfaceModel downcastModel = (InterfaceModel) model;

        downcastModel.setInterfaceName(className.getText());

        if (downcastModel.getMethods() != null) {
            downcastModel.getMethods().clear();
        }
        for (StackPane methodStackPane : methods) {
            if (methodStackPane instanceof EditableField editableField) {
                downcastModel.addMethod(editableField.getText());
            }
        }
    }

}
