import java.sql.*; 
import greenfoot.*;
import java.util.*;

public class user extends Actor implements Comparable<user>
{
    int id;
    float xx;
    float yy;
    int speed;
    int health;
    int damage;
    int points;
    String email;
    String password;
    int[] effects;
    boolean visibility;
    boolean active;
    databaseConnect database;
    String img;
    
    public user(int tempID, float x, float y, int speed, int health, int damage, int points, String email, String password, boolean visibility, String img, int[] effects)
    {
        id = tempID;
        this.xx = x;
        this.yy = y;
        this.speed = speed;
        this.health = health;
        this.damage = damage;
        this.points = points;
        this.email = email;
        this.password = password;
        this.effects = effects;
        this.visibility = visibility;
        this.img = img;
        this.effects = effects;
    }
    
    public user[] sortUsers(user[] tempUsers) {
        Arrays.sort(tempUsers);
        Collections.reverse(Arrays.asList(tempUsers));
        return tempUsers;
    }
    
    public void update() {}
    
    public void act() {}
    
    @Override
    public int compareTo(user o) {
        return Integer.compare(this.points, o.points);
    }
    
  
}
