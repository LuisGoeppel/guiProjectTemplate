import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnimationFile {

    String pathString;
    BufferedImage[] frames;

    int frameCount;
    int currentFrame;
    int animationSpeed;
    int animationCount;

    File gifFile;

    public AnimationFile(String pPathString) {
        pathString = pPathString;
        gifFile = new File(pathString);
        currentFrame = 0;
        animationSpeed = 1;
        animationCount = 0;

        try {
            ImageInputStream iis = ImageIO.createImageInputStream(gifFile);
            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            reader.setInput(iis);
            frameCount = reader.getNumImages(true);
            frames = new BufferedImage[frameCount];
            for (int i = 0; i < frameCount; i++) {
                frames[i] = reader.read(i);
            }
        } catch (IOException e) {
            System.out.println("gif File could not be loaded");
        }
    }

    public BufferedImage getNextFrame() {
        int oldFrame = currentFrame;
        animationCount++;

        if (animationCount >= animationSpeed) {
            currentFrame++;
            if (currentFrame >= frameCount) {
                currentFrame = 0;
            }
            animationCount = 0;
        }

        return frames[oldFrame];
    }

    public BufferedImage getFrame (int frameNumber) {
        if (frameNumber < frameCount) {
            return frames[frameNumber];
        }
        return null;
    }

    public void resetCurrentFrame() {
        currentFrame = 0;
    }

    public void setCurrentFrame(int number) {
        currentFrame = number;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setAnimationSpeed(int pSpeed) {
        animationSpeed = pSpeed;
    }
}
