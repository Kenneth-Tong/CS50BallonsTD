//This is an Interface Class for setting up the games and the Hotbar together
//Written by Shreyas Pal

import java.util.ArrayList;

public interface Arcade {

    public boolean running();
    public void startGame(int count);
    public void pauseGame();
    public void reset();
    public String getPlayerName();
    public String getHighScore();
    public void placeMonkeys(String type);
    public void setDisplay(Game d);

}

