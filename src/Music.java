//This is the Music class and it plays an audio track on loop
//Written by Shreyas Pal
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;

public class Music {

    private File file;
    private Clip clip;
    private AudioInputStream audioInputStream;
    private String fileName;

    public Music(String fileName) {
        this.fileName = fileName;
        try {
            file = new File (this.fileName);
        }
        catch(Exception e) {
            System.out.println("couldn't play");
        }
        SwingUtilities.invokeLater(() -> { });
    }

    public void start() {
        try {
            clip = AudioSystem.getClip();
            audioInputStream = AudioSystem.getAudioInputStream(file);
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e) {
            System.out.println("couldn't play");
        }
    }
    public void stop() {
        clip.close();
    }

}