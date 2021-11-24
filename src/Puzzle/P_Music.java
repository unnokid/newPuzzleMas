package Puzzle;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class P_Music {
    private File filePath;
    private Clip musicClip;

    public P_Music(String filePath){
        this.filePath = new File(filePath);
        setClip();
    }
    public void setClip(){
        try{
            musicClip = AudioSystem.getClip();
            musicClip.open(AudioSystem.getAudioInputStream(filePath));
        }
        catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1){
            e1.printStackTrace();
        }
    }
    public void startLoopMusic(){ //¿Ωæ« π´«— π›∫π 
        musicClip.start();
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void startMusic(){ //¿Ωæ« Ω√¿€
        musicClip.setFramePosition(0);
        musicClip.start();
    }
    public void stopMusic(){ //¿Ωæ« ∏ÿ√„
        musicClip.stop();
    }
}