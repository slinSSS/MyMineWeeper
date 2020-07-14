import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeadPanel extends JPanel {
    int MineCount=99;
    int time;

    JButton button = new JButton("重新开始");
    JLabel label1 = new JLabel("雷数:" + MineCount);
    JLabel label2 = new JLabel("用时:" + time + 's');
    MineWeeper mainframe;
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            time++;
            label2.setText("用时:" + time + 's');
            timer.start();
        }
    });

    public HeadPanel(MineWeeper mainframe){
        this.mainframe=mainframe;
        init();
    }
    public void init(){
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



    public void timerStop(){
        timer.stop();
    }
    public void timerStart(){
        timer.start();
    }
    public void setButtonText(String Text){
        button.setText(Text);
    }
    public void setLabel1Text(String Text){
        label1.setText(Text);
    }

    public int getMineCount() {
        return MineCount;
    }
}
