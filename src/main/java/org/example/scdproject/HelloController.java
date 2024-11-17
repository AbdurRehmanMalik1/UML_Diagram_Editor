package org.example.scdproject;

import UML.ClassDiagram;
import UML.InterfaceDiagram;
import UML.UMLObjects;
import UML.UseCase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    public Pane canvas;

    @FXML
    private Label welcomeText;
    List<UMLObjects> umlObjects = new ArrayList<>();

    @FXML
    protected void onAddClassDiagramClick() {
        ClassDiagram newClassDiagram = new ClassDiagram();
        newClassDiagram.setFocusTraversable(true);
        umlObjects.add(newClassDiagram);
        canvas.getChildren().add(newClassDiagram);

    }

    public void onUnfocusClick(ActionEvent actionEvent) {
        for(UMLObjects cd : umlObjects)
            if(cd instanceof ClassDiagram)
                ((ClassDiagram) cd).unfocusSelf();
    }

    public void onAddUseCaseClick() {
        UseCase newUseCase = new UseCase();
        newUseCase.setFocusTraversable(true);
        umlObjects.add(newUseCase);
        canvas.getChildren().add(newUseCase);
    }

    public void onAddInterfaceDiagramClick() {
        InterfaceDiagram interfaceDiagram = new InterfaceDiagram();
        interfaceDiagram.setFocusTraversable(true);
        umlObjects.add(interfaceDiagram);
        canvas.getChildren().add(interfaceDiagram);
    }
}
