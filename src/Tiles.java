
import java.awt.*;

public class Tiles {
    private Rectangle hitBox, drawBox;
    private Color color;
    private String name;
    private Location location;

    public Tiles(String n, int posX, int posY, int width, int height) { //color board
        switch(n)
        {
            case "G":
                name = "grass";
                color = new Color(154,205,50);
                break;
            case "W":
                name = "water";
                color = new Color(102, 178, 255);
                break;
            case "P":
                name = "path";
                color = new Color(222, 184, 135);
                break;
        }

        location = new Location(posX, posY);
        setBoxes(posX, posY, width, height);
    }
    public void setBoxes(int posX, int posY, int width, int height)
    {
        hitBox = drawBox = new Rectangle(posX, posY, width, height);
        drawBox = new Rectangle(posX, posY, width, height); //Make the collisions more smooth
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color c) {
        color = c;
    }
    public Rectangle getHitBox() {
        return hitBox;
    }
    public Rectangle getDrawBox() {
        return drawBox;
    }
    public String getName() {
        return name;
    }
    public Location getLocation() {
        return location;
    }


}
