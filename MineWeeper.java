import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.Timer;

public class MineWeeper {
    final int MineCount = 99;
    final int ROW = 16, COL = 30;
    final int[] dx = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    final int[] dy = new int[]{1, 0, -1, 1, -1, 1, 0, -1};

    int[][] map = new int[ROW][COL];
    JButton[][] buttons = new JButton[ROW][COL];
    Map<JButton, int[]> indexmap = new HashMap();
    Set<String> opened = new HashSet<>();
    int time = 0;

    JFrame f = new JFrame();

    JButton button = new JButton("重新开始");
    JLabel label1 = new JLabel("雷数:" + MineCount);
    JLabel label2 = new JLabel("用时:" + time + 's');

    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            time++;
            label2.setText("用时:" + time + 's');
            timer.start();
        }
    });

    public MineWeeper() {
        f.setSize(1600, 650);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setLayout(new BorderLayout());
        f.setTitle("扫雷");
        setHeader();

        setButton();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
        f.setVisible(true);
    }

    private void setHeader() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(label1);
        panel.add(button);
        panel.add(label2);
        f.add(panel, BorderLayout.NORTH);

    }

    private void setButton() {
        JPanel con = new JPanel();
        con.setLayout(new GridLayout(ROW, COL));
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                JButton button1 = new JButton();
                button1.setBackground(Color.yellow);
                indexmap.put(button1, new int[]{i, j});
                con.add(button1);
                buttons[i][j] = button1;
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int[] index = indexmap.get(button1);
                        if (opened.size() == 0) {
                            start(index);
                        }

                        if (map[index[0]][index[1]] == -1) {
                            button1.setBackground(Color.RED);
                            button1.setText("-1");
                            opened.add(Arrays.toString(index));
                            lose();
                        } else {
                            openButton(index);
                            if (opened.size() == ROW * COL - MineCount) suc();
                        }
                    }
                });
            }
        }
        f.add(con, BorderLayout.CENTER);
    }

    private void suc() {
        timer.stop();
        button.setText("你赢了");
        JOptionPane.showMessageDialog(f, "你赢了，点击确定重新开始");
        restart();
    }

    private void lose() {
        button.setText("你输了");
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (!opened.contains(Arrays.toString(new int[]{i, j}))) {
                    buttons[i][j].setEnabled(false);
                    buttons[i][j].setText("" + map[i][j]);
                    if (map[i][j] == -1) buttons[i][j].setBackground(Color.GRAY);
                    else buttons[i][j].setBackground(Color.blue);
                }
            }
        }
        timer.stop();
        JOptionPane.showMessageDialog(f, "你输了，点击确定重新开始");
        restart();
    }

    private void restart() {
        opened.clear();
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                map[i][j] = 0;
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(Color.yellow);
            }
        }
        time = 0;
        label2.setText("用时:" + time + 's');
        button.setText("重新开始");
        timer.stop();
    }

    private void start(int[] index) {
        addMine(index);
        setMap();
        timer.start();
    }

    private void addMine(int[] index) {
        Random r = new Random();
        for (int i = 0; i < MineCount; ) {
            int x = r.nextInt(ROW);
            int y = r.nextInt(COL);
            if (map[x][y] != -1 && !(Math.abs(x - index[0]) <= 1 && Math.abs(y - index[1]) <= 1)) {
                map[x][y] = -1;
                i++;
            }
        }
    }

    private void openButton(int[] index) {
        JButton button1 = buttons[index[0]][index[1]];
        button1.setText(map[index[0]][index[1]] + "");
        button1.setBackground(Color.green);
        button1.setEnabled(false);
        opened.add(Arrays.toString(index));
        if (map[index[0]][index[1]] == 0) {
            for (int i = 0; i < 8; i++) {
                index[0] += dx[i];
                index[1] += dy[i];
                if (index[0] >= 0 && index[0] < ROW && index[1] >= 0 && index[1] < COL) {
                    if (!opened.contains(Arrays.toString(index))) {
                        openButton(index);
                    }
                }
                index[0] -= dx[i];
                index[1] -= dy[i];
            }
        }
    }

    private void setMap() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (map[i][j] == 0) {
                    for (int n = 0; n < 8; n++) {
                        int x = i + dx[n], y = j + dy[n];
                        if (x >= 0 && x < ROW && y >= 0 && y < COL && map[x][y] == -1) map[i][j]++;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        MineWeeper a = new MineWeeper();
    }
}
