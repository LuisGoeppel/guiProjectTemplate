import Enumerations.MouseInputType;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInputHandler implements MouseListener {

    boolean input = false;
    int currentX = -1;
    int currentY = -1;
    MouseInputType currentInputType = MouseInputType.LEFT_CLICK;

    public MouseInputHandler(Launch launch) {
        launch.addMouseListener(this);
    }

    public boolean inputExists() {
        boolean temp = input;
        input = false;
        return temp;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public MouseInputType getCurrentInputType() {
        return currentInputType;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();
        currentInputType = e.getButton() == MouseEvent.BUTTON1
                ? MouseInputType.LEFT_CLICK : MouseInputType.RIGHT_CLICK;
        input = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
