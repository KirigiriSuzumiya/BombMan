package bombman;


import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class info_refresh implements Runnable{
    JProgressBar progressBar1;
    JProgressBar progressBar2;
    JTextArea info;
    JPanel loc_man;
    JLabel maplabel;
    DataOutputStream out;
    DataInputStream in;
    JPanel loc_man2;
    JPanel explode;
    JLabel controll_info;
    int hp,mp,x,y,x1,y1,ex_y,ex_x;
    info_refresh(JProgressBar progressBar1, JProgressBar progressBar2, JTextArea info ,DataOutputStream out, DataInputStream in,JLabel maplabel,JPanel loc_man, JPanel loc_man2,JPanel explode,JLabel controll_info) throws IOException {
        this.info=info;
        this.progressBar1=progressBar1;
        this.progressBar2=progressBar2;
        this.maplabel=maplabel;
        this.in=in;
        this.out=out;
        this.loc_man=loc_man;
        this.loc_man2=loc_man2;
        this.explode=explode;
        this.controll_info=controll_info;
    }



    @Override
    public void run() {
        explode.setOpaque(false);
        while(true) {
            try {
                out.writeUTF("getinfo");
                for (int i=0;i<6;i++)
                {
                    int re_info=in.readInt();
                    if (re_info>=10000)
                    {
                        ex_y=re_info % 100;
                        ex_x=re_info/100 % 100;
                        i=i-1;
                        explode.setOpaque(true);
                        Thread t=new Thread(new explo_display(explode,ex_x-1,ex_y-1));
                        t.start();
                    }
                    else
                    {
                        switch (i){
                            case 0 : x = re_info;break;
                            case 1 : y = re_info;break;
                            case 2 : x1 = re_info;break;
                            case 3 : y1 = re_info;break;
                            case 4 : hp = re_info;break;
                            case 5 : mp = re_info;break;
                        }
                    }
                }

                progressBar1.setValue(hp);
                progressBar2.setValue(mp);
                loc_man.setLocation(60 * x, 60 * y + 5);
                loc_man2.setLayout(null);
                loc_man2.setLocation(60 * x1, 60 * y1 + 5);
                explode.setLayout(null);
                explode.setLocation(60*(ex_x-1),60*(ex_y-2));

                sleep(300);
                if (hp <= 0) {
                    info.append("你被打败了！");
                    controll_info.setText("你被打败了");
                    return;
                }
            } catch (IOException e) {
                try {
                    System.out.println(in.readUTF());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
