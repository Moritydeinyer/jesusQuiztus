import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class button extends Actor
{
    boolean clicked;
    
    public button(String image) {
        setImage(image);
        clicked = false;
    }
    
    public void act() {
        if (Greenfoot.mouseClicked(this)) {clicked = true;}
    }
    
    public boolean clicked() {
        if (clicked) {
            clicked = false;
            return true;
        } 
        return false;
    }
    
    public void setBackgroundImage(String img) {
        setImage(img);
    }
}
