package UML.Line;

import javafx.scene.layout.Pane;

// Abstract base class for different line types
public abstract class Line extends javafx.scene.shape.Line {
    public Pane parentPane;

    public Line(double startX, double startY, double endX, double endY,Pane parentPane) {
        super(startX, startY, endX, endY);
        this.parentPane = parentPane;

        customDraw();
    }

    // Other behavior can be defined in subclasses
    public abstract void customDraw();
}

