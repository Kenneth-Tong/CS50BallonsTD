import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Scanner;

public class GuiGame extends JPanel implements ActionListener {
    private int money = 250;
    private final int Height, Width, TILESIZEW = 10, TILESIZEH = 10;
    private Tiles[][] board = new Tiles[TILESIZEW][TILESIZEH];
    private Player player;
    private ArrayList<Balloon> balloonList = new ArrayList<>();
    private HotBar hotbar;
    private Location[] pathLocationPoints;
    private int round = 1;
    private boolean newRound = true;

    public GuiGame(Player p, int w, int h) //TODO resizeable and doesn't overlap with hotbar
    {
        player = p;
        Height = h;
        Width = w;
        Timer timer = new Timer(25, this);
        timer.start();
        super.setLayout(null);
//        openingMessage();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        createBoard();
    }

    public void actionPerformed(ActionEvent e) {
        //TODO monkey shooting! + balloons
        if(newRound) { //round ended and now add balloons
            switch (round) {
                case 1:
                    balloonList.add(new Balloon(1, pathLocationPoints[0])); //TODO make balloons appear off-screen
                    break;
            }
        } else {
            updateBalloon();
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
            g.fillOval((int) pathLocationPoints[i].getX(),
                    (int) pathLocationPoints[i].getY(),
                    10,
                    10);
        }
        for(int i = 0; i < balloonList.size(); i++) {
            g.setColor(balloonList.get(i).getColor());
            g.fillOval((int) balloonList.get(i).getLocation().getX(),
                    (int) balloonList.get(i).getLocation().getY(),
                    10,
                    10);
            System.out.println("X: " +  balloonList.get(0).getLocation().getX() + " Y: " +  balloonList.get(0).getLocation().getY());
        }
        repaint();
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
        Scanner reader = readText("Test");
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
        pathLocationPoints = new Location[amountOfPaths];
        for(int r = 0; r < board.length; r++) {
            for(int c = 0; c < board[0].length; c++) {
                if(board[c][r] instanceof Pathway) {
                    pathLocationPoints[((Pathway) board[c][r]).getNumber()] = new Location(board[c][r].getDrawBox().getCenterX(),
                                    board[c][r].getDrawBox().getCenterY()); //set to center x
                }
            }
        }
    }
    public void updateBalloon()
    {
        //TODO make them not all go out at once and if reaching the end of the map
        for(int i = 0; i < balloonList.size(); i++) {
            double directX = balloonList.get(i).getLocation().getX() - pathLocationPoints[balloonList.get(i).getGoingToLocation()].getX();
            double directY = balloonList.get(i).getLocation().getY() - pathLocationPoints[balloonList.get(i).getGoingToLocation()].getY();
            if(directX == 0 && directY == 0) {
                balloonList.get(i).updateNextLocation(); //they're at the point they're supposed to go to
                directX = balloonList.get(i).getLocation().getX() - pathLocationPoints[balloonList.get(i).getGoingToLocation()].getX();
                directY = balloonList.get(i).getLocation().getY() - pathLocationPoints[balloonList.get(i).getGoingToLocation()].getY();
            } else if(directY > 0) {
                    balloonList.get(i).moveY(-1); //move down if greater than
                    balloonList.get(i).moveX(0);
            } else if (directY < 0) {
                balloonList.get(i).moveY(1);
                balloonList.get(i).moveX(0);
            } else if(directX > 0) {
                balloonList.get(i).moveX(1); //move to the right if greater than
                balloonList.get(i).moveY(0);
            } else if (directX < 0) {
                balloonList.get(i).moveY(0);
                balloonList.get(i).moveX(-1);
            }
            balloonList.get(i).getLocation().updateLocation(balloonList.get(i).getVelX(), balloonList.get(i).getVelY());
        }
    }
    public void startRound() {
        newRound = false;
    }
}
