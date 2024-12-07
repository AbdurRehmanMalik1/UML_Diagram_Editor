package Util;
import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserUtil {
    public static File openFileChooser(String description, String extension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extension));
        return fileChooser.showSaveDialog(null);
    }
}
