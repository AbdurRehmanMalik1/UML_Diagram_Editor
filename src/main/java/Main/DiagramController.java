package Main;

import CodeGeneration.CodeGenerator;
import Controllers.DiagramTreeItem;
import Controllers.MyContextMenu;
import Services.ProjectService;
import UML.Diagrams.UMLDiagram;
import UML.Diagrams.UseCaseDiagram;
import UML.Project;
import Util.*;
import Models.AssociationModel;
import Models.Model;
import UML.Diagrams.ClassDiagram;
import UML.ObjectFactories.ObjectFactory;
import UML.Objects.UMLObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import UML.Line.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;

public abstract class DiagramController {
    @FXML
    protected TextField modelNameField;
    @FXML
    protected TreeView<String> modelTree;
    @FXML
    protected TreeItem<String> rootNode;
    protected TreeItem<String> diagramNode;
    @FXML
    public Pane canvas;
    @FXML
    public ToggleGroup buttonToggleGroup;
    @FXML
    protected ComboBox<String> diagramTypeBox;

    List<UMLObject> umlObjects = new ArrayList<>();
    List<UML.Line.Line> associations = new ArrayList<>();
    LineFactory lineFactory = new LineFactory();
    UML.ObjectFactories.ObjectFactory objectFactory = new ObjectFactory();
    public BiConsumer<Double, Double> drawObjectFunc;
    Model copyTemp = null;
    private double mouseX;
    private double mouseY;
    private Project project;
    UMLDiagram diagram;
    @FXML
    public void initialize(List<AssociationModel> associationList, List<Model> models, Project project, UMLDiagram diagram) {
        this.project = project;
        this.diagram = diagram;

        this.diagramNode = new TreeItem<>();
        // Initialize context menu for canvas
        MyContextMenu.createContextMenu(canvas,
                this::onCopyClick,
                this::onPasteClick,
                this::onDeleteClick,
                this::onCutClick);

        canvas.setOnMouseMoved(mouseEvent -> {
            mouseX = mouseEvent.getSceneX();
            mouseY = mouseEvent.getSceneY();
        });

        canvas.setOnKeyPressed(this::handleKeyEvents);

        rootNode = new TreeItem<>(project.getProjectName());
        rootNode.setExpanded(true);
        modelTree.setRoot(rootNode);

        populateModelTree();

        modelTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof DiagramTreeItem) {
                UMLDiagram selectedDiagram = ((DiagramTreeItem) newValue).getDiagram();
                switchToDiagram(selectedDiagram);
            }
        });

        // Load the current diagram
        loadSavedDiagram(models, associationList);

        setButtonsToggle();



    }
    public void setProjectF(Project p){
        project = p;
    }
    public void setModelTree(TreeView<String> m){
        modelTree = m;
    }
    public TreeView<String> getModelTree(){
        return modelTree;
    }
    public Project getProjectF(){
        return project;
    }
    public List<UMLObject> getUMLObjects(){
        return  umlObjects;
    }
    public void setRootNode(TreeItem<String> s){
        rootNode = s;
    }
    public TreeItem<String> getRootNode(){
        return  rootNode;
    }
    private void handleKeyEvents(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            onDeleteClick();
        } else if (keyEvent.getCode() == KeyCode.C && keyEvent.isControlDown()) {
            onCopyClick();
        } else if (keyEvent.getCode() == KeyCode.V && keyEvent.isControlDown() && copyTemp != null) {
            onPasteClick();
        } else if (keyEvent.getCode() == KeyCode.X && keyEvent.isControlDown()) {
            onCutClick();
        } else if (keyEvent.getCode() == KeyCode.S && keyEvent.isControlDown()){
            onSaveProjectClick();
        }
    }
    private void populateModelTree() {
        rootNode.getChildren().clear();

        for (UMLDiagram diagram : project.getUmlDiagramList()) {
            DiagramTreeItem diagramItem = new DiagramTreeItem(diagram);
            rootNode.getChildren().add(diagramItem);
        }
    }
    private void switchToDiagram(UMLDiagram selectedDiagram) {
        // Ensure the diagram belongs to the current project
        ProjectService projectService = HelloApplication.getProjectService();  // Get the current ProjectService
        if (projectService != null && selectedDiagram != null) {
            Project currentProject = projectService.getCurrentProject();

            // Check if the diagram is already part of the project
            if (!currentProject.getUmlDiagramList().contains(selectedDiagram)) {
                // If not, add it to the project
                currentProject.addUmlDiagram(selectedDiagram);
                projectService.saveProject();  // Save the updated project with the new diagram
            }

            // Save the current diagram's state before switching
            this.diagram.setModelList(getModels());
            this.diagram.setAssociationList(getAssociations());
            projectService.saveProject();  // Save the project using the ProjectService

            // Directly use ProjectService to open the diagram view
            projectService.openDiagramView(selectedDiagram);
        } else {
            ToastMessage.showNegativeToast(canvas,"Invalid project service or diagram.",3);
        }
    }


    public abstract void setButtonsToggle();

    protected void unselectToggleButton(){
        drawObjectFunc = null;
        ToggleButton button =(ToggleButton) buttonToggleGroup.getSelectedToggle();
        if(button!=null){
            button.setSelected(false);
        }
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
            models.add(umlObject.getModel());
        }
        return models;
    }
    @FXML
    public void setModelName() {
        String newName = modelNameField.getText().trim();
        if (!newName.isEmpty()) {
            rootNode.setValue(newName);
            project.setProjectName(newName);
        }
    }
    public void addClassNode(String className) {
        TreeItem<String> classNode = new TreeItem<>(className);
        diagramNode.getChildren().add(classNode);
    }
    @FXML
    public void addDiagram() {
        String selectedDiagram = diagramTypeBox.getValue();

        switch (selectedDiagram) {
            case "Class Diagram":
                addClassDiagram();
                break;
            case "Use Case Diagram":
                addUseCaseDiagram();
                break;
        }
    }

    public void addClassDiagram() {
        // Create a new Class Diagram
        ClassDiagram newClassDiagram = new ClassDiagram();

        // Create a new DiagramTreeItem and add it to the model tree
        DiagramTreeItem diagramItem = new DiagramTreeItem(newClassDiagram);
        rootNode.getChildren().add(diagramItem);

        // Optionally, select the newly added diagram
        modelTree.getSelectionModel().select(diagramItem);
    }

    private void addUseCaseDiagram() {
        // Create a new Use Case Diagram
        UseCaseDiagram newUseCaseDiagram = new UseCaseDiagram();

        // Create a new DiagramTreeItem and add it to the model tree
        DiagramTreeItem diagramItem = new DiagramTreeItem(newUseCaseDiagram);
        rootNode.getChildren().add(diagramItem);

        // Optionally, select the newly added diagram
        modelTree.getSelectionModel().select(diagramItem);
    }


    public void addToCanvas(UMLObject umlObject, double x, double y) {
        addClassNode("Object " + umlObjects.size() + 1);
        umlObject.reloadModel();
        umlObject.setFocusTraversable(true);
        umlObjects.add(umlObject);
        canvas.getChildren().add(umlObject);
        umlObject.setLayoutX(x);
        umlObject.setLayoutY(y);
    }

    protected void handleLineDrawing(String lineType) {
        final UMLObject[] firstObject = {null};
        final UMLObject[] secondObject = {null};

        for (UMLObject umlObject : umlObjects) {
            umlObject.setOnMousePressed(event -> {
                if(event.getButton()== MouseButton.PRIMARY) {
                    if (firstObject[0] == null) {
                        firstObject[0] = umlObject;
                    } else if (secondObject[0] == null) {
                        secondObject[0] = umlObject;
                    }
                    if (secondObject[0] != null) {
                        drawLineBetweenObjects(firstObject[0], secondObject[0], lineType);
                        removeMouseHandlers();
                    }
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
    @FXML
    public void onDrawClick(String type) {
        unselectToggleButton();
        handleLineDrawing(type);
    }
    private void drawLineBetweenObjects(UMLObject object1, UMLObject object2, String lineType) {

        DistanceCalc.ResultPoint resultPoint = DistanceCalc.getShortestDistance(object1,object2);
        double startX =resultPoint.point1.x;
        double startY = resultPoint.point1.y;
        double endX =resultPoint.point2.x;
        double endY = resultPoint.point2.y;

        AssociationModel associationModel = new AssociationModel();
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
        line = lineFactory.createLine(lineType, startX, startY, endX, endY, canvas, associationModel, object1, object2);

        //This will automatically set both sides
        object1.addAssociatedLine(line);
        object2.addAssociatedLine(line);
        associations.add(line);

        if (line != null) {
            canvas.getChildren().add(line);
        }
    }

    @FXML
    public void onExportAsJSON() {
        try {
            // Save the current project file path
            String tempFilePath = project.getProjectFilePath();

            // Open file chooser with description and extension filter
            File file = FileChooserUtil.openFileChooser("JSON Files", "*.json");

            // Check if the user canceled the file chooser
            if (file == null) {
                return; // Exit if no file was selected
            }

            // Ensure the selected file has a .json extension
            String exportPath = file.getAbsolutePath();
            if (!exportPath.toLowerCase().endsWith(".json")) {
                exportPath += ".json";
            }

            // Set the new file path and save the project
            project.setProjectFilePath(exportPath);
            project.saveProject();

            // Restore the original file path
            project.setProjectFilePath(tempFilePath);

            // Show success toast
            ToastMessage.showPositiveToast(canvas, "Export Successful", 3);
        } catch (Exception e) {
            // Show failure toast
            ToastMessage.showNegativeToast(canvas, "Failed to Export", 3);
        }
    }


    @FXML
    public void onLoadDiagram() {
        List<AssociationModel> associationModels = getAssociations();
        List<Model> models = getModels();
        ClassDiagram classDiagram = new ClassDiagram(models, associationModels);
        classDiagram.loadDiagram();

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
                startObject = objectFactory.createUMLObject(associationModel.getStartModel());
                if (startObject != null) {
                    createdModels.put(associationModel.getStartModel().getModelId(), startObject);  // Store in Hashtable
                    addToCanvas(startObject,startObject.getModel().getX(),startObject.getModel().getY());
                }
            } else {
                startObject = createdModels.get(associationModel.getStartModel().getModelId()); // Reuse existing object
            }

            // Check and create end object if not already created
            if (!createdModels.containsKey(associationModel.getEndModel().getModelId())) {
                endObject = objectFactory.createUMLObject(associationModel.getEndModel());
                if (endObject != null) {
                    createdModels.put(associationModel.getEndModel().getModelId(), endObject); // Store in Hashtable
                    addToCanvas(endObject,endObject.getModel().getX(),endObject.getModel().getY());
                }
            } else {
                endObject = createdModels.get(associationModel.getEndModel().getModelId()); // Reuse existing object
            }

            if (startObject != null && endObject != null) {
                UML.Line.Line createdLine = lineFactory.createLineWithObjects(associationModel,canvas);
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
                    createdLine.updateLinePosition(createdLine.getStartObject(),true);
                    createdLine.updateLinePosition(createdLine.getEndObject(),false);
                });
            }


        }

        // After processing associations, add the remaining models (if not already added)
        for (Model model : loadedModels) {
            if (!createdModels.containsKey(model.getModelId())) {
                UMLObject umlObject = objectFactory.createUMLObject(model);
                if (umlObject != null) {
                    addToCanvas(umlObject,umlObject.getModel().getX(),umlObject.getModel().getY());
                    createdModels.put(model.getModelId(), umlObject); // Store in Hashtable
                }
            }
        }
    }

    @FXML
    public void onDeleteClick() {
        Node focusedNode = canvas.getScene().getFocusOwner();
        deleteItem(focusedNode);
    }
    public void deleteItem(Node focusedNode){
        if (focusedNode instanceof UMLObject obj) {
            associations.removeAll(obj.getAssociatedLines());
            obj.delete();
            umlObjects.remove(obj);
        } else if(focusedNode instanceof UML.Line.Line line) {
            associations.remove(line);
            line.delete();
        } else {
            ToastMessage.showNegativeToast(canvas,"No Item selected" , 3);
            //System.out.println("No UMLObject is focused.");
        }
    }

    @FXML
    public void onCodeGenerateClick() {
        try {
            // Open a directory chooser to let the user select a folder
            File directory = FileChooserUtil.openDirectoryChooser();

            // Check if the user canceled the directory selection
            if (directory == null) {
                ToastMessage.showNegativeToast(canvas, "Code generation canceled.", 3);
                return;
            }

            // Validate the selected path
            String selectedPath = directory.getAbsolutePath();
            if (!directory.isDirectory() || !directory.canWrite()) {
                ToastMessage.showNegativeToast(canvas, "Invalid or unwritable directory.", 3);
                return;
            }

            // Generate the code
            CodeGenerator codeGenerator = new CodeGenerator();
            codeGenerator.generateAllCode(getModels(), selectedPath);

            // Show success toast
            ToastMessage.showPositiveToast(canvas, "Code generated successfully at " + selectedPath, 3);
        } catch (Exception e) {
            // Show failure toast with exception details
            ToastMessage.showNegativeToast(canvas, "Failed to generate code: " + e.getMessage(), 3);
        }
    }

    @FXML
    public void onCopyClick() {
        Node focusedNode = canvas.getScene().getFocusOwner();
        if (focusedNode instanceof UMLObject obj) {
            obj.reloadModel();
            copyTemp = obj.getModel();
        }else {
            ToastMessage.showNegativeToast(canvas,"No Item selected" , 3);
        }
    }
    @FXML
    public void onPasteClick() {
        if (copyTemp != null) {
            UMLObject copiedObject = objectFactory.copyUMLObject(copyTemp);
            double localX = canvas.sceneToLocal(mouseX, mouseY).getX();
            double localY = canvas.sceneToLocal(mouseX, mouseY).getY();
            addToCanvas(copiedObject,localX,localY);
        }
    }

    @FXML
    public void onCutClick() {
        Node focusedNode = canvas.getScene().getFocusOwner();
        if (focusedNode instanceof UMLObject obj) {
            obj.reloadModel();
            copyTemp = obj.getModel();
            deleteItem(focusedNode);
        }else {
            ToastMessage.showNegativeToast(canvas,"No Item selected" , 3);
        }
    }

    @FXML
    public void onPNGClick() {
        try {
            ImageSaverUtil.savePNG(canvas);
            ToastMessage.showPositiveToast(canvas, "PNG Screenshot Taken Successfully", 3);
        } catch (ImageSaverUtil.ImageSaveException e) {
            ToastMessage.showNegativeToast(canvas, e.getMessage(), 3);
        }
    }

    @FXML
    public void onJPEGClick() {
        try {
            ImageSaverUtil.saveJPEG(canvas);
            ToastMessage.showPositiveToast(canvas, "JPEG Screenshot Taken Successfully", 3);
        } catch (ImageSaverUtil.ImageSaveException e) {
            ToastMessage.showNegativeToast(canvas, e.getMessage(), 3);
        }
    }
    @FXML
    public void onSaveProjectClick() {
        try {
            diagram.setModelList(getModels());
            diagram.setAssociationList(getAssociations());
            project.saveProject();
            if(canvas!=null)
                ToastMessage.showPositiveToast(canvas,"Save Project Successful",  3);
        }catch (Exception e){
            if(canvas!=null)
                ToastMessage.showNegativeToast(canvas,"Failed to Save Project",  3);
        }
    }

    @FXML
    private void onCloseButtonClick() {
        // Ask the user if they want to save the project before closing
        boolean saveChanges = Dialogs.showConfirmDialog(
                "Save Project",
                "Do you want to save changes to your project before closing?",
                "Save",
                "Don't Save"
        );

        if (saveChanges) {
            onSaveProjectClick();
        }

        try {
            // Load the opening Window.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OpeningWindow.fxml")); // Adjust path as needed
            BorderPane pane = loader.load();

            // Set the loaded scene to the primary stage
            Scene scene = new Scene(pane);
            HelloApplication.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            ToastMessage.showNegativeToast(canvas, "Failed to Close Project", 3);
        }
    }
    @FXML
    public void onExitButtonClick() {
        Platform.exit();
    }
}
