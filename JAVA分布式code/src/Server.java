
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

//继承ActionListener实现对所绘制图形的监听
public class Server extends JFrame implements ActionListener {
    int circle_x = 15, circle_y = 30;
    int rect_x = 40, rect_y = 40;
    int tri_x[] = {360, 390, 420, 360};
    int tri_y[] = {100, 60, 100, 100};
    boolean circle_flag = false;
    boolean rect_flag = false;
    boolean tri_flag = false;
    int dx = 0, dy = 0, rectdis = 0, tridis = 0;
    int circle_width = -1;
    int circle_height = -1;
    int Rect_width = -1;
    int Rect_height = -1;
    //按钮
    JButton bt1 = new JButton("添加圆形");
    JButton bt2 = new JButton("添加矩形");
    JButton bt3 = new JButton("添加三角形");
    JButton bt4 = new JButton("清除");


    public Server() {
        this.setBackground(Color.blue);
        this.getContentPane().setBackground(Color.green);
        this.getContentPane().setVisible(true);//设置true可见。
        //setDefaultCloseOperation()是设置用户在此窗体上发起 "close" 时默认执行的操作,
        // 这里是当点击右上角×号使用 System exit 方法退出应用程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗体的同时，终止程序的运行
        this.setVisible(true);//窗体在默认情况下是不可见的，只在后台运行，只有像这样设置可见后，在程序运行的时候，窗体才会出现。
        this.setTitle("Server");//设置标题

        //设置组件的宽高
        this.setSize(440, 440);
        bt1.setBackground(Color.red);
        bt2.setBackground(Color.red);
        bt3.setBackground(Color.red);
        bt4.setBackground(Color.red);

        //给按钮添加监听器
        bt1.addActionListener(this);
        bt2.addActionListener(this);
        bt3.addActionListener(this);
        bt4.addActionListener(this);

        //为JFrame顶层容器设置FlowLayout布局管理器
        this.setLayout(new FlowLayout());

        //把监听器添加到窗口中
        this.add(bt1);
        this.add(bt2);
        this.add(bt3);
        this.add(bt4);

        bt1.setLocation(10, 0);
    }

//    public void paint(Graphics g) {
//        super.paint(g);
//        if (circle_flag == true) {
//            g.setColor(Color.black);
//            //绘制圆
//            g.drawOval(circle_x, circle_y, circle_width, circle_height);
//            g.fillOval(circle_x, circle_y, circle_height, circle_height);
//        }
//        if (rect_flag == true) {
//            g.setColor(Color.blue);
//            //绘矩形
//            g.drawRect(rect_x, rect_y, Rect_width, Rect_height);
//            g.fillRect(rect_x, rect_y, Rect_width, Rect_height);
//        }
//        if (tri_flag == true) {
//            g.setColor(Color.cyan);
//            //绘制三角形
//            g.drawPolygon(tri_x, tri_y, 4);
//            g.fillPolygon(tri_x, tri_y, 4);
//        }
//    }

    public void myPain() throws IOException {
        ServerSocket ss = new ServerSocket(20000);
        while (true) {
            Socket s = ss.accept();
            PrintStream ps = new PrintStream(s.getOutputStream());
            setLocation();
            send(ps);
            ps.close();
            s.close();
            repaint();
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    private void setLocation() {
        //得到组件的宽高
        int framwidth = this.getWidth();
        int framheight = this.getHeight();
        //圆的x和y坐标
        circle_x = circle_x + dx;
        circle_y = circle_y + dy;
        //矩形的x和y坐标
        rect_x = rect_x + rectdis;
        rect_y = rect_y + rectdis;
        //三角形的x和y坐标
        for (int i = 0; i <= 3; i++) {
            tri_x[i] = tri_x[i] + tridis;
            tri_y[i] = tri_y[i] - tridis;
        }
        repaint();

        //顺时针旋转
        if (circle_x >= framwidth - 60 && circle_y <= 30) {//右边线行走
            dy = 5;
            dx = 0;
        } else if (circle_x >= 15 && circle_y >= framheight - 60) {//下线
            dy = 0;
            dx = -5;
        } else if (circle_x >= 10 && circle_y <= 30) {//上线
            dy = 0;
            dx = 5;
        } else if (circle_x <= 15 && circle_y >= 30) { //左线
            dy = -5;
            dx = 0;
        }

        //矩形的起始点坐标x在规定的左边
        if (rect_x <= 40) {
            rectdis = 4;
        }
        //矩形的起始点坐标x在规定的右边
        if (rect_x >= 320) {
            rectdis = -4;
        }
        //三角形的第二个点的x在第一个点的左边
        if (tri_x[1] <= 60) {
            tridis = 4;
        }
        //三角形的第二个点的x在第一个点的右边
        if (tri_x[1] >= 360) {
            tridis = -4;
        }
    }

    private void send(PrintStream ps) {
        //圆
        ps.println(circle_x);
        ps.println(circle_y);
        ps.println(circle_width);
        ps.println(circle_height);
        ps.println(circle_flag);
        //矩形
        ps.println(rect_x);
        ps.println(rect_y);
        ps.println(Rect_width);
        ps.println(Rect_height);
        ps.println(rect_flag);
        //三角形
        ps.println(tri_x[0]);
        ps.println(tri_y[0]);
        ps.println(tri_flag);
    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        Server sever = new Server();
        sever.myPain();
    }

    //监听那个按钮被触发
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        //第一个按钮触发，即绘制圆
        if (e.getSource() == bt1) {
            circle_flag = true;
            circle_width = 50;
            circle_height = 50;
            repaint();
        } else if (e.getSource() == bt2) {//第二个按钮触发，即绘制矩形
            rect_flag = true;
            Rect_width = 50;
            Rect_height = 50;
            repaint();
        } else if (e.getSource() == bt3) {//第三个按钮触发，即绘制三角形
            tri_flag = true;
            repaint();

        } else if (e.getSource() == bt4) {//第四个按钮触发，即清空
            rect_flag = false;
            circle_flag = false;
            tri_flag = false;
            repaint();
        }
    }

}
