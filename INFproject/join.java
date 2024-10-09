import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;

public class join extends Screen
{
    button button1;
    button button2;
    button button3;
    String joinNr;
    label label;
    label label2;
    
    databaseConnect database;
    
    game game;
    
    user user;
    
    main_menu main_menu;
    waiting waiting;
    
    public join(main_menu tempMainMenu, databaseConnect tempDatabase, user tempUser) {
        super(720, 1280, 1);
        main_menu = tempMainMenu;
        setBackground("join_screen.png");
        database = tempDatabase;
        user = tempUser;
        prepare();
    }
    
    private void prepare()
    {
        button1 = new button("quiet_button.png");
        addObject(button1,1176,685);
        button2 = new button("join.png");
        addObject(button2,562,410);
        button3 = new button("empty_btn.png");
        addObject(button3,558,265);
        label = new label("", 65);
        label.setTextColor(Color.WHITE);
        addObject(label,562, 263);
        label2 = new label("", 35);
        label2.setTextColor(Color.WHITE);
        addObject(label2,565, 482);
    }
    
    public void act() {
        if (button3.clicked()) {
            joinNr = Greenfoot.ask("Please enter a join number");
            StringBuilder sb = new StringBuilder();
            for (char c : joinNr.toCharArray()) {
                sb.append(c);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            label.setText(sb.toString());
        }
        if (button1.clicked()) {
            if (game != null) {
                main_menu.delete_game_db(game);
            }
            Greenfoot.setWorld(main_menu);
        }
        if (button2.clicked()) {
            try {
                game = database.gameSerializerJava(Integer.parseInt(joinNr));
                if (game.time == 0 && game.round == 1) {
                    user[] tempUsers = new user[game.users.length + 1];
                    for (int i = 0; i<game.users.length; i++) {
                        tempUsers[i] = game.users[i]; 
                    }
                    tempUsers[game.users.length] = user;
                    game.users = tempUsers;
                    database.gameSerializerDB(game, 2);
                    waiting = new waiting(main_menu, database, game, user);
                    Greenfoot.setWorld(waiting);
                } else {
                    label2.setText("game already started");
                }
            } catch (SQLException e) {
                label2.setText("invalid join number");
            }
        }
    }
}
