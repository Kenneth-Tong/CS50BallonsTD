//This is the main class, it sets up the JPanels and runs them

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Gui extends JFrame {
    private static int WIDTH = 850, HEIGHT = 850;
    private Arcade panelMap;

    public Gui() {
        super("Bloons Tower Defense 7");
        Player Player = new Player(); //TODO make panel
        super.setLayout(new BorderLayout());

        panelMap = new GuiGame(Player,WIDTH, HEIGHT);

        Game display = new Game(panelMap);
        //passing in a JavaArcade, therefore I know I can call getHighScore(), getScore()
        HotBar hotbar = new HotBar(panelMap, display);
        //Also passing in JavaArcade to ControlPanel, I know you will respond to buttons
        panelMap.setDisplay(display);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 5, 0, 5));
        panel.add(display, BorderLayout.NORTH);
        panel.add((JPanel)panelMap, BorderLayout.CENTER);
        panel.add(hotbar, BorderLayout.SOUTH);

        Container c = getContentPane();
        c.add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Gui window = new Gui();
        window.setBounds(0, 0, WIDTH + 16, HEIGHT + 75);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);
    }
}
