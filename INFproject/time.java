import greenfoot.*;

public class time extends Actor 
{
    int timer;
    long startTime;
    boolean timerStarted;

    public time() {
        this.timer = 0;
        timerStarted = false;
    }

    public void act() {
        if (timerStarted) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            int elapsedSeconds = (int) (elapsedTime / 1000);
            timer = elapsedSeconds;
        }
    }
    
    public void timerStart() {
        startTime = System.currentTimeMillis();
        timerStarted = true;
        timer = 0;
    }
    
    public String formatSeconds(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
    
    public int getTime() {
        if (timerStarted) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            int elapsedSeconds = (int) (elapsedTime / 1000);
            timer = elapsedSeconds;
            return timer;
        }
        return 0;
    }
}
