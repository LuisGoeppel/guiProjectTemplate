import Enumerations.KeyInputType;
import Interfaces.GraphicMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

class Launch extends Canvas implements Runnable, GraphicMethods {

    private static final long serialVersionUID = 1;
    public static final int WIDTH = 800;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 1;
    private static final String name = "Game";

    private final JFrame frame;

    private boolean running = false;
    public int tickCount = 0;

    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private final Game game;
    private final KeyInputHandler keyInputHandler;
    private final MouseInputHandler mouseInputHandler;
    private final Animation animation;

    private Launch() {
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        animation = new Animation(this);
        game = new Game(this, animation);
        keyInputHandler = new KeyInputHandler(this);
        mouseInputHandler = new MouseInputHandler(this);

        frame = new JFrame(name);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.requestFocus();

        frame.setVisible(true);
    }

    private synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    private synchronized void stop() {
        running = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;

            while (delta >= 1) {
                clearImage();
                ticks++;
                handleInput();
                game.tick();
                animation.tick();
                tickCount++;
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                frame.setTitle(String.format("ticks: %d, frames: %d%n", ticks, frames));
                frames = 0;
                ticks = 0;
            }
        }
    }

    private void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bufferStrategy.show();
    }

    private void handleInput() {
        if (keyInputHandler.up.isPressed()) game.handleKeyInput(KeyInputType.UP);
        if (keyInputHandler.down.isPressed()) game.handleKeyInput(KeyInputType.DOWN);
        if (keyInputHandler.left.isPressed()) game.handleKeyInput(KeyInputType.LEFT);
        if (keyInputHandler.right.isPressed()) game.handleKeyInput(KeyInputType.RIGHT);
        if (keyInputHandler.space.isPressed()) game.handleKeyInput(KeyInputType.SPACE);

        if (keyInputHandler.q.isPressed()) game.handleKeyInput(KeyInputType.Q);
        if (keyInputHandler.e.isPressed()) game.handleKeyInput(KeyInputType.E);
        if (keyInputHandler.r.isPressed()) game.handleKeyInput(KeyInputType.R);
        if (keyInputHandler.f.isPressed()) game.handleKeyInput(KeyInputType.F);

        if (mouseInputHandler.inputExists()) game.handleMouseInput(mouseInputHandler.getCurrentX(),
                mouseInputHandler.getCurrentY(), mouseInputHandler.getCurrentInputType());
    }

    //Draw Methods
    private int getRGBInt (Color c) {
        return 0xFF000000
                | ((c.getRed() << 16) & 0x00FF0000)
                | ((c.getGreen() << 8) & 0x0000FF00)
                | (c.getBlue() & 0x000000FF);
    }

    @Override
    public void putPixel (int red, int green, int blue, int xCord, int yCord) {
        putPixel(new Color(red, green, blue), xCord, yCord);
    }
    @Override
    public void putPixel (Color c, int xCord, int yCord) {
        pixels[yCord * WIDTH + xCord] = getRGBInt(c);
    }

    @Override
    public void fillCircle (Color c, int xCord, int yCord, int radius) {
        int color = getRGBInt(c);
        int start = Math.max(getPosition(xCord - radius, yCord - radius), 0);
        int end = Math.min(getPosition(xCord + radius, yCord + radius), pixels.length);
        for (int i = start; i <= end; i++) {
            if ((getX(i) - xCord) * (getX(i) - xCord) + (getY(i)
                    - yCord) * (getY(i) - yCord) <= radius * radius) {
                pixels[i] = color;
            }
        }
    }

    @Override
    public void fillRect (Color c, int xCord, int yCord, int width, int height) {
        int color = getRGBInt(c);
        int start = Math.max(getPosition(xCord, yCord), 0);
        int end = Math.min(getPosition(xCord + width, yCord + height), pixels.length);
        for (int i = start; i <= end; i++) {
            if (getX(i) >= xCord && getX(i) <= xCord + width) {
                pixels[i] = color;
            }
        }
    }

    @Override
    public void fillLine (Color c, int xStart, int yStart, int xEnd, int yEnd, int thickness) {
        int color = getRGBInt(c);
        double m = (double)(yStart - yEnd) / (double)(xStart - xEnd);
        double t = yStart - (m * xStart);
        int start = Math.min(getPosition(Math.min(xStart, xEnd), Math.min(yStart, yEnd)), 0);
        int end = Math.max(getPosition(Math.max(xStart, xEnd), Math.max(yStart, yEnd)), pixels.length);

        for (int i = start; i <= end; i++) {
            if (isInRange((int)(m * getX(i) + t), getY(i), thickness) && getX(i) > Math.min(xStart, xEnd) && getX(i) < Math.max(xStart, xEnd)) {
                pixels[i] = color;
            }
        }
    }

    @Override
    public void drawImage(BufferedImage image, int xCord, int yCord, int width, int height) {
        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();

        AffineTransformOp transform = new AffineTransformOp(
                AffineTransform.getScaleInstance((double) width / oldWidth, (double) height / oldHeight),
                AffineTransformOp.TYPE_BILINEAR);

        BufferedImage resizedImage = transform.filter(image, null);

        drawImage(resizedImage, xCord, yCord);
    }

    @Override
    public void drawImage(BufferedImage image, int xCord, int yCord) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] imagePixels = image.getRGB(0, 0, width, height, null, 0, width);

        int start = getPosition(xCord, yCord);
        int xOffset = WIDTH - width;

        for (int i = 0; i < imagePixels.length; i++) {
            int alpha = (imagePixels[i] >> 24) & 0xff;

            int currentXOffset = (i / width) * xOffset;
            if (start + i + currentXOffset < pixels.length) {
                pixels[start + i + currentXOffset] = applyAlphaValue( pixels[start + i + currentXOffset], imagePixels[i], alpha);
            }
        }
    }

    @Override
    public void clearImage() {
        Arrays.fill(pixels, 0);
    }

    private int applyAlphaValue(int rgbIntColorOld, int rgbIntColorNew, int alpha) {
        //alpha = 0: completelyTransparent

        int red = ((rgbIntColorNew >> 16) & 0xff) * (alpha / 255) + ((rgbIntColorOld >> 16) & 0xff) * ((255 - alpha) / 255);
        int green = ((rgbIntColorNew >> 8) & 0xff) * (alpha / 255) + ((rgbIntColorOld >> 8) & 0xff) * ((255 - alpha) / 255);
        int blue = (rgbIntColorNew & 0xff) * (alpha / 255) + (rgbIntColorOld & 0xff) * ((255 - alpha) / 255);

        return getRGBInt(new Color(red, green, blue));
    }

    private boolean isInRange(int value1, int value2, int range) {
        return Math.abs(value1 - value2) < range;
    }

    private int getX (int position) {
        return position % WIDTH;
    }

    private int getY (int position) {
        return position / WIDTH;
    }

    private int getPosition (int xCord, int yCord) {
        return xCord + yCord * WIDTH;
    }


    public static void main(String[] args) {
        new Launch().start();
    }
}
