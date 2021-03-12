public class DartMonkey extends Monkey{
    private int darts = -1;

    public DartMonkey (int x, int y)
    {
        super(x,y);
        setImage();
    }

    @Override
    public void setImage() {
        super.setImagePath("C:\\Users\\shrey\\OneDrive\\Documents\\5 Programming\\test\\src\\images\\DartMonkey.png");
    }

    public void attack() {
        //TODO
    }

}
