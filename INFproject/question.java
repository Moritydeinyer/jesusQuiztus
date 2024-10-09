import java.sql.*; 
import greenfoot.*;

public class question extends Actor
{
    int id;
    int points;
    String data;
    String answer;
    String answer_a;
    String answer_b;
    String answer_c;
    String answer_d;
    
    public question(int id, int points, String data, String a, String b, String c, String d, String answer)
    {
        this.id = id;
        this.points = points;
        this.data = data;
        this.answer = answer;
        answer_a = a;
        answer_b = b;
        answer_c = c;
        answer_d = d;
    }
    
    public void update() {}
}
