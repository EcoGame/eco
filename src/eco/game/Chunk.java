package eco.game;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

/**
 * This class stores a chunk of terrain data
 *
 * @author phil
 */
public class Chunk {

    public static final int chunksize = 16;

    private short[] map;
    private short[] structures;
    private float[] height;
    private short[] territory;
    private short[] density;

    private Point location;

    public static final float seaLevel = 0.15f;
    public static final float forestHeight = 0.25f;

    private volatile boolean isDirty = true;
    private volatile FloatBuffer vertex;
    private volatile FloatBuffer texture;
    private volatile FloatBuffer colors;
    private volatile int buffersize;

    private volatile boolean isDirtyStructure = true;
    private volatile FloatBuffer vertexStructure;
    private volatile FloatBuffer textureStructure;
    private volatile FloatBuffer colorsStructure;
    private volatile int buffersizeStructure;

    public Chunk(Point location) {
        this.location = location;
        map = new short[chunksize * chunksize];
        structures = new short[chunksize * chunksize];
        height = new float[chunksize * chunksize];
        territory = new short[chunksize * chunksize];
        density = new short[chunksize * chunksize];
    }

    public void generate() {
        for (int x = 0; x < chunksize; x++) {
            for (int y = 0; y < chunksize; y++) {
                height[(x * chunksize) + y] = NoiseSampler.getNoise(x + (location.getX() * chunksize), y + (location.getY() * chunksize));
                if (height[(x * chunksize) + y] > seaLevel) {
                    map[(x * chunksize) + y] = 1;
                }
            }
        }
    }

    public void decorate(){
        for (int x = 0; x < chunksize; x++) {
            for (int y = 0; y < chunksize; y++) {
                if (NoiseSampler.getNoise(x, y) >= forestHeight) {
                    if (getTile(x, y) == Tile.grass.id) {
                        setStructure(x, y, (short) 3);
                    }
                }
            }
        }
    }

    public short getTile(int x, int y) {
        return map[(x * chunksize) + y];
    }

    public short getStructure(int x, int y) {
        return structures[(x * chunksize) + y];
    }

    public void setTile(int x, int y, short tile) {
        map[(x * chunksize) + y] = tile;
        isDirty = true;
    }

    public void setStructure(int x, int y, short structure) {
        structures[(x * chunksize) + y] = structure;
        isDirtyStructure = true;
    }

    public float getHeight(int x, int y) {
        return height[(x * chunksize) + y];
    }

    public void updateMesh(FloatBuffer vertex, FloatBuffer texture, FloatBuffer color, int buffersize) {
        if (!isDirty) {
            Log.severe("Updating a chunk mesh that is not dirty!");
        }
        this.vertex = vertex;
        this.texture = texture;
        this.colors = color;
        this.buffersize = buffersize;
        this.vertex.flip();
        this.texture.flip();
        this.colors.flip();
        isDirty = false;
    }

    public void updateMeshStructure(FloatBuffer vertex, FloatBuffer texture, FloatBuffer color, int buffersize){
        if (!isDirtyStructure) {
            Log.severe("Updating a chunk mesh that is not dirty!");
        }
        this.vertexStructure = vertex;
        this.textureStructure = texture;
        this.colorsStructure = color;
        this.buffersizeStructure = buffersize;
        this.vertexStructure.flip();
        this.textureStructure.flip();
        this.colorsStructure.flip();
        isDirtyStructure = false;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public boolean isDirtyStructure() { return isDirtyStructure; }

    public void render() {
        if (vertex != null && texture != null && colors != null) {
            GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);

            GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
            GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

            GL11.glVertexPointer(3, 0, vertex);
            GL11.glTexCoordPointer(2, 0, texture);
            GL11.glColorPointer(4, 0, colors);

            GL11.glDrawArrays(GL11.GL_QUADS, 0, buffersize / 3);

            GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
            GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        }
    }

    public void renderStructures(){
        if (vertexStructure != null && textureStructure != null && colorsStructure != null) {
            if (ShaderManager.useShader) {
                ARBShaderObjects.glUseProgramObjectARB(ShaderManager.billboard);
            }
            GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);

            GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
            GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

            GL11.glVertexPointer(3, 0, vertexStructure);
            GL11.glTexCoordPointer(2, 0, textureStructure);
            GL11.glColorPointer(4, 0, colorsStructure);

            GL11.glDrawArrays(GL11.GL_QUADS, 0, buffersizeStructure / 3);

            GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
            GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            if (ShaderManager.useShader){
                ARBShaderObjects.glUseProgramObjectARB(0);
            }
        }
    }

    public int getStartX() {
        return location.getX() * chunksize;
    }

    public int getStartY() {
        return location.getY() * chunksize;
    }

    public Point getLocation() {
        return location;
    }

    public int getTerritory(int x, int y) {
        return territory[(x * chunksize) + y];
    }

    public void setTerritory(int x, int y, short claim) {
        territory[(x * chunksize) + y] = claim;
    }

    public void setDirty(){
        isDirty = true;
    }

}
