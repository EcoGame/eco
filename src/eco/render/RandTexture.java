package eco.render;

import eco.game.Point;

import java.util.ArrayList;
import java.util.Collections;

public class RandTexture {

    /**
     * A class that uses hashcodes to pick random textures for
     * different locations
     */

    private ArrayList<Point> textures = new ArrayList<>();

    public static int count = 0;

    public void addTexture(Point texture) {
        textures.add(texture);
        Collections.shuffle(textures);
        count++;
    }

    public Point sample(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        int hash = 7;
        hash = 71 * hash + x;
        hash = 71 * hash + y;
        hash %= 100;
        int numtex = textures.size();
        float temp = hash;
        temp /= (100);
        temp *= numtex;
        hash = (int) temp;
        return textures.get(hash);
    }

}
