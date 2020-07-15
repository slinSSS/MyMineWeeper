import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeadPanel extends JPanel {
    int MineCount;
    int time;

    JButton button = new JButton();
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    MineWeeper mainframe;
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            time++;
            label2.setText("用时:" + time + 's');
            timer.start();
        }
    });

    public HeadPanel(MineWeeper mainframe) {
        this.mainframe = mainframe;
        this.init();
        this.add(label1);
        this.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainframe.restart();
            }
        });
        this.add(label2);
        timerStop();
    }

    public void init() {
        timer.stop();
        time = 0;
        MineCount = mainframe.getSetting().getMineCount();
        button.setText("重新开始");
        label1.setText("雷数:" + MineCount);
        label2.setText("用时:" + time + 's');
    }

    public void timerStop() {
        timer.stop();
    }

    public void timerStart() {
        timer.start();
    }

    public void setButtonText(String Text) {
        button.setText(Text);
    }

    public void setLabel1Text(String Text) {
        label1.setText(Text);
    }
}
