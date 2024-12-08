package Util;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSaverUtil {

    /**
     * Exception class to handle image save errors.
     */
    public static class ImageSaveException extends Exception {
        public ImageSaveException(String message) {
            super(message);
        }
    }

    /**
     * Saves the given canvas as a PNG image.
     *
     * @param canvas The JavaFX `Pane` containing the content to be saved as an image.
     * @throws ImageSaveException if there is an error during the save operation.
     */
    public static void savePNG(Pane canvas) throws ImageSaveException {
        // Open a file chooser dialog to select the location and name of the file
        File file = FileChooserUtil.openFileChooser("PNG Files", "*.png");
        if (file != null) {
            try {
                // Create a snapshot of the canvas
                WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
                // Convert the snapshot to a BufferedImage
                BufferedImage bufferedImage = ImageConverter.convertToBufferedImage(image);

                // Attempt to save the image as a PNG file
                boolean isSaved = ImageIO.write(bufferedImage, "PNG", file);
                if (!isSaved) {
                    throw new ImageSaveException("Failed to save PNG image.");
                }
            } catch (IOException e) {
                throw new ImageSaveException("Error saving the PNG image: " + e.getMessage());
            }
        } else {
            throw new ImageSaveException("No file selected for PNG.");
        }
    }

    /**
     * Saves the given canvas as a JPEG image.
     *
     * @param canvas The JavaFX `Pane` containing the content to be saved as an image.
     * @throws ImageSaveException if there is an error during the save operation.
     */
    public static void saveJPEG(Pane canvas) throws ImageSaveException {
        // Open a file chooser dialog to select the location and name of the file
        File file = FileChooserUtil.openFileChooser("JPEG Files", "*.jpg");
        if (file != null) {
            try {
                // Create a snapshot of the canvas
                WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
                // Convert the snapshot to a BufferedImage
                BufferedImage bufferedImage = ImageConverter.convertToBufferedImage(image);

                // Attempt to save the image as a JPEG file
                boolean isSaved = ImageIO.write(bufferedImage, "JPEG", file);
                if (!isSaved) {
                    throw new ImageSaveException("Failed to save JPEG image.");
                }
            } catch (IOException e) {
                throw new ImageSaveException("Error saving the JPEG image: " + e.getMessage());
            }
        } else {
            throw new ImageSaveException("No file selected for JPEG.");
        }
    }
}
