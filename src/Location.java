
import java.awt.*;

public class Location {
    private double x;
    private double y;
    private Rectangle hitBox;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
        hitBox = new Rectangle((int) x - 1, (int) y - 1, 2, 2);
    }

    public void updateLocation(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}
