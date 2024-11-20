package Main;

import Models.AssociationModel;
import Models.ClassModel;
import Models.Model;
import Serializers.JSONSerializer;
import Serializers.Serializer;
import Services.ClassModelService;
import UML.Diagrams.ClassDiagram;
import UML.Objects.ClassObject;
import UML.Objects.InterfaceObject;
import UML.Objects.UMLObject;
import UML.Objects.UseCaseObject;
import Util.DistanceCalc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import UML.Line.*;
import java.util.ArrayList;
import java.util.List;

import static Util.DistanceCalc.getShortestDistance;

public class HelloController {

    @FXML
    public Pane canvas;

    @FXML
    public void initialize() {
        canvas.setFocusTraversable(false);
        canvas.focusedProperty().removeListener((observable, oldValue, newValue) -> {
        });
    }

    @FXML
    private Label welcomeText;
    List<UMLObject> umlObjects = new ArrayList<>();
    ClassModelService classModelService = new ClassModelService();
    private UMLObject selectedObject1 = null;  // To store the first selected object


    @FXML
    public void onAddClassDiagramClick() {
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
        ClassObject newClassDiagram = new ClassObject();
        addToCanvas(newClassDiagram, x, y);
        newClassDiagram.reloadClassModel();
        classModelService.saveClass(newClassDiagram.getClassModel());
    }

    public void onUnfocusClick(ActionEvent actionEvent) {
        for (UMLObject cd : umlObjects)
            if (cd instanceof ClassObject)
                ((ClassObject) cd).unfocusSelf();
    }

    public void onAddUseCaseClick() {
        UseCaseObject newUseCase = new UseCaseObject();
        newUseCase.setFocusTraversable(true);
        umlObjects.add(newUseCase);
        canvas.getChildren().add(newUseCase);
    }

    public void onAddInterfaceDiagramClick() {
        InterfaceObject interfaceDiagram = new InterfaceObject();
        interfaceDiagram.setFocusTraversable(true);
        umlObjects.add(interfaceDiagram);
        canvas.getChildren().add(interfaceDiagram);
    }

    public void onSaveDiagram() {

        Serializer jsonSerializer = new JSONSerializer();
        ClassObject classDiagram = (ClassObject) umlObjects.getFirst();
        classDiagram.reloadClassModel();
        classDiagram.getClassModel().setCoordinate(classDiagram.getLayoutX(), classDiagram.getLayoutY());
        Model model = classDiagram.getClassModel();
        jsonSerializer.serialize(model);
    }

    public void onLoadDiagram() {
        Serializer jsonSerializer = new JSONSerializer();
        String model = "{\"x\":250,\"y\":80.66666666666669,\"className\":\"Class Name\",\"attributes\":[\"dlawdlad\",\"New Attribute\"],\"methods\":[\"21321321\",\"New Met2131313hod\",\"New Method\"]}\n";
        Model classDiagramModel = jsonSerializer.deserialize(model, ClassModel.class);

        ClassObject newClassDiagram = new ClassObject();
        newClassDiagram.setFocusTraversable(true);
        newClassDiagram.setClassModel((ClassModel) classDiagramModel);
        newClassDiagram.reloadClassModel();
        umlObjects.add(newClassDiagram);
        canvas.getChildren().add(newClassDiagram);
    }

    public void onDrawAssociationClick(ActionEvent actionEvent) {
        handleLineDrawing("Association");
    }

    public void onDrawInheritanceClick(ActionEvent actionEvent) {
        handleLineDrawing("Inheritance");
    }

    public void onDrawAggregationClick(ActionEvent actionEvent) {
        handleLineDrawing("Aggregation");
    }

    public void onDrawCompositionClick(ActionEvent actionEvent) {
        handleLineDrawing("Composition");
    }

    private void handleLineDrawing(String lineType) {
        drawLineBetweenObjects(umlObjects.getFirst(), umlObjects.getLast(), lineType);

//        if (selectedObject1 == null) {
//            // No object selected yet, so set the first selected object
//            canvas.setOnMouseClicked(event -> {
//                //selectedObject1 = getObjectAtMousePosition(event.getX(), event.getY());
//                selectedObject1 = umlObjects.get(1);
//                if (selectedObject1 != null) {
//                    System.out.println("Object selected: " + selectedObject1);
//                }
//            });
//        } else {
//            // Select the second object
//            canvas.setOnMouseClicked(event -> {
//                //UMLObject selectedObject2 = getObjectAtMousePosition(event.getX(), event.getY());
//                UMLObject selectedObject2 = umlObjects.getFirst();
//                if (selectedObject2 != null && selectedObject1 != selectedObject2) {
//                    System.out.println("Second object selected: " + selectedObject2);
//                    drawLineBetweenObjects(selectedObject1, selectedObject2, lineType);
//                    selectedObject1 = null; // Reset selection for next drawing
//                }
//            });
//        }
    }

    private UMLObject getObjectAtMousePosition(double x, double y) {
        for (UMLObject umlObject : umlObjects) {
            if (umlObject.contains(x, y)) { // Assuming UMLObject has a `contains` method
                return umlObject;
            }
        }
        return null;
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

        // Set start and end models in the association model (optional based on your design)
        associationModel.setStartModel(object1.getModel());
        associationModel.setEndModel(object2.getModel());

        classModelService.saveClass(((ClassObject)object1).getClassModel());
        classModelService.saveClass(((ClassObject)object2).getClassModel());

        // Create the appropriate line object based on the lineType
        Line line = null;
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
                return; // If the line type is not recognized, return without drawing
        }

        // Add the line to the canvas if it's not null
        if (line != null) {
            canvas.getChildren().add(line);
        }

        System.out.println("Line coordinates: " + startX + ", " + startY + " to " + endX + ", " + endY);
    }




}
