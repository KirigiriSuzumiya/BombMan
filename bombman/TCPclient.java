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
            info.append("连接到主机：" + serverName + " ，端口号：" + port+"\n");
            Socket client = new Socket(serverName, port);
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("来自：" + client.getLocalSocketAddress()+"的连接");
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            info.append("服务器响应： " + in.readUTF()+"\n");
            info.append("现在开始初始化：\n");
            info.append("您的昵称是："+player_name+"\n");
            out.writeUTF(player_name);
            info.append("服务器响应： " + in.readUTF()+"\n");
            info.append("房间名："+room_name+"\n");
            out.writeUTF(room_name);
            info.append("服务器响应： " + in.readUTF()+"\n");
            while ((server_mes=in.readUTF()).equals("等待玩家进入房间~")){
                info.append(server_mes+"\n");
            }
            info.append("服务器响应： " + server_mes);
            clientgui gui = new clientgui(out,in);
            gui.run();

        }catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }


}
