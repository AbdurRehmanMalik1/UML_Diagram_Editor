package Controllers;

import Models.CD.Method;
import UML.Objects.InterfaceObject;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InterfaceDiagramController extends VBox {
    private final InterfaceObject parentClass;
    private final HBox classNameWrapper;

    private Button addMethodButton;
    private Button removeMethodButton;

    public InterfaceDiagramController(InterfaceObject parentClass, HBox classNameWrapper) {
        this.parentClass = parentClass;
        this.classNameWrapper = classNameWrapper;

        initComponents();
        addMethodButton.setFocusTraversable(false);
        removeMethodButton.setFocusTraversable(false);
        Platform.runLater(this::adjustButtonPosition);

        classNameWrapper.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) ->
                Platform.runLater(this::adjustButtonPosition));

        addButtonEvents();
    }

    private void initComponents() {
        addMethodButton = createButton("Add Method", "addMethod_icon.png");
        removeMethodButton = createButton("Remove Method", "removeMethod_icon.png");
        getChildren().add(addMethodButton);
        getChildren().add(removeMethodButton);
    }

    private Button createButton(String tooltipText, String iconFileName) {
        Button button = new Button();
        ImageView icon = new ImageView(getClass().getResource("/Images/" + iconFileName).toExternalForm());
        icon.setFitHeight(20);
        icon.setFitWidth(20);
        button.setGraphic(icon);

        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(button, tooltip);

        tooltip.setShowDelay(javafx.util.Duration.seconds(0.3));
        tooltip.setHideDelay(javafx.util.Duration.seconds(1));

        return button;
    }


    private void addButtonEvents() {
        addMethodButton.setOnMouseClicked(event -> {
            parentClass.addMethod("New Method");
            //parentClass.resizeOuterRect();
        });

        addMethodButton.setOnMouseClicked(event -> {
            String m = "New Method";
            Method method = new Method(m);
            parentClass.addMethod(m);
            //parentClass.resizeOuterRect();
        });

        removeMethodButton.setOnMouseClicked(event -> {
            StackPane selectedMethod = parentClass.getSelectedMethod();
            if (selectedMethod != null) {
                parentClass.removeMethod(selectedMethod);
                parentClass.resizeOuterRect();
            } else {
                System.out.println("No method selected to remove.");
            }
        });
    }

    private void adjustButtonPosition() {
        if (classNameWrapper != null && classNameWrapper.getLayoutBounds() != null) {
            double wrapperWidth = classNameWrapper.getLayoutBounds().getWidth();
            setLayoutX(classNameWrapper.getLayoutX() + wrapperWidth);
        }
    }
}
