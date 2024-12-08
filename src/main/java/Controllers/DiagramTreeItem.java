package Controllers;

import UML.Diagrams.UMLDiagram;
import javafx.scene.control.TreeItem;

/**
 * DiagramTreeItem is a custom TreeItem used to represent a UMLDiagram in a TreeView.
 * Each item displays the ID and name of the associated UML diagram.
 */
public class DiagramTreeItem extends TreeItem<String> {
    private final UMLDiagram diagram;  // The UML diagram associated with this tree item

    /**
     * Constructs a DiagramTreeItem for a given UML diagram.
     *
     * @param diagram the UMLDiagram object to be represented by this tree item
     */
    public DiagramTreeItem(UMLDiagram diagram) {
        super(String.format("%s", diagram.getName())); // Display ID and Name
        this.diagram = diagram;
    }

    /**
     * Retrieves the UML diagram associated with this tree item.
     *
     * @return the UMLDiagram object represented by this tree item
     */
    public UMLDiagram getDiagram() {
        return diagram;
    }
}
