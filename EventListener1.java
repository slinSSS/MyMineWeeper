import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EventListener1 extends MouseAdapter {
    private boolean flag = false;
    private boolean flaghelp = false;

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getModifiersEx() == (MouseEvent.BUTTON3_DOWN_MASK + MouseEvent.BUTTON1_DOWN_MASK)) {
            clickBoth();
            flag = true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (flag) {
            if (flaghelp) {
                flaghelp = false;
                flag = false;
            } else flaghelp = true;
            return;
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            clickLeft();
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            clickRight();
        }
    }

    public void clickBoth() {
    }

    public void clickLeft() {
    }

    public void clickRight() {
    }
}