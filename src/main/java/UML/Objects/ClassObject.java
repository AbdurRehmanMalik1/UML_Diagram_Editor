package UML.Objects;

import Controllers.ClassDiagramController;
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


public class ClassObject extends UMLObject {
    private final Group groupDiagram;
    private VBox detailsBox;
    private EditableField className;
    private List<StackPane> attributes;
    private List<StackPane> methods;
    private VBox attributeBox;
    private VBox methodBox;
    private ClassDiagramController controller;

    public ClassObject() {
        super();
        model = new ClassModel();
        groupDiagram = new Group();

        initComponents();

        groupDiagram.getChildren().add((Node) controller);

        groupDiagram.getChildren().addFirst(outerRect);
        outerRect.setVisibility(false);

        getChildren().add(groupDiagram);

        this.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 1) {
                requestFocus();
//                controller.setParentClass(this);
            }
        });

        this.layoutBoundsProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(this::resizeOuterRect)
        );

        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            outerRect.setVisibility(newValue);
            if (!newValue) {
                hideControllerIfNotFocused();
            } else {
                showController();
            }
        });

    }

    private void hideController() {
        // This will hide the controller when the ClassObject loses focus
       groupDiagram.getChildren().remove(controller);
    }
    private void hideControllerIfNotFocused() {
        boolean isAttributeFocused = false;
        for(StackPane e : attributes){
            if(((EditableField)e).isTextFieldFocused())
                isAttributeFocused = true;
        }
        boolean isMethodFocused = false;
        for(StackPane e : methods){
            if(((EditableField)e).isTextFieldFocused())
                isMethodFocused = true;
        }
        if (!isAttributeFocused && !isMethodFocused && !this.isFocused()) {
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
    private void showController() {
        // This will show the controller when the ClassObject gains focus
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

    public void resizeOuterRect() {
        Bounds boundsInScene = detailsBox.localToScene(detailsBox.getBoundsInLocal());
        Bounds boundsInGroup = groupDiagram.sceneToLocal(boundsInScene);
        outerRect.setLocation(boundsInGroup.getMinX() - 2, boundsInGroup.getMinY() - 2);
        outerRect.setSize(boundsInGroup.getWidth() + 4, boundsInGroup.getHeight() + 4);
    }

    private void initComponents() {
        detailsBox = new VBox();
        detailsBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

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

        controller = new ClassDiagramController(this, classNameWrapper);


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
        addFocusListeners(attributeBox,attributes);
        addFocusListeners(methodBox, methods);
    }

    public void addAttribute(Attribute temp) {
        StackPane attribute = new EditableField(temp.toString(), this::reloadModel);
        attribute.setFocusTraversable(true);
        attributes.add(attribute);
        attributeBox.getChildren().add(attribute);
    }

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
//    private Method parseMethod(String methodText) {
//        System.out.println("Method Text:[" + methodText + "]");
//
//        String accessModifier = "public"; // Default access modifier
//        String returnType = "void"; // Default return type
//        String methodName = "unknown"; // Default method name
//
//        // Check for access modifiers
//        if (methodText.startsWith("+ ")) {
//            accessModifier = "public";
//            methodText = methodText.substring(2).trim(); // Remove 'public ' prefix
//        } else if (methodText.startsWith("# ")) {
//            accessModifier = "protected";
//            methodText = methodText.substring(2).trim(); // Remove 'protected ' prefix
//        } else if (methodText.startsWith("- ")) {
//            accessModifier = "private";
//            methodText = methodText.substring(2).trim(); // Remove 'private ' prefix
//        }
//
//        // Use regular expressions to split the method text into return type, name, and parameters
//        String regex = "^\\s*(\\w+)\\s+([\\w]+)\\s*\\(([^)]*)\\)\\s*$";
//        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
//        java.util.regex.Matcher matcher = pattern.matcher(methodText);
//
//        if (matcher.matches()) {
//            returnType = matcher.group(1).trim(); // Capture the return type
//            methodName = matcher.group(2).trim(); // Capture the method name
//        } else {
//            // If no match is found, fall back to treating the whole text as the method name
//            methodName = methodText.trim();
//        }
//
//        return new Method(returnType, methodName, accessModifier);
//    }
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
            if(returnType.isEmpty()) {
                returnType = "void";
                methodText = methodText + returnType;
            }
        }

        methodName = methodText.trim(); // The rest is the method name

        return new Method(returnType, methodName, accessModifier);
    }






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

    public StackPane getSelectedAttribute() {
        for (StackPane s : attributes) {
            if (s instanceof EditableField && ((EditableField) s).isTextFieldFocused()) {
                return s;
            }
        }
        return null;
    }

    public void removeAttribute(StackPane selectedAttribute) {
        attributeBox.getChildren().remove(selectedAttribute);
        attributes.remove(selectedAttribute);
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
    }
}