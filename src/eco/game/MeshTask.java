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
 * 
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
    
    private long time;

    @Override
    public void run() {
        
        time = System.currentTimeMillis();
        if (time > newTime){
            newTime = time;
        }

        @SuppressWarnings("unused")
        long start = System.nanoTime();

        ArrayList<Chunk> chunks = RenderUtil.getDirtyChunks();

        for (Chunk c : chunks){
            index = 0;

            Tile tile;
            float height;
            float diff;

            for (int x = c.getStartX(); x < Chunk.chunksize + c.getStartX(); x++) {
                for (int y = c.getStartY(); y < Chunk.chunksize + c.getStartY(); y++) {
                    tile = World.getTile(x, y);
                    height = World.getHeight(x, y);
                    if (tile.id == Tile.water.id){
                        drawTile(x, y, height, tile.tex, tile.tey, 1f, 0.9f);
                        if (tile.shouldRenderE(x, y)){
                            diff = World.getHeightDiffE(x, y);
                            drawTileE(x, y, height, diff, tile.tex, tile.tey, 1f, 0.9f);
                        }
                        if (tile.shouldRenderW(x, y)){
                            diff = World.getHeightDiffW(x, y);
                            drawTileW(x, y, height, diff, tile.tex, tile.tey, 1f, 0.9f);
                        }
                        if (tile.shouldRenderS(x, y)){
                            diff = World.getHeightDiffS(x, y);
                            drawTileS(x, y, height, diff, tile.tex, tile.tey, 1f, 0.9f);
                        }
                        if (tile.shouldRenderN(x, y)){
                            diff = World.getHeightDiffN(x, y);
                            drawTileN(x, y, height, diff, tile.tex, tile.tey, 1f, 0.9f);
                        }
                    } else{
                        drawTile(x, y, height, tile.tex, tile.tey, RenderUtil.getRandomColorNoise(x, y), 1f);
                        if (tile.shouldRenderE(x, y)){
                            diff = World.getHeightDiffE(x, y);
                            drawTileE(x, y, height, diff, tile.tex, tile.tey, RenderUtil.getRandomColorNoise(x, y), 1f);
                        }
                        if (tile.shouldRenderW(x, y)){
                            diff = World.getHeightDiffW(x, y);
                            drawTileW(x, y, height, diff, tile.tex, tile.tey, RenderUtil.getRandomColorNoise(x, y), 1f);
                        }
                        if (tile.shouldRenderS(x, y)){
                            diff = World.getHeightDiffS(x, y);
                            drawTileS(x, y, height, diff, tile.tex, tile.tey, RenderUtil.getRandomColorNoise(x, y), 1f);
                        }
                        if (tile.shouldRenderN(x, y)){
                            diff = World.getHeightDiffN(x, y);
                            drawTileN(x, y, height, diff, tile.tex, tile.tey, RenderUtil.getRandomColorNoise(x, y), 1f);
                        }
                    }
                }
                if (time < newTime){
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

            synchronized(Render.lock){
                c.updateMesh(vertexData, textureData, colorData, buffersize);
            }

        }


        
        @SuppressWarnings("unused")
        long end = System.nanoTime();
        
        //System.out.println((end - start) / 1000000);
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


        Treble<Float, Float, Float> terrColor = Country.getTerritoryColor(World.getTerritory((int) x, (int) y));
        if (terrColor != null){
            colors[colorindex] = color - (terrColor.x * 0.8f);
            colors[colorindex + 1] = color - (terrColor.y * 0.8f);
            colors[colorindex + 2] = color - (terrColor.z * 0.8f);
            colors[colorindex + 4] = color - (terrColor.x * 0.8f);
            colors[colorindex + 5] = color - (terrColor.y * 0.8f);
            colors[colorindex + 6] = color - (terrColor.z * 0.8f);
            colors[colorindex + 8] = color - (terrColor.x * 0.8f);
            colors[colorindex + 9] = color - (terrColor.y * 0.8f);
            colors[colorindex + 10] = color - (terrColor.z * 0.8f);
            colors[colorindex + 12] = color - (terrColor.x * 0.8f);
            colors[colorindex + 13] = color - (terrColor.y * 0.8f);
            colors[colorindex + 14] = color - (terrColor.z * 0.8f);
        }

        /*if (World.getHeight((int) x + 1, (int) y + 1) > World.getHeight((int) x, (int) y)){
            colors[colorindex] = color - 0.1f;
            colors[colorindex + 1] = color - 0.1f;
            colors[colorindex + 2] = color - 0.1f;
            colors[colorindex + 3] = alpha;
        } else if (World.getHeight((int) x + 1, (int) y) > World.getHeight((int) x, (int) y)){
            colors[colorindex] = color - 0.05f;
            colors[colorindex + 1] = color - 0.05f;
            colors[colorindex + 2] = color - 0.05f;
            colors[colorindex + 3] = alpha;
        } else if (World.getHeight((int) x, (int) y + 1) > World.getHeight((int) x, (int) y)){
            colors[colorindex] = color - 0.05f;
            colors[colorindex + 1] = color - 0.05f;
            colors[colorindex + 2] = color - 0.05f;
            colors[colorindex + 3] = alpha;
        } else{
            colors[colorindex] = color;
            colors[colorindex + 1] = color;
            colors[colorindex + 2] = color;
            colors[colorindex + 3] = alpha;
        }
        
        if (World.getHeight((int) x - 1, (int) y + 1) > World.getHeight((int) x, (int) y)){
            colors[colorindex + 4] = color - 0.1f;
            colors[colorindex + 5] = color - 0.1f;
            colors[colorindex + 6] = color - 0.1f;
            colors[colorindex + 7] = alpha;
        } else if (World.getHeight((int) x - 1, (int) y) > World.getHeight((int) x, (int) y)){
            colors[colorindex + 4] = color - 0.05f;
            colors[colorindex + 5] = color - 0.05f;
            colors[colorindex + 6] = color - 0.05f;
            colors[colorindex + 7] = alpha;
        } else if (World.getHeight((int) x, (int) y + 1) > World.getHeight((int) x, (int) y)){
            colors[colorindex + 4] = color - 0.05f;
            colors[colorindex + 5] = color - 0.05f;
            colors[colorindex + 6] = color - 0.05f;
            colors[colorindex + 7] = alpha;
        } else{
            colors[colorindex + 4] = color;
            colors[colorindex + 5] = color;
            colors[colorindex + 6] = color;
            colors[colorindex + 7] = alpha;
        }
        
        if (World.getHeight((int) x - 1, (int) y - 1) > World.getHeight((int) x, (int) y)){
            colors[colorindex + 8] = color - 0.1f;
            colors[colorindex + 9] = color - 0.1f;
            colors[colorindex + 10] = color - 0.1f;
            colors[colorindex + 11] = alpha;
        } else if (World.getHeight((int) x - 1, (int) y) > World.getHeight((int) x, (int) y)){
            colors[colorindex + 8] = color - 0.05f;
            colors[colorindex + 9] = color - 0.05f;
            colors[colorindex + 10] = color - 0.05f;
            colors[colorindex + 11] = alpha;
        } else if (World.getHeight((int) x, (int) y - 1) > World.getHeight((int) x, (int) y)){
            colors[colorindex + 8] = color - 0.05f;
            colors[colorindex + 9] = color - 0.05f;
            colors[colorindex + 10] = color - 0.05f;
            colors[colorindex + 11] = alpha;
        } else{
            colors[colorindex + 8] = color;
            colors[colorindex + 9] = color;
            colors[colorindex + 10] = color;
            colors[colorindex + 11] = alpha;
        }
        
        if (World.getHeight((int) x + 1, (int) y - 1) > World.getHeight((int) x, (int) y)){
            colors[colorindex + 12] = color - 0.1f;
            colors[colorindex + 13] = color - 0.1f;
            colors[colorindex + 14] = color - 0.1f;
            colors[colorindex + 15] = alpha;
        } else if (World.getHeight((int) x + 1, (int) y) > World.getHeight((int) x, (int) y)){
            colors[colorindex + 12] = color - 0.05f;
            colors[colorindex + 13] = color - 0.05f;
            colors[colorindex + 14] = color - 0.05f;
            colors[colorindex + 15] = alpha;
        } else if (World.getHeight((int) x, (int) y - 1) > World.getHeight((int) x, (int) y)){
            colors[colorindex + 12] = color - 0.05f;
            colors[colorindex + 13] = color - 0.05f;
            colors[colorindex + 14] = color - 0.05f;
            colors[colorindex + 15] = alpha;
        } else{
            colors[colorindex + 12] = color;
            colors[colorindex + 13] = color;
            colors[colorindex + 14] = color;
            colors[colorindex + 15] = alpha;
        }*/

        
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

}
