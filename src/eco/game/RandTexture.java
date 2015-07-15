package eco.game;

import java.util.ArrayList;
import java.util.Collections;

public class RandTexture {
    
    /**
     * A class that uses hashcodes to pick random textures for
     * different locations
     * 
     */
    
    private ArrayList<Point> textures = new ArrayList<Point>();
    
    public void addTexture(Point texture){
        textures.add(texture);
        Collections.shuffle(textures);
    }
    
    public Point sample(int x, int y){
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
