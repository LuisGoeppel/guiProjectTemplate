import Enumerations.KeyInputType;
import Enumerations.MouseInputType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


public class Game {

    Launch graphics;
    Animation animation;

    /** -------------------------- Game Variables ----------------------------*/


    /** -----------------------------------------------------------------------*/

    public Game(Launch graphics, Animation animation) {
        this.graphics = graphics;
        this.animation = animation;
        init();
    }

    public Game(Launch graphics) {
        this.graphics = graphics;
        init();
    }

    /**
     * This methode will be called once before the game starts
     */
    public void init() {

    }

    /**
     * This methode will be called 60 times a second
     */
    public void tick() {
        graphics.putPixel(255, 255, 255, 100, 100);

        graphics.putPixel(255, 255, 255, 98, 100);
        graphics.putPixel(255, 255, 255, 99, 100);
        graphics.putPixel(255, 255, 255, 101, 100);
        graphics.putPixel(255, 255, 255, 102, 100);

        graphics.putPixel(255, 255, 255, 100, 98);
        graphics.putPixel(255, 255, 255, 100, 99);
        graphics.putPixel(255, 255, 255, 100, 101);
        graphics.putPixel(255, 255, 255, 100, 102);
    }

    /**
     * This methode will allow the class to interact with Keyboard input
     * It will be called everytime an input is detected
     * @param keyInputType which keyType has been pressed
     */
    public void handleKeyInput(KeyInputType keyInputType) {
        System.out.println("Key input detected: " + keyInputType.toString());
    }

    /**
     * This methode will allow the class to interact with Mouse input
     * It will be called everytime an input is detected
     * @param xCord the x Coordinate of the mouse input
     * @param yCord the y Coordinate of the mouse input
     * @param inputType if the detected input was a left or a right click
     */
    public void handleMouseInput(int xCord, int yCord, MouseInputType inputType) {
        System.out.printf("Mouse input detected: x = %d, y = %d%n", xCord, yCord);
    }

    /** --------------------------- Methods ------------------------------- */

}
