import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Gui extends JFrame implements ComponentListener {
    private static int WIDTH = 600, HEIGHT = 600;
    private GuiGame panelMap;
    public Gui() {
        super("Blimp Obelisk Shielding");
        Player Player = new Player("Kenny"); //TODO make panel
        super.setLayout(new BorderLayout());
        HotBar hotBarButtons = new HotBar(Player); //this will be used in the gui map
        JPanel panelButtons = hotBarButtons;
        super.add(panelButtons, BorderLayout.SOUTH);
        panelMap = new GuiGame(Player, WIDTH, HEIGHT);
        JPanel panel = panelMap;
        super.add(panel, BorderLayout.CENTER);
        panelMap.updateHotBar(hotBarButtons);
        hotBarButtons.setPanel(panelMap);
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
