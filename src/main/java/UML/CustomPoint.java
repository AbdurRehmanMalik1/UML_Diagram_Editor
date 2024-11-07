package UML;

import java.awt.geom.Point2D;

public class CustomPoint extends Point2D.Double {

    public CustomPoint(double layoutX, double layoutY) {
        super(layoutX, layoutY);
    }

    @Override
    public double getX() {
        return super.getX();
    }

    @Override
    public double getY() {
        return super.getY();
    }

    @Override
    public void setLocation(double x, double y) {
        super.setLocation(x, y);
    }
}
