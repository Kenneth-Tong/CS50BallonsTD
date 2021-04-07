// This class contains all the functions needed for the score and the ending message that is displayed above the game in the JPanel
//Written by Shreyas Pal

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Game extends JPanel
{
    private JLabel yourScoreText;
    private Arcade game;

    // Constructor
    public Game(Arcade t)
    {
        super(new GridLayout(2, 4, 10, 0));
        setBorder(new EmptyBorder(0, 0, 5, 0));
        Font gameNameFont = new Font("Monospaced", Font.BOLD, 24);

        JLabel gName = new JLabel("Balloons Tower Defense");
        gName.setForeground(Color.red);
        gName.setFont(gameNameFont);
        add(gName);

        add(new JLabel(" "));
        yourScoreText = new JLabel(" Your Score: " + 0);
        add(yourScoreText);

        game = t;
    }

    public void update(int points)
    {
        yourScoreText.setText(" Your Score: " + points);
    }

    public void gameOver(int points) {
        yourScoreText.setForeground(Color.BLUE);
        JOptionPane.showMessageDialog(null,
                "Thank you " + game.getPlayerName() + " for playing",
                "High Score", JOptionPane.PLAIN_MESSAGE);
    }
}