package Main;

import CodeGeneration.CodeGenerator;
import Models.AssociationModel;
import Models.ClassModel;
import Models.InterfaceModel;
import Models.Model;
import Serializers.JSONSerializer;
import Serializers.Serializer;
import UML.Diagrams.ClassDiagram;
import UML.ObjectFactories.ClassFactory;
import UML.ObjectFactories.InterfaceFactory;
import UML.ObjectFactories.ObjectFactory;
import UML.Objects.UMLObject;
import UML.Objects.UseCaseObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import UML.Line.*;

import java.util.*;

public class HelloController {
    @FXML
    private TextField modelNameField;

    @FXML
    private TreeView<String> modelTree;
    @FXML
    private TreeItem<String> rootNode;
    private TreeItem<String> diagramNode;
    @FXML
    public Pane canvas;

    List<UMLObject> umlObjects = new ArrayList<>();
    List<UML.Line.Line> associations = new ArrayList<>();

    ObjectFactory classFactory = new ClassFactory();
    ObjectFactory interfaceFactory = new InterfaceFactory();

    @FXML
    public void initialize() {
        canvas.focusedProperty().removeListener((observable, oldValue, newValue) -> {
        });
        canvas.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode()== KeyCode.DELETE)
                onDeleteClick();
        });
        rootNode = new TreeItem<>("Untitled"); // Default model name
        rootNode.setExpanded(true);

        diagramNode = new TreeItem<>("Model: Class Diagram"); // Default diagram type
        diagramNode.setExpanded(true);

        rootNode.getChildren().add(diagramNode);
        modelTree.setRoot(rootNode);
    }

    public List<AssociationModel> getAssociations() {
        List<AssociationModel> associationModels = new ArrayList<>();
        for(Line line: associations)
            associationModels.add(line.getAssociationModel());
        return associationModels;
    }
    public List<Model> getModels(){
        List<Model> models = new ArrayList<>();
        for(UMLObject umlObject:umlObjects) {
            umlObject.reloadModel();
            //System.out.println(umlObject.getModel());
            models.add(umlObject.getModel());
        }
        return models;
    }

    @FXML
    public void setModelName() {
        String newName = modelNameField.getText().trim();
        if (!newName.isEmpty()) {
            rootNode.setValue(newName);
        }
    }

    @FXML
    private ComboBox<String> diagramTypeBox;

    @FXML
    public void changeDiagramType() {
        String selectedType = diagramTypeBox.getValue();
        if (selectedType != null) {
            diagramNode.setValue("Model: " + selectedType);
        }
    }

    public void addClassNode(String className) {
        // Add a class to the diagram node
        TreeItem<String> classNode = new TreeItem<>(className);
        diagramNode.getChildren().add(classNode);
    }

    @FXML
    public void onAddClassDiagramClick() {
        addClassNode("Class Name 1");
        addClassDiagram(100, 100);
    }

    void addToCanvas(UMLObject umlObject, double x, double y) {
        umlObject.setFocusTraversable(true);
        umlObjects.add(umlObject);
        canvas.getChildren().add(umlObject);
        umlObject.setLayoutX(x);
        umlObject.setLayoutY(y);
    }

    void addClassDiagram(double x, double y) {
        UMLObject newClassDiagram = classFactory.create();
        addToCanvas(newClassDiagram, x, y);
        newClassDiagram.reloadModel();
    }

    @FXML
    public void onAddUseCaseClick() {
        UseCaseObject newUseCase = new UseCaseObject();
        newUseCase.setFocusTraversable(true);
        umlObjects.add(newUseCase);
        canvas.getChildren().add(newUseCase);
    }

    @FXML
    public void onAddInterfaceDiagramClick() {
        UMLObject interfaceDiagram = interfaceFactory.create();
        addToCanvas(interfaceDiagram, 0, 150);
    }

    @FXML
    public void onSaveFirstUmlObject() {
        Serializer jsonSerializer = new JSONSerializer();
        UMLObject umlObject = umlObjects.getFirst();
        umlObject.reloadModel();
        //umlObject.getModel().setCoordinate(umlObject.getLayoutX(), umlObject.getLayoutY());
        Model model = umlObject.getModel();
        jsonSerializer.serialize(model);
    }

    @FXML
    public void onDrawAssociationClick() {
        handleLineDrawing("Association");
    }
    @FXML
    public void onDrawInheritanceClick() {
        handleLineDrawing("Inheritance");
    }
    @FXML
    public void onDrawAggregationClick() {
        handleLineDrawing("Aggregation");
    }
    @FXML
    public void onDrawCompositionClick() {
        handleLineDrawing("Composition");
    }

    private void handleLineDrawing(String lineType) {
        // Variables to keep track of the first and second selected objects
        final UMLObject[] firstObject = {null};
        final UMLObject[] secondObject = {null};

        for (UMLObject umlObject : umlObjects) {
            umlObject.setOnMousePressed(event -> {
                // If the first object is not selected, set it
                if (firstObject[0] == null) {
                    firstObject[0] = umlObject;
                }
                // If the first object is selected, set the second one
                else if (secondObject[0] == null) {
                    secondObject[0] = umlObject;
                }

                if (secondObject[0] != null) {
                    drawLineBetweenObjects(firstObject[0], secondObject[0], lineType);
                    // Remove event handlers to avoid multiple line drawings
                    removeMouseHandlers();
                }
            });
        }
    }

    private void removeMouseHandlers() {
        for (UMLObject umlObject : umlObjects) {
            umlObject.setOnMousePressed(null);
            Platform.runLater(umlObject::resetMousePressedHandlers);
        }
    }

    private void drawLineBetweenObjects(UMLObject object1, UMLObject object2, String lineType) {

        System.out.println(object1.toString() + " to " + object2.toString());

        // Calculate the coordinates for the start and end points
        double startX = object1.getLayoutX() + object1.getWidth() / 2;
        double startY = object1.getLayoutY();
        double endX = object2.getLayoutX() + object2.getWidth() / 2;
        double endY = object2.getLayoutY();

        // Assuming AssociationModel is a class that links the objects
        AssociationModel associationModel = new AssociationModel();  // Create or fetch your association model here
        associationModel.setType(lineType);
        associationModel.setStartX(startX);
        associationModel.setStartY(startY);
        associationModel.setEndX(endX);
        associationModel.setEndY(endY);
        Model startModel = object1.getModel();
        Model endModel = object2.getModel();
        startModel.addStartAssociation(associationModel);
        endModel.addEndAssociation(associationModel);


        // Create the appropriate line object based on the lineType
        UML.Line.Line line;
        line = createLine(lineType, startX, startY, endX, endY, canvas, associationModel, object1, object2);

        //This will automatically set both sides
        object1.addAssociatedLine(line);
        object2.addAssociatedLine(line);
        associations.add(line);

        if (line != null) {
            canvas.getChildren().add(line);
        }
    }

    @FXML
    public void onSaveClassDiagram() {
        List<AssociationModel> associationModels = getAssociations();
        List<Model> models = getModels();
        ClassDiagram classDiagram = new ClassDiagram(models, associationModels);
        classDiagram.saveClassDiagram();
    }

    @FXML
    public void onLoadDiagram() {
        List<AssociationModel> associationModels = getAssociations();
        List<Model> models = getModels();
        ClassDiagram classDiagram = new ClassDiagram(models, associationModels);
        classDiagram.loadClassDiagram();

        List<AssociationModel> loadedAssociationModels = null;
        List<Model> loadedModels = null;
        if (classDiagram.getModels() != null)
            loadedModels = classDiagram.getModels();
        if (classDiagram.getAssociationList() != null)
            loadedAssociationModels = classDiagram.getAssociationList();

        if (loadedAssociationModels==null)
            loadedAssociationModels = new ArrayList<>();
        if (loadedModels == null)
            loadedModels = new ArrayList<>();

        loadSavedDiagram(loadedModels, loadedAssociationModels);
    }

    public void loadSavedDiagram(List<Model> loadedModels, List<AssociationModel> loadedAssociationModels) {
        System.out.println("Loading Diagram");

        canvas.getChildren().clear();
        umlObjects.clear();
        associations.clear();

        // Use a Hashtable to track already created UMLObjects by model ID
        Hashtable<Integer, UMLObject> createdModels = new Hashtable<>();

        // First, process associations
        for (AssociationModel associationModel : loadedAssociationModels) {
            UMLObject startObject;
            UMLObject endObject;

            // Check and create start object if not already created
            if (!createdModels.containsKey(associationModel.getStartModel().getModelId())) {
                startObject = createUMLObject(associationModel.getStartModel());
                if (startObject != null) {
                    createdModels.put(associationModel.getStartModel().getModelId(), startObject);  // Store in Hashtable
                    canvas.getChildren().add(startObject);
                    umlObjects.add(startObject);
                }
            } else {
                startObject = createdModels.get(associationModel.getStartModel().getModelId()); // Reuse existing object
            }

            // Check and create end object if not already created
            if (!createdModels.containsKey(associationModel.getEndModel().getModelId())) {
                endObject = createUMLObject(associationModel.getEndModel());
                if (endObject != null) {
                    createdModels.put(associationModel.getEndModel().getModelId(), endObject); // Store in Hashtable
                    canvas.getChildren().add(endObject);
                    umlObjects.add(endObject);
                }
            } else {
                endObject = createdModels.get(associationModel.getEndModel().getModelId()); // Reuse existing object
            }

            // Create the association line between the start and end objects
            if (startObject != null && endObject != null) {
                UML.Line.Line createdLine = createLine(associationModel);
                createdLine.setStartObject(startObject);
                createdLine.setEndObject(endObject);

                // Associate the created line with the objects
                startObject.addAssociatedLine(createdLine);
                endObject.addAssociatedLine(createdLine);

                // Set line position correctly (if required)
                createdLine.setStartX(startObject.getLayoutX());
                createdLine.setStartY(startObject.getLayoutY());
                createdLine.setEndX(endObject.getLayoutX());
                createdLine.setEndY(endObject.getLayoutY());
                associations.add(createdLine);
                // Add the line to the canvas
                Platform.runLater(() -> {
                    canvas.getChildren().add(createdLine);
                    createdLine.customDraw();
                });
            }
        }

        // After processing associations, add the remaining models (if not already added)
        for (Model model : loadedModels) {
            if (!createdModels.containsKey(model.getModelId())) {
                UMLObject umlObject = createUMLObject(model);
                if (umlObject != null) {
                    umlObjects.add(umlObject);
                    canvas.getChildren().add(umlObject);
                    createdModels.put(model.getModelId(), umlObject); // Store in Hashtable
                }
            }
        }
    }

    public UMLObject createUMLObject(Model model){
        UMLObject umlObject = null;
        if(model instanceof ClassModel) {
            umlObject = classFactory.create();
            umlObject.setModel(model);
        }
        else if(model instanceof InterfaceModel){
            umlObject = interfaceFactory.create();
            umlObject.setModel(model);
        }
        return umlObject;
    }

    public UML.Line.Line createLine(AssociationModel model){
        String type = model.getType();
        double startX = model.getStartX();
        double startY = model.getStartY();
        double endX = model.getEndX();
        double endY =model.getEndY();
        UMLObject startObject = createUMLObject(model.getStartModel());
        UMLObject endObject = createUMLObject(model.getEndModel());
        return createLine(type,startX,startY,endX,endY,canvas,model,startObject,endObject);
    }
    public UML.Line.Line createLine(String lineType, double startX, double startY, double endX, double endY, Pane canvas, AssociationModel associationModel, UMLObject object1, UMLObject object2){
        UML.Line.Line line = null;
        switch (lineType) {
            case "Association":
                line = new Association(startX, startY, endX, endY, canvas, associationModel, object1, object2);
                break;
            case "Aggregation":
                line = new Aggregation(startX, startY, endX, endY, canvas, associationModel, object1, object2);
                break;
            case "Composition":
                line = new Composition(startX, startY, endX, endY, canvas, associationModel, object1, object2);
                break;
            case "Inheritance":
                line = new Inheritance(startX, startY, endX, endY, canvas, associationModel, object1, object2);
                break;
            default:
                System.out.println("Invalid line type");
        }
        return line;
    }

    @FXML
    public void onDeleteClick() {
        Node focusedNode = canvas.getScene().getFocusOwner();
        if (focusedNode instanceof UMLObject obj) {
            associations.removeAll(obj.getAssociatedLines());
            obj.delete();
            umlObjects.remove(obj);
        } else if(focusedNode instanceof UML.Line.Line line) {
            associations.remove(line);
            line.delete();
        } else {
            System.out.println("No UMLObject is focused.");
        }
    }

    @FXML
    public void onCodeGenerateClick() {
        Node focusedNode = canvas.getScene().getFocusOwner();
        System.out.println(focusedNode.getLayoutY());
        if (focusedNode instanceof UMLObject obj) {
            CodeGenerator codeGenerator = new CodeGenerator();
            codeGenerator.generateCode(obj.getModel());
            System.out.println("Code has been generated");
        }
    }
}
