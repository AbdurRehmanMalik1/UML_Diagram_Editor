package UML.Objects;

import Models.ActorModel;
import Models.Model;
import UML.UI_Components.EditableField;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/**
 * Represents an actor object in the UML diagram.
 * This class extends the `UMLObject` class and provides a graphical representation of an actor with an editable name.
 */
public class ActorObject extends UMLObject {
    private final SVGPath actorSVG;         // The SVG path representing the visual appearance of the actor.
    private final EditableField field;      // The editable field for modifying the actor's name.

    /**
     * Constructor for creating an actor object with an initial name.
     *
     * @param initialText The initial text (name) for the actor.
     */
    public ActorObject(String initialText) {
        super();

        // Initialize the model if it's not already set.
        if (model == null) {
            model = new ActorModel();
        }

        // Create and configure the SVG path for the actor's graphical representation.
        actorSVG = new SVGPath();
        actorSVG.setContent("M75 25a20 20 0 1 0 0.01 0 M75 65v60 M50 80h50 M75 125l-25 40 M75 125l25 40");
        actorSVG.setFill(Color.TRANSPARENT);
        actorSVG.setStroke(Color.BLACK);
        actorSVG.setStrokeWidth(1);

        // Create an editable field for the actor's name.
        field = new EditableField(initialText, this::reloadModel);

        // Set up the container with the SVG path and editable field.
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(actorSVG, field);

        // Add the container to the ActorObject.
        getChildren().add(container);

        // Adjust the outer rectangle size and visibility based on the ActorObject's focus.
        outerRect.setSize(getWidth(), getHeight());
        outerRect.setVisibility(false);
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            outerRect.setVisibility(newValue);
        });

        // Set the default color for the actor.
        setActorColor(Color.rgb(231, 227, 227));
    }

    /**
     * Downcasts the generic `Model` to `ActorModel`.
     *
     * @return The downcasted `ActorModel`.
     */
    public ActorModel downcastModel() {
        return (ActorModel) model;
    }

    /**
     * Default constructor that initializes the actor object with the default name "Actor".
     */
    public ActorObject() {
        this("Actor");
    }

    /**
     * Returns the width of the ActorObject, calculated based on the SVG path and editable field.
     *
     * @return The width of the actor.
     */
    @Override
    public double getWidth() {
        return Math.max(actorSVG.getLayoutBounds().getWidth(), field.getWidth());
    }

    /**
     * Returns the height of the ActorObject, which includes the SVG path and the editable field height.
     *
     * @return The height of the actor.
     */
    @Override
    public double getHeight() {
        return actorSVG.getLayoutBounds().getHeight() + field.getHeight() + 5; // 5px spacing
    }

    /**
     * Returns the model associated with this ActorObject.
     *
     * @return The `ActorModel` associated with this actor.
     */
    @Override
    public Model getModel() {
        return model;
    }

    /**
     * Sets the model for this ActorObject.
     *
     * @param model The `Model` to be set.
     */
    @Override
    public void setModel(Model model) {
        this.model = model;
        field.setText(downcastModel().getActorName());
        this.setLayoutX(model.getX());
        this.setLayoutY(model.getY());
        reloadModel();
    }

    /**
     * Reloads the model based on the current state of the actor's name and position.
     */
    @Override
    public void reloadModel() {
        if (model != null) {
            downcastModel().setActorName(field.getText());
            model.setCoordinate(this.getLayoutX(), this.getLayoutY());
        }
    }

    /**
     * Sets the position of the actor object.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public void setPosition(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
    }

    /**
     * Sets the color of the actor.
     *
     * @param color The new color.
     */
    public void setActorColor(Color color) {
        actorSVG.setFill(color);
    }

    /**
     * Gets the name of the actor.
     *
     * @return The actor's name.
     */
    public String getActorName() {
        return field.getText();
    }

    /**
     * Gets the color of the actor.
     *
     * @return The actor's color.
     */
    public Color getColor() {
        return (Color) actorSVG.getFill();
    }

    /**
     * Gets the SVGPath representing the actor.
     *
     * @return The SVG path of the actor.
     */
    public SVGPath getActorSvg() {
        return actorSVG;
    }
}
