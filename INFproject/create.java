import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;

public class create extends Screen
{
    button button1;
    button button2;
    label label1;
    game game;
    label[] users;
    
    databaseConnect database;
    
    main_menu main_menu;
    
    user user;
    
    GreenfootSound waitingMusic;
    
    public create(main_menu tempMainMenu, databaseConnect tempDatabase, game tempGame, user tempUser) {
        super(720, 1280, 1);
        setBackground("start.png");
        database = tempDatabase;
        main_menu = tempMainMenu;
        game = tempGame;
        user = tempUser;
        waitingMusic = new GreenfootSound("waiting.mp3");
        prepare(game.join_nr);
        try {
            database.gameSerializerDB(game, 3);
        } catch (SQLException e) {}
    }
    
    private void prepare(int joinNr)
    {
        label hostLabel = new label(user.email, 20);
        hostLabel.setTextColor(Color.WHITE);
        addObject(hostLabel, 1177, 140);
        users = new label[1];
        users[0] = hostLabel;
        button1 = new button("quiet_button.png");
        addObject(button1,1176,685);
        button2 = new button("start_btn.png");
        addObject(button2,562,409);
        StringBuilder sb = new StringBuilder();
        for (char c : String.valueOf(joinNr).toCharArray()) {
            sb.append(c);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        label label = new label(sb.toString(), 65);
        label.setTextColor(Color.WHITE);
        addObject(label,562, 313);
        label1 = new label(String.valueOf(joinNr), 30);
        label1.setTextColor(Color.WHITE);
        addObject(label1, 1180, 24); 
        waitingMusic.setVolume(100);
        waitingMusic.playLoop();
    }
    
    public void musicStop() {
        try {
            for (int t = 100; t>=0; t--) {
                waitingMusic.setVolume(t);
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(30);
            }
        } catch (InterruptedException e) {System.out.println(e);}
    }
    
    public void act() {
        if (button1.clicked()) {
            main_menu.delete_game_db(game);
            musicStop();
            Greenfoot.setWorld(main_menu);
        }
        if (button2.clicked()) {
            try {
                game = database.gameSerializerJava(game.join_nr);
                game.time = 1;
                game.creator = true;
                database.gameSerializerDB(game, 2);
            } catch (SQLException e) {System.out.println(e);}
            musicStop();
            Greenfoot.setWorld(new choose_quarter(main_menu, database, game, user));
            }
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
        } catch (SQLException e) {} 
        Greenfoot.setSpeed(game.fps);
    }
}
