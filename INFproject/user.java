import java.sql.*; 
import greenfoot.*;
import java.util.*;

public class user extends Actor implements Comparable<user>
{
    int id;
    float xx;
    float yy;
    int speed;
    int health;
    int damage;
    int points;
    String email;
    String password;
    effect[] effects;
    boolean visibility;
    boolean active;
    databaseGateway database;
    String img;
    
    public user(int tempID, float x, float y, int speed, int health, int damage, int points, String email, String password, boolean visibility, String img, effect[] effects)
    {
        this.setImage("jesusLinks.png");
        id = tempID;
        this.xx = x;
        this.yy = y;
        this.speed = speed;
        this.health = health;
        this.damage = damage;
        this.points = points;
        this.email = email;
        this.password = password;
        this.effects = effects;
        this.visibility = visibility;
        this.img = img;
        this.effects = effects;
    }
    
    public user[] sortUsers(user[] tempUsers) {
        Arrays.sort(tempUsers);
        Collections.reverse(Arrays.asList(tempUsers));
        return tempUsers;
    }
    
    public void update() {}
    
    public void act() {}
    
    @Override
    public int compareTo(user o) {
        return Integer.compare(this.points, o.points);
    }
    
    public void walk(mapdesign[] MD, boolean tempJesus) {
        if (Greenfoot.isKeyDown("w")) {
            setRotation(270);
            move(speed);
            setRotation(0);
            int arr[] = new int[20];
            int i = 0;
            for (mapdesign mpdsg : MD) {
                if (isTouchingActor(mpdsg)) {
                    try {
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getGreen() == 255) {mpdsg.function = 2;}
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getGreen() == 0) {mpdsg.function = 1;}
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getRed() == 0) {mpdsg.function = 0;}
                    } catch (IndexOutOfBoundsException e) {}
                
                    if (!(Arrays.asList(arr).contains(mpdsg.function))) {
                        if (i < 19) {
                            arr[i] = mpdsg.function;
                            i++;
                        }
                    }
                }
            }
            for (int ii = 0; ii<=i;ii++) {
                setRotation(90);
                move(speed);
                setRotation(0);
                if (arr[ii] == 0) {
                    setRotation(270);
                    move(speed);
                    setRotation(0);
                }
                if (arr[ii] == 5) {
                    setRotation(270);
                    move(speed);
                    setRotation(0);
                }
                if (arr[ii] == 2) {
                    setRotation(270);
                    move(speed/2);
                    setRotation(0);
                }
            }
        }
        if (Greenfoot.isKeyDown("a")) {
            if (tempJesus) {
                img = "jesusLinks.png";
            }
            if (!tempJesus) {
                img = "juengerLinks.png";
            }
            setRotation(180);
            move(speed);
            setRotation(0);
            int arr[] = new int[20];
            int i = 0;
            for (mapdesign mpdsg : MD) {
                if (isTouchingActor(mpdsg)) {
                    try {
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getGreen() == 255) {mpdsg.function = 2;}
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getGreen() == 0) {mpdsg.function = 1;}
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getRed() == 0) {mpdsg.function = 0;}
                    } catch (IndexOutOfBoundsException e) {}
                    
                    if (!(Arrays.asList(arr).contains(mpdsg.function))) {
                        if (i < 19) {
                            arr[i] = mpdsg.function;
                            i++;
                        }
                    }
                }
            }
            for (int ii = 0; ii<=i;ii++) {
                setRotation(0);
                move(speed);
                if (arr[ii] == 0) {
                    setRotation(180);
                    move(speed);
                    setRotation(0);
                }
                if (arr[ii] == 5) {
                    setRotation(180);
                    move(speed);
                    setRotation(0);
                }
                if (arr[ii] == 2) {
                    setRotation(180);
                    move(speed/2);
                    setRotation(0);
                }
            }
        }
        if (Greenfoot.isKeyDown("s")) {
            setRotation(90);
            move(speed);
            setRotation(0);
            int arr[] = new int[20];
            int i = 0;
            for (mapdesign mpdsg : MD) {
                if (isTouchingActor(mpdsg)) {
                    try {
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getGreen() == 255) {mpdsg.function = 2;}
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getGreen() == 0) {mpdsg.function = 1;}
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getRed() == 0) {mpdsg.function = 0;}
                    } catch (IndexOutOfBoundsException e) {}
                    
                    if (!(Arrays.asList(arr).contains(mpdsg.function))) {
                        if (i < 19) {
                            arr[i] = mpdsg.function;
                            i++;
                        }
                    }
                }
            }
            for (int ii = 0; ii<=i;ii++) {
                setRotation(270);
                move(speed);
                setRotation(0);
                if (arr[ii] == 0) {
                    setRotation(90);
                    move(speed);
                    setRotation(0);
                }
                if (arr[ii] == 5) {
                    setRotation(90);
                    move(speed);
                    setRotation(0);
                }
                if (arr[ii] == 2) {
                    setRotation(90);
                    move(speed/2);
                    setRotation(0);
                }
            }
        }
        if (Greenfoot.isKeyDown("d")) {
            if (tempJesus) {
                img = "jesusRechts.png";
            }
            if (!tempJesus) {
                img = "juengerRechts.png";
            }
            setRotation(0);
            move(speed);
            int arr[] = new int[20];
            int i = 0;
            for (mapdesign mpdsg : MD) {
                if (isTouchingActor(mpdsg)) {
                    try {
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getGreen() == 255) {mpdsg.function = 2;}
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getGreen() == 0) {mpdsg.function = 1;}
                        if (mpdsg.getImage().getColorAt(getX(), getY()).getRed() == 0) {mpdsg.function = 0;}
                    } catch (IndexOutOfBoundsException e) {}
                    
                    if (!(Arrays.asList(arr).contains(mpdsg.function))) {
                        if (i < 19) {
                            arr[i] = mpdsg.function;
                            i++;
                        }
                    }
                }
            }
            for (int ii = 0; ii<=i;ii++) {
                setRotation(180);
                move(speed);
                setRotation(0);
                if (arr[ii] == 0) {
                    setRotation(0);
                    move(speed);
                }
                if (arr[ii] == 5) {
                    setRotation(0);
                    move(speed);
                    setRotation(0);
                }
                if (arr[ii] == 2) {
                    setRotation(0);
                    move(speed/2);
                }
            }
        }
    }
    
    public boolean isTouchingActor(Actor a) {
        if (this.intersects(a)) {return true;}
        return false;
    }
}
