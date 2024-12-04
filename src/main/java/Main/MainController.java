//package Main;
//
//import UML.Diagrams.ClassDiagram;
//import UML.Diagrams.UMLDiagram;
//import UML.Diagrams.UseCaseDiagram;
//import UML.Project;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TextInputDialog;
//import javafx.scene.layout.BorderPane;
//
//import java.io.IOException;
//import java.util.Optional;
//
//public class MainController {
//    private Project project;
//
//    @FXML
//    private ListView<String> diagramListView;
//    @FXML
//    private Button addClassDiagramButton;
//    @FXML
//    private Button addUseCaseDiagramButton;
//    @FXML
//    private Button renameDiagramButton; // New button for renaming
//
//    public void initialize(Project project) {
//        this.project = project;
//        refreshDiagramList();
//
//        // Add listeners for adding diagrams
//        addClassDiagramButton.setOnAction(e -> addDiagram(new ClassDiagram()));
//        addUseCaseDiagramButton.setOnAction(e -> addDiagram(new UseCaseDiagram()));
//        renameDiagramButton.setOnAction(e -> renameSelectedDiagram()); // Listener for renaming
//    }
//
//    private void refreshDiagramList() {
//        diagramListView.getItems().clear();
//        for (UMLDiagram diagram : project.getUmlDiagramList()) {
//            // Display unique ID and name
//            diagramListView.getItems().add(String.format("[%d] %s", diagram.getId(), diagram.getName()));
//        }
//
//        // Add listener for selecting a diagram from the list
//        diagramListView.setOnMouseClicked(e -> {
//            if (e.getClickCount() == 2) {  // Detect double-click on the diagram
//                String selectedDiagram = diagramListView.getSelectionModel().getSelectedItem();
//                if (selectedDiagram != null) {
//                    int id = parseIdFromListItem(selectedDiagram);
//                    UMLDiagram diagram = getDiagramById(id);
//                    if (diagram != null) {
//                        openDiagramView(diagram);  // Open the selected diagram view
//                    }
//                }
//            }
//        });
//    }
//
//    private int parseIdFromListItem(String listItem) {
//        // Extract the ID from the list item "[ID] Name"
//        int idEndIndex = listItem.indexOf(']');
//        return Integer.parseInt(listItem.substring(1, idEndIndex));
//    }
//
//    private UMLDiagram getDiagramById(int id) {
//        for (UMLDiagram diagram : project.getUmlDiagramList()) {
//            if (diagram.getId() == id) {
//                return diagram;
//            }
//        }
//        return null;  // Return null if no matching ID is found
//    }
//
//    private void addDiagram(UMLDiagram diagram) {
//        project.addUmlDiagram(diagram);
//        refreshDiagramList();
//        project.saveProject();
//    }
//
//    private void renameSelectedDiagram() {
//        String selectedDiagram = diagramListView.getSelectionModel().getSelectedItem();
//        if (selectedDiagram != null) {
//            int id = parseIdFromListItem(selectedDiagram);
//            UMLDiagram diagram = getDiagramById(id);
//            if (diagram != null) {
//                // Show a dialog to input the new name
//                TextInputDialog dialog = new TextInputDialog(diagram.getName());
//                dialog.setTitle("Rename Diagram");
//                dialog.setHeaderText("Enter a new name for the diagram:");
//                dialog.setContentText("New Name:");
//
//                Optional<String> result = dialog.showAndWait();
//                result.ifPresent(newName -> {
//                    diagram.setName(newName);
//                    refreshDiagramList();  // Update the list view
//                    project.saveProject(); // Save changes
//                });
//            }
//        }
//    }
//
//    private void openDiagramView(UMLDiagram diagram) {
//        String fxmlFile = diagram instanceof ClassDiagram ? "/views/ClassDiagram-view.fxml" : "/views/UseCaseDiagram-view.fxml";
//        String controllerName = diagram instanceof ClassDiagram ? "ClassController" : "UseCaseController";
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
//        BorderPane pane = null;
//        try {
//            pane = loader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (controllerName.equals("ClassController")) {
//            ClassController classController = loader.getController();
//            classController.initialize(diagram.getAssociationList(), diagram.getModels(), project, diagram);
//        } else if (controllerName.equals("UseCaseController")) {
//            UseCaseController useCaseController = loader.getController();
//            useCaseController.initialize(diagram.getAssociationList(), diagram.getModels(), project, diagram);
//        }
//
//        Scene scene = new Scene(pane);
//        HelloApplication.getPrimaryStage().setScene(scene);
//
//    }
//}
