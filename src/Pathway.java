//Written by Kenneth Tong
import java.awt.*;

public class Pathway extends Tiles {
    private int number = -1;

    public Pathway(String n, int i, int posX, int posY, int width, int height) { //tile
        super(n, posX, posY, width, height);
        super.setColor(new Color(222, 184, 135));
        number = i;
    }

    public int getNumber() {
        return number;
    }
}
