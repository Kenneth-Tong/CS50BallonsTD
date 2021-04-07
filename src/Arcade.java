//This is an Interface Class for setting up the games and the Hotbar together


import java.util.ArrayList;

public interface Arcade {

    public boolean running();
    public void startGame(int count);
    public void pauseGame();
    public void reset(boolean died);
    public String getPlayerName();
    public void placeMonkeys(String type);
    public void setDisplay(Game d);

}

