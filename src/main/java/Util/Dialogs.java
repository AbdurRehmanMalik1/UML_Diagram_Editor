package Util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Dialogs {
    /**
     * Displays a confirmation dialog with a specified title, content, and button labels.
     * Returns `true` if the user confirms the action by clicking the confirmation button, otherwise returns `false`.
     *
     * @param title The title of the dialog.
     * @param content The content message to be displayed in the dialog.
     * @param confirmText The text to display on the confirmation button.
     * @param cancelText The text to display on the cancel button.
     * @return `true` if the user clicks the confirmation button, `false` if they click the cancel button or close the dialog.
     */
    public static boolean showConfirmDialog(String title, String content, String confirmText, String cancelText) {
        // Create a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);

        // Define the confirm and cancel buttons
        ButtonType confirmButton = new ButtonType(confirmText);
        ButtonType cancelButton = new ButtonType(cancelText);

        // Add the buttons to the alert
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        // Show the alert and capture the user's response
        Optional<ButtonType> result = alert.showAndWait();

        // Return true if the user clicked the confirm button, otherwise false
        return result.isPresent() && result.get() == confirmButton;
    }
}
