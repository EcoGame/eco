package eco.game;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the game world
 *
 * @author phil
 */

public class World {

    public static int mapscale = 8;
    public static final int chunksize = Chunk.chunksize;
    public static int mapsize = chunksize * mapscale;

    private static final float seaLevel = Chunk.seaLevel;
    public static long mapseed = "seeds r cool".hashCode();

    private static HashMap<Point, Chunk> chunks = new HashMap<>();

    public static HashMap<Point, City> cities = new HashMap<>();


    public static void init(int generator) {
        World.generate(generator);
    }

    public static void generate(int generator) {

        NoiseSampler.initSimplexNoise((int) mapseed);
        NoiseSampler.setNoiseScale(8);

        for (int x = 0; x < mapscale; x++) {
            for (int y = 0; y < mapscale; y++) {
                chunks.put(new Point(x, y), new Chunk(new Point(x, y)));
            }
        }

        for (Chunk c : chunks.values()) {
            c.generate();
        }

        NoiseSampler.initSimplexNoise((int) mapseed);
        NoiseSampler.setNoiseScale(mapsize / 32);

        for (Chunk c : chunks.values()) {
            c.decorate();
        }
    }


    public static Chunk getChunk(int x, int y) {
        return chunks.get(new Point(x / chunksize, y / chunksize));
    }

    public static short getTileId(int x, int y) {
        if (x < 0 || y < 0 || x >= mapsize || y >= mapsize) {
            return 0;
        }
        return getChunk(x, y).getTile(x % chunksize, y % chunksize);
    }

    public static Tile getTile(int x, int y) {
        return Tile.getTile(getTileId(x, y));
    }

    public static short getStructureId(int x, int y){
        if (x < 0 || y < 0 || x >= mapsize || y >= mapsize) {
            return 0;
        }
        return getChunk(x, y).getStructure(x % chunksize, y % chunksize);
    }

    public static Structure getStructure(int x, int y) {return Structure.getStructure(getStructureId(x, y));}

    public static float getHeight(int x, int y) {
        if (x < 0 || y < 0 || x >= mapsize || y >= mapsize) {
            return 0;
        }
        if (getTileId(x, y) == 0) {
            return seaLevel;
        }
        Chunk c = getChunk(x, y);
        if (c == null) {
            return 0f;
        }
        return c.getHeight(x % chunksize, y % chunksize);
    }

    public static int getTerritory(int x, int y) {
        if (x < 0 || y < 0 || x >= mapsize || y >= mapsize) {
            return -1;
        }
        Chunk c = getChunk(x, y);
        if (c == null) {
            return -1;
        }
        return c.getTerritory(x % chunksize, y % chunksize);
    }

    public static void claimLand(int x, int y, short claim) {
        if (x < 0 || y < 0 || x >= mapsize || y >= mapsize) {
            return;
        }
        Chunk c = getChunk(x, y);
        if (c == null) {
            return;
        }
        c.setTerritory(x % chunksize, y % chunksize, claim);
    }

    public static boolean isDryLand(int x, int y) {
        return !(x < 0 || y < 0 || x >= mapsize || y >= mapsize) && getTileId(x, y) != 0;
    }

    public static boolean isUnclaimed(int x, int y){
        if (x < 0 || y < 0 || x >= mapsize || y >= mapsize) {
            return false;
        }
        Chunk c = getChunk(x, y);
        return c.getTerritory(x % chunksize, y % chunksize) == 0;
    }

    public static boolean isBorder(int x, int y){
        int territory = getTerritory(x, y);
        return  territory != getTerritory(x - 1, y) ||
                territory != getTerritory(x - 1, y - 1) ||
                territory != getTerritory(x, y - 1) ||
                territory != getTerritory(x + 1, y - 1) ||
                territory != getTerritory(x + 1, y) ||
                territory != getTerritory(x + 1, y + 1) ||
                territory != getTerritory(x, y + 1) ||
                territory != getTerritory(x - 1, y + 1);
    }

    public static float getHeightDiffE(int x, int y) {
        return getHeight(x, y) - getHeight(x, y - 1);
    }

    public static float getHeightDiffW(int x, int y) {
        return getHeight(x, y) - getHeight(x, y + 1);
    }

    public static float getHeightDiffN(int x, int y) {
        return getHeight(x, y) - getHeight(x + 1, y);
    }

    public static float getHeightDiffS(int x, int y) {
        return getHeight(x, y) - getHeight(x - 1, y);
    }

    public static ArrayList<Chunk> getAllChunks() {
        return new ArrayList<>(chunks.values());
    }

    public static void invalidateChunkCache(){
        for (Chunk c : getAllChunks()){
            c.setDirty();
        }
    }

}
