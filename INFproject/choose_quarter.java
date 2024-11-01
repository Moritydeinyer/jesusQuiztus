import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;
import java.util.*;

public class choose_quarter extends Screen
{
    button button1;
    
    label[] users;
    
    button q1;
    button q2;
    button q3;
    button q4;
    
    time timer; 
    
    databaseGateway database;
    
    main_menu main_menu;
    
    label time;
    
    game game;
    
    user user;
    
    int fail_connect;
    boolean creator;
    
    public choose_quarter(main_menu tempMainMenu, databaseGateway tempDatabase, game tempGame, user tempUser)
    {
        super(720, 1280, 1);
        setBackground("main_game.png");
        database = tempDatabase;
        main_menu = tempMainMenu;
        game = tempGame;
        user = tempUser;
        creator = game.creator;
        prepare();
    }
    
    private void prepare()
    {
        label hostLabel = new label(user.email, 20);
        hostLabel.setTextColor(Color.WHITE);
        addObject(hostLabel, 1177, 140);
        users = new label[1];
        users[0] = hostLabel;
        time = new label("0:00", 25);
        time.setTextColor(Color.WHITE);
        addObject(time,1185,52); 
        // 4 buttons each for corner INIT DEV
        q1 = new button("choose_quarter.png");
        addObject(q1,960,180);   
        q1.getImage().setTransparency(100);//150 selected
        q2 = new button("choose_quarter.png");
        q2.getImage().setTransparency(100);
        addObject(q2,320,180);   
        q3 = new button("choose_quarter.png");
        q3.getImage().setTransparency(100);
        addObject(q3,320,540);   
        q4 = new button("choose_quarter.png");
        q4.getImage().setTransparency(100);
        addObject(q4,960,540);   
        timer = new time();
        addObject(timer, 0, 0);
        label cS = new label("Choose Spawn", 85);
        cS.setTextColor(Color.WHITE);
        addObject(cS,537,275); 
        button1 = new button("quiet_button.png");
        addObject(button1,1176,685); //last layer
        try {
            game = database.gameSerializerJava(game.join_nr);
            for (user tempU : game.users) {
                boolean addUserLabel = true;
                for (label tempL : users) {
                    if (tempU.email.equals(tempL.getText())) {addUserLabel = false;}
                }
                if (addUserLabel) {
                    if (users.length >=20) {
                        users[19].setText("...");
                    } else {
                        label[] tempLabelUsers = new label[users.length+1];
                        for (int i = 0;i<users.length;i++) {
                            tempLabelUsers[i] = users[i];
                        }
                        label tempLabelToAdd = new label(tempU.email, 20);
                        tempLabelToAdd.setTextColor(Color.WHITE);
                        int tempX = users[users.length-1].getY() + 25;
                        addObject(tempLabelToAdd, 1177, tempX);
                        tempLabelUsers[users.length] = tempLabelToAdd;
                        users = tempLabelUsers;
                    }
                }
            }
        } catch (Exception e) {} 
        if (creator) {
            timer.timerStart();
        }
    }
    
    public void act() {
        try {
            fail_connect = 0;
            game = database.gameSerializerJava(game.join_nr);
            if (creator) {
                game.time = timer.timer;
                database.gameSerializerDB(game, 2);
            }
        } catch (Exception e) {fail_connect++;}
        if (q1.clicked()) {
            try {
                user.xx = (float) 960;
                user.yy = (float) 180;
                database.userSerializerDB(user, 2);
                q1.getImage().setTransparency(150);
                q2.getImage().setTransparency(100);
                q3.getImage().setTransparency(100);
                q4.getImage().setTransparency(100);
            } catch (Exception e) {}
        }
        if (q2.clicked()) { 
            try {
                user.xx = (float) 320;
                user.yy = (float) 180;
                database.userSerializerDB(user, 2);
                q1.getImage().setTransparency(100);
                q2.getImage().setTransparency(150);
                q3.getImage().setTransparency(100);
                q4.getImage().setTransparency(100);
            } catch (Exception e) {}
        }
        if (q3.clicked()) { 
            try {
                user.xx = (float) 320;
                user.yy = (float) 540;
                database.userSerializerDB(user, 2);
                q1.getImage().setTransparency(100);
                q2.getImage().setTransparency(100);
                q3.getImage().setTransparency(150);
                q4.getImage().setTransparency(100);
            } catch (Exception e) {}
        }
        if (q4.clicked()) { 
            try {
                user.xx = (float) 960;
                user.yy = (float) 540;
                database.userSerializerDB(user, 2);
                q1.getImage().setTransparency(100);
                q2.getImage().setTransparency(100);
                q3.getImage().setTransparency(100);
                q4.getImage().setTransparency(150);
            } catch (Exception e) {}
        }
        if (button1.clicked()) {
            main_menu.delete_game_db(game);
            Greenfoot.setWorld(main_menu);
        }     
        if (fail_connect >= 5) {
            fail_connect = 0;
            if (game.creator) {main_menu.delete_game_db(game);} 
            Greenfoot.setWorld(main_menu);
        }
        if (game.time >= 5 && user.xx == 0 && user.yy == 0) {
            int rN = getRandomNumber();
            switch(rN) {
                case 1:
                    q1.clicked = true;
                case 2:
                    q2.clicked = true;
                case 3:
                    q3.clicked = true;
                case 4:
                    q4.clicked = true;
            }
        }
        if (game.time >= 5 && user.xx > 0 && user.yy > 0) {
            game.creator = creator;
            Greenfoot.setWorld(new main_game(main_menu, database, game, user));
        }
        time.setText(timer.formatSeconds(5-game.time));
    }
    public int getRandomNumber() {
      Random random = new Random();
      int randomNumber = random.nextInt(4);
      return randomNumber + 1;
    }
}
