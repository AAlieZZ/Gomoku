import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gomoku extends JFrame {    //继承 JFrame 是 Swing 库中的一个类，它表示一个窗口，可以在其中添加组件并显示内容
    private int[][] board = new int[15][15];    //棋盘，数组中的元素值为0表示该位置没有棋子，为1表示该位置有黑子，为2表示该位置有白子
    private boolean isBlack = true; //当前落子颜色

    public Gomoku() {
        //设置窗口属性
        setTitle("五子棋");
        setSize(600, 600);
        setLocationRelativeTo(null);    //设置窗口相对于屏幕中心的位置
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //设置窗口的默认关闭操作：当前用户关闭窗口时，程序将退出

        //添加鼠标事件监听器
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / 40;
                int y = e.getY() / 40;
                if(x >= 0 && x < 15 && y >= 0 && y < 15 && board[x][y] == 0) {  //判断点击位置是否在棋盘范围内，并且该位置没有棋子
                    board[x][y] = isBlack ? 1 : 2;
                    isBlack = !isBlack;
                    repaint();
                }
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g); //调用父类中的 paint 方法完成一些基础的绘制工作，例如清除窗口内容并填充背景颜色
        //绘制棋盘
        for(int i = 0; i < 15; i++) {
            g.drawLine(20 + i *40, 20, 20 + i * 40, 580);
            g.drawLine(20, 20 + i * 40, 580, 20 + i * 40);
        }
        //绘制棋子
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                if(board[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillOval(20 +i * 40 - 15, 20 + j * 40 - 15, 30, 30);
                }
                else if(board[i][j] == 2) {
                    g.setColor(Color.WHITE);
                    g.fillOval(20 + i * 40 - 15, 20 + j * 40 - 15, 30, 30);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Gomoku().setVisible(true);
    }
}
