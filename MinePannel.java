import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class MinePannel extends JPanel {
    final int[] dx = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    final int[] dy = new int[]{1, 0, -1, 1, -1, 1, 0, -1};

    boolean lose, suc;
    int ROW, COL, MineCount, openCount, markCount;
    MineButton[][] buttons;
    MineWeeper mainFrame;
    EventListener1 listener = new EventListener1() {
        @Override
        public void clickBoth(int[] index) {
            count(index);
        }

        @Override
        public void clickLeft(int[] index) {
            if (buttons[index[0]][index[1]].isMarked()) return;
            if (openCount == 0) {
                start(index);
            }
            leftclick(index);
        }

        @Override
        public void clickRight(int[] index) {
            mark(index);
        }
    };

    public MinePannel(MineWeeper mainFrame) {
        this.mainFrame = mainFrame;
        ROW = mainFrame.getSetting().getROW();
        COL = mainFrame.getSetting().getCOL();
        MineCount = mainFrame.getSetting().getMineCount();
        buttons = new MineButton[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                buttons[i][j] = new MineButton(new int[]{i, j});
                buttons[i][j].addMouseListener(listener);
            }
        }
        this.setLayout(new GridLayout(ROW, COL));
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                this.add(buttons[i][j]);
            }
        }
    }

    public void init() {
        openCount = 0;
        markCount = 0;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                buttons[i][j].init();
            }
        }
    }

    private void count(int[] index) {
        countOpen(index);
        countMark(index);
    }

    private void countOpen(int[] index) {
        if (!buttons[index[0]][index[1]].isOpened()) return;
        int count1 = 0;
        for (int i = 0; i < 8; i++) {
            index[0] += dx[i];
            index[1] += dy[i];
            if (index[0] >= 0 && index[0] < ROW && index[1] >= 0 && index[1] < COL) {
                if (buttons[index[0]][index[1]].isMarked()) count1++;
            }
            index[0] -= dx[i];
            index[1] -= dy[i];
        }
        if (count1 == buttons[index[0]][index[1]].getId()) {
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
        if (!buttons[index[0]][index[1]].isOpened()) return;
        int count2 = 0;
        for (int i = 0; i < 8; i++) {
            index[0] += dx[i];
            index[1] += dy[i];
            if (index[0] >= 0 && index[0] < ROW && index[1] >= 0 && index[1] < COL) {
                if (!buttons[index[0]][index[1]].isOpened()) count2++;
            }
            index[0] -= dx[i];
            index[1] -= dy[i];
        }
        if (count2 == buttons[index[0]][index[1]].getId()) {
            for (int i = 0; i < 8; i++) {
                index[0] += dx[i];
                index[1] += dy[i];
                if (index[0] >= 0 && index[0] < ROW && index[1] >= 0 && index[1] < COL) {
                    if (!buttons[index[0]][index[1]].isMarked()) mark(index);
                }
                index[0] -= dx[i];
                index[1] -= dy[i];
            }
        }
    }

    private void leftclick(int[] index) {
        if (buttons[index[0]][index[1]].isMarked() || buttons[index[0]][index[1]].isOpened()) return;
        if (buttons[index[0]][index[1]].getId() == -1) {
            buttons[index[0]][index[1]].setOpened(true);
            buttons[index[0]][index[1]].setBackground(Color.RED);
            lose();
        } else {
            openButton(index);
            if (openCount == ROW * COL - MineCount) suc();
        }
    }

    private void start(int[] index) {
        lose = false;
        suc = false;
        addMine(index);
        setButtonsId();
        mainFrame.getHead().setLabel1Text("雷数:" + MineCount);
        mainFrame.getHead().timerStart();
    }

    private void setButtonsId() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (buttons[i][j].getId() == 0) {
                    for (int n = 0; n < 8; n++) {
                        int x = i + dx[n], y = j + dy[n];
                        if (x >= 0 && x < ROW && y >= 0 && y < COL && buttons[x][y].getId() == -1)
                            buttons[i][j].setId(buttons[i][j].getId() + 1);
                    }
                }
            }
        }
    }

    private void addMine(int[] index) {
        Random a = new Random();
        for (int i = 0; i < MineCount; ) {
            int x = a.nextInt(ROW);
            int y = a.nextInt(COL);
            if (buttons[x][y].getId() != -1 && !(Math.abs(x - index[0]) <= 1 && Math.abs(y - index[1]) <= 1)) {
                i++;
                buttons[x][y].setId(-1);
            }
        }
    }

    private void openButton(int[] index) {
        openCount++;
        buttons[index[0]][index[1]].setOpened(true);
        countOpen(index);
    }

    private void mark(int[] index) {
        if (buttons[index[0]][index[1]].isOpened()) return;
        if (!buttons[index[0]][index[1]].isMarked()) {
            markCount++;
            buttons[index[0]][index[1]].setMarked(true);
            mainFrame.head.setLabel1Text("雷数:" + (MineCount - markCount));
        } else {
            markCount--;
            buttons[index[0]][index[1]].setMarked(false);
            mainFrame.head.setLabel1Text("雷数:" + (MineCount - markCount));
        }
    }

    public void lose() {
        lose = true;
        mainFrame.getHead().setButtonText("你输了");
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (!buttons[i][j].isOpened()) {
                    buttons[i][j].setEnabled(false);
                    buttons[i][j].setText("" + buttons[i][j].getId());
                    if (buttons[i][j].getId() == -1) buttons[i][j].setBackground(Color.GRAY);
                    else buttons[i][j].setBackground(Color.blue);
                }
            }
        }
        mainFrame.head.timerStop();
        JOptionPane.showMessageDialog(this, "你输了，点击确定重新开始");
        mainFrame.restart();
    }

    public void suc() {
        suc = true;
        mainFrame.head.timerStop();
        mainFrame.head.setButtonText("你赢了");
        JOptionPane.showMessageDialog(this, "你赢了，点击确定重新开始");
        mainFrame.restart();
    }

}
