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

/**
 * Represents an Interface object in a UML diagram.
 */
public class InterfaceObject extends UMLObject {
    private final Group groupDiagram;
    private VBox detailsBox;
    private EditableField className;
    private List<StackPane> methods;
    private VBox methodBox;
    private InterfaceButtonController controller;

    /**
     * Constructor to initialize the InterfaceObject.
     */
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

        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            outerRect.setVisibility(newValue);
            if (!newValue) {
                hideController();
            } else {
                showController();
            }
        });
    }

    /**
     * Hides the controller when the InterfaceObject loses focus.
     */
    private void hideController() {
        groupDiagram.getChildren().remove(controller);
    }

    /**
     * Shows the controller when the InterfaceObject gains focus.
     */
    private void showController() {
        if (!groupDiagram.getChildren().contains(controller)) {
            groupDiagram.getChildren().add(controller);
        }
    }

    /**
     * Hides the controller if none of the methods are focused and the InterfaceObject is not focused.
     */
    private void hideControllerIfNotFocused() {
        boolean isMethodFocused = false;
        for (StackPane e : methods) {
            if (((EditableField) e).isTextFieldFocused())
                isMethodFocused = true;
        }
        if (!isMethodFocused && !this.isFocused()) {
            hideController();
        }
    }

    /**
     * Adds focus listeners to the methodBox and methods.
     * @param box the VBox containing the methods.
     * @param elements the list of StackPanes representing methods.
     */
    private void addFocusListeners(VBox box, List<StackPane> elements) {
        for (StackPane element : elements) {
            element.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    hideControllerIfNotFocused();
                }
            });
        }
    }

    /**
     * Gets the model associated with this InterfaceObject.
     * @return the InterfaceModel.
     */
    @Override
    public Model getModel() {
        return model;
    }

    /**
     * Gets the width of the InterfaceObject.
     * @return the width.
     */
    @Override
    public double getWidth() {
        return detailsBox.getWidth();
    }

    /**
     * Gets the height of the InterfaceObject.
     * @return the height.
     */
    @Override
    public double getHeight() {
        return detailsBox.getHeight();
    }

    /**
     * Resizes the outer rectangle based on the InterfaceObject's bounds.
     */
    public void resizeOuterRect() {
        Bounds boundsInScene = detailsBox.localToScene(detailsBox.getBoundsInLocal());
        Bounds boundsInGroup = groupDiagram.sceneToLocal(boundsInScene);
        outerRect.setLocation(boundsInGroup.getMinX() - 2, boundsInGroup.getMinY() - 2);
        outerRect.setSize(boundsInGroup.getWidth() + 4, boundsInGroup.getHeight() + 4);
    }

    /**
     * Initializes the components of the InterfaceObject.
     */
    private void initComponents() {
        detailsBox = new VBox();
        detailsBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        detailsBox.setBackground(Background.fill(Color.rgb(231, 227, 227)));

        VBox topBox = new VBox();
        Label interfaceLabel = new Label("<<interface>>");
        interfaceLabel.setAlignment(Pos.BASELINE_CENTER);
        HBox interfaceLabelWrapper = new HBox(interfaceLabel);
        interfaceLabelWrapper.setAlignment(Pos.BASELINE_CENTER);
        className = new EditableField("Interface Name");
        HBox classNameWrapper = new HBox(className);
        classNameWrapper.setAlignment(Pos.BASELINE_CENTER);

        topBox.getChildren().addAll(interfaceLabelWrapper, classNameWrapper);
        detailsBox.getChildren().add(topBox);

        controller = new InterfaceButtonController(this, interfaceLabelWrapper);

        methodBox = new VBox();
        methodBox.setPadding(new Insets(5, 0, 5, 0));
        methods = new ArrayList<>();
        methodBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 0, 0))));

        detailsBox.getChildren().add(methodBox);

        groupDiagram.getChildren().add(detailsBox);
        addFocusListeners(methodBox, methods);
    }

    /**
     * Adds a method to the InterfaceObject.
     * @param temp the Method to add.
     */
    public void addMethod(Method temp) {
        StackPane method = new EditableField(temp.toString());
        methods.add(method);
        methodBox.getChildren().add(method);
    }

    /**
     * Sets the model for the InterfaceObject.
     * @param model the InterfaceModel to set.
     */
    @Override
    public void setModel(Model model) {
        InterfaceModel interfaceModel = (InterfaceModel) model;
        this.setModel(interfaceModel);
    }

    /**
     * Sets the InterfaceModel for the InterfaceObject.
     * @param model the InterfaceModel to set.
     */
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

    /**
     * Parses a method description into a Method object.
     * @param methodText the method description text.
     * @return the parsed Method object.
     */
    private Method parseMethod(String methodText) {
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
            if (returnType.isEmpty()) {
                returnType = "void";
                methodText = methodText + returnType;
            }
        }

        methodName = methodText.trim(); // The rest is the method name

        return new Method(returnType, methodName, accessModifier);
    }

    /**
     * Reloads the model for the InterfaceObject.
     */
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
                downcastModel.getMethods().add(method);
            }
        }
    }
    /**
     * Returns the currently selected method (focused) from the list of methods.
     * A method is considered selected if its text field is currently focused.
     *
     * @return the selected StackPane representing the method, or null if no method is focused.
     */
    public StackPane getSelectedMethod() {
        for (StackPane s : methods) {
            if (s instanceof EditableField && ((EditableField) s).isTextFieldFocused()) {
                return s;
            }
        }
        return null;
    }

    /**
     * Removes a specified method from the InterfaceObject.
     * This method will also update the model to reflect the changes.
     *
     * @param selectedMethod the StackPane representing the method to be removed.
     */
    public void removeMethod(StackPane selectedMethod) {
        methodBox.getChildren().remove(selectedMethod);
        methods.remove(selectedMethod);
        reloadModel();
    }

    /**
     * Returns the list of methods associated with the InterfaceObject.
     *
     * @return a list of StackPanes, each representing a method.
     */
    public List<StackPane> getMethods() {
        return methods;
    }
}
