package eco.game;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * This class is an implementation of <i>Runnable</i> and is used to
 * asynchronously mesh the world's geometry. The only synchronized part is
 * actually buffering the data, because openGL is single threaded we must take
 * the rendering context.
 *
 * @author phil
 */

public class MeshTask implements Runnable {

    private static float tilesize = Render.tilesize;
    private static float heightConstant = Render.heightConstant;

    private static final float offset = Render.tilesize / 2f;

    private static float[] vertex = new float[0x2000000];
    private static float[] texture = new float[0x2000000];
    private static float[] colors = new float[0x2000000];

    private static int index = 0;

    private static long newTime;

    @Override
    public void run() {

        long time = System.currentTimeMillis();
        if (time > newTime) {
            newTime = time;
        }

        @SuppressWarnings("unused")
        long start = System.nanoTime();

        ArrayList<Chunk> chunks = RenderUtil.getDirtyChunks();

        for (Chunk c : chunks) {
            index = 0;

            Tile tile;
            float height;
            float diff;

            for (int x = c.getStartX(); x < Chunk.chunksize + c.getStartX(); x++) {
                for (int y = c.getStartY(); y < Chunk.chunksize + c.getStartY(); y++) {
                    tile = World.getTile(x, y);
                    height = World.getHeight(x, y);
                    if (tile.id == Tile.water.id) {
                        drawTile(x, y, height, tile.tex, tile.tey, 1f, 0.9f);
                        if (tile.shouldRenderE(x, y)) {
                            diff = World.getHeightDiffE(x, y);
                            drawTileE(x, y, height, diff, tile.tex, tile.tey, 1f, 0.9f);
                        }
                        if (tile.shouldRenderW(x, y)) {
                            diff = World.getHeightDiffW(x, y);
                            drawTileW(x, y, height, diff, tile.tex, tile.tey, 1f, 0.9f);
                        }
                        if (tile.shouldRenderS(x, y)) {
                            diff = World.getHeightDiffS(x, y);
                            drawTileS(x, y, height, diff, tile.tex, tile.tey, 1f, 0.9f);
                        }
                        if (tile.shouldRenderN(x, y)) {
                            diff = World.getHeightDiffN(x, y);
                            drawTileN(x, y, height, diff, tile.tex, tile.tey, 1f, 0.9f);
                        }
                    } else {
                        drawTile(x, y, height, tile.tex, tile.tey, RenderUtil.getRandomColorNoise(x, y), 1f);
                        if (tile.shouldRenderE(x, y)) {
                            diff = World.getHeightDiffE(x, y);
                            drawTileE(x, y, height, diff, tile.tex, tile.tey, RenderUtil.getRandomColorNoise(x, y), 1f);
                        }
                        if (tile.shouldRenderW(x, y)) {
                            diff = World.getHeightDiffW(x, y);
                            drawTileW(x, y, height, diff, tile.tex, tile.tey, RenderUtil.getRandomColorNoise(x, y), 1f);
                        }
                        if (tile.shouldRenderS(x, y)) {
                            diff = World.getHeightDiffS(x, y);
                            drawTileS(x, y, height, diff, tile.tex, tile.tey, RenderUtil.getRandomColorNoise(x, y), 1f);
                        }
                        if (tile.shouldRenderN(x, y)) {
                            diff = World.getHeightDiffN(x, y);
                            drawTileN(x, y, height, diff, tile.tex, tile.tey, RenderUtil.getRandomColorNoise(x, y), 1f);
                        }
                    }
                }
                if (time < newTime) {
                    Log.warning("Ran out of time for mesh!");
                    return;
                }
            }

            int buffersize = index;

            FloatBuffer vertexData = BufferUtils.createFloatBuffer(buffersize);
            FloatBuffer textureData = BufferUtils.createFloatBuffer(buffersize * 2 / 3);
            FloatBuffer colorData = BufferUtils.createFloatBuffer(buffersize / 3 * 4);

            vertexData.put(vertex, 0, index);
            textureData.put(texture, 0, index * 2 / 3);
            colorData.put(colors, 0, index / 3 * 4);

            synchronized (Render.lock) {
                c.updateMesh(vertexData, textureData, colorData, buffersize);
            }

        }

        chunks = RenderUtil.getDirtyStructureChunks();

        Structure s;
        for (Chunk c : chunks) {
            index = 0;
            for (int x = c.getStartX(); x < Chunk.chunksize + c.getStartX(); x++) {
                for (int y = c.getStartY(); y < Chunk.chunksize + c.getStartY(); y++) {
                    s = World.getStructure(x, y);
                    if (s != null) {
                        drawStructure(x, y, World.getHeight(x, y), s.getTex(), s.getTey());
                    }
                }
            }
            int buffersize = index;

            FloatBuffer vertexData = BufferUtils.createFloatBuffer(buffersize);
            FloatBuffer textureData = BufferUtils.createFloatBuffer(buffersize * 2 / 3);
            FloatBuffer colorData = BufferUtils.createFloatBuffer(buffersize / 3 * 4);

            vertexData.put(vertex, 0, index);
            textureData.put(texture, 0, index * 2 / 3);
            colorData.put(colors, 0, index / 3 * 4);

            synchronized (Render.lock) {
                c.updateMeshStructure(vertexData, textureData, colorData, buffersize);
            }
        }


        @SuppressWarnings("unused")
        long end = System.nanoTime();

        //System.out.println((end - start) / 1000000);
    }


    private static void drawStructure(float x, float y, float height, int tex, int tey){
        float color = 1f;
        float alpha = 1f;

        int texindex = index / 3 * 2;

        texture[texindex] = Render.atlas.getCoord(tex, false);
        texture[texindex + 1] = Render.atlas.getCoord(tey, true);
        texture[texindex + 2] = Render.atlas.getCoord(tex, true);
        texture[texindex + 3] = Render.atlas.getCoord(tey, true);
        texture[texindex + 4] = Render.atlas.getCoord(tex, true);
        texture[texindex + 5] = Render.atlas.getCoord(tey, false);
        texture[texindex + 6] = Render.atlas.getCoord(tex, false);
        texture[texindex + 7] = Render.atlas.getCoord(tey, false);

        int colorindex = index / 3 * 4;

        colors[colorindex] = color;
        colors[colorindex + 1] = color;
        colors[colorindex + 2] = color;
        colors[colorindex + 3] = alpha;
        colors[colorindex + 4] = color;
        colors[colorindex + 5] = color;
        colors[colorindex + 6] = color;
        colors[colorindex + 7] = alpha;
        colors[colorindex + 8] = color;
        colors[colorindex + 9] = color;
        colors[colorindex + 10] = color;
        colors[colorindex + 11] = alpha;
        colors[colorindex + 12] = color;
        colors[colorindex + 13] = color;
        colors[colorindex + 14] = color;
        colors[colorindex + 15] = alpha;

        vertex[index] = -x * tilesize - offset;
        vertex[index + 1] = height;
        vertex[index + 2] = -y * tilesize;

        vertex[index + 3] = -x * tilesize + offset;
        vertex[index + 4] = height;
        vertex[index + 5] = -y * tilesize;

        vertex[index + 6] = -x * tilesize + offset;
        vertex[index + 7] = height + tilesize;
        vertex[index + 8] = -y * tilesize;

        vertex[index + 9] = -x * tilesize - offset;
        vertex[index + 10] = height + tilesize;
        vertex[index + 11] = -y * tilesize;

        index += 12;
    }

    @SuppressWarnings("unused")
    private static void drawTile(float x, float y, float height,
                                 int tex, int tey) {
        drawTile(x, y, height, tex, tey, 1.0f, 1.0f);
    }

    private static void drawTile(float x, float y, float height, int tex,
                                 int tey, float color, float alpha) {

        int texindex = index / 3 * 2;

        texture[texindex] = Render.atlas.getCoord(tex, false);
        texture[texindex + 1] = Render.atlas.getCoord(tey, false);
        texture[texindex + 2] = Render.atlas.getCoord(tex, true);
        texture[texindex + 3] = Render.atlas.getCoord(tey, false);
        texture[texindex + 4] = Render.atlas.getCoord(tex, true);
        texture[texindex + 5] = Render.atlas.getCoord(tey, true);
        texture[texindex + 6] = Render.atlas.getCoord(tex, false);
        texture[texindex + 7] = Render.atlas.getCoord(tey, true);

        int colorindex = index / 3 * 4;

        colors[colorindex] = color;
        colors[colorindex + 1] = color;
        colors[colorindex + 2] = color;
        colors[colorindex + 3] = alpha;
        colors[colorindex + 4] = color;
        colors[colorindex + 5] = color;
        colors[colorindex + 6] = color;
        colors[colorindex + 7] = alpha;
        colors[colorindex + 8] = color;
        colors[colorindex + 9] = color;
        colors[colorindex + 10] = color;
        colors[colorindex + 11] = alpha;
        colors[colorindex + 12] = color;
        colors[colorindex + 13] = color;
        colors[colorindex + 14] = color;
        colors[colorindex + 15] = alpha;

        applyBorderColors((int) x, (int) y, colorindex, texindex);

        vertex[index] = -x * tilesize - offset;
        vertex[index + 1] = height;
        vertex[index + 2] = -y * tilesize - offset;

        vertex[index + 3] = -x * tilesize + offset;
        vertex[index + 4] = height;
        vertex[index + 5] = -y * tilesize - offset;

        vertex[index + 6] = -x * tilesize + offset;
        vertex[index + 7] = height;
        vertex[index + 8] = -y * tilesize + offset;

        vertex[index + 9] = -x * tilesize - offset;
        vertex[index + 10] = height;
        vertex[index + 11] = -y * tilesize + offset;

        index += 12;
    }

    @SuppressWarnings("unused")
    private static void drawTileN(float x, float y, float height, float length,
                                  int tex, int tey) {
        drawTileN(x, y, height, length, tex, tey, 1.0f, 1.0f);
    }

    private static void drawTileN(float x, float y, float height, float length,
                                  int tex, int tey, float color, float alpha) {
        int texindex = index / 3 * 2;

        texture[texindex] = Render.atlas.getCoord(tex, false);
        texture[texindex + 1] = Render.atlas.getCoord(tey, false);
        texture[texindex + 2] = Render.atlas.getCoord(tex, true);
        texture[texindex + 3] = Render.atlas.getCoord(tey, false);
        texture[texindex + 4] = Render.atlas.getCoord(tex, true);
        texture[texindex + 5] = Render.atlas.getCoord(tey, true);
        texture[texindex + 6] = Render.atlas.getCoord(tex, false);
        texture[texindex + 7] = Render.atlas.getCoord(tey, true);

        int colorindex = index / 3 * 4;
        colors[colorindex] = color;
        colors[colorindex + 1] = color;
        colors[colorindex + 2] = color;
        colors[colorindex + 3] = alpha;
        colors[colorindex + 4] = color;
        colors[colorindex + 5] = color;
        colors[colorindex + 6] = color;
        colors[colorindex + 7] = alpha;
        colors[colorindex + 8] = color;
        colors[colorindex + 9] = color;
        colors[colorindex + 10] = color;
        colors[colorindex + 11] = alpha;
        colors[colorindex + 12] = color;
        colors[colorindex + 13] = color;
        colors[colorindex + 14] = color;
        colors[colorindex + 15] = alpha;

        applyBorderColors((int) x, (int) y, colorindex, texindex);

        vertex[index] = -x * tilesize - offset;
        vertex[index + 1] = height;
        vertex[index + 2] = -y * tilesize - offset;

        vertex[index + 3] = -x * tilesize - offset;
        vertex[index + 4] = height - length;
        vertex[index + 5] = -y * tilesize - offset;

        vertex[index + 6] = -x * tilesize - offset;
        vertex[index + 7] = height - length;
        vertex[index + 8] = -y * tilesize + offset;

        vertex[index + 9] = -x * tilesize - offset;
        vertex[index + 10] = height;
        vertex[index + 11] = -y * tilesize + offset;

        index += 12;
    }

    @SuppressWarnings("unused")
    private static void drawTileW(float x, float y, float height, float length,
                                  int tex, int tey) {
        drawTileW(x, y, height, length, tex, tey, 1.0f, 1.0f);
    }

    private static void drawTileW(float x, float y, float height, float length,
                                  int tex, int tey, float color, float alpha) {

        int texindex = index / 3 * 2;

        texture[texindex] = Render.atlas.getCoord(tex, false);
        texture[texindex + 1] = Render.atlas.getCoord(tey, false);
        texture[texindex + 2] = Render.atlas.getCoord(tex, true);
        texture[texindex + 3] = Render.atlas.getCoord(tey, false);
        texture[texindex + 4] = Render.atlas.getCoord(tex, true);
        texture[texindex + 5] = Render.atlas.getCoord(tey, true);
        texture[texindex + 6] = Render.atlas.getCoord(tex, false);
        texture[texindex + 7] = Render.atlas.getCoord(tey, true);

        int colorindex = index / 3 * 4;
        colors[colorindex] = color;
        colors[colorindex + 1] = color;
        colors[colorindex + 2] = color;
        colors[colorindex + 3] = alpha;
        colors[colorindex + 4] = color;
        colors[colorindex + 5] = color;
        colors[colorindex + 6] = color;
        colors[colorindex + 7] = alpha;
        colors[colorindex + 8] = color;
        colors[colorindex + 9] = color;
        colors[colorindex + 10] = color;
        colors[colorindex + 11] = alpha;
        colors[colorindex + 12] = color;
        colors[colorindex + 13] = color;
        colors[colorindex + 14] = color;
        colors[colorindex + 15] = alpha;

        applyBorderColors((int) x, (int) y, colorindex, texindex);

        vertex[index] = -x * tilesize - offset;
        vertex[index + 1] = height;
        vertex[index + 2] = -y * tilesize - offset;

        vertex[index + 3] = -x * tilesize + offset;
        vertex[index + 4] = height;
        vertex[index + 5] = -y * tilesize - offset;

        vertex[index + 6] = -x * tilesize + offset;
        vertex[index + 7] = height - length;
        vertex[index + 8] = -y * tilesize - offset;

        vertex[index + 9] = -x * tilesize - offset;
        vertex[index + 10] = height - length;
        vertex[index + 11] = -y * tilesize - offset;

        index += 12;
    }

    @SuppressWarnings("unused")
    private static void drawTileS(float x, float y, float height, float length,
                                  int tex, int tey) {
        drawTileS(x, y, height, length, tex, tey, 1.0f, 1.0f);
    }

    private static void drawTileS(float x, float y, float height, float length,
                                  int tex, int tey, float color, float alpha) {

        int texindex = index / 3 * 2;

        texture[texindex] = Render.atlas.getCoord(tex, false);
        texture[texindex + 1] = Render.atlas.getCoord(tey, false);
        texture[texindex + 2] = Render.atlas.getCoord(tex, true);
        texture[texindex + 3] = Render.atlas.getCoord(tey, false);
        texture[texindex + 4] = Render.atlas.getCoord(tex, true);
        texture[texindex + 5] = Render.atlas.getCoord(tey, true);
        texture[texindex + 6] = Render.atlas.getCoord(tex, false);
        texture[texindex + 7] = Render.atlas.getCoord(tey, true);

        int colorindex = index / 3 * 4;
        colors[colorindex] = color;
        colors[colorindex + 1] = color;
        colors[colorindex + 2] = color;
        colors[colorindex + 3] = alpha;
        colors[colorindex + 4] = color;
        colors[colorindex + 5] = color;
        colors[colorindex + 6] = color;
        colors[colorindex + 7] = alpha;
        colors[colorindex + 8] = color;
        colors[colorindex + 9] = color;
        colors[colorindex + 10] = color;
        colors[colorindex + 11] = alpha;
        colors[colorindex + 12] = color;
        colors[colorindex + 13] = color;
        colors[colorindex + 14] = color;
        colors[colorindex + 15] = alpha;

        applyBorderColors((int) x, (int) y, colorindex, texindex);

        vertex[index] = -x * tilesize + offset;
        vertex[index + 1] = height;
        vertex[index + 2] = -y * tilesize - offset;

        vertex[index + 3] = -x * tilesize + offset;
        vertex[index + 4] = height - length;
        vertex[index + 5] = -y * tilesize - offset;

        vertex[index + 6] = -x * tilesize + offset;
        vertex[index + 7] = height - length;
        vertex[index + 8] = -y * tilesize + offset;

        vertex[index + 9] = -x * tilesize + offset;
        vertex[index + 10] = height;
        vertex[index + 11] = -y * tilesize + offset;

        index += 12;
    }

    @SuppressWarnings("unused")
    private static void drawTileE(float x, float y, float height, float length,
                                  int tex, int tey) {
        drawTileE(x, y, height, length, tex, tey, 1.0f, 1.0f);
    }

    private static void drawTileE(float x, float y, float height, float length,
                                  int tex, int tey, float color, float alpha) {
        int texindex = index / 3 * 2;

        texture[texindex] = Render.atlas.getCoord(tex, false);
        texture[texindex + 1] = Render.atlas.getCoord(tey, false);
        texture[texindex + 2] = Render.atlas.getCoord(tex, true);
        texture[texindex + 3] = Render.atlas.getCoord(tey, false);
        texture[texindex + 4] = Render.atlas.getCoord(tex, true);
        texture[texindex + 5] = Render.atlas.getCoord(tey, true);
        texture[texindex + 6] = Render.atlas.getCoord(tex, false);
        texture[texindex + 7] = Render.atlas.getCoord(tey, true);

        int colorindex = index / 3 * 4;
        colors[colorindex] = color;
        colors[colorindex + 1] = color;
        colors[colorindex + 2] = color;
        colors[colorindex + 3] = alpha;
        colors[colorindex + 4] = color;
        colors[colorindex + 5] = color;
        colors[colorindex + 6] = color;
        colors[colorindex + 7] = alpha;
        colors[colorindex + 8] = color;
        colors[colorindex + 9] = color;
        colors[colorindex + 10] = color;
        colors[colorindex + 11] = alpha;
        colors[colorindex + 12] = color;
        colors[colorindex + 13] = color;
        colors[colorindex + 14] = color;
        colors[colorindex + 15] = alpha;

        applyBorderColors((int) x, (int) y, colorindex, texindex);

        vertex[index] = -x * tilesize - offset;
        vertex[index + 1] = height;
        vertex[index + 2] = -y * tilesize + offset;

        vertex[index + 3] = -x * tilesize + offset;
        vertex[index + 4] = height;
        vertex[index + 5] = -y * tilesize + offset;

        vertex[index + 6] = -x * tilesize + offset;
        vertex[index + 7] = height - length;
        vertex[index + 8] = -y * tilesize + offset;

        vertex[index + 9] = -x * tilesize - offset;
        vertex[index + 10] = height - length;
        vertex[index + 11] = -y * tilesize + offset;

        index += 12;
    }

    public static boolean applyBorderColors(int x, int y, int colorindex, int texindex){
        if (!Main.fullDebug){
            return false;
        }
        if (World.isBorder(x, y)){
            Treble<Float, Float, Float> terrColor = Country.getTerritoryColor(World.getTerritory(x, y));
            if (terrColor != null) {
                colors[colorindex] = (terrColor.x);
                colors[colorindex + 1] = (terrColor.y);
                colors[colorindex + 2] = (terrColor.z);
                colors[colorindex + 4] = (terrColor.x);
                colors[colorindex + 5] = (terrColor.y);
                colors[colorindex + 6] = (terrColor.z);
                colors[colorindex + 8] = (terrColor.x);
                colors[colorindex + 9] = (terrColor.y);
                colors[colorindex + 10] = (terrColor.z);
                colors[colorindex + 12] = (terrColor.x);
                colors[colorindex + 13] = (terrColor.y);
                colors[colorindex + 14] = (terrColor.z);

                texture[texindex] = 1;
                texture[texindex + 1] = 1;
                texture[texindex + 2] = 1;
                texture[texindex + 3] = 1;
                texture[texindex + 4] = 1;
                texture[texindex + 5] = 1;
                texture[texindex + 6] = 1;
                texture[texindex + 7] = 1;
                return true;
            }
        }
        return false;
    }

}
