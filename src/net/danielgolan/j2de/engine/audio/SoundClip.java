package net.danielgolan.j2de.engine.audio;

import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SoundClip {
    private Clip clip = null;
    private FloatControl gainControl = null;

    public SoundClip(@NotNull String path){
        try {
            InputStream audioSrc = SoundClip.class.getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(Objects.requireNonNull(audioSrc));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat baseFormat = audioInputStream.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(),
                    16, baseFormat.getChannels(), baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(), false);
            AudioInputStream decodedAudioInputStream = AudioSystem.getAudioInputStream(decodeFormat, audioInputStream);

            clip = AudioSystem.getClip();
            clip.open(decodedAudioInputStream);

            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play(){
        if (clip == null)
            return;

        stop();
        clip.setFramePosition(0);
        while (!clip.isRunning()) clip.start();
    }

    public void stop() {
        if(clip.isRunning()) clip.stop();
    }

    public void close(){
        stop();
        clip.drain();
        clip.close();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
    }

    public void setVolume(final float volume){
        gainControl.setValue(volume);
    }

    public boolean isRunning() {
        return clip.isRunning();
    }
}
