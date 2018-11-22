package engine.display;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SoundManager {

    private HashMap<String, File> soundEffects;
    private HashMap<String, File> soundTrack;

    public SoundManager() {
        soundEffects = new HashMap<String, File>();
        soundTrack = new HashMap<String, File>();
    }

    public void loadSoundEffect(String id, String filename) {
        File soundFile = new File("resources" + File.separator + filename);
        this.soundEffects.put(id, soundFile);
    }

    public void playSoundEffect(String id) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.soundEffects.get(id));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void loadMusic(String id, String filename) {
        File soundFile = new File("resources" + File.separator + filename);
        this.soundTrack.put(id, soundFile);
    }

    public void playMusic(String id) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.soundTrack.get(id));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}
