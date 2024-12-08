package UML.Objects;

import Controllers.InterfaceButtonController;
import Models.CD.Method;
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
    private InterfaceButtonController controller;

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
        // This will hide the controller when the ClassObject loses focus
        groupDiagram.getChildren().remove(controller);
    }
    private void showController() {
        // This will show the controller when the InterfaceObject gains focus
        if (!groupDiagram.getChildren().contains( controller)) {
            groupDiagram.getChildren().add( controller);
        }

    }


    private void hideControllerIfNotFocused() {
        boolean isMethodFocused = false;
        for(StackPane e : methods){
            if(((EditableField)e).isTextFieldFocused())
                isMethodFocused = true;
        }
        if (!isMethodFocused && !this.isFocused()) {
            hideController();
        }
    }
    private void addFocusListeners(VBox box, List<StackPane> elements) {
        for (StackPane element : elements) {
            element.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    hideControllerIfNotFocused();
                }
            });
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
        detailsBox.setBackground(Background.fill(Color.rgb(231, 227, 227)));

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

        controller = new InterfaceButtonController(this, interfaceLabelWrapper);

        methodBox = new VBox();
        methodBox.setPadding(new Insets(5,0,5,0));
        methods = new ArrayList<>();
        methodBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 0, 0))));

        detailsBox.getChildren().add(methodBox);

        groupDiagram.getChildren().add(detailsBox);
        addFocusListeners(methodBox, methods);
    }



    public void addMethod(Method temp) {
        StackPane method = new EditableField(temp.toString());
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

        for (Method method : model.getMethods()) {
            addMethod(method);
        }
        this.setLayoutX(model.getX());
        this.setLayoutY(model.getY());
    }


    private Method parseMethod(String methodText) {
        System.out.println("Method Text:[" + methodText + "]");

        String accessModifier = "public"; // Default access modifier
        String returnType = "void"; // Default return type
        String methodName = "unknown"; // Default method name

        // Check for access modifiers
        if (methodText.startsWith("+ ")) {
            accessModifier = "public";
            methodText = methodText.substring(2).trim(); // Remove 'public ' prefix
        } else if (methodText.startsWith("# ")) {
            accessModifier = "protected";
            methodText = methodText.substring(2).trim(); // Remove 'protected ' prefix
        } else if (methodText.startsWith("- ")) {
            accessModifier = "private";
            methodText = methodText.substring(2).trim(); // Remove 'private ' prefix
        }

        // Use regular expressions to split the method text into return type, name, and parameters
        String regex = "^\\s*(\\w+)\\s+([\\w]+)\\s*\\(([^)]*)\\)\\s*:\\s*(\\w+)\\s*$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(methodText);

        int colonIndex = methodText.lastIndexOf(":");
        if (colonIndex != -1) {
            returnType = methodText.substring(colonIndex + 1).trim(); // Extract the return type
            methodText = methodText.substring(0, colonIndex).trim(); // Remove the return type part from the method text
            if(returnType.isEmpty()) {
                returnType = "void";
                methodText = methodText + returnType;
            }
        }

        methodName = methodText.trim(); // The rest is the method name

        return new Method(returnType, methodName, accessModifier);
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
                String methodText = editableField.getText();
                Method method = parseMethod(methodText);
                method.setAbstract(editableField.getIsAbstract());

                // If no access modifier is present or invalid, set to default (private)
                if (method.getAccessModifier() == null || !(method.getAccessModifier().equals("private") || method.getAccessModifier().equals("public") || method.getAccessModifier().equals("protected"))) {
                    method.setAccessModifier("private");
                }

                downcastModel.addMethod(method);
            }
        }
    }

    public StackPane getSelectedMethod() {
        for (StackPane s : methods) {
            if (s instanceof EditableField && ((EditableField) s).isTextFieldFocused()) {
                return s;
            }
        }
        return null;
    }

    public void removeMethod(StackPane selectedMethod) {
        methodBox.getChildren().remove(selectedMethod);
        methods.remove(selectedMethod);
        reloadModel();
    }
    public List<StackPane> getMethods(){
        return methods;
    }
}
