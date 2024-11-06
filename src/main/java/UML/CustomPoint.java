package UML;

import java.awt.geom.Point2D;

public class CustomPoint extends Point2D.Double {

    // Constructor to initialize the point with layoutX and layoutY
    public CustomPoint(double layoutX, double layoutY) {
        super(layoutX, layoutY);
    }

    // Getters for X and Y are already provided by Point2D.Double
    // But you can override them if you need custom logic

    @Override
    public double getX() {
        return super.getX();  // Returns the X value
    }

    @Override
    public double getY() {
        return super.getY();  // Returns the Y value
    }

    // You can also provide your own custom setLocation method if needed
    @Override
    public void setLocation(double x, double y) {
        super.setLocation(x, y);  // Updates the location of the point
    }
}
