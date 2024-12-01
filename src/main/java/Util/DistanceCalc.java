package Util;

import javafx.scene.Node;

public class DistanceCalc {
    public static ResultPoint getShortestDistance(Node object1, Node object2) {
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

    // Find the closest horizontal points
    double closestX1 = object1X + object1Width / 2;
    double closestX2 = object2X + object2Width / 2;
    if (object1X + object1Width < object2X) {
        closestX1 = object1X + object1Width;  // object1 is to the left
    } else if (object2X + object2Width < object1X) {
        closestX2 = object2X + object2Width;  // object2 is to the left
    }

    // Find the closest vertical points
    double closestY1 = object1Y + object1Height / 2;
    double closestY2 = object2Y + object2Height / 2;
    if (object1Y + object1Height < object2Y) {
        closestY1 = object1Y + object1Height;  // object1 is above
    } else if (object2Y + object2Height < object1Y) {
        closestY2 = object2Y + object2Height;  // object2 is above
    }

    // Calculate the horizontal and vertical distances
    double horizontalDistance = Math.abs(closestX1 - closestX2);
    double verticalDistance = Math.abs(closestY1 - closestY2);

    // Calculate the Euclidean distance between the closest points
    double shortestDistance = Math.sqrt(horizontalDistance * horizontalDistance + verticalDistance * verticalDistance);

    // Return the result with the shortest distance and the points causing it
    return new ResultPoint(shortestDistance, new Point(closestX1, closestY1), new Point(closestX2, closestY2));
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
