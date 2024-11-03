import java.sql.*; 
import greenfoot.*;

public class game extends Actor
{
    int id;
    int time;
    int join_nr;
    user[] users;
    map current_map;
    boolean creator;
    int round;
    int phase;
    int server_id;
    boolean pblc;
    
    int fps = 50;
    
    public game(int tempID, int tempTime, int tempJoinNr, user[] tempUsers, map tempCurrentMap, int tempRound, int tempPhase, int sErver_id, boolean tempPublic)
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
