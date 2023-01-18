import Interfaces.Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffect implements Sound {

    File soundFile;
    Clip clip;
    double volume;

    public SoundEffect(String filePath) {
        soundFile = new File(filePath);
        volume = 1;
    }

    @Override
    public void play() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));

            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException("Interfaces.Sound could not be played");
        }
    }

    @Override
    public void stop() {
        clip.stop();
    }

    @Override
    public void playAndRepeat() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));

            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException("Interfaces.Sound could not be played");
        }
    }

    @Override
    public void playAndRepeat(int nTimes) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));

            clip.loop(nTimes);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException("Interfaces.Sound could not be played");
        }
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public void setVolume(double volume) {
        if (volume > 1 || volume < 0) {
            throw new IllegalArgumentException("The volume must be between 0 and 1");
        }
        this.volume = volume;
    }
}
