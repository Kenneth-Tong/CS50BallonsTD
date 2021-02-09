import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    public Gui() {
        super("Blimp Obelisk Shielding");
        Player Player = new Player();
        super.setLayout(new BorderLayout());
        HotBar hotBarButtons = new HotBar(Player); //this will be used in the gui map
        JPanel panelButtons = hotBarButtons;
        super.add(panelButtons, BorderLayout.SOUTH);
        GuiGame panelMap = new GuiGame(Player, 600, 600);
        JPanel panel = panelMap;
        super.add(panel, BorderLayout.CENTER);
        panelMap.updateHotBar(hotBarButtons);
        hotBarButtons.setPanel(panelMap);
    }

    public static void main(String[] args) {
        Gui window = new Gui();
        window.setBounds(0, 0, 600, 600);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}
