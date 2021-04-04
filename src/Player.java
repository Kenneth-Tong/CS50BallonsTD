import java.util.ArrayList;

public class Player {
    private int lives;
    private int money, score;
    private String name;
    private ArrayList<Monkey> towers = new ArrayList<>();

    public Player() {
        money = 600;
        score = 0;
        lives = 150;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void reset() {
        money = 600;
        lives = 150;
        score = 0;
        towers.clear();
    }

    public boolean buy(Monkey m) {
        if(money - m.getValue() < 0) {
            return false;
        } else {
            money -= m.getValue();
            towers.add(m);
        }
        return true;
    }

    public boolean decreaseLife(int x) {
        lives -= x;
        if(lives < 1)
            return true;
        return false;
    }

    public int getLives() {
        return lives;
    }

    public ArrayList<Monkey> getTowers() {
        return towers;
    }

    public int getScore() {
        return score;
    }
}
