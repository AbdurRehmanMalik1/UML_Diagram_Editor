package org.example.scdproject;

import Models.ClassModel;
import Models.Model;
import Serializers.JSONSerializer;
import Serializers.Serializer;
import UML.ClassDiagram;
import UML.InterfaceDiagram;
import UML.UMLObject;
import UML.UseCase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    public Pane canvas;

    @FXML
    private Label welcomeText;
    List<UMLObject> umlObjects = new ArrayList<>();

    @FXML
    public void onAddClassDiagramClick() {
        ClassDiagram newClassDiagram = new ClassDiagram();
        newClassDiagram.setFocusTraversable(true);
        umlObjects.add(newClassDiagram);
        canvas.getChildren().add(newClassDiagram);

    }

    public void onUnfocusClick(ActionEvent actionEvent) {
        for(UMLObject cd : umlObjects)
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

    public void onSaveDiagram() {
        Serializer jsonSerializer = new JSONSerializer();
        ClassDiagram classDiagram = (ClassDiagram) umlObjects.getFirst();
        classDiagram.reloadClassModel();
        Model model = classDiagram.getClassModel();
        jsonSerializer.serialize(model);
    }

    public void onLoadDiagram() {
        Serializer jsonSerializer = new JSONSerializer();
        String model = "{\"className\":\"Class Name\",\"attributes\":[\"New Attribute\",\"New Attribute\"],\"methods\":[\"New Method\",\"+ getAge()\",\"setAge()\"]}";
        jsonSerializer.deserialize(model, ClassModel.class);

    }
}
