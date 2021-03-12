public class DartMonkey extends Monkey {
    private int darts = -1;

    public DartMonkey (int x, int y)
    {
        super(x,y);
        super.setName("DartMonkey");
        super.setImage(0);
        super.setValue(250);
        super.setVisionRadius(200);
    }

    public void attack() {
        //TODO
    }

}
