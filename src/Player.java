import java.util.ArrayList;

public class Player {
    private int lives = 150; //easy = 200, medium = 150, hard = 100, impopable = 1
    private int money = 600, score = 0;
    private String name;
    private ArrayList<Monkey> towers = new ArrayList<>();

    public Player(){

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

    public void sell(Monkey m) {
        money += m.getValue();
        for (int i = 0; i < towers.size(); i++) {
            if(towers.get(i) == m) {
                towers.remove(i);
                break;
            }
        }
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
    public void resetLives(){
        lives = 150;
    }

    public int getLives() {
        return lives;
    }

    public ArrayList<Monkey> getTowers() {
        return towers;
    }

    public void addPoint() {
        score++;
    }

    public int getScore() {
        return score;
    }
}
