package bombman;

import java.io.DataOutputStream;
import java.io.IOException;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;

public class bomb implements Runnable{
    String msg;
    int x,y;
    DataOutputStream out;
    User enemy;
    bomb(String msg,int x,int y ,DataOutputStream out,User enemy)
    {
        this.msg=msg;
        this.x=x;
        this.y=y;
        this.out=out;
        this.enemy=enemy;
    }
    @Override
    public void run() {
        if (msg.equals("up"))
        {
            y=y-2;
        }
        else if (msg.equals("down"))
        {
            y=y+2;
        }
        else if (msg.equals("left"))
        {
            x=x-2;
        }
        else if (msg.equals("right"))
        {
            x=x+2;
        }
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (enemy.x==x && enemy.y==y)
        {
            enemy.hp=enemy.hp-20;
            enemy.enemy.mp=enemy.enemy.mp+20;
        }
        else if (abs(enemy.x-x)<=1 && abs(enemy.y-y)<=1)
        {
            enemy.hp=enemy.hp-10;
            enemy.enemy.mp=enemy.enemy.mp+20;
        }
        if (enemy.enemy.x==x && enemy.enemy.y==y)
        {
            enemy.enemy.hp=enemy.enemy.hp-10;
            enemy.enemy.mp=enemy.enemy.mp+20;
        }
        else if (abs(enemy.enemy.x-x)<=1 && abs(enemy.enemy.y-y)<=1)
        {
            enemy.enemy.hp=enemy.enemy.hp-5;
            enemy.enemy.mp=enemy.enemy.mp+10;
        }
        try {
            out.writeInt(x*100+y+10000);
            enemy.out.writeInt(x*100+y+10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
