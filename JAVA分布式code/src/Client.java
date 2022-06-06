import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.*;

public class Client extends JFrame {
    int circle_x = 15, circle_y = 30;
    int circle_width = -1;
    int dx = 0, dy = 0;
    boolean circle_flag = false;
    int circle_height = -1;
    int rect_x = 325, rect_y = 310;
    boolean rectflag = false;
    int Rect_width = -1;
    int Rect_height = -1;
    int tri_x[] = {360, 390, 420, 360};
    int tri_y[] = {100, 60, 100, 100};
    boolean triflag = false;

    public Client() {
        this.setBackground(Color.blue);
        this.getContentPane().setBackground(Color.GREEN);
        this.getContentPane().setVisible(true);//如果改为true那么就变成了红色。
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        //this.setSize(350,350);
        this.setBounds(400, 300, 440, 440);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Client");
    }

    //计算x3和y3的数组
    private void cousultPolygon(int x30, int y30) {
        tri_x[0] = x30;
        tri_x[1] = x30 + 30;
        tri_x[2] = x30 + 60;
        tri_x[3] = x30;

        tri_y[0] = y30;
        tri_y[1] = y30 - 40;
        tri_y[2] = y30;
        tri_y[3] = y30;
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (circle_flag == true) {
            g.setColor(Color.black);
            g.drawOval(circle_x, circle_y, circle_width, circle_height);
            g.fillOval(circle_x, circle_y, circle_height, circle_height);
        }
        if (rectflag == true) {
            g.setColor(Color.blue);
            //绘矩形
            g.drawRect(rect_x, rect_y, Rect_width, Rect_height);
            g.fillRect(rect_x, rect_y, Rect_width, Rect_height);
        }
        if (triflag == true) {
            cousultPolygon(tri_x[0], tri_y[0]);
            g.setColor(Color.cyan);
            //绘制三角形
            g.drawPolygon(tri_x, tri_y, 4);
            g.fillPolygon(tri_x, tri_y, 4);
        }
    }

    public void mypaint() {
        try {
            while (true) {
                Socket socket = new Socket("localhost", 20000);
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader((is)));
                accept(br);
                repaint();
                br.close();
                socket.close();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    //3、接收数据
    private void accept(BufferedReader br) throws IOException {
        //获取圆数据
        String rxstr = br.readLine();
        String rystr = br.readLine();
        circle_x = Integer.parseInt(rxstr);
        circle_y = Integer.parseInt(rystr);
        String rOval_width = br.readLine();
        String rOval_height = br.readLine();
        circle_width = Integer.parseInt(rOval_width);
        circle_height = Integer.parseInt(rOval_height);
        String _ovalflag = br.readLine();
        circle_flag = Boolean.parseBoolean(_ovalflag);
        //获取矩形数据
        String rectx = br.readLine();
        String recty = br.readLine();
        rect_x = Integer.parseInt(rectx);
        rect_y = Integer.parseInt(recty);
        String rRect_width = br.readLine();
        String rRect_height = br.readLine();
        Rect_width = Integer.parseInt(rRect_width);
        Rect_height = Integer.parseInt(rRect_height);
        String _rectflag = br.readLine();
        rectflag = Boolean.parseBoolean(_rectflag);
        //获取三角形数据
        String x30 = br.readLine();
        String y30 = br.readLine();
        tri_x[0] = Integer.parseInt(x30);
        tri_y[0] = Integer.parseInt(y30);
        String _triflag = br.readLine();
        triflag = Boolean.parseBoolean(_triflag);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Client boll = new Client();
        boll.mypaint();
    }
}




