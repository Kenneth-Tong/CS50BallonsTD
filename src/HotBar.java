import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;

public class HotBar extends JPanel implements ActionListener{
    private JButton dartMonkey, superMonkey, scubaMonkey, startButton, pauseButton, resetButton;
    private Arcade game;
    private Game stats;

    private int count = 0;

    public HotBar(Arcade t, Game g) {
        game = t;
        stats = g;

        super.setLayout(new GridLayout(3, 4));

        dartMonkey = new JButton("Dart Monkey - $250");
        dartMonkey.addActionListener(this);
        add(dartMonkey);

        scubaMonkey = new JButton("Scuba Monkey - $300");
        scubaMonkey.addActionListener(this);
        add(scubaMonkey);

        superMonkey = new JButton("Super Monkey - $2000");
        superMonkey.addActionListener(this);
        add(superMonkey);

        add(Box.createHorizontalStrut(40));
        add(Box.createHorizontalStrut(40));
        add(Box.createHorizontalStrut(40));

        startButton = new JButton("Start Round");
        startButton.addActionListener(this);
        add(startButton);

        pauseButton = new JButton("Pause Round");
        pauseButton.addActionListener(this);
        add(pauseButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        add(resetButton);

    }

    public void actionPerformed(ActionEvent e)
    {
        JButton button = (JButton)e.getSource();
        if (button == startButton)
        {
            if (!game.running())
            {
                ((JPanel)(game)).requestFocus(); //need to provide the JPanel focus
                game.startGame(count);
                stats.update(0);
                startButton.setText("Resume");
                stats.repaint();
                count++;
            }
        }
        else if(button == pauseButton)
        {
            game.pauseGame();
            startButton.setEnabled(true);
            repaint();
        }
        else if(button == resetButton)
        {
            game.reset();
            stats.repaint();
            startButton.setText("Start");
            startButton.setEnabled(true);
            repaint();
        }
        else if(button == dartMonkey)
        {
            if (game.running())
                game.placeMonkeys("dart");
        }

        else if(button == superMonkey)
        {
            if (game.running())
                game.placeMonkeys("super");
        }
        else if(button == scubaMonkey)
        {
            if (game.running())
                game.placeMonkeys("scuba");
        }

        ((JPanel)(game)).requestFocus();
    }

}


