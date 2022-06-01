package bombman;
import java.net.*;
import java.io.*;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class service implements Runnable {
    Socket server;
    static User[] allplayer=new User[100];
    static int player_count=0;
    public service (Socket server1){
        server=server1;
    }
    int[][] map1=  new int[][]{
            {1,0,1,1,1,0,1,0,1,1},
            {1,1,1,0,1,0,1,1,1,1},
            {0,1,1,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,1,1},
            {-1,-1,-1,0,0,0,0,0,1,1},
            {0,0,-1,0,0,0,-1,-1,-1,0},
            {1,0,-1,0,-1,0,-1,1,-1,-1},
            {0,0,0,0,-1,-1,-1,0,0,1},
            {1,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0}
    };
    public void run() {
        try {
            DataInputStream in = new DataInputStream(server.getInputStream());
            System.out.println(in.readUTF());
            DataOutputStream out = null;

            out = new DataOutputStream(server.getOutputStream());

            out.writeUTF("���������ӳɹ���" + server.getLocalSocketAddress() + "��ã�");
            String name = in.readUTF();
            out.writeUTF(name + "��ã�");
            String RoomNum = in.readUTF();
            out.writeUTF(name + "������" + RoomNum + "���䣡");
            System.out.println(name + '-' + server.getRemoteSocketAddress() + "������" + RoomNum + "����");
            User player = new User(name,RoomNum,out);
            for (int i=1;i<=player_count;i++)
            {
                if (RoomNum.equals(allplayer[i].room) && allplayer[i].enemy==null)
                {
                    allplayer[i].enemy=player;
                    player.enemy=allplayer[i];
                    player.seat=2;
                    player.enemy.seat=1;
                }

            }
            player_count++;
            allplayer[player_count]= player;
            while (player.enemy==null)
            {
                sleep(1000);
                out.writeUTF("�ȴ���ҽ��뷿��~");
            }
            out.writeUTF("��Ķ�����"+player.enemy.name+"!��ʼ���İѣ�");
            System.out.println(RoomNum+"���䣺"+player.name+" �� "+player.enemy.name+"�ɹ���ԣ�");

            out.writeUTF(player.name+" V.S "+player.enemy.name); //�ṩ������
            for (int i=0;i<10;i++)
            {
                for(int j=0;j<10;j++)
                {
                    out.writeInt(map1[i][j]);
                }
            }
            String receive_msg=in.readUTF();//�ȴ����׼��
            System.out.println(player.name+"��׼��");
            if (player.seat==1)
            {
                player.x=5;
                player.y=1;
            }
            else
            {
                player.x=5;
                player.y=10;
            }
            while (true)
            {
                receive_msg=in.readUTF();

                if (receive_msg.equals("getinfo"))
                {
                    if (player.mp>=100){player.hp=player.hp+20;player.mp=0;}
                    if (player.hp>=100)player.hp=100;
                    if (player.seat==1)
                    {
                        out.writeInt(player.x);
                        out.writeInt(player.y);
                        out.writeInt(player.enemy.x);
                        out.writeInt(player.enemy.y);
                        out.writeInt(player.hp);
                        out.writeInt(player.mp);
                    }
                    else
                    {
                        out.writeInt(player.enemy.x);
                        out.writeInt(player.enemy.y);
                        out.writeInt(player.x);
                        out.writeInt(player.y);
                        out.writeInt(player.hp);
                        out.writeInt(player.mp);
                    }
                }
                else
                {
                    System.out.println("�ͻ��˷��ͣ�"+receive_msg);
                    if (receive_msg.equals("w"))
                    {
                        if (player.y-1>0 && map1[player.y-1][player.x]==map1[player.y-2][player.x])
                            player.y=player.y-1;
                    }
                    else if (receive_msg.equals("a"))
                    {
                        if (player.x-1>=0 && map1[player.y-1][player.x]==map1[player.y-1][player.x-1])
                            player.x=player.x-1;
                    }
                    else if (receive_msg.equals("s"))
                    {
                        if (player.y+1<=10 && map1[player.y-1][player.x]==map1[player.y][player.x])
                            player.y=player.y+1;
                    }
                    else if (receive_msg.equals("d"))
                    {
                        if (player.x+1<10 && map1[player.y-1][player.x]==map1[player.y-1][player.x+1])
                        player.x=player.x+1;
                    }
                    else if (receive_msg.equals("up") || receive_msg.equals("down") || receive_msg.equals("left") || receive_msg.equals("right"))
                    {
                        Thread t=new Thread(new bomb(receive_msg,player.x,player.y,out,player.enemy));
                        t.start();
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
