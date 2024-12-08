package UML.Objects;

import Models.Model;
import UML.Line.Line;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
/**
 * Abstract base class representing a UML (Unified Modeling Language) object.
 * It provides basic properties and behavior for UML elements such as rectangles with corner circles.
 */
public abstract class UMLObject extends Parent {
    // Fields
    transient public OuterRectangle outerRect; // The outer rectangular shape of the UML object
    protected Model model; // The associated model of the UML object
    List<Line> associatedLines; // Lines associated with this UML object
    transient CustomPoint point; // Current position of the UML object
    protected Runnable redrawLine; // Runnable to trigger line redraw

    /**
     * Constructs a UMLObject with a specified redraw function.
     *
     * @param redrawFunc The function to call when the UML object's appearance needs to be redrawn.
     */
    public UMLObject(Runnable redrawFunc) {
        super();
        outerRect = new OuterRectangle();
        setRunnable(redrawFunc);
        associatedLines = new ArrayList<>();
    }

    /**
     * Constructs a UMLObject with default settings.
     * Initializes position, mouse events, and outer rectangle.
     */
    public UMLObject() {
        point = new CustomPoint(getLayoutX(), getLayoutY());
        addMouseEvents();
        outerRect = new OuterRectangle();
        associatedLines = new ArrayList<>();
    }

    /**
     * Adds an associated line to the UML object.
     *
     * @param line The line to be associated with the UML object.
     */
    public void addAssociatedLine(Line line) {
        associatedLines.add(line);
    }

    /**
     * Retrieves the list of lines associated with this UML object.
     *
     * @return A list of associated lines.
     */
    public List<UML.Line.Line> getAssociatedLines() {
        return associatedLines;
    }

    /**
     * Sets the runnable that redraws the lines associated with this UML object.
     *
     * @param runnable The runnable to set.
     */
    protected void setRunnable(Runnable runnable) {
        this.redrawLine = runnable;
    }

    /**
     * Adds mouse events (press and drag) to the UML object.
     */
    private void addMouseEvents() {
        setOnMousePressed(new MousePressedHandler());
        setOnMouseDragged(new MouseDraggedHandler());
    }

    /**
     * Abstract method to get the width of the UML object.
     *
     * @return The width of the UML object.
     */
    public abstract double getWidth();

    /**
     * Abstract method to get the height of the UML object.
     *
     * @return The height of the UML object.
     */
    public abstract double getHeight();

    /**
     * Abstract method to get the model associated with this UML object.
     *
     * @return The associated model.
     */
    public abstract Model getModel();

    /**
     * Abstract method to set the model associated with this UML object.
     *
     * @param model The model to set.
     */
    public abstract void setModel(Model model);

    /**
     * Reloads the model's associations with this UML object.
     * Clears existing associations and updates them based on the associated lines.
     */
    public void reloadModel() {
        if (model == null || associatedLines == null) {
            return;
        }

        model.getIncomingAssociations().clear();
        model.getOutgoingAssociations().clear();

        for (UML.Line.Line line : associatedLines) {
            if (line.getStartObject() == this) {
                model.addStartAssociation(line.getAssociationModel());
            } else if (line.getEndObject() == this) {
                model.addEndAssociation(line.getAssociationModel());
            }
        }

        model.setCoordinate(this.getLayoutX(), this.getLayoutY());
    }

    /**
     * Deletes the UML object and all associated lines.
     * Removes the UML object from its parent container.
     */
    public void delete() {
        while (!associatedLines.isEmpty()) {
            associatedLines.getFirst().delete();
        }
        if (getParent() != null && getParent() instanceof Pane) {
            ((Pane) getParent()).getChildren().remove(this);
        }
    }

    /**
     * Resets the mouse pressed handlers for the UML object.
     */
    public void resetMousePressedHandlers() {
        this.setOnMousePressed(new MousePressedHandler());
    }

    /**
     * Inner class handling mouse pressed events.
     * Sets the initial position of the UML object when it is pressed.
     */
    public class MousePressedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            point.setLocation(event.getSceneX(), event.getSceneY());
        }
    }

    /**
     * Inner class handling mouse dragged events.
     * Updates the position of the UML object when it is dragged,
     * ensuring it stays within the bounds of its parent container.
     */
    public class MouseDraggedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
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
            if (!associatedLines.isEmpty())
                updateLines();

            point.setLocation(event.getSceneX(), event.getSceneY());
        }
    }

    /**
     * Inner class representing an outer rectangle for the UML object.
     * Includes corner circles at each corner of the rectangle.
     */
    public class OuterRectangle extends Rectangle {
        private final Circle topLeft;
        private final Circle topRight;
        private final Circle bottomLeft;
        private final Circle bottomRight;

        /**
         * Constructs an OuterRectangle with corner circles.
         */
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

        /**
         * Creates a corner circle with a fixed radius.
         *
         * @return A circle representing a corner of the rectangle.
         */
        private Circle createCornerCircle() {
            double cornerRadius = 3;
            Circle circle = new Circle(cornerRadius);
            circle.setFill(Color.TRANSPARENT);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(1);
            return circle;
        }

        /**
         * Adds the outer rectangle and its corner circles to a group.
         *
         * @param group The group to add the rectangle to.
         */
        public void addToGroup(Group group) {
            group.getChildren().add(this); // Add the rectangle itself
            group.getChildren().addAll(topLeft, topRight, bottomLeft, bottomRight); // Add corner circles
        }

        /**
         * Sets the location of the outer rectangle.
         *
         * @param x The x-coordinate of the rectangle.
         * @param y The y-coordinate of the rectangle.
         */
        public void setLocation(double x, double y) {
            setLayoutX(x);
            setLayoutY(y);
            updateCorners();
        }

        /**
         * Sets the size of the outer rectangle.
         *
         * @param width  The width of the rectangle.
         * @param height The height of the rectangle.
         */
        public void setSize(double width, double height) {
            setWidth(width);
            setHeight(height);
            updateCorners();
        }

        /**
         * Updates the position of the corner circles based on the rectangle's layout.
         */
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

        /**
         * Sets the visibility of the outer rectangle and its corner circles.
         *
         * @param visible True to make the rectangle visible, false to hide it.
         */
        public void setVisibility(boolean visible) {
            this.setVisible(visible);
            topRight.setVisible(visible);
            topLeft.setVisible(visible);
            bottomRight.setVisible(visible);
            bottomLeft.setVisible(visible);
        }
    }

    /**
     * Updates the positions of all associated lines based on the UML object's current location.
     */
    protected void updateLines() {
        for (Line line : associatedLines) {
            if (line.getStartObject() == this) {
                line.updateLinePosition(this, true);
            } else if (line.getEndObject() == this) {
                line.updateLinePosition(this, false);
            }
        }
    }

    /**
     * Represents a custom point with specific coordinates.
     */
    public static class CustomPoint {
        private double x;
        private double y;

        /**
         * Constructs a CustomPoint with the specified coordinates.
         *
         * @param x The x-coordinate.
         * @param y The y-coordinate.
         */
        public CustomPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Sets the coordinates of the custom point.
         *
         * @param x The x-coordinate.
         * @param y The y-coordinate.
         */
        public void setLocation(double x, double y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Gets the x-coordinate of the custom point.
         *
         * @return The x-coordinate.
         */
        public double getX() {
            return x;
        }

        /**
         * Gets the y-coordinate of the custom point.
         *
         * @return The y-coordinate.
         */
        public double getY() {
            return y;
        }
    }
}
