package eco.game;

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
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

/**
 * This class does all the rendering.
 * 
 * @author phil
 * 
 */

public class Render{

	public static float rot;
	public static final float rotSpeed = 0.05f;

	public static volatile boolean shouldRender = true;

	static TextureAtlas atlas;
	static TrueTypeFont font;

	public static final float tilesize = 0.2f;

	public static volatile boolean mesh = false;

	public static Camera camera = new Camera(-World.mapsize / 2f * tilesize,
			-8f, World.mapsize / 2f * tilesize);
	
	public static volatile FloatBuffer vertex = null;
	public static volatile FloatBuffer texture = null;
	public static volatile FloatBuffer colors = null;
	public static volatile int buffersize;

	public static boolean overhead = false;

	public static float heightConstant = 0.025f * (World.mapsize / 64);

	public static boolean preferMultiThreading = true;
	public static boolean multithreading = true;
	public static boolean multiThreadStructures = false;
	
	public static final Object lock = new Object();
	
	public static final RandTexture treeTexture = new RandTexture();
	public static final RandTexture smallHouseTexture = new RandTexture();
	public static final RandTexture bigHouseTexture = new RandTexture();
    public static final RandTexture smallCastleTexture = new RandTexture();
    public static final RandTexture bigCastleTexture = new RandTexture();
    
    private static final int minTPS = 1;

	/* Main draw function */
	public static void draw() {
		/* Gets ready to draw */
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		initFrustrum();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		if (preferMultiThreading && Main.framesPerTick <= minTPS && multithreading){
			System.out.println("###########");
			System.out.println("# WARNING #");
			System.out.println("###########");
			System.out.println("TPS is too low for safe multithreading!");
			System.out.println("Switching to display lists.....");
			multithreading = false;
		} else if (preferMultiThreading && Main.framesPerTick > minTPS && !multithreading){
			multithreading = true;
		}
		
		if (!preferMultiThreading){
			multithreading = false;
		}

		/* Look through the camera */
		camera.look();

		/* Bind the textureatlas */
		atlas.bind();

		int mapsize = World.mapsize;
		rot += 0.05f;
		float offset = mapsize * tilesize / 2f;
		glTranslatef(-offset, 0f, -offset);
		glRotatef(rot, 0.0f, 1.0f, 0.0f);
		glTranslatef(offset, 0f, offset);

		/* Array rendering */
		synchronized(lock){
			if (multithreading) {
				if (vertex != null && texture != null && colors != null){
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
		}

		/* DisplayList rendering */
		if (!multithreading) {
			/* Call the display list */
			GL11.glCallList(DisplayLists.getIndex());
		}
		
		/* Render structures */
		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (World.structures[x][y] == 1) {
					try {
						World.cities.get(new Point(x, y)).updatePop(
								(int) World.popdensity[x][y]);
						drawCity((-x) * tilesize, World.noise[x][y]
								* heightConstant, (-y) * tilesize, 1,
								World.cities.get(new Point(x, y)));
					} catch (Exception e) {
						World.cities.put(new Point(x, y), new City(
								new Point(x, y), false));
					}
				}
				if (World.structures[x][y] == 2) {
					try {
						World.cities.get(new Point(x, y)).updatePop(
								(int) World.popdensity[x][y]);
						drawCity((-x) * tilesize, World.noise[x][y]
								* heightConstant, (-y) * tilesize, 2,
								World.cities.get(new Point(x, y)));
					} catch (Exception e) {
						World.cities.put(new Point(x, y), new City(
								new Point(x, y), true));
					}
				}
				if (World.structures[x][y] == 3) {
					drawStructure((-x) * tilesize, World.noise[x][y]
							* heightConstant, (-y) * tilesize, treeTexture.sample(x, y));
				}
				if (World.decorations[x][y] == 1){
					drawStructure((-x) * tilesize, 48
							* heightConstant, (-y) * tilesize, 12);
				}
				if (World.decorations[x][y] == 2){
					//drawStructure((-x) * tilesize, (Math.max(48,World.noise[x][y] + 20))
						//	* heightConstant, (-y) * tilesize, 13); //Cloud drawing
				}
				if (World.decorations[x][y] == 3){
					drawStructure((-x) * tilesize, World.noise[x][y]
							* heightConstant, (-y) * tilesize, 14);
				}
				if (World.decorations[x][y] == 4){
					drawStructure((-x) * tilesize, World.noise[x][y]
							* heightConstant, (-y) * tilesize, 15);
				}

			}
		}

		/* Draw city names */
		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				try {
					if (World.structures[x][y] == 1) {
						drawCityName((-x) * tilesize, World.noise[x][y]
								* heightConstant, (-y) * tilesize,
								World.cities.get(new Point(x, y)));
					}
					if (World.structures[x][y] == 2) {
						drawCityName((-x) * tilesize, World.noise[x][y]
								* heightConstant, (-y) * tilesize,
								World.cities.get(new Point(x, y)));
					}
				} catch (Exception e) {
				}
			}
		}

		/* Gets ready for orthogonal drawing */
		initOrtho();

		GL11.glDisable(GL11.GL_DEPTH_TEST);

		/* Draw the menu bar */
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		glColor3f(169f / 255f, 145f / 255f, 126f / 255f);
		glBegin(GL_QUADS);
		glVertex2f(0, Main.height);
		glVertex2f(Main.width, Main.height);
		glVertex2f(Main.width, 6f * Main.height / 8f);
		glVertex2f(0, 6f * Main.height / 8f);
		glEnd();
		glColor3f(1f, 1f, 1f);

		glColor3f(157f / 255f, 130f / 255f, 117f / 255f);
		glBegin(GL_QUADS);
		glVertex2f(10, Main.height - 00);
		glVertex2f(Main.width - 10, Main.height - 00);
		glVertex2f(Main.width - 10, (6f * Main.height / 8f) + 10);
		glVertex2f(10, (6f * Main.height / 8f) + 10);
		glEnd();
		glColor3f(1f, 1f, 1f);

		glColor3f(169f / 255f, 145f / 255f, 126f / 255f);
		glBegin(GL_QUADS);
		glVertex2f(0, (6f * Main.height / 8f));
		glVertex2f(20, (6f * Main.height / 8f));
		glVertex2f(20, (6f * Main.height / 8f) + 20);
		glVertex2f(0, (6f * Main.height / 8f) + 20);
		glEnd();
		glColor3f(1f, 1f, 1f);

		glColor3f(169f / 255f, 145f / 255f, 126f / 255f);
		glBegin(GL_QUADS);
		glVertex2f(Main.width, (6f * Main.height / 8f));
		glVertex2f(Main.width - 20, (6f * Main.height / 8f));
		glVertex2f(Main.width - 20, (6f * Main.height / 8f) + 20);
		glVertex2f(Main.width, (6f * Main.height / 8f) + 20);
		glEnd();
		glColor3f(1f, 1f, 1f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		UIManager.render();
		UIManager.render2();
		
		/* Draw all the messages */
		for (Message message : Message.getMessages()) {
			Render.drawString(message.getMessage(), message.getX(), message.getY());
		}
	}

	/* Draw the main menu */
	public static void drawMainMenu() {
		/* Get ready to draw */
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		initOrtho();

		/* Colors */
		Treble<Float, Float, Float> backColor = Util
				.convertColor(new Treble<Float, Float, Float>(157f, 130f, 117f));
		Treble<Float, Float, Float> borderColor = Util
				.convertColor(new Treble<Float, Float, Float>(169f, 145f, 126f));

		/* Draw the background */
		glDisable(GL11.GL_TEXTURE_2D);
		glColor3f(borderColor.getX(), borderColor.getY(), borderColor.getZ());
		glBegin(GL_QUADS);
		glVertex2f(0, 0);
		glVertex2f(0, Main.height);
		glVertex2f(Main.width, Main.height);
		glVertex2f(Main.width, 0);
		glEnd();
		glColor3f(backColor.getX(), backColor.getY(), backColor.getZ());
		glBegin(GL_QUADS);
		glVertex2f(10, 10);
		glVertex2f(10, Main.height - 10);
		glVertex2f(Main.width - 10, Main.height - 10);
		glVertex2f(Main.width - 10, 10);
		glEnd();
		glColor3f(borderColor.getX(), borderColor.getY(), borderColor.getZ());
		glBegin(GL_QUADS);
		glVertex2f(0, 0);
		glVertex2f(20, 0);
		glVertex2f(20, 20);
		glVertex2f(0, 20);
		glVertex2f(Main.width, 0);
		glVertex2f(Main.width - 20, 0);
		glVertex2f(Main.width - 20, 20);
		glVertex2f(Main.width, 20);
		glVertex2f(0, Main.height);
		glVertex2f(20, Main.height);
		glVertex2f(20, Main.height - 20);
		glVertex2f(0, Main.height - 20);
		glVertex2f(Main.width, Main.height);
		glVertex2f(Main.width - 20, Main.height);
		glVertex2f(Main.width - 20, Main.height - 20);
		glVertex2f(Main.width, Main.height - 20);
		glEnd();
		glColor3f(1f, 1f, 1f);
		glEnable(GL11.GL_TEXTURE_2D);

		float centx = Main.width / 2f;
		float size = 128;
		float pad = 0;

		/* Bind the textures */
		atlas.getTexture().bind();

		/* Draw the title */
		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(4, false));
		glVertex2f(centx - (size / 2) - size - pad, 30);
		glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(4, false));
		glVertex2f(centx + (size / 2) - size - pad, 30);
		glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(4, true));
		glVertex2f(centx + (size / 2) - size - pad, 30 + size);
		glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(4, true));
		glVertex2f(centx - (size / 2) - size - pad, 30 + size);
		glTexCoord2f(atlas.getCoord(1, false), atlas.getCoord(4, false));
		glVertex2f(centx - (size / 2), 30);
		glTexCoord2f(atlas.getCoord(1, true), atlas.getCoord(4, false));
		glVertex2f(centx + (size / 2), 30);
		glTexCoord2f(atlas.getCoord(1, true), atlas.getCoord(4, true));
		glVertex2f(centx + (size / 2), 30 + size);
		glTexCoord2f(atlas.getCoord(1, false), atlas.getCoord(4, true));
		glVertex2f(centx - (size / 2), 30 + size);
		glTexCoord2f(atlas.getCoord(2, false), atlas.getCoord(4, false));
		glVertex2f(centx - (size / 2) + size + pad, 30);
		glTexCoord2f(atlas.getCoord(2, true), atlas.getCoord(4, false));
		glVertex2f(centx + (size / 2) + size + pad, 30);
		glTexCoord2f(atlas.getCoord(2, true), atlas.getCoord(4, true));
		glVertex2f(centx + (size / 2) + size + pad, 30 + size);
		glTexCoord2f(atlas.getCoord(2, false), atlas.getCoord(4, true));
		glVertex2f(centx - (size / 2) + size + pad, 30 + size);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);

		/* Draw the buttons */
		UIManager.renderMenu();
		

		/* Draw all the text */
		UIManager.renderMenu2();
		drawString("Generation Settings", ((Main.width / 2) + 280), 224);
		
		/* Splash Text */
		font.drawString(15, 15, "Version "+Main.vn, new Color(200, 200, 200));

		String splash = SplashText.getSplash();
		centx = (Main.width - font.getWidth(splash)) / 2f;
		
		font.drawString(centx, 690, splash, new Color(147,206,239));

		/* Draw all the messages */
		for (Message message : Message.getMessages()) {
			Render.drawString(message.getMessage(), message.getX(), message.getY());
		}

	}

	/* Draw a structure that faces the camera */
	public static void drawStructure(float x, float y, float z, int texpos) {
		glPushMatrix();

		float offset = (tilesize / 2);

		int tex = texpos % 8;
		int tey = texpos / 8;

		glTranslatef(x, 0, z);
		glRotatef(-rot, 0f, 1f, 0f);
		glTranslatef(-x, 0, -z);

		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, false));
		glVertex3f(x - offset, y + offset * 2, z);
		glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, false));
		glVertex3f(x + offset, y + offset * 2, z);
		glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, true));
		glVertex3f(x + offset, y, z);
		glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, true));
		glVertex3f(x - offset, y, z);
		glEnd();

		glPopMatrix();
	}
	
	/* Draw a structure that faces the camera */
	public static void drawStructure(float x, float y, float z, Point texture) {
		glPushMatrix();

		float offset = (tilesize / 2);

		int tex = texture.getX();
		int tey = texture.getY();

		glTranslatef(x, 0, z);
		glRotatef(-rot, 0f, 1f, 0f);
		glTranslatef(-x, 0, -z);

		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, false));
		glVertex3f(x - offset, y + offset * 2, z);
		glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, false));
		glVertex3f(x + offset, y + offset * 2, z);
		glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, true));
		glVertex3f(x + offset, y, z);
		glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, true));
		glVertex3f(x - offset, y, z);
		glEnd();

		glPopMatrix();
	}

	/* Draw a city structure */
	public static void drawCity(float x, float y, float z, int type, City city) {
		glPushMatrix();


		float offset = (tilesize / 8);
		
		Point smalltex = new Point(0, 0);
		Point bigtex = new Point(0, 1);

		int tex = smalltex.getX();
		int tey = smalltex.getY();
		int tex2 = bigtex.getX();
		int tey2 = bigtex.getY();
		
		int texposr = 15;
		int texr = texposr % 8;
		int teyr = texposr / 8;

		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < 4; k++) {
				float tempx = x + ((i * offset) * 2.0f) - (offset * 3.0f);
				float tempz = z + ((k * offset) * 2.0f) - (offset * 3.0f);
				float centx = x + ((i * offset) * 2.0f) - (offset * 3.0f);
				float centz = z + ((k * offset) * 2.0f) - (offset * 3.0f);
				if (type == 1){
					smalltex = smallHouseTexture.sample((int) tempx * 1, (int) tempz * 1);
					bigtex = bigHouseTexture.sample((int) tempx * 1, (int) tempz * 1);
					tex = smalltex.getX();
					tey = smalltex.getY();
					tex2 = bigtex.getX();
					tey2 = bigtex.getY();
				}
                if (type == 2){
					smalltex = smallCastleTexture.sample((int) tempx * 1, (int) tempz * 1);
					bigtex = bigCastleTexture.sample((int) tempx * 1, (int) tempz * 1);
					tex = smalltex.getX();
					tey = smalltex.getY();
					tex2 = bigtex.getX();
					tey2 = bigtex.getY();
				}
				glPushMatrix();
				glTranslatef(centx, 0, centz);
				glRotatef(-rot, 0f, 1f, 0f);
				glTranslatef(-centx, 0, -centz);
				if (city.getBuilding(i, k) == 1) {
					glBegin(GL_QUADS);
					glTexCoord2f(atlas.getCoord(tex, false),
							atlas.getCoord(tey, false));
					glVertex3f(tempx - offset, y + offset * 4, tempz);
					glTexCoord2f(atlas.getCoord(tex, true),
							atlas.getCoord(tey, false));
					glVertex3f(tempx + offset, y + offset * 4, tempz);
					glTexCoord2f(atlas.getCoord(tex, true),
							atlas.getCoord(tey, true));
					glVertex3f(tempx + offset, y, tempz);
					glTexCoord2f(atlas.getCoord(tex, false),
							atlas.getCoord(tey, true));
					glVertex3f(tempx - offset, y, tempz);
					glEnd();
				} else if (city.getBuilding(i, k) == 2) {
					glBegin(GL_QUADS);
					glTexCoord2f(atlas.getCoord(tex2, false),
							atlas.getCoord(tey2, false));
					glVertex3f(tempx - offset, y + offset * 4, tempz);
					glTexCoord2f(atlas.getCoord(tex2, true),
							atlas.getCoord(tey2, false));
					glVertex3f(tempx + offset, y + offset * 4, tempz);
					glTexCoord2f(atlas.getCoord(tex2, true),
							atlas.getCoord(tey2, true));
					glVertex3f(tempx + offset, y, tempz);
					glTexCoord2f(atlas.getCoord(tex2, false),
							atlas.getCoord(tey2, true));
					glVertex3f(tempx - offset, y, tempz);
					glEnd();
				}
				else if (city.getBuilding(i, k) == -1){
					glBegin(GL_QUADS);
					glTexCoord2f(atlas.getCoord(texr, false),
							atlas.getCoord(teyr, false));
					glVertex3f(tempx - offset, y + offset * 4, tempz);
					glTexCoord2f(atlas.getCoord(texr, true),
							atlas.getCoord(teyr, false));
					glVertex3f(tempx + offset, y + offset * 4, tempz);
					glTexCoord2f(atlas.getCoord(texr, true),
							atlas.getCoord(teyr, true));
					glVertex3f(tempx + offset, y, tempz);
					glTexCoord2f(atlas.getCoord(texr, false),
							atlas.getCoord(teyr, true));
					glVertex3f(tempx - offset, y, tempz);
					glEnd();
				}
				glPopMatrix();
			}
		}

		//glPopMatrix();
	}

	/* Draw a city nameplate */
	public static void drawCityName(float x, float y, float z, City city) {
		String name = city.getName();
		if (name.equals("")) {
			return;
		}
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		glPushMatrix();
		int width = font.getWidth(city.getName());
		width /= 2f;
		int height = font.getHeight(city.getName());
		height /= 2;
		float size = 0.005f;
		glTranslatef(0, 0, z);
		glScalef(size, -size, size);
		float xpos = (x * (1f / size)) - 0;
		float ypos = y * (-1f / size) - 50;
		glTranslatef(xpos, ypos, 0);
		glRotatef(-rot, 0f, 1f, 0f);
		glTranslatef(-xpos, -ypos, 0);
		if (city.getName().contains(City.capitalEpithet)) {
			glColor3f(173f / 255f, 93f / 255f, 93f / 255f);
			font.drawString((x * (1f / size)) - width, y * (-1f / size) - 50,
					city.getName(), new Color(245f / 255f, 63f / 255f,
							63f / 255f, 2f));
		} else {
			font.drawString((x * (1f / size)) - width, y * (-1f / size) - 50,
					city.getName(), new Color(1f, 1f, 1f, 2f));
		}
		glDisable(GL11.GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glTranslatef(0, 0, z - 1);
		glColor4f(0f, 0f, 0f, 0.25f);
		glVertex2f(xpos - width, ypos - height + 6);
		glVertex2f(xpos + width, ypos - height + 6);
		glVertex2f(xpos + width, ypos + height + 6);
		glVertex2f(xpos - width, ypos + height + 6);
		glEnd();
		glColor4f(1f, 1f, 1f, 1f);
		glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		glPopMatrix();
	}

	/* Draw a string */
	public static void drawString(String message, float x, float y) {
		font.drawString(x, y, message);
	}

	/* Draw the paused overlay */
	public static void drawPaused() {
		glLoadIdentity();
		initOrtho();

		Treble<Float, Float, Float> pauseColor = Util
				.convertColor(new Treble<Float, Float, Float>(186f, 179f, 178f));

		glDisable(GL11.GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glColor4f(pauseColor.getX(), pauseColor.getY(), pauseColor.getZ(),
				0.05f);
		glVertex2f(0f, 0f);
		glVertex2f(Display.getWidth(), 0f);
		glVertex2f(Display.getWidth(), Display.getHeight());
		glVertex2f(0f, Display.getHeight());
		glEnd();
		glColor4f(1f, 1f, 1f, 1f);
		glEnable(GL11.GL_TEXTURE_2D);

		atlas.bind();
		
		glDisable(GL_DEPTH_TEST);

		glEnable(GL_DEPTH_TEST);

	}

	/* Draw the gameover overlay */
	public static void drawGameOver() {
		Treble<Float, Float, Float> pauseColor = Util
				.convertColor(new Treble<Float, Float, Float>(168f, 78f, 78f));

		glDisable(GL11.GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glColor4f(pauseColor.getX(), pauseColor.getY(), pauseColor.getZ(),
				0.15f);
		glVertex2f(0f, 0f);
		glVertex2f(Display.getWidth(), 0f);
		glVertex2f(Display.getWidth(), Display.getHeight());
		glVertex2f(0f, Display.getHeight());
		glEnd();
		glColor4f(1f, 1f, 1f, 1f);
		glEnable(GL11.GL_TEXTURE_2D);
		
		atlas.bind();

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

	/* Creates the openGl context */
	public static void init() {
		Log.setVerbose(false);
		/* Creates textureatlas */
		try {
			if (Main.isInEclipse) {
				atlas = new TextureAtlas(
						TextureLoader.getTexture(
								"PNG",
								ResourceLoader
										.getResourceAsStream("assets/textureatlas.png"),
								GL_NEAREST));
			} else {
				atlas = new TextureAtlas(
						TextureLoader.getTexture(
								"PNG",
								ResourceLoader
										.getResourceAsStream("../assets/textureatlas.png"),
								GL_NEAREST));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/* Creates the icon */
		try {
			/*
			 * Display.setIcon(new ByteBuffer[] { new ImageIOImageData()
			 * .imageToByteBuffer( ImageIO.read(new File("../assets/icon.png")),
			 * false, false, null) });
			 */
		} catch (Exception e) {
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

		GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
				GL11.GL_REPEAT);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_REPEAT);

		initFrustrum();

		GL11.glClearColor(152f / 255f, 242f / 255f, 255f / 255f, 1.0f);

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
			e.printStackTrace();
		}


		treeTexture.addTexture(new Point(2, 1));
		treeTexture.addTexture(new Point(5, 4));
		
		smallHouseTexture.addTexture(new Point(0, 1));
		smallHouseTexture.addTexture(new Point(1, 6));
		smallHouseTexture.addTexture(new Point(4, 6));
		smallHouseTexture.addTexture(new Point(5, 6));
		smallHouseTexture.addTexture(new Point(6, 6));
		smallHouseTexture.addTexture(new Point(7, 6));
		smallHouseTexture.addTexture(new Point(1, 7));
		smallHouseTexture.addTexture(new Point(4, 7));
		smallHouseTexture.addTexture(new Point(5, 7));
		smallHouseTexture.addTexture(new Point(6, 7));
		smallHouseTexture.addTexture(new Point(7, 7));
		
		bigHouseTexture.addTexture(new Point(3, 1));
		bigHouseTexture.addTexture(new Point(4, 3));
		bigHouseTexture.addTexture(new Point(5, 3));
		bigHouseTexture.addTexture(new Point(2, 5));
		bigHouseTexture.addTexture(new Point(3, 5));
		bigHouseTexture.addTexture(new Point(0, 6));
		bigHouseTexture.addTexture(new Point(2, 6));
		bigHouseTexture.addTexture(new Point(3, 6));
		bigHouseTexture.addTexture(new Point(0, 7));
		bigHouseTexture.addTexture(new Point(2, 7));
		bigHouseTexture.addTexture(new Point(3, 7));
        
        smallCastleTexture.addTexture(new Point(1, 1));
        smallCastleTexture.addTexture(new Point(4, 4));
        
        bigCastleTexture.addTexture(new Point(1, 3));
        bigCastleTexture.addTexture(new Point(3, 4));
		
		DisplayLists.init();
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

}
