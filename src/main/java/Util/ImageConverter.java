package Util;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

public class ImageConverter {

    /**
     * Converts a JavaFX `WritableImage` to a `BufferedImage`.
     *
     * @param writableImage The JavaFX `WritableImage` to be converted.
     * @return A `BufferedImage` representation of the `WritableImage`.
     */
    public static BufferedImage convertToBufferedImage(WritableImage writableImage) {
        int width = (int) writableImage.getWidth(); // Get the width of the image
        int height = (int) writableImage.getHeight(); // Get the height of the image

        // Create a new BufferedImage with the same dimensions and ARGB format
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        PixelReader pixelReader = writableImage.getPixelReader(); // Get the PixelReader from the WritableImage

        // Iterate over each pixel in the WritableImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the ARGB value of the current pixel
                int argb = pixelReader.getArgb(x, y);
                // Set the ARGB value to the corresponding pixel in the BufferedImage
                bufferedImage.setRGB(x, y, argb);
            }
        }
        return bufferedImage; // Return the converted BufferedImage
    }
}
