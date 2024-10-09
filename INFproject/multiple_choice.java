import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;
import java.util.*;

public class multiple_choice extends Screen
{
    label time;
    label question;
    
    button button1;
    
    time timer; 
    
    main_menu main_menu;
    
    game game;
    user user;
    
    int fail_connect;
    boolean creator;
    
    databaseConnect database;
    
    button mc1;
    button mc2;
    button mc3;
    button mc4;
    
    label mctxt1;
    label mctxt2;
    label mctxt3;
    label mctxt4;
    
    question Question;
    
    int answer;
    
    public multiple_choice(game tempGame, databaseConnect tempDatabase, user tempUser, main_menu tempMainMenu, boolean tempCreator)
    {
        super(720, 1280, 1);
        setBackground("main_game.png");
        game = tempGame;
        database = tempDatabase;
        user = tempUser;
        main_menu = tempMainMenu;
        creator = tempCreator;
        prepare();
    }
    
    public void prepare() {
        time = new label("0:00", 25);
        time.setTextColor(Color.WHITE);
        addObject(time,1185,52); 
        try {
            Random r = new Random();
            question[] Q = database.questionSerializerJavaAll();
            Question = Q[r.nextInt(Q.length)];
            //answer options first layer  
            mctxt1 = new label(Question.answer_a, 35);
            mctxt1.setTextColor(Color.WHITE);
            addObject(mctxt1,268,425);
            mctxt2 = new label(Question.answer_b, 35);
            mctxt2.setTextColor(Color.WHITE);
            addObject(mctxt2,268,605);
            mctxt3 = new label(Question.answer_c, 35);
            mctxt3.setTextColor(Color.WHITE);
            addObject(mctxt3,805,605);
            mctxt4 = new label(Question.answer_d, 35);
            mctxt4.setTextColor(Color.WHITE);
            addObject(mctxt4,805,425);
            question = new label(Question.data, 35);
            question.setTextColor(Color.WHITE);
            addObject(question,562,165);
        } catch (SQLException e) {}
        mc1 = new button("multiple_choice.png");
        addObject(mc1, 268, 425);
        mc1.getImage().setTransparency(100);
        mc2 = new button("multiple_choice.png");
        addObject(mc2, 268, 605);
        mc2.getImage().setTransparency(100);
        mc3 = new button("multiple_choice.png");
        addObject(mc3, 805, 605);
        mc3.getImage().setTransparency(100);
        mc4 = new button("multiple_choice.png");
        addObject(mc4, 805, 425);
        mc4.getImage().setTransparency(100);
        button1 = new button("quiet_button.png");
        addObject(button1,1176,685); //last layer
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
        } catch (SQLException e) {fail_connect++;}
        if (mc1.clicked()) {
            answer = 1;
            mc1.getImage().setTransparency(150);
            mc2.getImage().setTransparency(100);
            mc3.getImage().setTransparency(100);
            mc4.getImage().setTransparency(100);
        }
        if (mc2.clicked()) {
            answer = 1;
            mc1.getImage().setTransparency(100);
            mc2.getImage().setTransparency(150);
            mc3.getImage().setTransparency(100);
            mc4.getImage().setTransparency(100);
        }
        if (mc3.clicked()) {
            answer = 1;
            mc1.getImage().setTransparency(100);
            mc2.getImage().setTransparency(100);
            mc3.getImage().setTransparency(150);
            mc4.getImage().setTransparency(100);
        }
        if (mc4.clicked()) {
            answer = 1;
            mc1.getImage().setTransparency(100);
            mc2.getImage().setTransparency(100);
            mc3.getImage().setTransparency(100);
            mc4.getImage().setTransparency(150);
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
        time.setText(timer.formatSeconds(5-game.time));

        // if auswertung und effect verteilung
        // choose quarter
    }
}
