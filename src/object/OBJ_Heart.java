package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class OBJ_Heart extends SuperObject {
    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";
        try {
            // Încărcăm fișierul de imagine folosind ImageIO.read(new File(String))
            File file = new File("res/objects/heart_full.png");
            File file2 = new File("res/objects/heart_half.png");
            File file3 = new File("res/objects/heart_blank.png");
            if (file.exists()) {
                image = ImageIO.read(file);
            } else {
                // Tratează cazul în care fișierul de imagine nu poate fi găsit
                System.err.println("Fișierul de imagine heart_full.png nu a putut fi găsit.");
            }
            if (file2.exists()) {
                image2 = ImageIO.read(file2);
            } else {
                System.err.println("Fișierul de imagine heart_half.png nu a putut fi găsit.");
            }
            if (file3.exists()) {
                image3 = ImageIO.read(file3);
            } else {
                System.err.println("Fișierul de imagine heart_blank.png nu a putut fi găsit.");
            }
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            // Tratează cazul în care apare o excepție în timpul încărcării imaginii
            e.printStackTrace();
        }
    }
}
