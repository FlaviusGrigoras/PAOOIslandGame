package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        try {
            soundURL[0] = getClass().getResource("sounds/intro.wav");
            soundURL[1] = getClass().getResource("sounds/coin.wav");
            soundURL[2] = getClass().getResource("sounds/hitmonster.wav");
            soundURL[3] = getClass().getResource("sounds/receivedamage.wav");
            soundURL[4] = getClass().getResource("sounds/swingweapon.wav");
            soundURL[6] = getClass().getResource("sounds/levelup.wav");
            soundURL[7] = getClass().getResource("sounds/cursor.wav");
            soundURL[8] = getClass().getResource("sounds/powerup.wav");
            soundURL[9] = getClass().getResource("sounds/burning.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFile(int i) {
        try {
            if (soundURL[i] != null) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                clip = AudioSystem.getClip();
                clip.open(ais);
            } else {
                System.out.println("URL-ul sunetului este null pentru indexul " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        } else {
            System.out.println("Clipul este null. Asigura-te că ai apelat metoda setFile() înainte de play().");
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("Clipul este null. Asigura-te că ai apelat metoda setFile() înainte de loop().");
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        } else {
            System.out.println("Clipul este null. Asigura-te că ai apelat metoda setFile() înainte de stop().");
        }
    }

    public void setVolume(float volume) {
        if (clip != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
        } else {
            System.out.println("Clipul este null. Asigura-te că ai apelat metoda setFile() înainte de setVolume().");
        }
    }
}
