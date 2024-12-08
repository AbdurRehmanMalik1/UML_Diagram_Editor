package Util;

import UML.Objects.UMLObject;

public class DistanceCalc {

    /**
     * Calculates the shortest distance between two UMLObject instances on a JavaFX Pane.
     * It determines the closest points on the objects and returns the Euclidean distance along with those points.
     *
     * @param object1 The first UMLObject whose coordinates and dimensions are used in the calculation.
     * @param object2 The second UMLObject whose coordinates and dimensions are used in the calculation.
     * @return A `ResultPoint` containing the shortest distance and the closest points on the objects.
     */
    public static ResultPoint getShortestDistance(UMLObject object1, UMLObject object2) {
        // Get the coordinates and dimensions of object1
        double object1X = object1.getLayoutX();
        double object1Y = object1.getLayoutY();
        double object1Width = object1.getBoundsInLocal().getWidth();
        double object1Height = object1.getBoundsInLocal().getHeight();

        // Get the coordinates and dimensions of object2
        double object2X = object2.getLayoutX();
        double object2Y = object2.getLayoutY();
        double object2Width = object2.getBoundsInLocal().getWidth();
        double object2Height = object2.getBoundsInLocal().getHeight();

        // Closest X calculation
        double closestX1 = object1X;
        double closestX2 = object2X;

        if (object1X + object1Width < object2X) {
            // object1 is to the left of object2
            closestX1 = object1X + object1Width; // furthest right of object1
            closestX2 = object2X; // closest point on object2 to the left
        } else if (object2X + object2Width < object1X) {
            // object2 is to the left of object1
            closestX1 = object1X; // closest point on object1 to the right
            closestX2 = object2X + object2Width; // furthest left of object2
        } else {
            // Overlapping horizontally
            closestX1 = object1X + object1Width / 2; // center point of object1
            closestX2 = object2X + object2Width / 2; // center point of object2
        }

        // Closest Y calculation
        double closestY1 = object1Y;
        double closestY2 = object2Y;

        if (object1Y + object1Height < object2Y) {
            // object1 is above object2
            closestY1 = object1Y + object1Height; // furthest bottom of object1
            closestY2 = object2Y; // closest point on object2 above
        } else if (object2Y + object2Height < object1Y) {
            // object2 is above object1
            closestY1 = object1Y; // closest point on object1 below
            closestY2 = object2Y + object2Height; // furthest top of object2
        } else {
            // Overlapping vertically
            closestY1 = object1Y + object1Height / 2; // center point of object1
            closestY2 = object2Y + object2Height / 2; // center point of object2
        }

        // Return the shortest distance and the calculated points
        return new ResultPoint(
                Math.hypot(closestX2 - closestX1, closestY2 - closestY1), // Euclidean distance
                new Point(closestX1, closestY1),
                new Point(closestX2, closestY2)
        );
    }

    // Helper class to store the points and distance
    public static class ResultPoint {
        double distance;
        public Point point1;
        public Point point2;

        public ResultPoint(double distance, Point point1, Point point2) {
            this.distance = distance;
            this.point1 = point1;
            this.point2 = point2;
        }
    }

    // Helper class to represent a point with x and y coordinates
    public static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

}
