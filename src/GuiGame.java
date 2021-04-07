/*
This is the Gui Class for our code, it has all the visual components of our game
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GuiGame extends JPanel implements ActionListener, Arcade {
    private int Height, Width, TILESIZEW = 10, TILESIZEH = 10, levelNumber;
    private Tiles[][] board = new Tiles[TILESIZEW][TILESIZEH];
    private final Player player;

    private javax.swing.Timer timer;

    private ArrayList<Balloon> balloonList = new ArrayList<>();
    private ArrayList<Dart> dartList = new ArrayList<>();

    private Game game;

    private Location[] pathLocationPoints;
    private int round = 1;
    private boolean newRound = true, pathUp = false, pathDown = false, pathRight = false, pathLeft = false;

    private boolean active = false;
    private boolean dartM = false, superM = false, scubaM = false;
    private boolean start = true, pause, info;

    public GuiGame(Player p, int w, int h) //constructor that sets up the player, window size and creates the board
    {
        player = p;
        Height = h;
        Width = w;
        createBoard(true);
        levelNumber = 1;
        timer = new javax.swing.Timer(10, this);
        super.setLayout(null);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void actionPerformed (ActionEvent e){ //actions for the timers
        if (active) {
            if (newRound) { //if new round sets up the balloons
                try {
                    readBalloons();
                } catch (IOException ioException) {
                    System.out.println("Error with reading balloon.txt");
                }
                newRound = false;
            } else { //updates all the darts and monkeys and balloons while the round is running
                updateBalloon();
                if (balloonList.size() == 0) {
                    newRound = true;
                    round++;
                    if (round == 10) {
                        JOptionPane.showMessageDialog(null, "You finished the level! Onto the" +
                                " next!", "Game Complete", JOptionPane.PLAIN_MESSAGE);
                        reset(false);
                    }
                }
                for(Monkey m : player.getTowers()){
                    Dart d = m.attack(balloonList);
                    if(d != null){
                        dartList.add(d);
                    }
                }
                updateDart();
            }
            repaint();
        } else { //defeat message
            JOptionPane.showMessageDialog(null, "DEFEAT\nRounds Survived: " + round,
                    "Game Over", JOptionPane.PLAIN_MESSAGE);
            game.gameOver(player.getScore());
        }
    }

    public void paintComponent(Graphics g) { //painting class, paints everything on the board
        super.paintComponent(g);
        for (int i = 0; i < board.length; i++) { //draw board //gets all the tiles on the board and paints them
            for (int c = 0; c < board[0].length; c++) {
                g.setColor(board[c][i].getColor());
                g.fillRect((int) board[c][i].getDrawBox().getX(),
                        (int) board[c][i].getDrawBox().getY(),
                        (int) board[c][i].getDrawBox().getWidth(),
                        (int) board[c][i].getDrawBox().getHeight());
            }
        }

        for(int i = 0; i < balloonList.size(); i++) { //gets all the balloons and paints them
            g.setColor(balloonList.get(i).getColor());
            g.fillOval((int) balloonList.get(i).getLocation().getX() - balloonList.get(i).getRadius() / 2, //have to center the points
                    (int) balloonList.get(i).getLocation().getY() - balloonList.get(i).getRadius() / 2, //have to center the points
                    balloonList.get(i).getRadius(),
                    balloonList.get(i).getRadius());
        }
        for(Monkey m : player.getTowers()) { //gets all the monkeys and paints them
            m.draw(g, this);
        }
        for(Dart d : dartList) { //gets all the darts and paints them
            g.setColor(Color.black);
            g.fillOval((int) d.getPosX(), (int) d.getPosY(), 5, 5);
        }
        g.setColor(Color.black); //paints the players lives and money
        Font font = new Font("Comic Sans", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("Money: " + player.getMoney(), 20, 80);
        g.drawString("Lives: " + player.getLives(), Width - 150, 80);
        game.update(player.getScore());

        if (start){ //starting message
            g.drawString("Click Start to begin playing!", 80, Height - 200);
        } else if (pause) {//pausing message
            g.drawString("Game Paused", 150, Height - 150);
        }

        font = new Font("Comic Sans", Font.BOLD, 15);
        g.setFont(font);
        if (info){ //monkey placement instructions
            g.drawString("Click anywhere on the screen to place the monkey.", 50, Height - 150);
        }
    }


    public void updateDart(){
        for (int i = 0; i < dartList.size(); i++) {
            Dart d = dartList.get(i);
            d.update();
            if (d.getPosX() < 0 || d.getPosX() > Width || d.getPosY() < 0 || d.getPosY() > Height) {
                dartList.remove(d);
                i--;
            }else{
                for (Balloon b : balloonList) {
                    if (b.getHitBox().contains(d.getPosX(), d.getPosY())) {

                        int tempval = b.pop();
                        repaint();
                        if(tempval != 0){
                            balloonList.remove(b);
                            player.addMoney(10 + 5*tempval);
                            player.addScore(tempval);
                        }

                        dartList.remove(d);
                        i--;
                        break;
                    }
                }
            }
        }
    }


    public Scanner readText (String l){
        Scanner r = null;
        try {
            r = new Scanner(new File(l + ".txt"));
        } catch (FileNotFoundException e) {
            System.out.println(l + ".txt does not exist");
        }
        return r;
    }

    public void createBoard(boolean level) {
        int pathLocationCount = 0;
        String nameOfLevel;
        if (level) {
            nameOfLevel = "level1";
        } else {
            nameOfLevel = "level2";
        }
        Scanner reader = readText(nameOfLevel);
        for (int i = 0; i < board.length; i++) {
            for (int c = 0; c < board[0].length; c++) {
                int balloonTile = -1;
                String colorTile = "";
                try { //regular drawing, not a connection
                    balloonTile = reader.nextInt();
                } catch (Exception e) {
                    colorTile = reader.next();
                }
                if(!colorTile.equals("")) { //is a color tile
                    board[c][i] = new Tiles(colorTile,
                            c * Width / board.length,
                            i * Height / board[0].length,
                            Width / board[0].length,
                            Height / board.length);
                } else if(balloonTile != -1) {
                    board[c][i] = new Pathway(colorTile,
                            balloonTile,
                            c * Width / board.length,
                            i * Height / board[0].length,
                            Width / board[0].length,
                            Height / board.length);
                    pathLocationCount++;
                }
            }
        }
        reader.close();
        setPathway(pathLocationCount);
    }


    public void setPathway(int amountOfPaths) {
        pathLocationPoints = new Location[amountOfPaths + 2];
        for(int r = 0; r < board.length; r++) {
            for(int c = 0; c < board[0].length; c++) {
                if(board[c][r] instanceof Pathway) {
                    pathLocationPoints[((Pathway) board[c][r]).getNumber()] = new
                            Location(board[c][r].getDrawBox().getCenterX(),
                                    board[c][r].getDrawBox().getCenterY()); //set to center x
                }
            }
        }
        /** set location of where balloons come from **/
        setEntranceExit(true);
        setEntranceExit(false);
    }

    public void setEntranceExit(boolean exit) {
        if(exit) {
            int exitNumber = pathLocationPoints.length - 1;
            if (pathLocationPoints[exitNumber - 1].getX() < Width / board[0].length) { //before the first tile
                pathLocationPoints[exitNumber] = new Location(pathLocationPoints[exitNumber - 1].getX()
                        - Width / board[0].length,pathLocationPoints[exitNumber - 1].getY());
            } else if (pathLocationPoints[exitNumber - 1].getX() > Width - Width / board[0].length) { //after last tile
                pathLocationPoints[exitNumber] = new Location(pathLocationPoints[exitNumber - 1].getX() +
                        Width / board[0].length, pathLocationPoints[exitNumber - 1].getY());
            } else if (pathLocationPoints[exitNumber - 1].getY() < Height / board[0].length) {
                pathLocationPoints[exitNumber] = new Location(pathLocationPoints[exitNumber - 1].getX(),
                        pathLocationPoints[exitNumber - 1].getY() - Height / board[0].length);
            } else if (pathLocationPoints[exitNumber - 1].getY() > Height - Height / board[0].length) {
                pathLocationPoints[exitNumber] = new Location(pathLocationPoints[exitNumber - 1].getX(),
                        pathLocationPoints[exitNumber - 1].getY() + Height / board[0].length);
            }
        } else {
            if(pathLocationPoints[1].getX() <  Width / board[0].length) { //before the first tile
                pathLocationPoints[0] = new Location(pathLocationPoints[1].getX() - Width / board[0].length,
                        pathLocationPoints[1].getY());
                pathLeft = true;
            } else if (pathLocationPoints[1].getX() > Width - Width / board[0].length) { //after last tile
                pathLocationPoints[0] = new Location(pathLocationPoints[1].getX() + Width / board[0].length,
                        pathLocationPoints[1].getY());
                pathRight = true;
            } else if (pathLocationPoints[1].getY() < Height / board[0].length) {
                pathLocationPoints[0] = new Location(pathLocationPoints[1].getX(),
                        pathLocationPoints[1].getY() - Height / board[0].length);
                pathDown = true;
            } else if (pathLocationPoints[1].getY() > Height - Height / board[0].length) {
                pathLocationPoints[0] = new Location(pathLocationPoints[1].getX(),
                        pathLocationPoints[1].getY() + Height / board[0].length);
                pathUp = true;
            }
        }
    }

    public void updateBalloon(){
        for (int i = 0; i < balloonList.size(); i++) {
            Balloon b = balloonList.get(i);
            if((b.getLocation().getX() - b.getPathBox().getWidth() < 0
                    || b.getLocation().getX() > Width
                    || b.getLocation().getY() - b.getPathBox().getHeight() < 0
                    || b.getLocation().getY() > Height) && b.getGoingToLocation() > pathLocationPoints.length - 2) {
                if(player.decreaseLife(b.getLives())) {
                    System.out.println("end");
                    break;
                }
                balloonList.remove(b);
                i--;
                continue;
            }
            b = balloonList.get(i);

            Location nextPoint = pathLocationPoints[b.getGoingToLocation()];
            double directX = b.getLocation().getX() - nextPoint.getX();
            double directY = b.getLocation().getY() - nextPoint.getY();

            if (collides(b.getPathBox(), nextPoint.getHitBox())) {
                b.setLocation(new Location(nextPoint.getX(), nextPoint.getY()));
                b.updateNextLocation(); //they're at the point they're supposed to go to
                directX = b.getLocation().getX() - pathLocationPoints[b.getGoingToLocation()].getX();
                directY = b.getLocation().getY() - pathLocationPoints[b.getGoingToLocation()].getY();
            }
            if (directY > 0) { //x will always override y
                b.moveY(-0.5); //move down if greater than
                b.moveX(0);
            } else if (directY < 0) {
                b.moveY(0.5);
                b.moveX(0);
            } else if (directX > 0) {
                b.moveY(0);
                b.moveX(-0.5); //move to the right if greater than
            } else if (directX < 0) {
                b.moveY(0);
                b.moveX(0.5);
            }
            b.updateLocation();
            b.updateBox();
        }
    }

    public void readBalloons() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Balloons.txt"));
        String line = "";
        for(int i = 0; i < round; i++) {
            line = reader.readLine();
        }
        for(int i = 0; i < line.length(); i++) {
            int life = -1;
            int amount = -1;

            if(Character.isDigit(line.charAt(i))) {
                life = findNumber(line, i, '(');
                while(line.charAt(i) != '(') {
                    i++;
                }
                i++;
                amount = findNumber(line, i, ')');
                i++;

                double xLocation = pathLocationPoints[0].getX();
                double yLocation = pathLocationPoints[0].getY();
                for(int k = 0; k < amount; k++) {
                    if (pathUp) {
                        balloonList.add(new Balloon(life, new Location(xLocation,
                                yLocation + Height / board[0].length * k)));
                    } else if (pathDown) {
                        balloonList.add(new Balloon(life, new Location(xLocation,
                                yLocation - Height / board[0].length * k)));
                    } else if (pathRight) {
                        balloonList.add(new Balloon(life, new Location(xLocation + Width / board.length *
                                k, yLocation)));
                    } else if (pathLeft) {
                        balloonList.add(new Balloon(life, new Location(xLocation - Width / board.length *
                                k, yLocation)));
                    }
                }
            }
        }
    }

    public int findNumber(String line, int startingValue, char find) {
        for(int j = startingValue; j < line.length(); j++) {
            if (line.charAt(j) == find) {
                return Integer.valueOf(line.substring(startingValue, j));
            }
        }
        return -1;
    }

    public boolean collides(Rectangle r1, Rectangle r2) {
        return r1.intersects(r2);
    }



    public void placeMonkeys(String type){ // this method places the monkeys
        if (type.equals("dart"))
        {
            dartM = true;
            showInstructions(4,"DartMonkey");
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { // get mouse listener to listen for clicks
                    if (dartM) {
                        int x = e.getX(), y = e.getY(); //gets the x,y coordinates for the clicks
                        if (checkSpot("normal", x, y)) { //check if spot is valid
                            DartMonkey m = new DartMonkey(x, y); //if valid it creates a monkey and adds it to the monkey list
                            if (player.buy(m)) {
                                hideInstructions(2);
                                repaint();
                            } else {
                                showInstructions(0, "DartMonkey"); // if not it tells them they can't afford the monkey
                            }
                        } else {
                            showInstructions(5, "DartMonkey"); //tells them the spot is valid
                        }
                        dartM = false;
                    }
                }
            });
        }
        else if (type.equals("super")) //does the same thing as above but for super monkey
        {
            superM = true;
            showInstructions(4,"SuperMonkey");
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (superM) {
                        int x = e.getX(), y = e.getY();
                        if (checkSpot("normal", x, y)) {
                            SuperMonkey m = new SuperMonkey(x, y);
                            if (player.buy(m)) {
                                hideInstructions(2);
                                repaint();
                            } else {
                                showInstructions(0, "SuperMonkey");
                            }
                        } else {
                            showInstructions(5, "SuperMonkey");
                        }
                        superM = false;
                    }
                }
            });
        }
        else if (type.equals("scuba")) { //does the same thing as above but for scuba monkey
            scubaM = true;
            showInstructions(4,"ScubaMonkey");
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (scubaM) {
                        int x = e.getX(), y = e.getY();
                        if (checkSpot("water", x, y)) {
                            ScubaMonkey m = new ScubaMonkey(x, y);
                            if (player.buy(m)) {
                                hideInstructions(2);
                                repaint();
                            } else {
                                showInstructions(0, "ScubaMonkey");
                            }
                        } else {
                            showInstructions(5, "ScubaMonkey");
                        }
                        scubaM = false;
                    }
                }
            });
        }
    }


    private boolean checkSpot (String type, int x, int y) {
        //this method checks whether a spot is valid to place a certain type of monkey
        Location[] corners = new Location[4];
        //this creates the 4 coordinates of the monkey hitbox by using the coordinates provided as its center
        corners[0] = new Location(x - (16), y - (16));
        corners[1] = new Location(x - (16), y + (16));
        corners[2] = new Location(x + (16), y - (16));
        corners[3] = new Location(x + (16), y + (16));
        for (Tiles[] row : board) { //then the method goes through all the tiles on the board
            for (Tiles column : row) {
                Rectangle temp = column.getHitBox(); //it gets the hitbox of the tile
                if(column.getName() == null){
                    //if the current tile is an alignment tile and it contains any 4 corners, it returns false
                     for (Location corner : corners) {
                        if (temp.contains(corner.getX(), corner.getY()))
                            return false;
                    }
                }
                else {//if they are normal tiles
                    if (column.getName().equals("path")) {  //it check if any path tiles contain the monkeys
                        for (Location corner : corners) {
                            if (temp.contains(corner.getX(), corner.getY()))
                                return false;
                        }
                    }
                    if (column.getName().equals("grass") && type.equals("water")) {
                        //checks if the any grass tiles contain water monkeys
                        for (Location corner : corners) {
                            if (temp.contains(corner.getX(), corner.getY()))
                                return false;
                        }
                    }
                    if (column.getName().equals("water") && type.equals("normal")) {
                        //checks if any water tiles contain normal monkeys
                        for (Location corner : corners) {
                            if (temp.contains(corner.getX(), corner.getY()))
                                return false;
                        }
                    }
                }
            }
        }
        return true; //returns true if valid
    }


    public void showInstructions(int type, String monkey) {
        //this method essentially sets boolean values true which then shows certain instructions/messages on the screen
        if (type == 0) { //error message when the player trys buying a monkey with not enough money
            JOptionPane.showMessageDialog(null,
                    "You need more money for this monkey!",
                    "Invalid Purchase",
                    JOptionPane.PLAIN_MESSAGE);
        }
        if (type == 1) { //type 1 is starting
            start = true;
        }else if (type == 2){ // type 2 is pausing
            pause = true;
        }else if (type == 4){ //type 4 is resetting
            info = true;
        }else if (type == 5){ //type 5 is when a monkey is placed in an incorrect spot
            pauseGame();
            JOptionPane.showMessageDialog(null,
                    monkey + " can't be placed there!" +
                            "\nYour monkey was either too close to the path or on the wrong tile!" +
                            "\nRemember: ScubaMonkeys can only be on Water, rest of the monkeys can only be on Grass.",
                    "Invalid Spot", JOptionPane.PLAIN_MESSAGE);
        }
        repaint();
    }


    public void hideInstructions(int type) {
        //this method essentially sets boolean values false which then stops certain instructions from being painted on the screen
        if (type == 1){ //type 1 are starting/stopping/pausing messages
            start = false;
            pause = false;
            info = false;
        }
        else if  (type == 2) // type 2 is instructions for placing monkey
            info = false;
        repaint();
    }


    public String getPlayerName() {// gets player name
        return player.getName();
    }


    public boolean running() { // checks to see if the game is running
        return active;
    }


    public void setDisplay(Game d) {
        game = d; // updates the display for the game class
    }


    public void startGame(int count) {
        //starts the game by prompting user to enter name and then starting timers and setting up the board
        if (count == 0) {
            player.setName((String) JOptionPane.showInputDialog(this,
                    "Enter your name:", "Enter Name", JOptionPane.PLAIN_MESSAGE,
                    null, null, "name"));
        }
        timer.start(); // stars the game
        active = true;
        hideInstructions(1);
        repaint();
    }

    public void pauseGame() { //pauses the game by pausing all timers and pausing the music
        pause = true;
        active = false;
        timer.stop();
        //music.stop();
        repaint();
    }


    public void reset(boolean died ) {
        // resets the game by clearing all the balloons, stopping the timers and recreating board
        player.reset();
        pathUp = false;
        pathDown = false;
        pathRight = false;
        pathLeft = false;
        balloonList.clear();
        dartList.clear();
        active = false;
        timer.stop(); // stops the timers
        hideInstructions(1);
        start = true;
        round = 1;
        if (!died) { //resets the levels
            createBoard(false);
        } else {
            if (levelNumber == 1)
                createBoard(true);
            else
                createBoard(false);
        }
        newRound = true;
        timer = new javax.swing.Timer(10, this); //creates new timers
        super.setLayout(null);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        repaint();
    }
}
