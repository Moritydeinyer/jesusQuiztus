import java.sql.*; 
import greenfoot.*;

public class mapdesign extends Actor
{
    int id;
    float xx;
    float yy;
    int function; // example 1 = solid; 2 = 0.5xspeed
    int bboxLeftTop;
    int bboxRightBottom;
    String source;
    String data;
    long startMillisFunction;
    
    public mapdesign(int id, float x, float y, int function, String tempSource, String tempData)
    {
        bboxLeftTop = 100;     //init
        bboxRightBottom = 100; //init
        this.id = id;
        this.xx = x;
        this.yy = y;
        this.function = function;
        source = tempSource;
        data = tempData;
        setImage(source);
        startMillisFunction = System.currentTimeMillis();
        }
    
    public void act() {
    }
    
    public void update() {}
}
