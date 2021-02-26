import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GuiGame extends JPanel implements ActionListener {
    private int Height, Width, TILESIZEW = 10, TILESIZEH = 10;
    private Tiles[][] board = new Tiles[TILESIZEW][TILESIZEH];
    private Player player;
    private ArrayList<Balloon> balloonList = new ArrayList<>(); //TODO layer balloons via double arraylist within an arraylist
    private HotBar hotbar;
    private Location[] pathLocationPoints;
    private int round = 1;
    private boolean newRound = true, endGame = false, pathUp = false, pathDown = false, pathRight = false, pathLeft = false;

    public GuiGame(Player p, int w, int h) //TODO resizeable and doesn't overlap with hotbar
    {
        player = p;
        Height = h;
        Width = w;
        createBoard();
        Timer timer = new Timer(10, this);
        timer.start();
        super.setLayout(null);
//        openingMessage();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    public void actionPerformed (ActionEvent e){
        //TODO monkey shooting! + balloons
        if (!endGame) {
            if (newRound) { //round ended and now add balloons
                try {
                    readBalloons();
                } catch (IOException ioException) {
                    System.out.println("Error with reading balloon.txt");
                }
                newRound = false; //TODO make button t
            } else {
                updateBalloon();
                if (balloonList.size() == 0) {
                    newRound = true;
                    round++;
                }
            }
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "DEFEAT\nRounds Survived: " + round, "Game Over", JOptionPane.PLAIN_MESSAGE);
            //TODO make a reset button
        }
    }
    public void reset() {
        //TODO
    }
    public void resizeUpdate(int h, int w) {
        Height = h;
        Width = w;
        createBoard();
        System.out.println("HI");
        for(Balloon b: balloonList) {
            b.setRadius(board[0][0].getDrawBox().height);
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < board.length; i++) { //draw board
            for (int c = 0; c < board[0].length; c++) {
                g.setColor(board[c][i].getColor());
                g.fillRect((int) board[c][i].getDrawBox().getX(),
                        (int) board[c][i].getDrawBox().getY(),
                        (int) board[c][i].getDrawBox().getWidth(),
                        (int) board[c][i].getDrawBox().getHeight());
            }
        }
        for (int i = 0; i < pathLocationPoints.length; i++) { //draw board
//            g.fillOval((int) pathLocationPoints[i].getX(),
//                    (int) pathLocationPoints[i].getY(),
//                    10,
//                    10);
            g.setColor(Color.BLACK);
            g.drawRect((int) pathLocationPoints[i].getX(),
                    (int) pathLocationPoints[i].getHitBox().getY(),
                    (int) pathLocationPoints[i].getHitBox().getWidth(),
                    (int) pathLocationPoints[i].getHitBox().getHeight());
        }
        for(int i = 0; i < balloonList.size(); i++) {
            g.setColor(balloonList.get(i).getColor());
            g.fillOval((int) balloonList.get(i).getLocation().getX() - balloonList.get(i).getRadius() / 2, //have to center the points
                    (int) balloonList.get(i).getLocation().getY() - balloonList.get(i).getRadius() / 2, //have to center the points
                    balloonList.get(i).getRadius(),
                    balloonList.get(i).getRadius());
            g.setColor(Color.BLACK);
            g.drawRect((int) balloonList.get(i).getHitBox().getX(),
                    (int) balloonList.get(i).getHitBox().getY(),
                    (int) balloonList.get(i).getHitBox().getWidth(),
                    (int) balloonList.get(i).getHitBox().getHeight());
        }
        Font font = new Font("Comic Sans", Font.BOLD, 30); //TODO make it maybe on hotbar or nicer
        g.setFont(font);
        g.drawString(Integer.toString(player.getLives()), 40, 120);
    }

    public void updateHotBar (HotBar hotbar){
        this.hotbar = hotbar;
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

    public void createBoard() { //TODO find out why it prints sideways map
        int pathLocationCount = 0;
        Scanner reader = readText("Level4");
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
//        System.out.println(amountOfPaths);
        for(int r = 0; r < board.length; r++) {
            for(int c = 0; c < board[0].length; c++) {
                if(board[c][r] instanceof Pathway) {
//                    System.out.println(((Pathway) board[c][r]).getNumber());
                    pathLocationPoints[((Pathway) board[c][r]).getNumber()] = new Location(board[c][r].getDrawBox().getCenterX(),
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
                pathLocationPoints[exitNumber] = new Location(pathLocationPoints[exitNumber - 1].getX() - Width / board[0].length,
                        pathLocationPoints[exitNumber - 1].getY());
            } else if (pathLocationPoints[exitNumber - 1].getX() > Width - Width / board[0].length) { //after last tile
                pathLocationPoints[exitNumber] = new Location(pathLocationPoints[exitNumber - 1].getX() + Width / board[0].length,
                        pathLocationPoints[exitNumber - 1].getY());
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
    public void updateBalloon()
    {
        //TODO make them not all go out at once and if reaching the end of the map
        for (int i = 0; i < balloonList.size(); i++) {
            Balloon b = balloonList.get(i);
            //TODO make gui and map line up
            if((b.getLocation().getX() - b.getHitBox().getWidth() < 0 || b.getLocation().getX() > Width || b.getLocation().getY() - b.getHitBox().getHeight() < 0 || b.getLocation().getY() > Height) && b.getGoingToLocation() > pathLocationPoints.length - 2) {
                if(player.decreaseLife(b.getLives())) {
                    System.out.println("end");
                    endGame = true;
                    break;
                }
                balloonList.remove(b);
                i--;
            }
//            if(i == 0) {
//                System.out.println(b.getGoingToLocation());
//            }
            Location nextPoint = pathLocationPoints[b.getGoingToLocation()];
            double directX = b.getLocation().getX() - nextPoint.getX();
            double directY = b.getLocation().getY() - nextPoint.getY();
            //System.out.println(directX + " " + directY);
            //System.out.println(b.getHitBox().getX() + " " + b.getHitBox().getY());
            //System.out.println(nextPoint.getHitBox().getX() + " " + nextPoint.getHitBox().getY());
            if (collides(b.getHitBox(), nextPoint.getHitBox())) { //TODO
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
//            if(i == 0) {
//                System.out.println(b.getLocation().getX());
//            }
            b.updateHitBox();
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
//            if(line.charAt(i) == '<') {
//                i++;
//                int space = findNumber(line, i, '>');
//            } else
            if(Character.isDigit(line.charAt(i))) {
                life = findNumber(line, i, '(');
                while(line.charAt(i) != '(') {
                    i++;
                }
                i++;
                amount = findNumber(line, i, ')');
                i++;
                //TODO if possible add balloons slowly into the array instead of infinite amount
                double xLocation = pathLocationPoints[0].getX();
                double yLocation = pathLocationPoints[0].getY();
                for(int k = 0; k < amount; k++) {
                    if (pathUp) {
                        balloonList.add(new Balloon(life, new Location(xLocation, yLocation + Height / board[0].length * k)));
                    } else if (pathDown) {
                        balloonList.add(new Balloon(life, new Location(xLocation, yLocation - Height / board[0].length * k)));
                    } else if (pathRight) {
                        balloonList.add(new Balloon(life, new Location(xLocation + Width / board.length * k, yLocation)));
                    } else if (pathLeft) {
                        balloonList.add(new Balloon(life, new Location(xLocation - Width / board.length * k, yLocation)));
                    }
                }
            }
        }
    }
    public int findNumber(String line, int startingValue, char find) {
        for(int j = startingValue; j < line.length(); j++) {
            if (line.charAt(j) == find) {
                //System.out.println(line.substring(startingValue, j));
                return Integer.valueOf(line.substring(startingValue, j));
            }
        }
        return -1;
    }

    public boolean collides(Rectangle r1, Rectangle r2) {
        return r1.intersects(r2);
    }
    public void startRound() {
        newRound = false;
    }
}
