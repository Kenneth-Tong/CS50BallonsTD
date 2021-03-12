public class WaterMonkey extends Monkey {
    private int darts = -1;

    public WaterMonkey (int x, int y)
    {
        super(x,y);
        setImage();
    }


    public void setImage() {
        super.setImagePath("C:\\Users\\shrey\\OneDrive\\Documents\\5 Programming\\test\\src\\images\\ScubaMonkey.png");
    }

    public void attack() {
        //TODO
    }

}
