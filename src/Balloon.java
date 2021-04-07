

import java.awt.*;

public class Balloon {
    private int lives, goingToLocation = 0, hitbox = 35, radius = 50, pathbox = 1;
    //hitbox height and width is best at 35 for darts
    private Color color;
    private double speed, velX, velY; //relative to red
    private Rectangle hitBox, pathBox;
    private Location location;
    private int value;

    public Balloon (int lives, Location location) {
        this.lives = lives;
        value = lives;
        hitBox = new Rectangle(hitbox, hitbox);
        pathBox = new Rectangle(pathbox, pathbox);
        updateColor(lives);
        this.location = location;
    }

    public void updateColor(int lives) {
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
    }

    public int pop() {
        lives--;
        updateColor(lives);
        if(lives <= 0) return value;
        else return 0;
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

    public void moveY(double y) {
        velY = y * speed;
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

    public Rectangle getPathBox() {
        return pathBox;
    }

    public void updateBox() {
        hitBox.setLocation((int) location.getX()-hitbox/2, (int) location.getY()-hitbox/2);
        pathBox.setLocation((int) location.getX()-pathbox/2, (int) location.getY()-pathbox/2);
    }

    public int getLives() {
        return lives;
    }

    public int getRadius() {
        return radius;
    }
}
