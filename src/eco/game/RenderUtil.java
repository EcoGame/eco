package eco.game;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

/**
 * A class that holds some utility methods used by <i>Render</i>
 * Mostly just to keep other classes free of clutter
 *
 * @author phil
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
        if (!file.mkdirs()) {
            Log.warning("Failed to create screenshots directory!");
        }
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

    public static ArrayList<Chunk> getDirtyChunks() {
        ArrayList<Chunk> chunks = World.getAllChunks();
        ArrayList<Chunk> dirty = new ArrayList<>();
        for (Chunk c : chunks) {
            if (c.isDirty()) {
                dirty.add(c);
            }
        }
        return dirty;
    }

    public static ArrayList<Chunk> getDirtyStructureChunks(){
        ArrayList<Chunk> chunks = World.getAllChunks();
        ArrayList<Chunk> dirty = new ArrayList<>();
        for (Chunk c : chunks){
            if (c.isDirtyStructure()){
                dirty.add(c);
            }
        }
        return dirty;
    }

    /* Creates the openGl context */
    public static void init() {
        loadResources();

        Render.atlas.getTexture().bind();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_ALPHA_TEST);
        glAlphaFunc(GL_GREATER, 0.0f);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_REPEAT);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_REPEAT);

        initFrustrum();

        setDefaultClearColor();

    }

    private static void loadResources() {

        org.newdawn.slick.util.Log.setVerbose(false);
        /* Creates textureatlas */
        try {
            if (Main.isInEclipse) {
                Render.atlas = new TextureAtlas(
                        TextureLoader.getTexture(
                                "PNG",
                                ResourceLoader
                                        .getResourceAsStream("assets/textureatlas.png"),
                                GL_NEAREST));
            } else {
                Render.atlas = new TextureAtlas(
                        TextureLoader.getTexture(
                                "PNG",
                                ResourceLoader
                                        .getResourceAsStream("../assets/textureatlas.png"),
                                GL_NEAREST));
            }
        } catch (IOException e) {
            Log.severe("Could not create texture atlas!");
            Log.severe("Aborting!");
            e.printStackTrace();
            Util.quit(1);
        }
        Log.success("Created texture atlas");

        /* Creates the font */
        try {
            InputStream inputStream;
            if (Main.isInEclipse) {
                inputStream = ResourceLoader
                        .getResourceAsStream("assets/font.ttf");
            } else {
                inputStream = ResourceLoader
                        .getResourceAsStream("../assets/font.ttf");
            }
            Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont = awtFont.deriveFont(16f); // set font size
            font = new TrueTypeFont(awtFont, true);
        } catch (Exception e) {
            Log.severe("Could not create font!");
            Log.severe("Aborting!");
            e.printStackTrace();
            Util.quit(1);
        }
        Log.success("Created font");

        Render.treeTexture.addTexture(new Point(2, 1));
        Render.treeTexture.addTexture(new Point(5, 4));

        Render.smallHouseTexture.addTexture(new Point(0, 1));
        Render.smallHouseTexture.addTexture(new Point(1, 6));
        Render.smallHouseTexture.addTexture(new Point(4, 6));
        Render.smallHouseTexture.addTexture(new Point(5, 6));
        Render.smallHouseTexture.addTexture(new Point(6, 6));
        Render.smallHouseTexture.addTexture(new Point(7, 6));
        Render.smallHouseTexture.addTexture(new Point(1, 7));
        Render.smallHouseTexture.addTexture(new Point(4, 7));
        Render.smallHouseTexture.addTexture(new Point(5, 7));
        Render.smallHouseTexture.addTexture(new Point(6, 7));
        Render.smallHouseTexture.addTexture(new Point(7, 7));

        Render.bigHouseTexture.addTexture(new Point(3, 1));
        Render.bigHouseTexture.addTexture(new Point(4, 3));
        Render.bigHouseTexture.addTexture(new Point(5, 3));
        Render.bigHouseTexture.addTexture(new Point(2, 5));
        Render.bigHouseTexture.addTexture(new Point(3, 5));
        Render.bigHouseTexture.addTexture(new Point(0, 6));
        Render.bigHouseTexture.addTexture(new Point(2, 6));
        Render.bigHouseTexture.addTexture(new Point(3, 6));
        Render.bigHouseTexture.addTexture(new Point(0, 7));
        Render.bigHouseTexture.addTexture(new Point(2, 7));
        Render.bigHouseTexture.addTexture(new Point(3, 7));

        Render.smallCastleTexture.addTexture(new Point(1, 1));
        Render.smallCastleTexture.addTexture(new Point(4, 4));

        Render.bigCastleTexture.addTexture(new Point(1, 3));
        Render.bigCastleTexture.addTexture(new Point(3, 4));

        org.newdawn.slick.util.Log.info("Added " + RandTexture.count + " random textures");

        /* Creates the icon */
        File icon128 = new File("../assets/icon128.png");
        File icon32 = new File("../assets/icon32.png");
        File icon16 = new File("../assets/icon16.png");
        if (Main.isInEclipse) {
            icon128 = new File("assets/icon128.png");
            icon32 = new File("assets/icon32.png");
            icon16 = new File("assets/icon16.png");
        }
        try {
            Display.setIcon(new ByteBuffer[]{
                    new ImageIOImageData().imageToByteBuffer(ImageIO.read(icon128), false, false, null),
                    new ImageIOImageData().imageToByteBuffer(ImageIO.read(icon32), false, false, null),
                    new ImageIOImageData().imageToByteBuffer(ImageIO.read(icon16), false, false, null)
            });
            Log.success("Loaded icon");
        } catch (Exception e) {
            Log.warning("Failed to load icon!");
            e.printStackTrace();
        }

    }

    public static void setClearColor(int r, int g, int b) {
        glClearColor(r / 255f, g / 255f, b / 255f, 1f);
    }

    public static void setDefaultClearColor() {
        GL11.glClearColor(152f / 255f, 242f / 255f, 255f / 255f, 1.0f);
    }

    public static void prepDraw() {
        glClear(GL11.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static Treble<Float, Float, Float> getColor(int r, int g, int b){
        return new Treble<>(r / 255f, g / 255f, b / 255f);
    }

    public static Treble<Float, Float, Float> invertColor(Treble<Float, Float, Float> color){
        return new Treble<>(1f - color.x, 1f - color.y, 1f - color.z);
    }
}
