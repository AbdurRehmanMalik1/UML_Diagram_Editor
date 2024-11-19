package UML.Objects;

import UML.Moveable;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public abstract class UMLObject extends Moveable {
    transient public OuterRectangle outerRect;
    public UMLObject(){
        super();
        outerRect = new OuterRectangle();
    }
    public abstract double getWidth();

    public abstract double getHeight();

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

}
