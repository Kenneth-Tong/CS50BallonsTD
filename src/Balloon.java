//import javafx.scene.shape.Circle;

import java.awt.*;

public class Balloon {
    private int lives, goingToLocation = 0, hitboxWidth = 1, hitboxHeight = 1, radius = 16;
    private Color color;
    private double speed, velX, velY; //relative to red
    private Rectangle hitBox;
    private Location location;
//    private String resistance;

    public Balloon (int lives, Location location) { //TODO add boolean camo and regen
        this.lives = lives;
        hitBox = new Rectangle(hitboxWidth, hitboxHeight);
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

    public Balloon (Location location, int type) { //white black balloons
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
        hitBox = new Rectangle(hitboxWidth, hitboxHeight);
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

    public void updateLocation() {
        location.updateLocation(velX, velY);
    }

    public void setLocation(Location l) {
        location = l;
    }

    public void moveX(double x) {
        velX = x * speed;
    }

    public double getVelX() {
        return velX;
    }

    public void moveY(double y) {
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

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void updateHitBox() {
        hitBox.setLocation((int) location.getX(), (int) location.getY());
    }

    public int getLives() {
        return lives;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
