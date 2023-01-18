package Interfaces;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Contains graphic methods
 */
public interface GraphicMethods {

    /**
     * Put a pixel with the given Color on the screen
     * @param x the x-Coordinate of the pixel
     * @param y the y-Coordinate of the pixel
     * @param r the red-Value of the color
     * @param g the green-Value of the color
     * @param b the blue-Value of the color
     */
    void putPixel(int x, int y, int r, int g, int b);

    /**
     * Put a pixel with the given Color on the screen
     * @param x the x-Coordinate of the pixel
     * @param y the y-Coordinate of the pixel
     * @param c the Color of the pixel
     */
    void putPixel(Color c, int x, int y);

    /**
     * Put a filled Circle with the given Color on the Screen
     * @param c the Color of the Circle
     * @param x the x-Coordinate of the Circle's center
     * @param y the y-Coordinate of the Circle's center
     * @param r the radius of the Circle
     */
    void fillCircle(Color c, int x, int y, int r);

    /**
     * Put a filled Rectangle with the given Color on the Screen
     * @param c the Color of the Rectangle
     * @param x the x-Coordinate of the Rectangle's top-Left Corner
     * @param y the y-Coordinate of the Rectangle's top-Left Corner
     * @param width the width of the Rectangle
     * @param height the height of the Rectangle
     */
    void fillRect(Color c, int x, int y, int width, int height);

    /**
     * Put a Line with the given Color on the Screen
     * @param c the Color of the Line
     * @param xStart the x-Coordinate of the starting Point of the line
     * @param yStart the y-Coordinate of the starting Point of the line
     * @param xEnd the x-Coordinate of the ending Point of the line
     * @param yEnd the y-Coordinate of the ending Point of the line
     * @param thickness
     */
    void fillLine(Color c, int xStart, int yStart, int xEnd, int yEnd, int thickness);

    /**
     * Draws the given image on the screen, keeping it's original width and height
     * The given coordinates will be the images top left corner
     * @param image The image that should be drawn as a Buffered Image
     * @param xCord The x Coordinate on which the image should be drawn
     * @param yCord The y Coordinate on which the image should be drawn
     */
    void drawImage(BufferedImage image, int xCord, int yCord);

    /**
     Draws the given image on the screen. The given coordinates will be the images top left corner
     * @param image The image that should be drawn as a Buffered Image
     * @param xCord The x Coordinate on which the image should be drawn
     * @param yCord The y Coordinate on which the image should be drawn
     * @param width The width with which the image should be drawn
     * @param height The height with which the image should be drawn
     */
    void drawImage(BufferedImage image, int xCord, int yCord, int width, int height);

    /**
     * clears the Screen from previous Instructions.
     * Should normally be called every tick
     */
    void clearImage();
}
