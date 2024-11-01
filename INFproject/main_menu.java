import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;

public class main_menu extends Screen
{
    button button1;
    button button2;
    button button3;
    button button4;
    button button5;
    
    databaseGateway database;
    
    join join;
    create create;
    //signin signin;
    
    user user;

    public main_menu() {
        super(720, 1280, 1);
        setBackground("main_menu.png");
        database = new databaseGateway();
        prepare();
    }
    
    private void prepare()
    {
        button1 = new button("create_button.png");
        addObject(button1,557,407);
        button2 = new button("join.png");
        addObject(button2,557,498);
        button3 = new button("quiet_button.png");
        addObject(button3,1176,685);
        button4 = new button("sign_in_main.png");
        addObject(button4,1176,615);
        button5 = new button("search.png");
        addObject(button5,557,625);
        //signin = new signin(this, database);
    }
    
    public void act() {
        try {
            if (user == null) {
                String email = Greenfoot.ask("Please enter your username");
                effect FX = new effect(34, -10, 1, -10, "x", 0); // init effect
                database.effectSerializerDB(FX, 3);                
                effect[] effects = { FX }; //DEV get by predefined IDs
                user = new user(1, 0, 0, 5, 10, 1, 0, email, "1234", true, "juengerRechts.png", effects);
                database.userSerializerDB(user, 3);
                join = new join(this, database, user);
            }
            if (button1.clicked()) {
                
                //DEVELOPMENT
                map tempMap = database.mapSerializerJava(104);
                database.mapSerializerDB(tempMap, 3);
                for (mapdesign md : tempMap.mapdesigns) {
                    database.mapdesignSerializerDB(md, 3);
                }
                database.mapSerializerDB(tempMap, 2);
                
                user[] tempUsers = { user };
                int joinNr = database.getNewJoinNr();
                game tempGame = new game(1, 0, joinNr, tempUsers, tempMap, 1, 0, 0, false);
                create = new create(this, database, tempGame, user);
                Greenfoot.setWorld(create);
            }
            if (button2.clicked()) {Greenfoot.setWorld(join);}
            if (button3.clicked()) {
                for (effect e : user.effects) {
                    if (e.function != -10) {database.effectSerializerDB(e, 1);}
                }
                database.userSerializerDB(user, 1);
                user = null;
                Greenfoot.stop();
            }
            if (button4.clicked()) {System.out.println("coming soon");}//Greenfoot.setWorld(signin);}
            if (button5.clicked()) {
                game[] games = database.gameSerializerJavaAll();
                int joinNr = 0;
                for (game g : games) {
                    if (g.pblc && g.round == 1 && g.time == 0) {
                        joinNr = g.join_nr;
                    }
                }
                if (joinNr == 0) {
                    map tempMap = database.mapSerializerJava(104);
                    database.mapSerializerDB(tempMap, 3);
                    for (mapdesign md : tempMap.mapdesigns) {
                        database.mapdesignSerializerDB(md, 3);
                    }
                    database.mapSerializerDB(tempMap, 2);
                    user[] tempUsers = { user };
                    int newJoinNr = database.getNewJoinNr();
                    game tempGame = new game(1, 0, newJoinNr, tempUsers, tempMap, 1, 0, 0, true);
                    database.gameSerializerDB(tempGame, 3);
                    joinNr = tempGame.join_nr;
                    game game = database.gameSerializerJava(joinNr);
                    waiting waiting = new waiting(this, database, game, user);
                    Greenfoot.setWorld(waiting);
                } else {
                    game game = database.gameSerializerJava(joinNr);
                    if (game.time == 0 && game.round == 1) {
                        user[] tempUsers = new user[game.users.length + 1];
                        for (int i = 0; i<game.users.length; i++) {
                            tempUsers[i] = game.users[i]; 
                        }
                        tempUsers[game.users.length] = user;
                        game.users = tempUsers;
                        database.gameSerializerDB(game, 2);
                        waiting waiting = new waiting(this, database, game, user);
                        Greenfoot.setWorld(waiting);
                    }
                }
            }
        } catch (Exception e) {System.out.println(e);}
    }
    
    public void delete_game(main_game tempGame) {
        delete_game_db(tempGame.game);
    }
    
    public void delete_game_db(game game) {
        try {
            if (game.creator = true) {
                for (mapdesign md : game.current_map.mapdesigns) {
                    database.mapdesignSerializerDB(md, 1);
                }
                database.mapSerializerDB(game.current_map, 1);
                database.gameSerializerDB(game, 1);
            }
            game = null;
        } catch (Exception e) {System.out.println(e);}
    }
}