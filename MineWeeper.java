import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.Timer;

public class MineWeeper extends JFrame {

    Setting setting = new Setting("easy");
    HeadPanel head = new HeadPanel(this);
    MinePannel body = new MinePannel(this);


    public MineWeeper() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setTitle("扫雷");
        this.setVisible(true);

        this.add(head, BorderLayout.NORTH);
        this.setJMenuBar(new Menu(this));
        this.add(body);

        this.pack();
        this.validate();
    }


    public void restart() {
        head.init();
        body.init();
    }

    public void restartForAnotherSetting(Setting setting) {
        this.setting = setting;
        head.init();
        this.remove(body);
        body = new MinePannel(this);
        this.add(body);

        this.pack();
        this.validate();
    }

    public static void main(String[] args) {
        new MineWeeper();
    }

    public Setting getSetting() {
        return setting;
    }

    public HeadPanel getHead() {
        return head;
    }
}

