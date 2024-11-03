import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;

public class boot extends World
{
    databaseGateway database;
    int max_connections;                
    int server_id;                      
    int autostart_players;              
    int current_connections;
    game[] current_games;
    
    public boot()
    {    
        super(600, 400, 1); 
        max_connections = 5;                            //DEV select the max of games to host
        server_id = 67;                                 //DEV select a unique SERVER_ID. please request it from an administrator.
        autostart_players = 2;                          //DEV select the autostart by x players 
        current_games = new game[7];
        database = new databaseGateway();
    }
    
    public void act() 
    {
        try {
            game[] checkGame = database.gameSerializerJavaAll();
            boolean tempActiv = false;
            current_connections = 0;
            for (game g : checkGame) {
                if (g.server_id == server_id) {
                    boolean tempAdd = true;
                    current_connections++;
                    for (int i = 0; i < 7; i++) {
                        try {
                            if (g.join_nr == current_games[i].join_nr) {
                
                                tempAdd = false;
                                current_games[i].time = g.time;
                                current_games[i].users = g.users;
                                current_games[i].round = g.round;
                                current_games[i].phase = g.phase;
                                current_games[i].server_id = g.server_id;
                                current_games[i].checkToDelete = true;
                                
                                if (current_games[i].stat == 0 && g.time == 0 && g.round == 1 && g.users.length >= autostart_players) {
                                    if (current_games[i].timer.timer >= 10) {
                                        g.time = 1;
                                        current_games[i].timer.timerStart();
                                        database.gameSerializerDB(g, 2);
                                        current_games[i].stat = 1;
                                    }
                                }
                                if (current_games[i].stat == 1) {
                                    g.time = current_games[i].timer.timer;
                                    current_games[i].time = g.time;
                                    database.gameSerializerDB(g, 2);
                                }
                                if (current_games[i].time >= 5 && current_games[i].stat == 1) {
                                    g.time = 1;
                                    database.gameSerializerDB(g, 2);
                                    current_games[i].timer.timerStart();
                                    current_games[i].stat = 2;
                                }
                                
                                
                                if (current_games[i].stat == 2) {
                                    g.time = current_games[i].timer.timer;
                                    current_games[i].time = g.time;
                                    database.gameSerializerDB(g, 2);
                                    boolean gO = false;
                                    for (user u : g.users) {
                                       if (u.health <= 0) {
                                           gO = true;
                                       } 
                                    }
                                    if ((g.time>=300 || gO) && g.round <= 5) {                                        //hard limit 5 minutes [300] || 1th player death 
                                       g.round++;   
                                       current_games[i].stat = 3;
                                       current_games[i].round = g.round;
                                       System.out.println(gO);
                                       System.out.println(g.time);
                                    }
                                    database.gameSerializerDB(g, 2);
                                }
                                if (current_games[i].stat == 3) {
                                    System.out.println("d11");
                                    current_games[i].stat = 4;
                                    for (user u : current_games[i].users) {
                                        if (u.health <= 0) {
                                            u.points = u.points + current_games[i].time / 4 * (current_games[i].users.length);
                                        } else {
                                            int tempX = current_games[i].time/10;
                                            if (tempX == 0) {tempX=1;}
                                            u.points = u.points + u.health * current_games[i].users.length * 30 / tempX;
                                        }
                                        u.health = 10;
                                        database.userSerializerDB(u, 2);
                                    }
                                }
                                
                                if (current_games[i].stat == 4) {
                                    current_games[i].numberQuestions = 0;
                                    current_games[i].startTime = System.currentTimeMillis();
                                    current_games[i].stat = 5;
                                    current_games[i].time = 0;
                                    g.time = 0;
                                    database.gameSerializerDB(g, 2);
                                }
                                
                                if (current_games[i].stat == 5) {
                                    if (current_games[i].numberQuestions <= 5) {
                                        long elapsedTime = System.currentTimeMillis() - current_games[i].startTime;
                                        int elapsedSeconds = (int) (elapsedTime / 1000);
                                        current_games[i].time = elapsedSeconds;
                                        g.time = current_games[i].time;
                                        g.phase = 1;
                                        database.gameSerializerDB(g, 2);
                                        if (current_games[i].time >= 7) {
                                            current_games[i].startTime = System.currentTimeMillis();
                                            current_games[i].numberQuestions++;
                                        }
                                    }
                                    if (current_games[i].time >= 7 && current_games[i].numberQuestions >= 5) {
                                        if (current_games[i].round == 6) {
                                            current_games[i].stat = 8;
                                            current_games[i].timer.timerStart();
                                        } else {
                                            current_games[i].time = 0;
                                            g.time = current_games[i].time;
                                            database.gameSerializerDB(g, 2);
                                            current_games[i].startTime = System.currentTimeMillis();
                                            current_games[i].stat = 6;
                                        }
                                    }
                                }
                                
                                boolean chooseSpawn = false;
                                if (current_games[i].stat == 6) {
                                    long elapsedTime = System.currentTimeMillis() - current_games[i].startTime;
                                    int elapsedSeconds = (int) (elapsedTime / 1000);
                                    current_games[i].time = elapsedSeconds;
                                    g.phase = 2;
                                    g.time = current_games[i].time;
                                    database.gameSerializerDB(g, 2); 
                                    if (current_games[i].time >= 6) { //DEV
                                        current_games[i].timer.timerStart();
                                        current_games[i].stat = 2;
                                        g.phase = 0;
                                        database.gameSerializerDB(g, 2);
                                        for (user u : current_games[i].users) {
                                            u.health=10;
                                            database.userSerializerDB(u, 2);
                                        }
                                    }
                                }
                                
                                
                                if (current_games[i].stat == 8) {
                                    if (current_games[i].timer.timer >= 20) {
                                        //DEV DELETE GAME
                                        database.gameSerializerDB(current_games[i], 1);
                                        int tempI = 0;
                                        game[] tG = new game[7];
                                        for (int ii=0; ii<7; ii++) {
                                            try {
                                                int xXx = current_games[ii].join_nr;
                                                if (current_games[ii].id != current_games[i].id) {
                                                    tG[tempI] = current_games[ii];
                                                    tempI++;
                                                }    
                                            } catch (NullPointerException e) {}
                                        }
                                        current_games = tG;
                                    }
                                }
                            }
                        } catch (NullPointerException e) {}
                    }
                    
                    if (tempAdd) {
                        int tempI = 0;
                        game[] tG = new game[7];
                        for (int i=0; i<7; i++) {
                            try {
                                int xXx = current_games[i].join_nr;
                                tG[tempI] = current_games[i];
                                tempI++;
                            } catch (NullPointerException e) {}
                        }
                        if (tempI<7) {
                            g.timer = new time();
                            g.timer.timerStart();
                            g.checkToDelete = true;
                            g.stat = 0;
                            addObject(g.timer, 0, 0);
                            tG[tempI] = g;
                            current_games = tG;
                        }
                    }
                    tempActiv = true;
                }
                if (g.pblc == true && g.server_id == 0 && current_connections < max_connections) {
                    //claim game
                    g.server_id = server_id;
                    database.gameSerializerDB(g, 2);
                }
            } 
            
        } catch (Exception e) {}
    }
}
