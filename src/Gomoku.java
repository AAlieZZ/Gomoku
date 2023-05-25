import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gomoku extends JFrame {    //继承 JFrame 是 Swing 库中的一个类，它表示一个窗口，可以在其中添加组件并显示内容
    private int[][] board = new int[15][15];    //棋盘，数组中的元素值为0表示该位置没有棋子，为1表示该位置有黑子，为2表示该位置有白子
    private boolean isBlack = true; //当前落子颜色
    private int[] logX = new int[225];  //记录 x 坐标
    private int[] logY = new int[225];  //记录 y 坐标
    private int pieces = 0; //记录落子

    public Gomoku() {
        //设置窗口属性
        setTitle("五子棋");
        setSize(600, 720);
        setLocationRelativeTo(null);    //设置窗口相对于屏幕中心的位置
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //设置窗口的默认关闭操作：当前用户关闭窗口时，程序将退出

        //添加鼠标事件监听器
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / 40;
                int y = (e.getY() - 50) / 40;
                if(x >= 0 && x < 15 && y >= 0 && y < 15 && board[x][y] == 0) {  //判断点击位置是否在棋盘范围内，并且该位置没有棋子
                    board[x][y] = isBlack ? 1 : 2;
                    isBlack = !isBlack;
                    repaint();
                    test(x, y);
                    logX[pieces] = x;
                    logY[pieces] = y;
                    pieces++;
                }
            }
        });

        //认输按钮
        JButton buttonA = new JButton("认输");
        add(buttonA, BorderLayout.SOUTH);
        buttonA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GG(!isBlack).setVisible(true);
            }
        });

        //悔棋按钮
        JButton buttonB = new JButton("悔棋");
        add(buttonB, BorderLayout.NORTH);
        buttonB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(pieces > 0) {
                    pieces--;
                    isBlack = !isBlack;
                }
                board[logX[pieces]][logY[pieces]] = 0;
                repaint();
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g); //调用父类中的 paint 方法完成一些基础的绘制工作，例如清除窗口内容并填充背景颜色
        //绘制棋盘
        for(int i = 0; i < 15; i++) {
            g.drawLine(20 + i *40, 70, 20 + i * 40, 630);
            g.drawLine(20, 70 + i * 40, 580, 70 + i * 40);
        }
        //绘制棋子
        if(isBlack) g.setColor(Color.BLACK);
        else g.setColor(Color.WHITE);
        g.fillOval(20 + 7 * 40 - 20, 65 + 15 * 40 - 20, 40, 40);
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                if(board[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillOval(20 + i * 40 - 15, 70 + j * 40 - 15, 30, 30);
                }
                else if(board[i][j] == 2) {
                    g.setColor(Color.WHITE);
                    g.fillOval(20 + i * 40 - 15, 70 + j * 40 - 15, 30, 30);
                }
            }
        }
    }

    public void test(int x, int y) {
        int x0, y0, points = 0;
        //检测行
        for(int i = 0; i < 14; i++) {
            if(board[i][y] > 0 && board[i][y] == board[i+1][y]) points++;
            else points = 0;
            if(points > 3) new GG(!isBlack).setVisible(true);
        }
        points = 0;
        //检测列
        for(int i = 0; i < 14; i++) {
            if(board[x][i] > 0 && board[x][i] == board[x][i+1]) points++;
            else points = 0;
            if(points > 3) new GG(!isBlack).setVisible(true);
        }
        points = 0;
        //检测斜
        if(x > y) {
            x0 = x - y;
            y0 = 0;
        }
        else {
            x0 = 0;
            y0 = y - x;
        }
        while(x0 >= 0 && x0 < 14 && y0 >= 0 && y0 < 14) {
            if(board[x0][y0] > 0 && board[x0][y0] == board[x0+1][y0+1]) points++;
            else points = 0;
            if(points > 3) new GG(!isBlack).setVisible(true);
            x0++;
            y0++;
        }
        points = 0;
        //反向
        if(14 - x > y) {
            x0 = x + y;
            y0 = 0;
        }
        else {
            x0 = 14;
            y0 = y - (14 - x);
        }
        while(x0 > 0 && x0 < 15 && y0 >= 0 && y0 < 14) {
            if(board[x0][y0] > 0 && board[x0][y0] == board[x0-1][y0+1]) points++;
            else points = 0;
            if(points > 3) new GG(!isBlack).setVisible(true);
            x0--;
            y0++;
        }
        points = 0;
    }

    public static void main(String[] args) {
        new Gomoku().setVisible(true);
    }
}

class GG extends JFrame {    //继承 JFrame 是 Swing 库中的一个类，它表示一个窗口，可以在其中添加组件并显示内容
    private boolean isBlack = true;

    public GG(boolean isBlack) {
        this.isBlack = isBlack;
        //设置窗口属性
        setTitle("游戏结束");
        setSize(500, 500);
        setLocationRelativeTo(null);    //设置窗口相对于屏幕中心的位置
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //设置窗口的默认关闭操作：当前用户关闭窗口时，程序将退出
        //按钮
        JButton button = new JButton("好");
        add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g); //调用父类中的 paint 方法完成一些基础的绘制工作，例如清除窗口内容并填充背景颜色
        //显示胜负
        g.setFont(new Font("楷体", Font.PLAIN, 100));
        if(isBlack) g.drawString("黑胜", 128, 256);
        else g.drawString("白胜", 128, 256);
    }
}