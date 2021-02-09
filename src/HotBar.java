import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotBar extends JPanel implements ActionListener {
    private JButton dartMonkey, startButton;
    private JPanel panel;
    private Player player;

    public HotBar(Player player) {
        this.player = player;
        dartMonkey = new JButton("Dart Monkey");
        dartMonkey.addActionListener(this);
        super.add(dartMonkey);

        startButton = new JButton("Start Round");
        startButton.addActionListener(this);
        super.add(startButton);
    }

    public void actionPerformed(ActionEvent e) {

    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
}
