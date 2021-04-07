//Written By Edward Stump and Kenneth Tong
import java.util.ArrayList;

public class ScubaMonkey extends Monkey {
    private int darts = -1;
    private final int vision = 20, COOLDOWN = 50;
    private double timer;

    public ScubaMonkey (int x, int y)
    {
        super(x, y, 2.5);
        super.setName("ScubaMonkey");
        super.setImage(0);
        super.setValue(250);
    }

    @Override

    public Dart attack(ArrayList<Balloon> targets) {
        if (timer > 0) timer--;
        Location targ = null;
        for (Balloon b : targets) {
            if (Math.sqrt(Math.pow(b.getLocation().getX() - this.getLocation().getX(), 2) +
                    Math.pow(b.getLocation().getY() - this.getLocation().getY(), 2)) <= vision) {
                targ = b.getLocation();
            }
        }
        if (timer == 0 && targ != null) {
            timer = COOLDOWN;
            return new Dart(this.getVelocity() *
                    Math.cos(Math.atan((this.getLocation().getY() - targ.getY()) /
                            (this.getLocation().getX() - targ.getX()))),
                    this.getVelocity() * Math.sin(Math.atan((this.getLocation().getY() - targ.getY()) /
                            (this.getLocation().getX() - targ.getX()))),
                    this.getLocation().getX(), this.getLocation().getY());

        }
        return null;
    }
}
