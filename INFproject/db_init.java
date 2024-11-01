import java.sql.*; 
import greenfoot.*;

public class db_init extends Screen
{
    databaseGateway database;
    
    public db_init()
    {
        super(720, 1280, 1);
        setBackground("main_game.png"); //DEV
        prepare();
        database = new databaseGateway();
    }
   
    private void prepare()
    {
        mapdesign mapdesign = new mapdesign(1, 562f, 313f, 1, "Wall.jpg", "data");
        addObject(mapdesign,(int)mapdesign.xx,(int)mapdesign.yy);
    }
}
