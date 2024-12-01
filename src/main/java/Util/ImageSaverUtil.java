package Util;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSaverUtil {
    public static class ImageSaveException extends Exception {
        public ImageSaveException(String message) {
            super(message);
        }
    }
    public static void savePNG(Pane canvas) throws ImageSaveException {
        File file = FileChooserUtil.openFileChooser("PNG Files", "*.png");
        if (file != null) {
            try {
                WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
                BufferedImage bufferedImage = ImageConverter.convertToBufferedImage(image);

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

    // Method to save the screenshot as JPEG
    public static void saveJPEG(Pane canvas) throws ImageSaveException {
        File file = FileChooserUtil.openFileChooser("JPEG Files", "*.jpg");
        if (file != null) {
            try {
                WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
                BufferedImage bufferedImage = ImageConverter.convertToBufferedImage(image);

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
