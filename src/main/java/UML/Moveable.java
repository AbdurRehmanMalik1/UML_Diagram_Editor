package UML;

import javafx.scene.Node;
import javafx.scene.Parent;

public abstract class Moveable extends Parent {
    CustomPoint point;
    Moveable(){
        point = new CustomPoint(getLayoutX(),getLayoutY());
        addMouseEvents();
    }
    private void addMouseEvents() {
    setOnMousePressed(event -> {
        point.setLocation(event.getSceneX(), event.getSceneY());
    });
    setOnMouseDragged(event -> {
        double deltaX = event.getSceneX() - point.getX();
        double deltaY = event.getSceneY() - point.getY();

        setLayoutX(getLayoutX() + deltaX);
        setLayoutY(getLayoutY() + deltaY);

        point.setLocation(event.getSceneX(), event.getSceneY());
    });
}
}
