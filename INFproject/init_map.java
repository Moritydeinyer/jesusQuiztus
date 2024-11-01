import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;

public class init_map extends Screen
{
    databaseGateway database;
    
    public init_map()
    {
        super(720, 1280, 1);
        database = new databaseGateway();
        //effect e1 = new effect(0, 15, 5, 15, "Double Speed", 0); //function 15
        //effect e2 = new effect(0, 15, 5, 16, "Invisibility", 0); //function 16
        //effect e3 = new effect(0, 15, 5, 17, "Double Damage", 0); //function 17
        //effect e4 = new effect(0, 15, 5, 18, "Regeneration", 0); //function 18
        mapdesign m1 = new mapdesign(0, 640, 360, 1, "main_game_farb_codec.png", "");
        //try {
            //database.effectSerializerDB(e1, 3);
            //database.effectSerializerDB(e2, 3);
            //database.effectSerializerDB(e3, 3);
            //database.effectSerializerDB(e4, 3);
            //database.mapdesignSerializerDB(m1, 3);
            //mapdesign[] md = { m1 };
            //map map = new map(0, md);
            //database.mapSerializerDB(map, 3);
            //System.out.println(map.id);
            //map tempMap = database.mapSerializerJava(48);
            //game game = database.gameSerializerJava(47036);
            //effect FX = new effect(4, -10, 1, -10, "", 0); // init effect
            //database.effectSerializerDB(FX, 3);
            //System.out.println(FX.id);
        //} catch (SQLException e) {System.out.println("fd");}
    }
}
