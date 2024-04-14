package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;
}

/*
    Insula inconjurata de ocean
    tiles[0] = Colt. Ocean in stanga si sus
    tiles[1] = Margine. Ocean doar sus
    tiles[2] = Colt. Ocean in dreapta si sus

    tiles[14] = Margine. Ocean doar in stanga
    tiles[15] = Pamant fara ocean
    tiles[16] = Margine. Ocean doar in dreapta

    tiles[28] = Colt. Ocean in stanga si jos
    tiles[29] = Margine. Ocean doar in sus
    tiles[30] = Colt. Ocean in dreapta si jos

    Lac inconjurat de pamant
    tiles[3] = Lac cu pamant stanga si sus complet
    tiles[4] = Lac cu pamant dreapta si sus complet

    tiles[17] = Lac cu pamant stanga si jos complet
    tiles[18] = Lac cu pamant dreapta si jos complet

    Golf in partea de sus a insulei:
    tiles[17] + tiles[18]
    Gold in partea de jos a insulei:
    tiles[3] + tiles[4]
    Golf in partea stanga a insulei:
    tiles[4] + tiles[18]
    Golf in partea dreapta a insulei:
    tiles[3] + tiles[17]



    Variatii apa ocean
    tiles[31] = Ocean simplu
    tiles[32] = Ocean simplu variatie 2
    tiles[33] = Ocean simplu variatie 3
    tiles[34] = Ocean simplu variatie 4
    tiles[35] = Ocean simplu variatie 5
    tiles[36] = Ocean simplu variatie 6
    */