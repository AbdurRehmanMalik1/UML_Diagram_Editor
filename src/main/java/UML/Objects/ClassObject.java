package UML.Objects;

import Controllers.ClassButtonController;
import Models.CD.Attribute;
import Models.CD.Method;
import Models.ClassModel;
import Models.Model;
import UML.UI_Components.EditableField;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a Class Object in the UML diagram.
 * Extends `UMLObject` and provides specific behavior and rendering for class diagrams.
 */
public class ClassObject extends UMLObject {
    private final Group groupDiagram;
    private VBox detailsBox;
    private EditableField className;
    private List<StackPane> attributes;
    private List<StackPane> methods;
    private VBox attributeBox;
    private VBox methodBox;
    private ClassButtonController controller;

    /**
     * Constructor that initializes the ClassObject.
     * Sets up the class model and UI components.
     */
    public ClassObject() {
        super();
        model = new ClassModel();
        groupDiagram = new Group();

        initComponents();

        groupDiagram.getChildren().add((Node) controller);

        // Add outer rectangle visibility management
        groupDiagram.getChildren().addFirst(outerRect);
        outerRect.setVisibility(false);

        getChildren().add(groupDiagram);

        this.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 1) {
                requestFocus();
//                controller.setParentClass(this);
            }
        });

        // Update outer rectangle on layout changes
        this.layoutBoundsProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(this::resizeOuterRect)
        );

        // Show/hide controller based on focus state
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            outerRect.setVisibility(newValue);
            if (!newValue) {
                hideControllerIfNotFocused();
            } else {
                showController();
            }
        });
    }

    /**
     * Hides the controller from the UI.
     * Called when the ClassObject loses focus.
     */
    private void hideController() {
        groupDiagram.getChildren().remove(controller);
    }

    /**
     * Determines whether the controller should be hidden based on focus state.
     * This method checks if the ClassObject itself or any of its attributes or methods are focused.
     * If none of them are focused and the ClassObject loses focus, the controller is hidden.
     */
    private void hideControllerIfNotFocused() {
        boolean isAttributeFocused = false;
        for (StackPane e : attributes) {
            if (((EditableField) e).isTextFieldFocused())
                isAttributeFocused = true;
        }
        boolean isMethodFocused = false;
        for (StackPane e : methods) {
            if (((EditableField) e).isTextFieldFocused())
                isMethodFocused = true;
        }
        if (!isAttributeFocused && !isMethodFocused && !this.isFocused()) {
            hideController();
        }
    }

    /**
     * Adds focus listeners to the elements in a given VBox.
     * Hides the controller if any element loses focus.
     *
     * @param box      the VBox containing elements to add listeners to.
     * @param elements the list of elements to monitor for focus changes.
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
     * Shows the controller in the UI.
     * Called when the ClassObject gains focus.
     */
    private void showController() {
        if (!groupDiagram.getChildren().contains(controller)) {
            groupDiagram.getChildren().add(controller);
        }
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public void setModel(Model model) {
        ClassModel classModel = (ClassModel) model;
        this.setModel(classModel);
    }

    @Override
    public double getWidth() {
        return className.getWidth();
    }

    @Override
    public double getHeight() {
        return detailsBox.getHeight();
    }
    /**
     * Sets the model for the ClassObject and updates its properties based on the provided ClassModel.
     *
     * @param model the ClassModel to set as the model for this ClassObject.
     */
    public void setModel(ClassModel model) {
        this.model = model;
        if (model.isAbstract()) {
            className.toggleItalic();
        }
        if (model.getClassName() != null && !model.getClassName().isEmpty()) {
            className.setText(model.getClassName());
        }

        for (Attribute attribute : model.getAttributes()) {
            addAttribute(attribute);
        }

        for (Method method : model.getMethods()) {
            addMethod(method);
        }
        this.setLayoutX(model.getX());
        this.setLayoutY(model.getY());
    }

    /**
     * Resizes the outer rectangle surrounding the ClassObject to match the size and position of the detailsBox.
     */
    public void resizeOuterRect() {
        Bounds boundsInScene = detailsBox.localToScene(detailsBox.getBoundsInLocal());
        Bounds boundsInGroup = groupDiagram.sceneToLocal(boundsInScene);
        outerRect.setLocation(boundsInGroup.getMinX() - 2, boundsInGroup.getMinY() - 2);
        outerRect.setSize(boundsInGroup.getWidth() + 4, boundsInGroup.getHeight() + 4);
    }

    /**
     * Initializes the UI components for the ClassObject, such as the detailsBox, className field, attributes, and methods.
     */
    private void initComponents() {
        detailsBox = new VBox();
        detailsBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        detailsBox.setBackground(Background.fill(Color.rgb(231, 227, 227)));

        EditableField classNameField = new EditableField("Class Name", this::reloadModel);
        classNameField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.I) {
                classNameField.toggleItalic();
                ClassModel classModel = (ClassModel) model;
                classModel.setAbstract(!classModel.isAbstract());
            }
        });
        classNameField.setAlignment(Pos.BASELINE_CENTER);
        className = classNameField;
        HBox classNameWrapper = new HBox(className);
        classNameWrapper.setAlignment(Pos.BASELINE_CENTER);
        detailsBox.getChildren().add(classNameWrapper);

        controller = new ClassButtonController(this, classNameWrapper);

        attributeBox = new VBox();
        attributeBox.setPadding(new Insets(5, 0, 5, 0));
        attributes = new ArrayList<>();

        attributeBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 1, 0))));

        detailsBox.getChildren().add(attributeBox);

        methodBox = new VBox();
        methodBox.setPadding(new Insets(5, 0, 5, 0));
        methods = new ArrayList<>();
        detailsBox.getChildren().add(methodBox);

        groupDiagram.getChildren().add(detailsBox);
        addFocusListeners(attributeBox, attributes);
        addFocusListeners(methodBox, methods);
    }

    /**
     * Adds an attribute to the ClassObject.
     *
     * @param temp the Attribute to add.
     */
    public void addAttribute(Attribute temp) {
        StackPane attribute = new EditableField(temp.toString(), this::reloadModel);
        attribute.setFocusTraversable(true);
        attributes.add(attribute);
        attributeBox.getChildren().add(attribute);
    }

    /**
     * Adds a method to the ClassObject.
     *
     * @param temp the Method to add.
     */
    public void addMethod(Method temp) {
        EditableField method = new EditableField(temp.toString(), this::reloadModel);
        method.setIsAbstract(temp.isAbstract());
        methods.add(method);
        methodBox.getChildren().add(method);
        method.setOnKeyPressed(keyEvent -> {
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.I) {
                method.toggleItalic();
            }
        });
    }
    /**
     * Updates the model with the latest data from the ClassObject.
     * This includes updating the class name, attributes, and methods for the ClassModel.
     *
     * The method clears existing attributes and methods, then re-adds them based on the current state of the ClassObject.
     *
     * @throws IllegalArgumentException if an attribute or method string cannot be parsed correctly.
     */
    @Override
    public void reloadModel() {
        super.reloadModel();

        ClassModel downcastModel = (ClassModel) model;

        downcastModel.setClassName(className.getText());

        // Handle Attributes
        if (downcastModel.getAttributes() != null) {
            downcastModel.getAttributes().clear();
        }
        for (StackPane attributeStackPane : attributes) {
            if (attributeStackPane instanceof EditableField editableField) {
                String attributeText = editableField.getText();
                Attribute attribute;
                try {
                    attribute = parseAttribute(attributeText);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error occurred: " + e.getMessage());
                    // Remove colons and assign sanitized text as the name
                    String sanitizedText = attributeText.replace(":", "").trim();
                    attribute = new Attribute(sanitizedText, "String", "private");
                }
                downcastModel.addAttribute(attribute);
            }
        }

        // Handle Methods
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

    /**
     * Parses a method string to create a Method object.
     * The method string format can include access modifiers ('+ ', '# ', '- ') and return types.
     * If no access modifier or return type is present, defaults are set.
     *
     * @param methodText the string representation of the method.
     * @return a Method object representing the parsed method.
     * @throws IllegalArgumentException if the method text is incorrectly formatted.
     */
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
     * Parses an attribute string to create an Attribute object.
     * The attribute string format should be '- name : type', '+ name : type', or '# name : type'.
     * The access modifier should be the first character ('-', '+', or '#').
     *
     * @param attributeString the string representation of the attribute.
     * @return an Attribute object representing the parsed attribute.
     * @throws IllegalArgumentException if the attribute string is incorrectly formatted.
     */
    public Attribute parseAttribute(String attributeString) {
        attributeString = attributeString.trim();
        String accessModifier;
        String name;
        String type;

        switch (attributeString.charAt(0)) {
            case '-' -> accessModifier = "private";
            case '+' -> accessModifier = "public";
            case '#' -> accessModifier = "protected";
            default -> throw new IllegalArgumentException("Invalid access modifier: " + attributeString.charAt(0));
        }

        String[] parts = attributeString.substring(1).trim().split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid attribute format. Expected '- name : type'.");
        }

        name = parts[0].trim();
        type = parts[1].trim();

        return new Attribute(name, type, accessModifier);
    }

    /**
     * Gets the currently selected attribute StackPane (the one with focus) from the ClassObject.
     *
     * @return the selected attribute StackPane, or null if no attribute is focused.
     */
    public StackPane getSelectedAttribute() {
        for (StackPane s : attributes) {
            if (s instanceof EditableField && ((EditableField) s).isTextFieldFocused()) {
                return s;
            }
        }
        return null;
    }

    /**
     * Removes the specified attribute from the ClassObject and reloads the model.
     *
     * @param selectedAttribute the StackPane representing the attribute to remove.
     */
    public void removeAttribute(StackPane selectedAttribute) {
        attributeBox.getChildren().remove(selectedAttribute);
        attributes.remove(selectedAttribute);
        reloadModel();
    }

    /**
     * Gets the currently selected method StackPane (the one with focus) from the ClassObject.
     *
     * @return the selected method StackPane, or null if no method is focused.
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
     * Removes the specified method from the ClassObject and reloads the model.
     *
     * @param selectedMethod the StackPane representing the method to remove.
     */
    public void removeMethod(StackPane selectedMethod) {
        methodBox.getChildren().remove(selectedMethod);
        methods.remove(selectedMethod);
        reloadModel();
    }

    /**
     * Downcasts the model to a ClassModel type.
     *
     * @return the ClassModel object.
     */
    public ClassModel downcastModel() {
        return (ClassModel) model;
    }

    /**
     * Gets the list of attributes in the ClassObject.
     *
     * @return a List of StackPane representing the attributes.
     */
    public List<StackPane> getAttributes() {
        return attributes;
    }

    /**
     * Gets the list of methods in the ClassObject.
     *
     * @return a List of StackPane representing the methods.
     */
    public List<StackPane> getMethods() {
        return methods;
    }

}