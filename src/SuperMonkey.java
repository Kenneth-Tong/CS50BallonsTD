public class SuperMonkey extends Monkey {
    private int darts = -1;

    public SuperMonkey (int x, int y)
    {
        super(x,y);
        super.setName("SuperMonkey");
        super.setImage(0);
        super.setValue(2000);
        super.setVisionRadius(200);
    }

    public void attack() {
        //TODO
    }

}
