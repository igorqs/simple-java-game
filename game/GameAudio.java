/**
  * Autor: Igor Quadros Silva
  * Matricula: 201610519
  * Inicio: 06 de marco de 2021
  * Ultima Alteracao: 07 de marco de 2021
  * Nome: Press SPACE to shoot and arrows to move
  */

package game;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameAudio {
  private static final String LOOP_SOUNDTRACK = "resources/audio/soundtrack.wav";
  private static final String FIRE_EFFECT = "resources/audio/fire2.wav";
  private static final String EXPLOSION_EFFECT = "resources/audio/explosion.wav";
  Clip fireClip;
  Clip explosionClip;

  public GameAudio() {
    try {
      // Set up and plays the soundtrack

      AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(LOOP_SOUNDTRACK));
      DataLine.Info info = new DataLine.Info(Clip.class, audioIn.getFormat());

      Clip clip = (Clip)AudioSystem.getLine(info);
      clip.open(audioIn);
      clip.loop(Clip.LOOP_CONTINUOUSLY);

      // Set up the fire sound effects

      audioIn = AudioSystem.getAudioInputStream(new File(FIRE_EFFECT));
      info = new DataLine.Info(Clip.class, audioIn.getFormat());

      fireClip = (Clip)AudioSystem.getLine(info);
      fireClip.open(audioIn);

      // Set up the explosion sound effects

      audioIn = AudioSystem.getAudioInputStream(new File(EXPLOSION_EFFECT));
      info = new DataLine.Info(Clip.class, audioIn.getFormat());

      explosionClip = (Clip)AudioSystem.getLine(info);
      explosionClip.open(audioIn);

    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  public void fireSound() {
    Thread thread = new Thread(new PlayClip(fireClip));
    thread.start();
  }

  public void explosionSound() {
    //Thread thread = new Thread(new PlayClip(explosionClip));
    //thread.start();
  }

  private class PlayClip implements Runnable {
    Clip clip;

    public PlayClip(Clip clip) {
      this.clip = clip;
    }

    @Override
    public void run() {
      clip.stop();
      clip.setFramePosition(0);
      clip.start();
    }
  }
}
