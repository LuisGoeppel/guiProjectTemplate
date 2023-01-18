import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputHandler implements KeyListener {

    public KeyInputHandler(Launch launch) {
        launch.addKeyListener(this);

        up = new Key();
        down = new Key();
        left = new Key();
        right = new Key();
        space = new Key();

        q = new Key();
        e = new Key();
        r = new Key();
        f = new Key();
    }

    public class Key {
        private boolean pressed = false;

        public void toggle (boolean isPressed) {
            pressed = isPressed;
        }

        public boolean isPressed() {
            return pressed;
        }
    }

    public Key up, down, left, right;
    public Key space, q, e, r, f;


    @Override
    public void keyTyped(KeyEvent e) {
        //do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e.getKeyCode(), false);
    }

    private void toggle(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) up.toggle(isPressed);
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) down.toggle(isPressed);
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) left.toggle(isPressed);
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) right.toggle(isPressed);

        if (keyCode == KeyEvent.VK_Q) q.toggle(isPressed);
        if (keyCode == KeyEvent.VK_E) e.toggle(isPressed);
        if (keyCode == KeyEvent.VK_R) r.toggle(isPressed);
        if (keyCode == KeyEvent.VK_F) f.toggle(isPressed);

        if (keyCode == KeyEvent.VK_SPACE) space.toggle(isPressed);
    }
}
