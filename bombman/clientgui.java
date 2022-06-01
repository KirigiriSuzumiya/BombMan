package bombman;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class clientgui {
    DataOutputStream out;
    DataInputStream in;
    public JPanel panel1;
    public JProgressBar progressBar1;
    public JProgressBar progressBar2;
    public JPanel fightwindow;
    public JProgressBar progressBar3;
    public JLabel maplabel;
    public JButton cancel;
    public JButton prepare;
    public JTextArea info;
    private JLabel controll_info;
    private JPanel loc_man;
    private JPanel loc_man2;
    private JPanel explode;
    private JLabel pic;
    int[][] map=new int[10][10];
    Thread t;

    public clientgui(DataOutputStream out1, DataInputStream in1) throws IOException {

        this.out=out1;
        this.in=in1;
        info.setText("请稍后！");
        prepare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (prepare.getText().equals("准备"))
                    {
                        System.out.println("地图数据读取中……");
                        info.setText(info.getText()+"\n地图数据读取中……");
                        for(int i=0;i<10;i++){
                            for (int j=0;j<10;j++){
                                map[i][j]=in.readInt();
                            }
                        }
                        System.out.println("地图数据读取完成！");
                        info.setText(info.getText()+"\n地图数据读取完成！");
                        out.writeUTF("ready!");
                        info.setText(info.getText()+"\n准备完成！");
                        info.setText(info.getText()+"\n--------------------\n");
                        info.setText(info.getText()+"\n游戏指南：\nWASD移动\n上下左右键投掷炸弹\n炸弹命中敌我都会扣血与加能量\n能量满了能加血");
                        info.setText(info.getText()+"\n--------------------\n");
                        prepare.setText("开始");
                        controll_info.setText("阅读指南");
                    }
                    else{
                        info.setText(info.getText()+"开始游戏！");

                        controll_info.setText("开始战斗！");
                        prepare.setEnabled(false);
                        panel1.requestFocus();
                        explode.setLayout(null);
                        explode.setOpaque(false);
                        explode.setLocation(180,180);
                        info_refresh refresh=new info_refresh(progressBar1,progressBar2,info,out,in,maplabel,loc_man,loc_man2,explode,controll_info);
                        t=new Thread(refresh);
                        t.start();

                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                panel1.requestFocus();
                //if (prepare.isEnabled())return;
                String keyin="";
                switch (e.getKeyCode()){
                    case 38 : keyin="up";break;
                    case 37 : keyin="left";break;
                    case 40 : keyin="down";break;
                    case 39 : keyin="right";break;
                    case 87 : keyin="w";break;
                    case 65 : keyin="a";break;
                    case 83 : keyin="s";break;
                    case 68 : keyin="d";break;
                    default : keyin="";
                }
                try {
                    out.writeUTF(keyin);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //controll_info.setText(keyin);
            }
        });
        panel1.addFocusListener(new FocusAdapter() {
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.requestFocus();
            }
        });
    }

    public void run() throws IOException {
        String server_mes;
        server_mes=in.readUTF();
        JFrame frame = new JFrame(server_mes);
        frame.setContentPane(new clientgui(out,in).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
