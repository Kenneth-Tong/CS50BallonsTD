public class SuperMonkey extends Monkey {
    private int darts = -1;

    public SuperMonkey (int x, int y)
    {
        super(x,y);
        setImage();
    }

    @Override
    public void setImage() {
        super.setImagePath("C:\\Users\\shrey\\OneDrive\\Documents\\5 Programming\\test\\src\\images\\SuperMonkey.png");
    }

    public void attack() {
        //TODO
    }

}
