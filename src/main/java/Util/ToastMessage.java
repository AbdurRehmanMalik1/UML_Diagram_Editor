package Util;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ToastMessage {

   public static void showPositiveToast(Pane parentPane, String message, int durationSeconds) {
      removeExistingToasts(parentPane);

      Label toastLabel = new Label(message);
      toastLabel.setFont(new Font("Arial", 16));
      toastLabel.setTextFill(Color.LIGHTGREEN);
      toastLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 10px; -fx-background-radius: 3px;");

      double xPos = (parentPane.getWidth() - toastLabel.getWidth()) / 2.5;
      toastLabel.setLayoutX(xPos);
      toastLabel.setLayoutY(10);

      parentPane.getChildren().add(toastLabel);

      PauseTransition pause = new PauseTransition(Duration.seconds(durationSeconds));
      pause.setOnFinished(event -> parentPane.getChildren().remove(toastLabel));
      pause.play();
   }

   public static void showNegativeToast(Pane parentPane, String message, int durationSeconds) {
      removeExistingToasts(parentPane);

      Label toastLabel = new Label(message);
      toastLabel.setFont(new Font("Arial", 16));
      toastLabel.setTextFill(Color.RED);
      toastLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 10px; -fx-background-radius: 3px;");

      double xPos = (parentPane.getWidth() - toastLabel.getWidth()) / 2.5;
      toastLabel.setLayoutX(xPos);
      toastLabel.setLayoutY(10);

      parentPane.getChildren().add(toastLabel);

      PauseTransition pause = new PauseTransition(Duration.seconds(durationSeconds));
      pause.setOnFinished(event -> parentPane.getChildren().remove(toastLabel));
      pause.play();
   }

   private static void removeExistingToasts(Pane parentPane) {
      parentPane.getChildren().removeIf(child -> child instanceof Label);
   }
}
