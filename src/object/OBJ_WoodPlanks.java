package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class OBJ_WoodPlanks extends SuperObject {
    public OBJ_WoodPlanks() {
        name = "Coin";
        try {
            // Încărcăm fișierul de imagine folosind getResourceAsStream()
            InputStream inputStream = getClass().getResourceAsStream("objects/WoodPlanks.png");
            if (inputStream != null) {
                image = ImageIO.read(inputStream);
            } else {
                // Tratează cazul în care fișierul de imagine nu OBJ_Coinpoate fi găsit
                System.err.println("Fișierul de imagine WoodPlanks.png nu a putut fi găsit.");
            }
        } catch (IOException e) {
            // Tratează cazul în care apare o excepție în timpul încărcării imaginii
            e.printStackTrace();
        }
    }
}
