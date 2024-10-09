import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;

public class signin extends Screen
{
    button button1;
    button button2;
    button button3;
    button button4;
    label label1;
    label label2;
    label label3;
    
    user user;
    
    String password;
    String email;
    
    databaseConnect database;
    
    main_menu main_menu;
    
    public signin(main_menu tempMainMenu, databaseConnect tempDatabase) {
        super(720, 1280, 1);
        setBackground("signin_menu.png");
        database = tempDatabase;
        main_menu = tempMainMenu;
        prepare();
    }
    
    private void prepare()
    {
        button1 = new button("quiet_button.png");
        addObject(button1,1176,685);
        button2 = new button("signin_btn.png");
        addObject(button2, 583, 536);
        button3 = new button("account_select_btn.png");
        addObject(button3,585,395); //pasword
        button4 = new button("account_select_btn.png");
        addObject(button4, 585, 261); //email
        label1 = new label("", 20);
        label1.setTextColor(Color.WHITE);
        addObject(label1,578, 589);
        label2 = new label("", 20);
        label2.setTextColor(Color.WHITE);
        addObject(label2,581, 260);
        label3 = new label("", 20);
        label3.setTextColor(Color.WHITE);
        addObject(label3,581, 397);
    }
    
    public void act() {
        if (button1.clicked()) {Greenfoot.setWorld(main_menu);}
        if (button2.clicked()) {}
        if (button3.clicked()) {
            password = Greenfoot.ask("Please enter your password");
            label3.setText("*****");
        }
        if (button4.clicked()) {
            email = Greenfoot.ask("Please enter your email");
            label2.setText(email);
        }
        if (password != null && email != null) {
            try {
                user tempUser = database.userSerializerJava(0, "", email);
                if (tempUser.password == password) { //load user
                
                } else { //wrong password
                    label3.setText("");
                    label2.setText("");
                    label1.setText("invalid password");
                }
            } catch (SQLException e) { //create new user
            
            } 
        }
    }
}
