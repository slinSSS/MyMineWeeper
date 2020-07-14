import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.Timer;

public class MineWeeper extends JFrame {

    Setting setting=new Setting(16,30,99,true);
    HeadPanel head=new HeadPanel(this);
    MinePannel body=new MinePannel(this);


    public MineWeeper() {
        this.setSize(1600, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setTitle("扫雷");

        this.add(head, BorderLayout.NORTH);
        this.setJMenuBar(new Menu(this));
        this.add(body);
        this.pack();
        this.validate();

        this.setVisible(true);
    }


    public void restart() {
        head.init();
        body.init();

        this.pack();
        this.validate();
    }

    public void restartForAnotherSetting(Setting setting){
        this.setting=setting;
        head.init();
        this.remove(body);
        body=new MinePannel(this);
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

