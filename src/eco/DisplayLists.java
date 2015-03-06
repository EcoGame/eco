package eco;

import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.opengl.GL11;

public class DisplayLists {
	public static int grassHandle;
	public static int waterHandle;
	public static int stoneHandle;
	public static int farmHandle;
	public static int houseHandle;
	public static int castleHandle;

	public static final int indexes = 1;

	public static final float offset = Render.tilesize / 2f;
	public static final float tilesize = Render.tilesize;

	public static int index;

	public static void init(){
		index = GL11.glGenLists(indexes);
		if (index == 0){
			System.out.println("Error in display list initiation!");
		}
	}

	public static void mesh(){
		GL11.glDeleteLists(index, 1);
		GL11.glNewList(index, GL11.GL_COMPILE_AND_EXECUTE);
		for (int x = 0; x < World.mapsize; x++){
			for (int y = 0; y < World.mapsize; y++){
				if (World.map[x][y] == 0){

					float height = 48 * Render.heightConstant;

					drawTile(x, y, height, 0, 0);

					if (World.getHeight(x + 1, y) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
					  drawTileN(x, y, height, diff, 0, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
					  drawTileS(x, y, height, diff, 0, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 0, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 0, 0);
					}
				}
				if (World.map[x][y] == 1){

					float height = World.getHeight(x, y) * Render.heightConstant;
					drawTile(x, y, height, 1, 0);

					if (World.getHeight(x + 1, y) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
					  drawTileN(x, y, height, diff, 1, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
					  drawTileS(x, y, height, diff, 1, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 1, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 1, 0);
					}
				}
				if (World.map[x][y] == 2){

					float height = World.getHeight(x, y) * Render.heightConstant;

					drawTile(x, y, height, 3, 0);

					if (World.getHeight(x + 1, y) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
						drawTileN(x, y, height, diff, 3, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
						drawTileS(x, y, height, diff, 3, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 3, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 3, 0);
					}
				}
				if (World.map[x][y] == 3){

					float height = World.getHeight(x, y) * Render.heightConstant;
					drawTile(x, y, height, 2, 0);

					if (World.getHeight(x + 1, y) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
						drawTileN(x, y, height, diff, 2, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
						drawTileS(x, y, height, diff, 2, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 2, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)){
						float diff = World.getHeight(x, y) - World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 2, 0);
					}
				}
			}
		}
		GL11.glEndList();
	}

	public static void drawTile(float x, float y, float height, int tex, int tey){
		GL11.glBegin(GL11.GL_QUADS);
		glTexCoord2f(Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize + offset);
		glTexCoord2f(Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize + offset);
		GL11.glEnd();
	}

	public static void drawTileN(float x, float y, float height, float length, int tex, int tey){
		GL11.glBegin(GL11.GL_QUADS);
		glTexCoord2f(Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize - offset, height - length, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize - offset, height - length, -y * tilesize + offset);
		glTexCoord2f(Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize + offset);
		GL11.glEnd();
	}

	public static void drawTileW(float x, float y, float height, float length, int tex, int tey){
		GL11.glBegin(GL11.GL_QUADS);
		glTexCoord2f(Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize + offset, height - length, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize - offset, height - length, -y * tilesize - offset);
		GL11.glEnd();
	}

	public static void drawTileS(float x, float y, float height, float length, int tex, int tey){
		GL11.glBegin(GL11.GL_QUADS);
		glTexCoord2f(Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize + offset, height - length, -y * tilesize - offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize + offset, height - length, -y * tilesize + offset);
		glTexCoord2f(Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize + offset);
		GL11.glEnd();
	}

	public static void drawTileE(float x, float y, float height, float length, int tex, int tey){
		GL11.glBegin(GL11.GL_QUADS);
		glTexCoord2f(Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize - offset, height, -y * tilesize + offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, false));
		glVertex3f(-x * tilesize + offset, height, -y * tilesize + offset);
		glTexCoord2f(Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize + offset, height - length, -y * tilesize + offset);
		glTexCoord2f(Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, true));
		glVertex3f(-x * tilesize - offset, height - length, -y * tilesize + offset);
		GL11.glEnd();
	}
}
