import java.sql.*; 
import greenfoot.*;

public class game extends Actor
{
    int id;
    int time;
    int join_nr;
    user[] users;
    int current_map;
    boolean creator;
    int round;
    int phase;
    int server_id;
    boolean pblc;
    boolean checkToDelete;
    int stat;
    int actualRound = 0;
    int rightQuestions;
    int numberQuestions;
    long startTime;
    
    time timer;
    
    int fps = 80;
    
    public game(int tempID, int tempTime, int tempJoinNr, user[] tempUsers, int tempCurrentMap, int tempRound, int tempPhase, int sErver_id, boolean tempPublic)
    {
        id = tempID;
        phase = tempPhase;
        time = tempTime;
        join_nr = tempJoinNr;
        users = tempUsers;
        current_map = tempCurrentMap;
        this.creator = false;
        round = tempRound;
        server_id = sErver_id;
        pblc = tempPublic;
    }
    
    public void update() {
    }
}
