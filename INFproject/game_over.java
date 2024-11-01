import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;

public class game_over extends Screen
{
    button button1;
    
    databaseGateway database;
    
    time timer;
    
    main_menu main_menu;
    
    int fail_connect;
    
    label joinNr;
    label[] s;
    label[] sp;
    label I;
    label II;
    label III;
    label IVVI;
    int highScore;
    int tempHighScore;
    boolean creator;
    
    game game;
    
    user user;
    
    public game_over(main_menu tempMainMenu, databaseGateway tempDatabase, user tempUser, game tempGame, boolean c) {
        super(720, 1280, 1);
        setBackground("gameover.png");
        database = tempDatabase;
        main_menu = tempMainMenu;
        user = tempUser;
        game = tempGame;
        creator = c;
        try {
            game = database.gameSerializerJava(game.join_nr);
        } catch (Exception e) {}
        prepare();
    }
    
    private void prepare()
    {
        button1 = new button("quiet_button.png");
        addObject(button1,1176,685);
        timer = new time();
        timer.timerStart();
        joinNr = new label(String.valueOf(game.join_nr), 30);
        joinNr.setTextColor(Color.WHITE);
        addObject(joinNr, 1180, 24);
        
        I = new label("", 35);
        I.setTextColor(Color.WHITE);
        addObject(I, 536, 330);
        II = new label("", 30);
        II.setTextColor(Color.WHITE);
        addObject(II, 536, 375);
        III = new label("", 30);
        III.setTextColor(Color.WHITE);
        addObject(III, 536, 420);
        IVVI = new label("", 25);
        IVVI.setTextColor(Color.WHITE);
        addObject(IVVI, 536, 470);
        s = new label[game.users.length];
        sp = new label[game.users.length];
        int i = 0;
        int y = 0;
        for (user u : user.sortUsers(game.users)) {
            if (i==0) {
                I.setText("1. " + u.email + "  /  " + u.points);
            }
            if (i==1) {
                II.setText("2. " + u.email + "  /  " + u.points);
            }
            if (i==2) {
                III.setText("3. " + u.email + "  /  " + u.points);
            }
            if (i>=3 && i<=7) {
                IVVI.setText(IVVI.getText() + "  |  " + u.email + " / " + u.points);
            }
            label tempN = new label(u.email, 20);
            tempN.setTextColor(Color.WHITE);
            if (y==0) {y=120;}
            addObject(tempN, 1177, y+55);
            label tempP = new label(""+u.points, 20);
            tempP.setTextColor(Color.WHITE);
            addObject(tempP, 1177, y+70);
            sp[i] = tempP;
            s[i] = tempN;
            y = tempN.getY();
            i++;
        }
    }
    
    public void act() {
        if (button1.clicked()) {
            Greenfoot.setWorld(main_menu);
            if (creator) {main_menu.delete_game_db(game);}
        }
        try {
           game = database.gameSerializerJava(game.join_nr);
           fail_connect = 0;
        } catch (Exception e) {
           System.out.println(e);
           fail_connect++; 
        }
        if (fail_connect >= 5 || timer.timer >= 300) {
           fail_connect = 0;
           Greenfoot.setWorld(main_menu);
        }
    }
}
