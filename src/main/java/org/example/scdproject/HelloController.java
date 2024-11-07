package org.example.scdproject;

import UML.ClassDiagram;
import javafx.event.ActionEvent;
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
        ClassDiagram newClassDiagram = new ClassDiagram();
        newClassDiagram.setFocusTraversable(true);
        classDiagrams.add(newClassDiagram);
        canvas.getChildren().add(newClassDiagram);

    }

    public void onUnfocusClick(ActionEvent actionEvent) {
        for(ClassDiagram cd : classDiagrams)
            cd.unfocusSelf();
    }
}
