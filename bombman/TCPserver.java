package bombman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
public class TCPserver extends Thread{
    ServerSocket self_socket;
    TCPserver(int port) throws IOException {
        self_socket = new ServerSocket(port);
        self_socket.setSoTimeout(0);
    }
    public void run()
    {
        while(true)
        {
            try
            {
                System.out.println("等待远程连接，端口号为：" + self_socket.getLocalPort() + "...");
                Socket server = self_socket.accept();
                service service1 =new service(server);
                Thread t1=new Thread(service1);
                t1.start();
            }catch(SocketTimeoutException s)
            {
                System.out.println("Socket timed out!");
            }catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public static void main(String [] args)
    {
        try
        {
            Thread t = new TCPserver(2333);
            t.run();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
