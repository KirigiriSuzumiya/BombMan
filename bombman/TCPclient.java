package bombman;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;


public class TCPclient implements Runnable{
    String serverName;
    String player_name;
    String room_name;
    JTextArea info;

    public TCPclient(String serverName, String player_name, String room_name, JTextArea info)
    {
        this.serverName=serverName;
        this.player_name=player_name;
        this.room_name=room_name;
        this.info=info;
    }

    @Override
    public  void run() {
        int port=2333;
        String server_mes;
        try
        {
            info.append("���ӵ�������" + serverName + " ���˿ںţ�" + port+"\n");
            Socket client = new Socket(serverName, port);
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("���ԣ�" + client.getLocalSocketAddress()+"������");
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            info.append("��������Ӧ�� " + in.readUTF()+"\n");
            info.append("���ڿ�ʼ��ʼ����\n");
            info.append("�����ǳ��ǣ�"+player_name+"\n");
            out.writeUTF(player_name);
            info.append("��������Ӧ�� " + in.readUTF()+"\n");
            info.append("��������"+room_name+"\n");
            out.writeUTF(room_name);
            info.append("��������Ӧ�� " + in.readUTF()+"\n");
            while ((server_mes=in.readUTF()).equals("�ȴ���ҽ��뷿��~")){
                info.append(server_mes+"\n");
            }
            info.append("��������Ӧ�� " + server_mes);
            clientgui gui = new clientgui(out,in);
            gui.run();

        }catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }


}
