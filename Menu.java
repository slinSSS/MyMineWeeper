import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar {
    private JMenu MenuGame=new JMenu("游戏");
    private JMenuItem menuItemRestart=new JMenuItem("重新开始");
    private JMenuItem menuItemEasy=new JMenuItem("初级");
    private JMenuItem menuItemMedium=new JMenuItem("中级");
    private JMenuItem menuItemHard=new JMenuItem("高级");

    private MineWeeper mainFrame;

    public Menu(MineWeeper mainFrame){
        this.mainFrame=mainFrame;
        init();
    }
    private void init(){
        MenuGame.add(menuItemRestart);
        menuItemRestart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.restart();

            }
        });
        MenuGame.add(menuItemEasy);
        menuItemEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.restart();
            }
        });

        this.add(MenuGame);
    }
}
