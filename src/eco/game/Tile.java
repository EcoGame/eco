package eco.game;

/**
 * This class represents a terrain tile
 *
 * @author phil
 */
public class Tile {

    public short id;

    public int tex;
    public int tey;

    public String name;

    private static Tile[] tiles = new Tile[256];

    public Tile(int id, String name, int tex, int tey) {
        this.id = (short) id;
        this.name = name;
        this.tex = tex;
        this.tey = tey;
        tiles[id] = this;
    }

    public static Tile getTile(int id) {
        return tiles[id];
    }

    public static final Tile water = new Tile(0, "Water", 0, 0);
    public static final Tile grass = new Tile(1, "Grass", 1, 0);
    public static final Tile stone = new Tile(2, "Stone", 2, 0);
    public static final Tile farmland = new Tile(3, "Farmland", 3, 0);
    public static final Tile marble = new Tile(4, "Marble", 9, 0);

    public boolean shouldRenderE(int x, int y) {
        return World.getHeight(x, y) > World.getHeight(x, y - 1);
    }

    public boolean shouldRenderW(int x, int y) {
        return World.getHeight(x, y) > World.getHeight(x, y + 1);
    }

    public boolean shouldRenderN(int x, int y) {
        return World.getHeight(x, y) > World.getHeight(x + 1, y);
    }

    public boolean shouldRenderS(int x, int y) {
        return World.getHeight(x, y) > World.getHeight(x - 1, y);
    }


}
