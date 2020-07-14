import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EventListener1 extends MouseAdapter {
    private boolean flag = false;
    private boolean flaghelp = false;
    private int id=0;

    public void init(){
        flag=false;
        flaghelp=false;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        int[] index=((MineButton)e.getSource()).getIndex();
        if (e.getModifiersEx() == (MouseEvent.BUTTON3_DOWN_MASK + MouseEvent.BUTTON1_DOWN_MASK)) {
//            System.out.println("j");
            flag = true;
            clickBoth(index);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println("mouseClicked" + " " + id++ + " " + flag);
        int[] index=((MineButton)e.getSource()).getIndex();
        if (flag) {
            if (flaghelp) {
                flaghelp = false;
                flag = false;
            } else flaghelp = true;
            return;
        }
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