package Main;

import UML.Diagrams.UMLDiagram;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DiagramController {
    private UMLDiagram diagram;
    @FXML
    private Label diagramLabel;

    public void initialize(UMLDiagram diagram) {
        this.diagram = diagram;
        diagramLabel.setText("Editing: " + diagram.getClass().getSimpleName());
    }
}
