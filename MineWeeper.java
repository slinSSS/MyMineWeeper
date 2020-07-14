import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.Timer;

public class MineWeeper extends JFrame {
    final int ROW = 16, COL = 30;
    final int[] dx = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    final int[] dy = new int[]{1, 0, -1, 1, -1, 1, 0, -1};

    HeadPanel head=new HeadPanel(this);
    boolean lose, suc;
    int[][] map = new int[ROW][COL];
    JButton[][] buttons = new JButton[ROW][COL];
    Map<JButton, int[]> indexmap = new HashMap<>();
    Set<String> opened = new HashSet<>();
    Set<String> marked = new HashSet<>();


    public MineWeeper() {
        this.setSize(1600, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setTitle("扫雷");
        this.add(head, BorderLayout.NORTH);

        setButton();

        this.setVisible(true);
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
                button1.addMouseListener(new EventListener1() {
                    int[] index = indexmap.get(button1);

                    @Override
                    public void clickBoth() {
                        count(index);
                    }

                    @Override
                    public void clickLeft() {
                        if (marked.contains(Arrays.toString(index))) return;
                        if (opened.size() == 0) {
                            start(index);
                        }
                        leftclick(index);
                    }

                    @Override
                    public void clickRight() {
                        mark(index);
                    }
                });
            }
        }
        this.add(con, BorderLayout.CENTER);
    }

    private void mark(int[] index) {
        JButton button1 = buttons[index[0]][index[1]];
        if (opened.contains(Arrays.toString(index))) return;
        if (!marked.contains(Arrays.toString(index))) {
            marked.add(Arrays.toString(index));
            button1.setBackground(Color.GRAY);
            head.setLabel1Text("雷数:" + (head.getMineCount() - marked.size()));
        } else {
            marked.remove(Arrays.toString(index));
            button1.setBackground(Color.yellow);
            head.setLabel1Text("雷数:" + (head.getMineCount() - marked.size()));
        }
    }

    private void count(int[] index) {
        countOpen(index);
        countMark(index);
    }

    private void countOpen(int[] index) {
        if (!opened.contains(Arrays.toString(index))) return;
        int count1 = 0;
        for (int i = 0; i < 8; i++) {
            index[0] += dx[i];
            index[1] += dy[i];
            if (index[0] >= 0 && index[0] < ROW && index[1] >= 0 && index[1] < COL) {
                if (marked.contains(Arrays.toString(index))) count1++;
            }
            index[0] -= dx[i];
            index[1] -= dy[i];
        }
        if (count1 == map[index[0]][index[1]]) {
            for (int i = 0; i < 8; i++) {
                index[0] += dx[i];
                index[1] += dy[i];
                if (index[0] >= 0 && index[0] < ROW && index[1] >= 0 && index[1] < COL) {
                    leftclick(index);
                    if (lose || suc) break;
                }
                index[0] -= dx[i];
                index[1] -= dy[i];
            }
        }
    }

    private void countMark(int[] index) {
        if (!opened.contains(Arrays.toString(index))) return;
        int count2 = 0;
        for (int i = 0; i < 8; i++) {
            index[0] += dx[i];
            index[1] += dy[i];
            if (index[0] >= 0 && index[0] < ROW && index[1] >= 0 && index[1] < COL) {
                if (!opened.contains(Arrays.toString(index))) count2++;
            }
            index[0] -= dx[i];
            index[1] -= dy[i];
        }
        if (count2 == map[index[0]][index[1]]) {
            for (int i = 0; i < 8; i++) {
                index[0] += dx[i];
                index[1] += dy[i];
                if (index[0] >= 0 && index[0] < ROW && index[1] >= 0 && index[1] < COL) {
                    if (!marked.contains(Arrays.toString(index))) mark(index);
                }
                index[0] -= dx[i];
                index[1] -= dy[i];
            }
        }
    }

    private void leftclick(int[] index) {
        JButton button1 = buttons[index[0]][index[1]];
        if (marked.contains(Arrays.toString(index))) return;
        if (map[index[0]][index[1]] == -1) {
            button1.setBackground(Color.RED);
            button1.setText("-1");
            opened.add(Arrays.toString(index));
            lose();
        } else {
            openButton(index);
            if (opened.size() == ROW * COL - head.getMineCount()) suc();
        }
    }

    private void suc() {
        suc = true;
        head.timerStop();
        head.setButtonText("你赢了");
        JOptionPane.showMessageDialog(this, "你赢了，点击确定重新开始");
        restart();
    }

    private void lose() {
        lose = true;
        head.setButtonText("你输了");
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
        head.timerStop();
        JOptionPane.showMessageDialog(this, "你输了，点击确定重新开始");
        restart();
    }

    public void restart() {
        opened.clear();
        marked.clear();
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                map[i][j] = 0;
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(Color.yellow);
            }
        }
        this.remove(head);
        head=new HeadPanel(this);
        this.add(head, BorderLayout.NORTH);
    }

    private void start(int[] index) {
        suc = false;
        lose = false;
        addMine(index);
        setMap();
        head.setLabel1Text("雷数:" + head.getMineCount());
        head.timerStart();
    }

    private void addMine(int[] index) {
        Random r = new Random();
        for (int i = 0; i < head.getMineCount(); ) {
            int x = r.nextInt(ROW);
            int y = r.nextInt(COL);
            if (map[x][y] != -1 && !(Math.abs(x - index[0]) <= 1 && Math.abs(y - index[1]) <= 1)) {
                map[x][y] = -1;
                i++;
            }
        }
    }

    private void openButton(int[] index) {
        if (opened.contains(Arrays.toString(index))) return;
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
        countOpen(index);
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
        new MineWeeper();
    }

}

