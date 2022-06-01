package bombman;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class clientlink {
    private JTextField roomname;
    private JTextField address;
    private JButton linkButton;
    private JTextField name;
    private JPanel mainPanel;
    public JTextArea info;

    static JFrame frame;
    public clientlink() {
        info.setText("请稍后");
        linkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linkButton.setEnabled(false);
                linkButton.setText("连接中");
                String serverName = address.getText();
                String player_name = name.getText();
                String room_name = roomname.getText();
                Thread t = new Thread(new TCPclient(serverName,player_name,room_name,info));
                t.start();
            }
        });
    }

    public static void  main(String[] args) {
        frame = new JFrame("clientlink");
        frame.setContentPane(new clientlink().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
