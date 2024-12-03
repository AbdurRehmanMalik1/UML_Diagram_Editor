package Main;

import UML.Diagrams.UMLDiagram;
import UML.Project;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MainController {
    private Project project;

    @FXML
    private ListView<String> diagramListView;

    public void initialize(Project project) {
        this.project = project;

        for (UMLDiagram diagram : project.getUmlDiagramList()) {
            diagramListView.getItems().add(diagram.getClass().getName()); // Assuming UMLDiagram has a `getName()` method
        }

        diagramListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadDiagram(newValue);
            }
        });
    }

    private void loadDiagram(String diagramName) {
        // Logic to load the selected diagram (e.g., open a new DiagramController)
        // ...
    }
}
