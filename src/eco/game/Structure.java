package eco.game;

/**
 * A class that represents a structure
 *
 * @author phil
 */
public class Structure {

    public String name;
    private int tex;
    private int tey;
    public int id;

    private static Structure[] structures = new Structure[256];

    public Structure(int id, String name, int tex, int tey){
        this.id = id;
        this.name = name;
        this.tex = tex;
        this.tey = tey;
        structures[id] = this;
    }

    public static final Structure city = new Structure(1, "City", 0, 1);
    public static final Structure castle = new Structure(2, "Castle", 1, 1);
    public static final Structure forest = new Structure(3, "Forest", 2, 1);

    public static Structure getStructure(int id){
        return structures[id];
    }

    public int getTex(){
        return tex;
    }

    public int getTey(){
        return tey;
    }

}
