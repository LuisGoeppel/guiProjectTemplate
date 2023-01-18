import java.util.ArrayList;
import java.util.List;

public class Animation {

    private static class AnimationObject {
        protected int xCord;
        protected int yCord;
        protected int width;
        protected int height;
        protected int age;
        protected boolean shouldPlay;
        protected AnimationFile file;

        public AnimationObject(int xCord, int yCord, int width, int height, AnimationFile file) {
            this.xCord = xCord;
            this.yCord = yCord;
            this.width = width;
            this.height = height;
            this.file = file;

            shouldPlay = true;
            age = 0;
        }
    }
    private final Launch graphics;

    private final List<AnimationObject> animationObjects;

    public Animation(Launch pGraphics) {
        graphics = pGraphics;
        animationObjects = new ArrayList<>();
    }

    public void tick() {
        for (AnimationObject object : animationObjects) {
            object.age++;
            graphics.drawImage(object.file.getNextFrame(),
                    object.xCord, object.yCord, object.width, object.height);
            if (object.age > object.file.animationSpeed * object.file.frameCount) {
                object.shouldPlay = false;
            }
        }
        for (int i = 0; i < animationObjects.size(); i++) {
            if (!animationObjects.get(i).shouldPlay) {
                animationObjects.remove(i);
            }
        }
    }

    public void playAnimation(AnimationFile animationFile, int x, int y, int w, int h) {
        AnimationObject object = new AnimationObject(x, y, w, h, animationFile);
        animationObjects.add(object);
    }
}
