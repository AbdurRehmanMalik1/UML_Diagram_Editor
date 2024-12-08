package Util;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ToastMessage {

   /**
    * Displays a positive toast message on the provided parent pane.
    * The toast is displayed in green text for positive feedback and fades out after the specified duration.
    *
    * @param parentPane     The JavaFX `Pane` on which to display the toast.
    * @param message        The message to be displayed in the toast.
    * @param durationSeconds The duration in seconds before the toast fades out.
    */
   public static void showPositiveToast(Pane parentPane, String message, int durationSeconds) {
      // Remove any existing toasts to ensure only one message is displayed at a time
      removeExistingToasts(parentPane);

      // Create a label for the toast message
      Label toastLabel = new Label(message);
      toastLabel.setFont(new Font("Arial", 16)); // Set the font and size
      toastLabel.setTextFill(Color.LIGHTGREEN); // Set text color to light green
      toastLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 10px; -fx-background-radius: 3px;"); // Set background style

      // Position the toast label at the top of the parent pane
      toastLabel.setLayoutX(0); // X position (centered)
      toastLabel.setLayoutY(10); // Y position (10 pixels from the top)

      // Add the toast to the parent pane
      parentPane.getChildren().add(toastLabel);

      // Create a pause transition to remove the toast after the specified duration
      PauseTransition pause = new PauseTransition(Duration.seconds(durationSeconds));
      pause.setOnFinished(event -> parentPane.getChildren().remove(toastLabel)); // Remove the toast on transition finish
      pause.play(); // Start the transition
   }

   /**
    * Displays a negative toast message on the provided parent pane.
    * The toast is displayed in red text for negative feedback and fades out after the specified duration.
    *
    * @param parentPane     The JavaFX `Pane` on which to display the toast.
    * @param message        The message to be displayed in the toast.
    * @param durationSeconds The duration in seconds before the toast fades out.
    */
   public static void showNegativeToast(Pane parentPane, String message, int durationSeconds) {
      // Remove any existing toasts to ensure only one message is displayed at a time
      removeExistingToasts(parentPane);

      // Create a label for the toast message
      Label toastLabel = new Label(message);
      toastLabel.setFont(new Font("Arial", 16)); // Set the font and size
      toastLabel.setTextFill(Color.RED); // Set text color to red
      toastLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 10px; -fx-background-radius: 3px;"); // Set background style

      // Position the toast label at the center of the parent pane
      double xPos = (parentPane.getWidth() - toastLabel.getWidth()) / 2.5; // Center the toast horizontally
      toastLabel.setLayoutX(xPos); // X position (centered)
      toastLabel.setLayoutY(10); // Y position (10 pixels from the top)

      // Add the toast to the parent pane
      parentPane.getChildren().add(toastLabel);

      // Create a pause transition to remove the toast after the specified duration
      PauseTransition pause = new PauseTransition(Duration.seconds(durationSeconds));
      pause.setOnFinished(event -> parentPane.getChildren().remove(toastLabel)); // Remove the toast on transition finish
      pause.play(); // Start the transition
   }

   /**
    * Removes any existing toast messages from the parent pane.
    * This is done to ensure that only one toast message is visible at a time.
    *
    * @param parentPane The JavaFX `Pane` from which to remove existing toasts.
    */
   private static void removeExistingToasts(Pane parentPane) {
      parentPane.getChildren().removeIf(child -> child instanceof Label); // Remove all Label instances from the pane
   }
}
