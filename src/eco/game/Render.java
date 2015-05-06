package dizigma.dizquest;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.SharedDrawable;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class Render {

	public static int width = 1280;
	public static int height = 720;
	
	private static int fpsGoal = 60;
	
	private static TrueTypeFont font;
	public static TextureAtlas atlas;
	
	private static Treble<Float, Float, Float> backgroundColor;
	
	private static boolean debug = false;
	
	private static int fov = 90;
	
	private static int listStart;
	private static int lists = 10000;
	private static ArrayList<Integer> freeLists = new ArrayList<Integer>();
	
	public static float heightConstant = 3.25f;
	public static float tilesize = 32f;
	
	public static void draw(Camera camera, World world){
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		
		initFrustrum();
		camera.look();
		
		atlas.texture.bind();
		
		glPushMatrix();
		glScalef(16f, 16f, 16f);

		
		Dizquest.player.update();
		
		for (Chunk c : world.getChunks()){
			c.render();
		}
		
		Treble<Float, Float, Float> raycast = RayCast.cast(camera.position, camera.getHeading());
		Treble<Float, Float, Float> position = camera.position;
		
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.4f, 0.4f, 0.4f);
		//GL11.glVertex3f(position.x, position.y, position.z);
		GL11.glVertex3f(0, 0, 0);
		GL11.glVertex3f(-1000, -1000, -1000);
		//GL11.glVertex3f(raycast.x, raycast.y, raycast.z);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnd();

		glPopMatrix();
		
		initOrtho();
		drawDebug(camera);
		
		FPSCounter.tick();
		Display.update();
		Display.sync(fpsGoal);
	}
	
	
	public static void drawDebug(Camera camera){
		font.drawString(10, 10, "FPS: "+FPSCounter.getFPS());
		if (debug){
			font.drawString(10, 30, camera.position.toString());
			font.drawString(10, 50, "<"+Dizquest.player.velocity.x+","+Dizquest.player.velocity.y+","+Dizquest.player.velocity.z+">");
		}
	}
	
	public static void toggleDebug(){
		debug ^= true;
	}
	
	public static int getFreeDisplaylist(){
		if (freeLists.size() == 0){
			Logger.severe("No more displaylists are free!");
			return -1;
		}
		int list = freeLists.get(0);
		freeLists.remove(0);
		return list;
	}
	
	public static void initDisplay(){
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			Logger.severe("Failed to create the display!");
			e.printStackTrace();
			return;
		}
		Display.setResizable(true);
		Display.setVSyncEnabled(true);
		Display.setTitle("Dizquest "+Dizquest.vn);
		try {
			ThreadManager.drawable = new SharedDrawable(Display.getDrawable());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Logger.success("Created the display");
	}
	
	public static void initGL() {
		Log.setVerbose(false);
		try {
			InputStream inputStream = ResourceLoader
							.getResourceAsStream("assets/font.ttf");
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(16f);
			font = new TrueTypeFont(awtFont, false);
			Logger.success("Loaded font");
		} catch (Exception e) {
			Logger.severe("Failed to load font! Is the assets directory intact?");
			return;
		}
		try {
			atlas = new TextureAtlas(TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("assets/atlas.png"),
					GL_NEAREST));
			Logger.success("Loaded texture atlas");
		} catch (Exception e) {
			Logger.severe("Failed to load texture atlas! Is the assets directory intact?");
			return;
		}
		backgroundColor = Utils.convertColor(new Treble<Float, Float, Float>(171f, 219f, 255f));
		

		glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z,
				1.0f);
	     glClearDepth(1f); 
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.0f);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.0000000000000000f);
		glDisable(GL_CULL_FACE);
		glDisable(GL_LIGHTING);
		
		glEnableClientState(GL_COLOR_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glEnableClientState(GL_VERTEX_ARRAY);
		
		listStart = GL11.glGenLists(lists);
		if (listStart == 0) {
			Logger.severe("Error in display list initiation!");
			return;
		}
		else{
			Logger.success("Allocated "+lists+" displaylists");
			for (int i = listStart; i < lists + listStart; i++){
				freeLists.add(i);
			}
		}
		Logger.success("Initiated openGL");
	}
	
	public static void initFrustrum() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(fov / 2f, (float) Display.getWidth()
				/ (float) Display.getHeight(), 1.0f, 10000f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	}

	public static void initOrtho() {
		glClearDepth(1f);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_MODELVIEW);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 100, -100);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
}
