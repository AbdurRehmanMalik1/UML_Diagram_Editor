package UML;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Moveable extends Parent {
    CustomPoint point;
    public OuterRectangle outerRect;

    public Moveable(){
        point = new CustomPoint(getLayoutX(),getLayoutY());
        addMouseEvents();
        outerRect = new OuterRectangle();
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


    public static class OuterRectangle extends  Rectangle {
        OuterRectangle(){
            setFill(Color.TRANSPARENT);
            setStroke(Color.BLACK);
            setStrokeWidth(1);
        }

        public void setLocation(double x, double y) {
            setLayoutX(x);
            setLayoutX(y);
        }
        public void setSize(double width, double height){
            setWidth(width);
            setHeight(height);
        }
    }
}
