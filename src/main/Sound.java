package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

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
            soundURL[10] = getClass().getResource("sounds/cuttree.wav");
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
                fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                checkVolume();
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

    public void checkVolume() {
        switch (volumeScale) {
            case 0:
                volume = -80f;
                break;
            case 1:
                volume = -20f;
                break;
            case 2:
                volume = -12f;
                break;
            case 3:
                volume = -5f;
                break;
            case 4:
                volume = 1f;
                break;
            case 5:
                volume = 6f;
                break;
            default:
                volume = 0f;
                break;
        }
        fc.setValue(volume);
    }
}
