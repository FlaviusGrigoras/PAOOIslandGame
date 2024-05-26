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
            soundURL[14] = getClass().getResource("sounds/Merchant.wav");
            soundURL[15] = getClass().getResource("sounds/dungeon.wav");
            soundURL[16] = getClass().getResource("sounds/chipwall.wav");
            soundURL[17] = getClass().getResource("sounds/dooropen.wav");
            soundURL[18] = getClass().getResource("sounds/unlock.wav");

            System.out.println("Sound URLs initialized.");
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
                System.out.println("File set to: " + soundURL[i]);
            } else {
                System.out.println("Sound URL is null for index: " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
            clip.start();
            System.out.println("Clip started.");
            // Adăugăm o verificare în buclă pentru a aștepta până când clipul începe redarea
            int tries = 0;
            while (!clip.isRunning() && tries < 10) {
                try {
                    Thread.sleep(10); // Așteptăm 10 milisecunde înainte de a verifica din nou
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tries++;
            }
            if (clip.isRunning()) {
                System.out.println("Clip is running.");
            } else {
                System.out.println("Clip failed to start.");
            }
        } else {
            System.out.println("Clipul este null. Asigura-te că ai apelat metoda setFile() înainte de play().");
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Clip set to loop.");
        } else {
            System.out.println("Clipul este null. Asigura-te că ai apelat metoda setFile() înainte de loop().");
        }
    }

    public void stop() {
        System.out.println("Attempting to stop clip...");
        if (clip != null) {
            if (clip.isRunning()) {
                try {
                    clip.stop();
                    clip.flush();  // Clears the data line
                    clip.setFramePosition(0);  // Resets the clip to the start
                    System.out.println("Clip stopped.");
                } catch (Exception e) {
                    System.out.println("An error occurred while stopping the clip: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Clip is not running.");
            }
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
