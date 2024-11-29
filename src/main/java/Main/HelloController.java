package Main;

import Models.ClassModel;
import Models.Model;
import Serializers.JSONSerializer;
import Serializers.Serializer;
import Services.ClassModelService;
import UML.Objects.ClassObject;
import UML.Objects.InterfaceObject;
import UML.Objects.UMLObject;
import UML.Objects.UseCaseObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private TextField modelNameField;

    @FXML
    private TreeView<String> modelTree;

    private TreeItem<String> rootNode;
    private TreeItem<String> diagramNode;
    @FXML
    public Pane canvas;

    @FXML
    private Label welcomeText;
    List<UMLObject> umlObjects = new ArrayList<>();
    //ClassModelService classModelService = new ClassModelService();

    @FXML
    public void initialize() {
        // Set up the TreeView structure
        rootNode = new TreeItem<>("Untitled"); // Default model name
        rootNode.setExpanded(true);

        diagramNode = new TreeItem<>("Model: Class Diagram"); // Default diagram type
        diagramNode.setExpanded(true);

        rootNode.getChildren().add(diagramNode);
        modelTree.setRoot(rootNode);
    }

    @FXML
    public void setModelName() {
        String newName = modelNameField.getText().trim();
        if (!newName.isEmpty()) {
            rootNode.setValue(newName);
        }
    }

    @FXML
    public void addClass() {
        addClassNode("New Class"); // Replace with a unique or user-specified name
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
        ClassObject newClassDiagram = new ClassObject();
        newClassDiagram.setFocusTraversable(true);
        umlObjects.add(newClassDiagram);
        canvas.getChildren().add(newClassDiagram);
        //classModelService.saveClass(newClassDiagram.getClassModel());
    }

    public void onUnfocusClick(ActionEvent actionEvent) {
        for(UMLObject cd : umlObjects)
            if(cd instanceof ClassObject)
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
        classDiagram.getClassModel().setCoordinate(classDiagram.getLayoutX(),classDiagram.getLayoutY());
        Model model = classDiagram.getClassModel();
        jsonSerializer.serialize(model);
    }

    public void onLoadDiagram() {
        Serializer jsonSerializer = new JSONSerializer();
        String model = "{\"x\":250,\"y\":80.66666666666669,\"className\":\"Class Name\",\"attributes\":[\"dlawdlad\",\"New Attribute\"],\"methods\":[\"21321321\",\"New Met2131313hod\",\"New Method\"]}\n";
        Model classDiagramModel =  jsonSerializer.deserialize(model, ClassModel.class);

        ClassObject newClassDiagram = new ClassObject();
        newClassDiagram.setFocusTraversable(true);
        newClassDiagram.setClassModel((ClassModel) classDiagramModel);
        newClassDiagram.reloadClassModel();
        umlObjects.add(newClassDiagram);
        canvas.getChildren().add(newClassDiagram);
    }
}
