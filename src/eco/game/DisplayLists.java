package eco.game;

import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.opengl.GL11;

/**
 * A class that contains methods that create display lists for faster rendering
 * than immediate mode. <i>init()</i> must be called for proper use. Calling
 * <i>mesh</i> will compile and execute the displaylist, so do not use
 * <i>glCallList()</i> and <i>mesh</i> in the same frame.
 * 
 * @author phil
 * 
 */

public class DisplayLists {

	private static final int indexes = 1;

	private static final float offset = Render.tilesize / 2f;
	private static final float tilesize = Render.tilesize;

	private static int index;

	public static void init() {
		index = GL11.glGenLists(indexes);
		if (index == 0) {
			System.out.println("Error in display list initiation!");
		}
	}

	public static void mesh() {
		GL11.glDeleteLists(index, 1);
		GL11.glNewList(index, GL11.GL_COMPILE_AND_EXECUTE);
		for (int x = 0; x < World.mapsize; x++) {
			for (int y = 0; y < World.mapsize; y++) {
				if (Main.renderPopMap){
					if (World.popdensity[x][y] != 0){
						GL11.glColor3f(World.popdensity[x][y] / 128f, World.popdensity[x][y] / 128f, World.popdensity[x][y] / 128f);
					}
					else{
						GL11.glColor3f(1f, 1f, 1f);
					}
				}
				if (World.map[x][y] == 0) {

					float height = 48 * Render.heightConstant;

					drawTile(x, y, height, 0, 0);

					if (World.getHeight(x + 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
						drawTileN(x, y, height, diff, 0, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
						drawTileS(x, y, height, diff, 0, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 0, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 0, 0);
					}
				}
				if (World.map[x][y] == 1) {

					float height = World.getHeight(x, y)
							* Render.heightConstant;
					if (World.decorations[x][y] == 5){
						drawTile(x, y, height, 4, 0);
					} else{
						drawTile(x, y, height, 1, 0);
					}

					if (World.getHeight(x + 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
						drawTileN(x, y, height, diff, 1, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
						drawTileS(x, y, height, diff, 1, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 1, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 1, 0);
					}
				}
				if (World.map[x][y] == 2) {

					float height = World.getHeight(x, y)
							* Render.heightConstant;

					drawTile(x, y, height, 3, 0);

					if (World.getHeight(x + 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
						drawTileN(x, y, height, diff, 3, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
						drawTileS(x, y, height, diff, 3, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 3, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 3, 0);
					}
				}
				if (World.map[x][y] == 3) {

					float height = World.getHeight(x, y)
							* Render.heightConstant;
					drawTile(x, y, height, 2, 0);

					if (World.getHeight(x + 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
						drawTileN(x, y, height, diff, 2, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
						drawTileS(x, y, height, diff, 2, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 2, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 2, 0);
					}
				}
				if (World.map[x][y] == 5) {
					float height = World.getHeight(x, y)
							* Render.heightConstant;
					drawTile(x, y, height, 4, 0);

					if (World.getHeight(x + 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
						drawTileN(x, y, height, diff, 4, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
						drawTileS(x, y, height, diff, 4, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 4, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 4, 0);
					}
				}
			}
		}
		GL11.glEndList();
	}

	private static void drawTile(float x, float y, float height, int tex,
			int tey) {
		GL11.glBegin(GL11.GL_QUADS);
		glTexCoord2f(Render.atlas.getCoord(tex, false),
				Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true),
				Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true),
				Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize + offset);
		glTexCoord2f(Render.atlas.getCoord(tex, false),
				Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize + offset);
		GL11.glEnd();
	}

	private static void drawTileN(float x, float y, float height, float length,
			int tex, int tey) {
		GL11.glBegin(GL11.GL_QUADS);
		glTexCoord2f(Render.atlas.getCoord(tex, false),
				Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true),
				Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize - offset, height - length, -y * tilesize
				- offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true),
				Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize - offset, height - length, -y * tilesize
				+ offset);
		glTexCoord2f(Render.atlas.getCoord(tex, false),
				Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize + offset);
		GL11.glEnd();
	}

	private static void drawTileW(float x, float y, float height, float length,
			int tex, int tey) {
		GL11.glBegin(GL11.GL_QUADS);
		glTexCoord2f(Render.atlas.getCoord(tex, false),
				Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true),
				Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true),
				Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize + offset, height - length, -y * tilesize
				- offset);
		glTexCoord2f(Render.atlas.getCoord(tex, false),
				Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize - offset, height - length, -y * tilesize
				- offset);
		GL11.glEnd();
	}

	private static void drawTileS(float x, float y, float height, float length,
			int tex, int tey) {
		GL11.glBegin(GL11.GL_QUADS);
		glTexCoord2f(Render.atlas.getCoord(tex, false),
				Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true),
				Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize + offset, height - length, -y * tilesize
				- offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true),
				Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize + offset, height - length, -y * tilesize
				+ offset);
		glTexCoord2f(Render.atlas.getCoord(tex, false),
				Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize + offset);
		GL11.glEnd();
	}

	private static void drawTileE(float x, float y, float height, float length,
			int tex, int tey) {
		GL11.glBegin(GL11.GL_QUADS);
		glTexCoord2f(Render.atlas.getCoord(tex, false),
				Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize + offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true),
				Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize + offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true),
				Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize + offset, height - length, -y * tilesize
				+ offset);
		glTexCoord2f(Render.atlas.getCoord(tex, false),
				Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize - offset, height - length, -y * tilesize
				+ offset);
		GL11.glEnd();
	}

	public static int getIndex() {
		return index;
	}
}
