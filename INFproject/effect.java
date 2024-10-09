import java.sql.*; 
import greenfoot.*;

public class effect extends Actor
{
    int id;
    int intensity;
    int function;
    int lasting_time;
    String description;
    int activated;
    
    public effect(int id, int lasting_time, int intensity, int function, String description, int activated)
    {
        this.id = id;
        this.lasting_time = lasting_time;
        this.intensity = intensity;
        this.function = function;
        this.description = description;
        this.activated = activated;
    }
    
    public void update() {}
}
