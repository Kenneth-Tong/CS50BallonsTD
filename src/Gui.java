import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Gui extends JFrame implements ComponentListener {
    private static int WIDTH = 850, HEIGHT = 850;
    private Arcade panelMap;

    public Gui() {
        super("Blimp Obelisk Shielding");
        Player Player = new Player(); //TODO make panel
        super.setLayout(new BorderLayout());

        panelMap = new GuiGame(Player,WIDTH, HEIGHT);

        Game display = new Game(panelMap); //passing in a JavaArcade, therefore I know I can call getHighScore(), getScore()
        HotBar hotbar = new HotBar(panelMap, display); //Also passing in JavaArcade to ControlPanel, I know you will respond to buttons
        panelMap.setDisplay(display);


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 5, 0, 5));
        panel.add(display, BorderLayout.NORTH);
        panel.add((JPanel)panelMap, BorderLayout.CENTER);
        panel.add(hotbar, BorderLayout.SOUTH);

        /*
        super.add(panel, BorderLayout.CENTER);
        panelMap.updateHotBar(hotBarButtons);
        hotBarButtons.setPanel(panelMap);

         */

        Container c = getContentPane();
        c.add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Gui window = new Gui();
        window.setBounds(0, 0, WIDTH + 16, HEIGHT + 75);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public void componentResized(ComponentEvent e) {
        System.out.println("HI");
        Component c = (Component)e.getSource();
        panelMap.resizeUpdate(c.getHeight(), c.getWidth());
    }

    public void componentMoved(ComponentEvent e) {

    }

    public void componentShown(ComponentEvent e) {

    }

    public void componentHidden(ComponentEvent e) {

    }
}
