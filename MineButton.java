import javax.swing.*;
import java.awt.*;

public class MineButton extends JButton {
    final Dimension size =new Dimension(42,34);
    boolean opened;
    boolean marked;
    int id;
    int[] index;

    public MineButton(int[] index){
        this.index=index;
        this.setPreferredSize(size);
        this.setBackground(Color.yellow);
    }

    public void init(){
        opened=false;
        marked=false;
        id=0;
        this.setText("");
        this.setEnabled(true);
        this.setBackground(Color.yellow);
    }

    public void setOpened(boolean opened){
        this.opened=opened;
        this.setText(""+id);
        this.setEnabled(false);
        this.setBackground(Color.green);
    }

    public boolean isOpened() {
        return opened;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
        if(marked) this.setBackground(Color.gray);
        else this.setBackground(Color.yellow);
    }

    public boolean isMarked() {
        return marked;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int[] getIndex() {
        return index;
    }

}
