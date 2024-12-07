package Util;



import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Dialogs {
    public static boolean showConfirmDialog(String title, String content, String confirmText, String cancelText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        ButtonType confirmButton = new ButtonType(confirmText);
        ButtonType cancelButton = new ButtonType(cancelText);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == confirmButton;
    }
}
