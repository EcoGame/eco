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
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import org.newdawn.slick.Color;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class Render {

	public static float rot;

	public static float x = 0;
	public static float y = 0;
	public static float z = 0;

	public static volatile boolean shouldRender = true;

	static TextureAtlas atlas;
	static TrueTypeFont font;

	public static final float tilesize = 0.2f;

	public static volatile boolean mesh = false;

	public static Camera camera = new Camera(-World.mapsize / 2f * tilesize, -8f, World.mapsize / 2f * tilesize);

	public static volatile int vertex_handle;
	public static volatile int texture_handle;
	public static volatile int structure_vertex_handle;
	public static volatile int structure_texture_handle;
	public static volatile int buffersize;
	public static volatile int structure_buffersize;

	public static float heightConstant = MeshTask.heightConstant;

	public static final float rotSpeed = 0.05f;

	public static boolean multithreading = false;

	public static boolean multiThreadStructures = false;

	public static void initDisplay(){
		try {
			Display.setDisplayMode(new DisplayMode(Main.width, Main.height));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Display.setTitle("Eco "+Main.vn);
		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Display.setVSyncEnabled(true);

	}

	public static void init(){

		Log.setVerbose(false);

		World.generate();


		//while (!World.isValid()){
        //World.generate();
		//}

		try {
			if (Main.isInEclipse){
				atlas = new TextureAtlas(TextureLoader.getTexture("PNG",
                                                                  ResourceLoader.getResourceAsStream("assets/textureatlas.png"),
                                                                  GL_NEAREST));
            }
			else{
				atlas = new TextureAtlas(TextureLoader.getTexture("PNG",
                                                                  ResourceLoader.getResourceAsStream("../assets/textureatlas.png"),
                                                                  GL_NEAREST));
			}
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

		GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        /* GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_NEAREST);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 3);*/

		initFrustrum();
		//initOrtho();
		GL11.glClearColor(152f / 255f, 242f / 255f, 255f / 255f, 1.0f);

	    try {
	    	InputStream inputStream;
	    	if (Main.isInEclipse){
	        	inputStream = ResourceLoader.getResourceAsStream("assets/font.ttf");
	        }
	        else{
	        	inputStream = ResourceLoader.getResourceAsStream("../assets/font.ttf");
	        }

	        Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
	        awtFont = awtFont.deriveFont(16f); // set font size
	        font = new TrueTypeFont(awtFont, true);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		texture_handle = glGenBuffers();
		vertex_handle = glGenBuffers();
		structure_texture_handle = glGenBuffers();
		structure_vertex_handle = glGenBuffers();

		DisplayLists.init();
	}

	public static void initFrustrum(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(Main.fov / 2f, Main.windowwidth / Main.windowheight, 0.1f, 1000f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT,GL_NICEST);
		glEnable(GL_BLEND);
	}

	public static void initOrtho(){
		glClearDepth(1);
		glViewport(0,0,Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_MODELVIEW);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Main.width, Main.height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		//GL11.glClearColor(0.5f, 0.5f, 0.5f, 1);
	}

	public static void draw(){
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		//glRotatef(30f, 1.0f, 0.0f, 0.0f);
		//glTranslatef(x, y, z);

		initFrustrum();

		GL11.glEnable(GL11.GL_DEPTH_TEST);

		camera.look();

		atlas.texture.bind();

		int mapsize = World.mapsize;

		rot += 0.05f;

		float offset = mapsize * tilesize / 2f;
		glTranslatef(-offset, 0f, -offset);
		glRotatef(rot, 0.0f, 1.0f, 0.0f);
		glTranslatef(offset, 0f, offset);


		if (multithreading){
			if (vertex_handle == 0 ||  texture_handle == 0){
				return;
			}
			else{
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_handle);
				GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);

				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texture_handle);
				GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0L);

				GL11.glDrawArrays(GL11.GL_QUADS, 0, buffersize / 3);
			}
			if (structure_vertex_handle == 0 ||  structure_texture_handle == 0){
				return;
			}
			if (multiThreadStructures){
				glPushMatrix();
				glTranslatef(-offset, 0f, -offset);
				glRotatef(-camera.yaw, 0.0f, 1.0f, 0.0f);
				glTranslatef(offset, 0f, offset);
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, structure_vertex_handle);
				GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);

				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, structure_texture_handle);
				GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0L);
				GL11.glDrawArrays(GL11.GL_QUADS, 0, structure_buffersize / 3);
				glPopMatrix();
			}
			else{
				for (int x = 0; x < mapsize; x++){
					for (int y = 0; y < mapsize; y++){
						if (World.structures[x][y] == 1){
							drawStructure((-x + 0.5f) * tilesize,World.noise[x][y] * heightConstant,  (-y + 0.5f) * tilesize, 4);
						}
						if (World.structures[x][y] == 2){
							drawStructure((-x + 0.5f) * tilesize, World.noise[x][y] * heightConstant, (-y + 0.5f) * tilesize, 5);
						}
						if (World.structures[x][y] == 3){
							drawStructure((-x + 0.5f) * tilesize, World.noise[x][y] * heightConstant, (-y + 0.5f) * tilesize, 6);
						}
					}
				}
			}
		}

		atlas.getTexture().bind();
		if (!multithreading){
			GL11.glCallList(DisplayLists.index);
				for (int x = 0; x < mapsize; x++){
					for (int y = 0; y < mapsize; y++){
						if (World.structures[x][y] == 1){
							drawStructure((-x) * tilesize,World.noise[x][y] * heightConstant,  (-y) * tilesize, 4);
						}
						if (World.structures[x][y] == 2){
							drawStructure((-x) * tilesize, World.noise[x][y] * heightConstant, (-y) * tilesize, 5);
						}
						if (World.structures[x][y] == 3){
							drawStructure((-x) * tilesize, World.noise[x][y] * heightConstant, (-y) * tilesize, 6);
						}
					}
				}
		}
		initOrtho();

		GL11.glDisable(GL11.GL_DEPTH_TEST);


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

		atlas.getTexture().bind();
		glBegin(GL_QUADS);
			glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, false));
			glVertex2f(50, 575);
			glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, false));
			glVertex2f(75, 575);
			glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, true));
			glVertex2f(75, 600);
			glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, true));
			glVertex2f(50, 600);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);


		glBegin(GL_QUADS);
			glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(1, false));
			glVertex2f(50, 610);
			glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(1, false));
			glVertex2f(75, 610);
			glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(1, true));
			glVertex2f(75, 635);
			glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(1, true));
			glVertex2f(50, 635);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);

		glBegin(GL_QUADS);
			glTexCoord2f(atlas.getCoord(1, false), atlas.getCoord(1, false));
			glVertex2f(50, 645);
			glTexCoord2f(atlas.getCoord(1, true), atlas.getCoord(1, false));
			glVertex2f(75, 645);
			glTexCoord2f(atlas.getCoord(1, true), atlas.getCoord(1, true));
			glVertex2f(75, 670);
			glTexCoord2f(atlas.getCoord(1, false), atlas.getCoord(1, true));
			glVertex2f(50, 670);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);

		UIManager.render();

		drawString(String.valueOf(Warrior.wPop)+" Warriors", 85, 657);

		drawString(String.valueOf(Farmer.fPop)+" Farmers", 85, 627);

		drawString(String.valueOf(Wheat.tWheat), 85, 587);
		if (Util.getWheatRate() > 0){
			font.drawString(85 + font.getWidth(String.valueOf(Wheat.tWheat+" ")), 587, " ("+Util.getWheatRateForDisplay()+")", Color.green);
		}
		else{
			font.drawString(85 + font.getWidth(String.valueOf(Wheat.tWheat+" ")), 587, " ("+Util.getWheatRateForDisplay()+")", Color.red);
		}

		drawString("Conscription Rate: "+((int) (100 * Main.desiredWarriorRatio))+"%", 285, 657);

		drawString("Frames Per Tick: "+String.valueOf(Main.framesPerTick), 285, 627);

		drawString("Feed Displaced: "+String.valueOf(Main.displacedEat), 585, 657);

		drawString("Favor Warrior Rations: "+String.valueOf(!Main.favorFarmers), 585, 627);

		glPushMatrix();


		for (Message message : World.messages){
			if (message.time > 0){
				message.time--;
				drawString(message.message, message.x, message.y);
			}
		}

		for (int i = 0; i < World.messages.size(); i++){
			if (World.messages.get(i).time <= 0){
				World.messages.remove(i);
			}
		}

		glPopMatrix();
	}

	public static void drawDisplayList(int index, float x, float y, float z, float rot){
		//glPushMatrix();
		glTranslatef(x, y, z);
		GL11.glCallList(index);
		glTranslatef(-x, -y, -z);
		//glPopMatrix();
	}

	public static void drawTile(float x, float z, Treble<Float, Float, Float> color){


		glColor3f(color.x, color.y, color.z);
		glBegin(GL_QUADS);
        glVertex3f(x, 1f, z);
        glVertex3f(x + tilesize, 1f, z );
        glVertex3f(x + tilesize, 1f, z + tilesize);
        glVertex3f(x, 1f, z + tilesize);
		glEnd();
		glColor3f(1f, 1f, 1f);
	}

	public static void drawTile(float x, float z, int texpos){

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

	public static void drawStructure(float x, float z, int texpos){
		glPushMatrix();

		float offset = (tilesize / 2);

		int tex = texpos % 4;
		int tey = texpos / 4;

		glTranslatef(x, 0, z);
		glRotatef(-rot, 0f, 1f, 0f);
		glTranslatef(-x, 0, -z);

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

	public static void drawStructure(float x, float y, float z, int texpos){
		glPushMatrix();

		float offset = (tilesize / 2);

		int tex = texpos % 4;
		int tey = texpos / 4;

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

	public static void drawString(String message, float x, float y){
		font.drawString(x, y, message);
	}

	public static void drawPaused() {

		float centerX = Display.getWidth() / 2f;
		float centerY = Display.getHeight() / 2f;

		float textWidth = font.getWidth("Paused") / 2f;
		float textHeight = font.getHeight("Paused") / 2f;

		Treble<Float, Float, Float> pauseColor = Util.convertColor(new Treble<Float, Float, Float>(186f,179f,178f));

		glDisable(GL11.GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glColor4f(pauseColor.x, pauseColor.y, pauseColor.z, 0.05f);
		glVertex2f(0f, 0f);
		glColor4f(pauseColor.x, pauseColor.y, pauseColor.z, 0.05f);
		glVertex2f(Display.getWidth(), 0f);
		glColor4f(pauseColor.x, pauseColor.y, pauseColor.z, 0.05f);
		glVertex2f(Display.getWidth(), Display.getHeight());
		glColor4f(pauseColor.x, pauseColor.y, pauseColor.z, 0.05f);
		glVertex2f(0f, Display.getHeight());
		glEnd();
		glColor4f(1f, 1f, 1f, 1f);
		glEnable(GL11.GL_TEXTURE_2D);

		glDisable(GL_DEPTH_TEST);
		font.drawString(centerX - textWidth, centerY - textHeight, "Paused");
		glEnable(GL_DEPTH_TEST);

	}

	public static void drawGameOver(String reason) {

		float centerX = Display.getWidth() / 2f;
		float centerY = Display.getHeight() / 2f;

		float textWidth = font.getWidth("Game Over") / 2f;
		float textHeight = font.getHeight("Game Over") / 2f;
		float textWidth2 = font.getWidth(reason) / 2f;

		Treble<Float, Float, Float> pauseColor = Util.convertColor(new Treble<Float, Float, Float>(168f,78f,78f));

		glDisable(GL11.GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glColor4f(pauseColor.x, pauseColor.y, pauseColor.z, 0.15f);
		glVertex2f(0f, 0f);
		glColor4f(pauseColor.x, pauseColor.y, pauseColor.z, 0.15f);
		glVertex2f(Display.getWidth(), 0f);
		glColor4f(pauseColor.x, pauseColor.y, pauseColor.z, 0.15f);
		glVertex2f(Display.getWidth(), Display.getHeight());
		glColor4f(pauseColor.x, pauseColor.y, pauseColor.z, 0.15f);
		glVertex2f(0f, Display.getHeight());
		glEnd();
		glColor4f(1f, 1f, 1f, 1f);
		glEnable(GL11.GL_TEXTURE_2D);

		glDisable(GL_DEPTH_TEST);
		font.drawString(centerX - textWidth, centerY - textHeight, "Game Over");
		font.drawString(centerX - textWidth2, centerY - textHeight + 30, reason);
		glEnable(GL_DEPTH_TEST);

	}

}
