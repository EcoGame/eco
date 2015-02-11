package eco;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Render {
	
	public static float rot;
	
	public static float x = 0;
	public static float y = 0;
	public static float z = 0;
	
	static TextureAtlas atlas;
	static TrueTypeFont font;
	
	public static final float tilesize = 0.2f;

	public static Camera camera = new Camera(-World.mapsize / 4f * tilesize, -2f, -World.mapsize / 4f * tilesize);
	
	public static void initDisplay(){
		try {
			Display.setDisplayMode(new DisplayMode(Main.width, Main.height));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Display.setTitle("Eco");
		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Display.setVSyncEnabled(true);

	}
	
	public static void init() {
	
		//mapseed = System.currentTimeMillis();
		
		World.generate();
		
		while (!World.isValid()) {
			World.generate();
		}
		
		try {
			atlas = new TextureAtlas(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("../assets/textureatlas.png"),  GL_NEAREST));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		atlas.getTexture().bind();
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.0f);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		
       /* GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 3);*/
		
		initFrustrum();
		//initOrtho();
		GL11.glClearColor(152f / 255f, 242f / 255f, 255f / 255f, 1.0f);
		     
	    try {
	        InputStream inputStream = ResourceLoader.getResourceAsStream("../assets/font.ttf");
	         
	        Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
	        awtFont = awtFont.deriveFont(24f); // set font size
	        font = new TrueTypeFont(awtFont, false);
	             
	    } catch (Exception e) {
	        e.printStackTrace();
	    }   
	}
	
	public static void initFrustrum() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(Main.fov / 2f, Main.width / Main.height, 0.1f, 1000f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT,GL_NICEST);
		glEnable(GL_BLEND);
	}
	
	public static void initOrtho() {
		glClearDepth(1);
		glViewport(0,0,Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_MODELVIEW);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		//GL11.glClearColor(0.5f, 0.5f, 0.5f, 1);
	}
	
	public static void draw() {
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		//glRotatef(30f, 1.0f, 0.0f, 0.0f);
		//glTranslatef(x, y, z);
		camera.look();
		
		atlas.texture.bind();
		
		int mapsize = World.mapsize;
		
		float offset = mapsize / 12f;
		glTranslatef(-offset, 0f, -offset);
		glRotatef(rot, 0.0f, 1.0f, 0.0f);
		glTranslatef(offset, 0f, offset);

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (World.structures[x][y] == 1) {
					drawStructure((-x + 1.5f) * tilesize, (-y + 1.5f) * tilesize, 4);
				}
				if (World.structures[x][y] == 2){
					drawStructure((-x + 1.5f) * tilesize, (-y + 1.5f) * tilesize, 5);
				}
				if (World.map[x][y] == 0){
					drawTile(-x * tilesize, -y * tilesize, 0);
				}
				if (World.map[x][y] == 1){
					drawTile(-x * tilesize, -y * tilesize, 1);
				}
				if (World.map[x][y] == 2){
					drawTile(-x * tilesize, -y * tilesize, 3);
				}
			}
		}
		
		for (Message message : World.messages) {
			if (message.time > 0) {
				message.time--;
				drawString(message.message, message.x, message.y);
			}
		}
		
		for (int i = 0; i < World.messages.size(); i++) {
			if (World.messages.get(i).time <= 0){
				World.messages.remove(i);
			}
		}
	}
	
	public static void drawTile(float x, float z, Treble<Float, Float, Float> color) {
		
		glColor3f(color.x, color.y, color.z);
		glBegin(GL_QUADS);
			glVertex3f(x, 1f, z);
			glVertex3f(x + tilesize, 1f, z );
			glVertex3f(x + tilesize, 1f, z + tilesize);
			glVertex3f(x, 1f, z + tilesize);
		glEnd();
		glColor3f(1f, 1f, 1f);
	}
	
	public static void drawTile(float x, float z, int texpos) {
		
		int tex = texpos % 4;
		int tey = texpos / 4;
		
		glBegin(GL_QUADS);
			glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, false));
			glVertex3f(x, 0f, z);
			glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, false));
			glVertex3f(x + tilesize, 0f, z );
			glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, true));
			glVertex3f(x + tilesize, 0f, z + tilesize);
			glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, true));
			glVertex3f(x, 0f, z + tilesize);
		glEnd();
		
		glColor3f(1.0f, 1.0f, 1.0f);
	}
	
	public static void drawStructure(float x, float z, int texpos) {
		glPushMatrix();
		
		float offset = (tilesize / 2);
		
		int tex = texpos % 4;
		int tey = texpos / 4;
		
		glTranslatef(x - offset, 0, z - offset);
		glRotatef(-rot, 0f, 1f, 0f);
		glTranslatef(-x - offset, 0, -z - offset);
		
		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, false));
		glVertex3f(x - offset, offset * 2, z);
		glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, false));
		glVertex3f(x + offset, offset * 2, z);
		glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, true));
		glVertex3f(x + offset, 0f, z);
		glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, true));
		glVertex3f(x - offset, 0f, z);
		glEnd();
		
		glPopMatrix();
	}
	
	public static void drawString(String message, float x, float y) {
		initOrtho();
		font.drawString(x, y, message);
		initFrustrum();
	}

}
