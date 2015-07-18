package eco.game;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.TrueTypeFont;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * A class that holds some utility methods used by <i>Render</i>
 * Mostly just to keep other classes free of clutter
 *
 * @author phil
 *
 */
public class RenderUtil {
    static TrueTypeFont font;

    public static Treble<Float, Float, Float> convertColor(
            Treble<Float, Float, Float> base) {
        return new Treble<>(base.x / 255f, base.y / 255f,
                base.z / 255f);
    }

    public static void applyRandomColorNoise(int x, int y) {
        NoiseSampler.initSimplexNoise((int) World.mapseed);
        NoiseSampler.setNoiseScale(World.mapsize / 32);
        float noise = NoiseSampler.getNoise(x, y) / 6f;
        GL11.glColor3f(1f - noise, 1f - noise, 1f - noise);
    }

    public static float getRandomColorNoise(int x, int y) {
        NoiseSampler.initSimplexNoise((int) World.mapseed);
        NoiseSampler.setNoiseScale(World.mapsize / 32);
        float noise = NoiseSampler.getNoise(x, y) / 6f;
        return 1f - noise;
    }

    /* Draw a string */
    public static void drawString(String message, float x, float y) {
        font.drawString(x, y, message);
    }

    /* Creates the display */
    public static void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(Main.width, Main.height));
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        Display.setTitle("Eco " + Main.vn);
        try {
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        Display.setVSyncEnabled(true);
    }

    /* Creates a frustrum projection */
    public static void initFrustrum() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(Main.fov / 2f, Main.windowwidth / Main.windowheight,
                0.1f, 100000000000000f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_ALPHA_TEST);
        glDepthFunc(GL_LEQUAL);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glEnable(GL_BLEND);
        glDisable(GL11.GL_CULL_FACE);
        glDepthFunc(GL_LEQUAL);
    }

    /* Creates an orthogonal projection */
    public static void initOrtho() {
        glClearDepth(1);
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        glMatrixMode(GL_MODELVIEW);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Main.width, Main.height, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public static void takeScreenshot() {
        glReadBuffer(GL_FRONT);
        int width = Display.getDisplayMode().getWidth();
        int height = Display.getDisplayMode().getHeight();
        int bpp = 4;
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
        glReadPixels(0, 0, width, height, GL_RGBA,
                GL_UNSIGNED_BYTE, buffer);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        File file = new File("../screenshots/" + dateFormat.format(date));
        file.mkdirs();
        String format = "PNG";
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int i = (x + (width * y)) * bpp;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16)
                        | (g << 8) | b);
            }
        }

        try {
            ImageIO.write(image, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Chunk> getDirtyChunks(){
        ArrayList<Chunk> chunks = World.getAllChunks();
        ArrayList<Chunk> dirty = new ArrayList<>();
        for (Chunk c : chunks){
            if (c.isDirty()){
                dirty.add(c);
            }
        }
        return dirty;
    }
}
