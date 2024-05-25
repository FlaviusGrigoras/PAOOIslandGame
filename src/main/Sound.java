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
            System.out.println("Initializing sound URLs...");
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
            soundURL[11] = getClass().getResource("sounds/GameOver.wav");
            soundURL[12] = getClass().getResource("sounds/stairs.wav");
            soundURL[13] = getClass().getResource("sounds/sleep.wav");
            System.out.println("Sound URLs initialized.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFile(int i) {
        try {
            System.out.println("Setting file for index: " + i);
            if (soundURL[i] != null) {
                System.out.println("Opening audio stream for: " + soundURL[i]);
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                System.out.println("Getting clip instance...");
                clip = AudioSystem.getClip();
                System.out.println("Opening clip with audio stream...");
                clip.open(ais);
                fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                checkVolume();
                System.out.println("Clip set successfully.");
            } else {
                System.out.println("URL-ul sunetului este null pentru indexul " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        System.out.println("Attempting to play clip...");
        if (clip != null) {
            clip.start();
            System.out.println("Clip played.");
        } else {
            System.out.println("Clipul este null. Asigura-te că ai apelat metoda setFile() înainte de play().");
        }
    }

    public void loop() {
        System.out.println("Attempting to loop clip...");
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Clip is looping.");
        } else {
            System.out.println("Clipul este null. Asigura-te că ai apelat metoda setFile() înainte de loop().");
        }
    }

    public void stop() {
        System.out.println("Attempting to stop clip...");
        if (clip != null) {
            clip.stop();
            System.out.println("Clip stopped.");
        } else {
            System.out.println("Clipul este null. Asigura-te că ai apelat metoda setFile() înainte de stop().");
        }
    }

    public void checkVolume() {
        System.out.println("Checking volume with volumeScale: " + volumeScale);
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
        System.out.println("Setting volume to: " + volume);
        fc.setValue(volume);
    }
}
