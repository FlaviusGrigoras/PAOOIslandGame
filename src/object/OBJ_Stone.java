package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class OBJ_Stone extends SuperObject {
    GamePanel gp;
    public OBJ_Stone(GamePanel gp) {
        name = "Stone";
        try {
            // Încărcăm fișierul de imagine folosind ImageIO.read(new File(String))
            File file = new File("res/objects/Stone.png");
            if (file.exists()) {
                image = ImageIO.read(file);
            } else {
                // Tratează cazul în care fișierul de imagine nu poate fi găsit
                System.err.println("Fișierul de imagine Stone.png nu a putut fi găsit.");
            }
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            // Tratează cazul în care apare o excepție în timpul încărcării imaginii
            e.printStackTrace();
        }
        collision=true;
        solidArea.x=8*3;
        solidArea.y=5*3;
        solidArea.width=8*3;
        solidArea.height=11*3;
    }
}
