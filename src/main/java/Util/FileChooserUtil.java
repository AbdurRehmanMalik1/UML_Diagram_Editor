package Util;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserUtil {

    /**
     * Opens a save file chooser dialog with the given description and extension filter.
     *
     * @param description A description for the file type (e.g., "JSON Files").
     * @param extension   The file extension filter (e.g., "*.json").
     * @return The selected file or null if the user cancels.
     */
    public static File openFileChooser(String description, String extension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extension));
        return fileChooser.showSaveDialog(null);
    }
    /**
     * Opens a directory chooser dialog to select a folder.
     *
     * @return The selected directory or null if the user cancels.
     */
    public static File openDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory for Code Generation");
        return directoryChooser.showDialog(null); // Replace null with an owner window if needed
    }
}
