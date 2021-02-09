import javafx.scene.shape.Circle;

import java.awt.*;

public class Balloon {
    private int lives, goingToLocation;
    private Color color;
    private double speed, velX, velY; //relative to red
    private Circle hitBox;
    private Location location;
//    private String resistance;

    public Balloon (int lives, Location location) { //TODO add boolean camo and regen
        this.lives = lives;
        if(lives != 6)
        {
            hitBox = new Circle(5);
        } else {
            hitBox = new Circle(10);
        }
        switch(lives) {
            case 1:
                color = Color.RED;
                speed = 1;
                break;
            case 2:
                color = Color.BLUE;
                speed = 1.4;
                break;
            case 3:
                color = Color.GREEN;
                speed = 1.8;
                break;
            case 4:
                color = Color.YELLOW;
                speed = 3.2;
                break;
            case 5:
                color = Color.PINK;
                speed = 3.5;
                break;
        }
        this.location = location;
    }

    public Balloon (Location location, int type) {
        switch(type)
        {
            case 1:
                color = Color.BLACK;
                speed = 1.8;
                break;
            case 2:
                color = Color.WHITE;
                speed = 2;
                break;
        }
        this.location = location;
    }

    public void pop() { //TODO spawn in more depending on balloon
        lives--;
    }

    public boolean checkDead() {
        if (lives < 1) {
            return true;
        }
        return false;
    }

    public Location getLocation() {
        return location;
    }

    public void moveX(int x) {
        velX = x * speed;
    }

    public double getVelX() {
        return velX;
    }

    public void moveY(int y) {
        velY = y * speed;
    }

    public double getVelY() {
        return velY;
    }

    public int getGoingToLocation() {
        return goingToLocation;
    }

    public void updateNextLocation() {
        goingToLocation++;
    }

    public Color getColor() {
        return color;
    }
}
