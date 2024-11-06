package org.example.scdproject;

import UML.ClassDiagram;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    public Group canvas;

    @FXML
    private Label welcomeText;
    List<ClassDiagram> classDiagrams = new ArrayList<>();

    @FXML
    protected void onAddClassDiagramClick() {
        // Create new ClassDiagram instance
        ClassDiagram newClassDiagram = new ClassDiagram();

        // Add it to the Group directly (instead of a layout container)
        classDiagrams.add(newClassDiagram);
        canvas.getChildren().add(newClassDiagram);
    }

    @FXML
    public void onAddAttributeClick() {
        // Add attribute to a specific diagram
        ClassDiagram single = classDiagrams.getFirst();  // Access the first diagram (just an example)
        single.addAttribute("here is a new attribute");
    }

    @FXML
    public void onAddMethodClick() {
        ClassDiagram single = classDiagrams.getFirst();  // Access the first diagram (just an example)
        single.addMethod("Here is a new method");
    }
}
