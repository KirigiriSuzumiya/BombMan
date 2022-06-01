package bombman;


import javax.swing.*;

import static java.lang.Thread.sleep;

public class explo_display implements Runnable{
    JPanel explode;
    int x,y;
    explo_display(JPanel explode,int x,int y){
        this.explode=explode;
        this.x=x;
        this.y=y;
    }

    @Override
    public void run() {
        try {
            sleep(1500);
            explode.setLocation(0,0);
            explode.setOpaque(false);
            explode.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
