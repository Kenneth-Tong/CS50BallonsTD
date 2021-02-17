import javafx.scene.shape.Circle;

import java.util.ArrayList;

public abstract class Monkey {
    //do vision and the arraylist of balloons
    private ArrayList<Balloon> balloonsInSight;
    private Circle visionBox;
    private Location location;
    private int value;

    public Monkey (int x, int y)
    {
        location = new Location(x,y);
    }

    public abstract void attack();
    //public abstract void setImage()

    public ArrayList<Balloon> getBalloonsInSight() {
        return balloonsInSight;
    }

    public boolean inSight(Circle balloonHitBox) {
        if(Math.pow(Math.abs(balloonHitBox.getRadius() - visionBox.getRadius()), 2) <= Math.pow(balloonHitBox.getCenterX() - visionBox.getCenterX(), 2) + Math.pow(balloonHitBox.getCenterY() - visionBox.getCenterY(), 2) &&
                Math.pow(balloonHitBox.getCenterX() - visionBox.getCenterX(), 2) + Math.pow(balloonHitBox.getCenterY() - visionBox.getCenterY(), 2) <= Math.pow(Math.abs(balloonHitBox.getRadius() + visionBox.getRadius()), 2)) {
            return true;
        }
        return false;
    }

    public int getValue() {
        return value;
    }
}
