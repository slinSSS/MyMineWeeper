import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EventListener1 extends MouseAdapter {
    private int id=0;

    @Override
    public void mousePressed(MouseEvent e) {
        int[] index=((MineButton)e.getSource()).getIndex();
        if (e.getModifiersEx() == (MouseEvent.BUTTON3_DOWN_MASK + MouseEvent.BUTTON1_DOWN_MASK)) {
//            System.out.println("j");
            clickBoth(index);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println("mouseClicked" + " " + id++ + " ");
        int[] index=((MineButton)e.getSource()).getIndex();
        if (e.getButton() == MouseEvent.BUTTON1) {
            clickLeft(index);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            clickRight(index);
        }
    }

    public void clickBoth(int[] index) {
    }

    public void clickLeft(int[] index) {
    }

    public void clickRight(int[] index) {
    }
}