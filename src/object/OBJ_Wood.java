package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class OBJ_Wood extends SuperObject {
    public OBJ_Wood() {
        name = "Wood";
        try {
            // Încărcăm fișierul de imagine folosind getResourceAsStream()
            InputStream inputStream = getClass().getResourceAsStream("objects/Wood.png");
            if (inputStream != null) {
                image = ImageIO.read(inputStream);
            } else {
                // Tratează cazul în care fișierul de imagine nu OBJ_Coinpoate fi găsit
                System.err.println("Fișierul de imagine Wood.png nu a putut fi găsit.");
            }
        } catch (IOException e) {
            // Tratează cazul în care apare o excepție în timpul încărcării imaginii
            e.printStackTrace();
        }
        collision=true;
        solidArea.x=5*3;
        solidArea.y=8*3;
        solidArea.width=11*3;
        solidArea.height=8*3;
    }
}
