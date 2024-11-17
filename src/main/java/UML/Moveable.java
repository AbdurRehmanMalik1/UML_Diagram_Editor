package UML;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public abstract class Moveable extends Parent {
    CustomPoint point;


    public Moveable(){
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

            double newPointX = getLayoutX() + deltaX;
            double newPointY = getLayoutY() + deltaY;

            double parentWidth = getParent().getLayoutBounds().getWidth();
            double parentHeight = getParent().getLayoutBounds().getHeight();

            if (newPointX >= 0 && newPointX + getBoundsInParent().getWidth() <= parentWidth) {
                setLayoutX(newPointX);
            }

            if (newPointY >= 0 && newPointY + getBoundsInParent().getHeight() <= parentHeight) {
                setLayoutY(newPointY);
            }
            point.setLocation(event.getSceneX(), event.getSceneY());
        });
    }
}
