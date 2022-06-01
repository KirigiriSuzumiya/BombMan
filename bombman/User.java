package bombman;

import java.io.DataOutputStream;

public class User {
    public String name;
    public String room;
    public User enemy;
    public int x,y,seat;
    DataOutputStream out;
    int hp,mp;
    User(String name1,String room1,DataOutputStream out){
        name=name1;
        room=room1;
        hp=100;
        mp=0;
        this.out=out;
    }
    public void attack(int damage)
    {
        enemy.hp= enemy.hp-damage;
    }

}
