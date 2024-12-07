package Controllers;

import UML.Diagrams.UMLDiagram;
import javafx.scene.control.TreeItem;

public class DiagramTreeItem extends TreeItem<String> {
    private final UMLDiagram diagram;

    public DiagramTreeItem(UMLDiagram diagram) {
        super(String.format("[%d] %s", diagram.getId(), diagram.getName())); // Display ID and Name
        this.diagram = diagram;
    }

    public UMLDiagram getDiagram() {
        return diagram;
    }
}