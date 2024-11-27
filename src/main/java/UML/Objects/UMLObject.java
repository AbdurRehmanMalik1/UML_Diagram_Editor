package UML.Objects;

import Models.Model;
import UML.CustomPoint;
import UML.Line.Line;
import UML.Moveable;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public abstract class UMLObject extends Moveable {
    transient public OuterRectangle outerRect;
    protected Model model;
    List<Line> associatedLines;
    transient CustomPoint point;
    protected Runnable redrawLine;

    public UMLObject(Runnable redrawFunc){
        super();
        outerRect = new OuterRectangle();
        setRunnable(redrawFunc);
        associatedLines = new ArrayList<>();
    }
    public UMLObject (){
        //super();
        point = new CustomPoint(getLayoutX(),getLayoutY());
        addMouseEvents();
        outerRect = new OuterRectangle();
        associatedLines = new ArrayList<>();
    }
    public void addAssociatedLine(Line line){
        associatedLines.add(line);
    }

    protected void setRunnable(Runnable runnable){
        this.redrawLine = runnable;
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
            if(!associatedLines.isEmpty())
                updateLines();
            point.setLocation(event.getSceneX(), event.getSceneY());

        });
    }
//    public void setRedrawLineLogic(Runnable redrawer){
//    }
    public abstract double getWidth();

    public abstract double getHeight();
    public abstract Model getModel();

    public abstract void reloadModel();

    public class OuterRectangle extends Rectangle {
        private final Circle topLeft;
        private final Circle topRight;
        private final Circle bottomLeft;
        private final Circle bottomRight;

        public OuterRectangle() {
            setFill(Color.TRANSPARENT);
            setStroke(Color.BLACK);
            setStrokeWidth(1);

            getStrokeDashArray().addAll(2.0, 5.0);

            topLeft = createCornerCircle();
            topRight = createCornerCircle();
            bottomLeft = createCornerCircle();
            bottomRight = createCornerCircle();

            getChildren().addAll(topLeft, topRight, bottomLeft, bottomRight);
        }

        private Circle createCornerCircle() {
            double cornerRadius = 3;
            Circle circle = new Circle(cornerRadius);
            circle.setFill(Color.TRANSPARENT);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(1);
            return circle;
        }

        public void addToGroup(Group group) {
            group.getChildren().add(this); // Add the rectangle itself
            group.getChildren().addAll(topLeft, topRight, bottomLeft, bottomRight); // Add corner circles
        }

        public void setLocation(double x, double y) {
            setLayoutX(x);
            setLayoutY(y);
            updateCorners();
        }

        public void setSize(double width, double height) {
            setWidth(width);
            setHeight(height);
            updateCorners();
        }

        private void updateCorners() {
            double x = getLayoutX();
            double y = getLayoutY();
            double w = getWidth();
            double h = getHeight();

            topLeft.setCenterX(x);
            topLeft.setCenterY(y);

            topRight.setCenterX(x + w);
            topRight.setCenterY(y);

            bottomLeft.setCenterX(x);
            bottomLeft.setCenterY(y + h);

            bottomRight.setCenterX(x + w);
            bottomRight.setCenterY(y + h);
        }
        public void setVisibility(boolean b) {
            this.setVisible(b);
            topRight.setVisible(b);
            topLeft.setVisible(b);
            bottomRight.setVisible(b);
            bottomLeft.setVisible(b);
        }
    }
    protected void updateLines() {
        for (Line line : associatedLines) {
            line.updateLineStart();
            line.updateLineEnd();
        }
    }
}
