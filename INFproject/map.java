import java.sql.*; 
import greenfoot.*;

public class map extends Actor
{
    int id;
    mapdesign[] mapdesigns;
    
    public map(int tempID, mapdesign[] mapdesigns)
    {
        id = tempID;
        this.mapdesigns = mapdesigns;
    }
    
    public void update() {}
}
