

public class Dart {
    private double vX, vY, posX, posY;

    public Dart(double vX, double vY, double posX, double posY) {
        this.vX = vX;
        this.vY = vY;
        this.posX = posX;
        this.posY = posY;
    }

    public void update()
    {
        posX += vX;
        posY += vY;
    }

    public double getvY() {
        return vY;
    }

    public double getvX() {
        return vX;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
