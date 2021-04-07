import java.util.ArrayList;

public class DartMonkey extends Monkey {
    private int darts = -1;
    private final int vision = 100, COOLDOWN = 50;
    private double timer;

    public DartMonkey (int x, int y)
    {
        super(x, y, 2.5);
        super.setName("DartMonkey");
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

            double angle = Math.atan((this.getLocation().getY() - targ.getY()) / (this.getLocation().getX() - targ.getX()));

            if(targ.getX() < this.getLocation().getX()) angle += Math.PI;

            updateDirection(angle);

            return new Dart(this.getVelocity() * Math.cos(angle),this.getVelocity() * Math.sin(angle),
                    this.getLocation().getX(), this.getLocation().getY());
        }
        return null;
    }

    public void updateDirection(double angle){
        if(angle <= Math.PI/8.0 || angle > Math.PI*15.0/8.0) {
            //East, right
        }else if(angle <= Math.PI*3.0/8.0){
            //north east, up right
        }else if(angle <= Math.PI*5.0/8.0){
            //north, up
        }else if(angle <= Math.PI*7.0/8.0){
            //north west, up left
        }else if(angle <= Math.PI*9.0/8.0){
            //west, left
        }else if(angle <= Math.PI*11.0/8.0){
            //south west, down left
        }else if(angle <= Math.PI*13.0/8.0){
            //south, down
        }else if(angle <= Math.PI*15.0/8.0){
            //south east, down right
        }
    }
}
