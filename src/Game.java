// Represents current Game Stats

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Game extends JPanel
{
    private JTextField gameNameText, currentHighScorer, currentHighScore;
    private int yourScore;
    private JLabel yourScoreText;
    private Arcade game;

    // Constructor
    public Game(Arcade t)
    {
        super(new GridLayout(2, 4, 10, 0));
        setBorder(new EmptyBorder(0, 0, 5, 0));
        Font gameNameFont = new Font("Monospaced", Font.BOLD, 24);

        JLabel gName = new JLabel("Bloons Tower Defense");
        gName.setForeground(Color.red);
        gName.setFont(gameNameFont);
        add(gName);

        add(new JLabel(" "));
        yourScoreText = new JLabel(" Your Score: " + 0);
        add(yourScoreText);
        add(new JLabel(" Current High Score: " + t.getHighScore()));

        game = t;
    }


    public void update(int points)
    {
        yourScoreText.setText(" Your Score: " + points);
    }

    public void gameOver(int points)
    {
        if(points > Integer.parseInt(game.getHighScore())){
            if (points > Integer.parseInt(game.getHighScore())) {
                yourScoreText.setForeground(Color.BLUE);
                JOptionPane.showMessageDialog(null,
                        "Congratulations " + game.getPlayerName() + "!\nYou are the new high scorer!!!",
                        "High Score", JOptionPane.PLAIN_MESSAGE);
                try {
                    PrintWriter writer = new PrintWriter(new File("C:\\Users\\shrey\\OneDrive\\Documents\\5 Programming\\AP Java\\Supreme\\highScores.txt"));
                    writer.println(points);
                    writer.close();
                } catch (Exception e) {
                    System.out.println("pp");
                }
            }
        }

    }

}