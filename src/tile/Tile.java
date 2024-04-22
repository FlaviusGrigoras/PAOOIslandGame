package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;
}

/*
    Insula inconjurata de ocean
    tiles[0] = Colt. Ocean in stanga si sus CORNER_TOP_LEFT
    tiles[1] = Margine. Ocean doar sus EDGE_TOP
    tiles[2] = Colt. Ocean in dreapta si sus CORNER_TOP_RIGHT

    tiles[3] = Margine. Ocean doar in stanga EDGE_LEFT
    tiles[4] = Pamant fara ocean ISLAND
    tiles[5] = Margine. Ocean doar in dreapta EDGE_RIGHT

    tiles[6] = Colt. Ocean in stanga si jos CORNER_BOTTOM_LEFT
    tiles[7] = Margine. Ocean doar in sus EDGE_BOTTOM
    tiles[8] = Colt. Ocean in dreapta si jos CORNER_BOTTOM_RIGHT

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

    tiles[5] = Copac
    tiles[6] = Stanca

    Variatii apa ocean
    tiles[31] = Ocean simplu
    tiles[32] = Ocean simplu variatie 1
    tiles[33] = Ocean simplu variatie 2
    tiles[34] = Ocean simplu variatie 3
    tiles[35] = Ocean simplu variatie 4
    tiles[36] = Ocean simplu variatie 5
    */