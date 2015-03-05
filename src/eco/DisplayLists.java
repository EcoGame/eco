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
		GL11.glNewList(index, GL11.GL_COMPILE);
		for (int x = 0; x < World.mapsize; x++){
			for (int y = 0; y < World.mapsize; y++){
				/*if (World.structures[x][y] == 1){
					GL11.glBegin(GL11.GL_QUADS);
					glTexCoord2f(Render.atlas.getCoord(0, false), Render.atlas.getCoord(1, true));
					glVertex3f(-x * tilesize - offset, 0f, -y * tilesize);
					glTexCoord2f(Render.atlas.getCoord(0, true), Render.atlas.getCoord(1, true));
					glVertex3f(-x * tilesize + offset, 0f, -y * tilesize);
					glTexCoord2f(Render.atlas.getCoord(0, true), Render.atlas.getCoord(1, false));
					glVertex3f(-x * tilesize + offset, offset * 2, -y * tilesize);
					glTexCoord2f(Render.atlas.getCoord(0, false), Render.atlas.getCoord(1, false));
					glVertex3f(-x * tilesize - offset, offset * 2, -y * tilesize);
					GL11.glEnd();
				}
				if (World.structures[x][y] == 2){
					GL11.glBegin(GL11.GL_QUADS);
					glTexCoord2f(Render.atlas.getCoord(1, false), Render.atlas.getCoord(1, true));
					glVertex3f(-x * tilesize - offset, offset * 2, -y * tilesize);
					glTexCoord2f(Render.atlas.getCoord(1, true), Render.atlas.getCoord(1, true));
					glVertex3f(-x * tilesize + offset, offset * 2, -y * tilesize);
					glTexCoord2f(Render.atlas.getCoord(1, true), Render.atlas.getCoord(1, false));
					glVertex3f(-x * tilesize + offset, 0f, -y * tilesize);
					glTexCoord2f(Render.atlas.getCoord(1, false), Render.atlas.getCoord(1, false));
					glVertex3f(-x * tilesize - offset, 0f, -y * tilesize);
					GL11.glEnd();
				}
				if (World.structures[x][y] == 3){
					GL11.glBegin(GL11.GL_QUADS);
					glTexCoord2f(Render.atlas.getCoord(2, false), Render.atlas.getCoord(1, true));
					glVertex3f(-x * tilesize - offset, offset * 2, -y * tilesize);
					glTexCoord2f(Render.atlas.getCoord(2, true), Render.atlas.getCoord(1, true));
					glVertex3f(-x * tilesize + offset, offset * 2, -y * tilesize);
					glTexCoord2f(Render.atlas.getCoord(2, true), Render.atlas.getCoord(1, false));
					glVertex3f(-x * tilesize + offset, 0f, -y * tilesize);
					glTexCoord2f(Render.atlas.getCoord(2, false), Render.atlas.getCoord(1, false));
					glVertex3f(-x * tilesize - offset, 0f, -y * tilesize);
					GL11.glEnd();
				}*/
				if (World.map[x][y] == 0){
					GL11.glBegin(GL11.GL_QUADS);
					glTexCoord2f(Render.atlas.getCoord(0, false), Render.atlas.getCoord(0, false));
					glVertex3f(-x * tilesize - offset, 0f, -y * tilesize - offset);
					glTexCoord2f(Render.atlas.getCoord(0, true), Render.atlas.getCoord(0, false));
					glVertex3f(-x * tilesize + offset, 0f, -y * tilesize - offset);
					glTexCoord2f(Render.atlas.getCoord(0, true), Render.atlas.getCoord(0, true));
					glVertex3f(-x * tilesize + offset, 0f, -y * tilesize + offset);
					glTexCoord2f(Render.atlas.getCoord(0, false), Render.atlas.getCoord(0, true));
					glVertex3f(-x * tilesize - offset, 0f, -y * tilesize + offset);




					GL11.glEnd();
				}
				if (World.map[x][y] == 1){
					GL11.glBegin(GL11.GL_QUADS);
					glTexCoord2f(Render.atlas.getCoord(1, false), Render.atlas.getCoord(0, false));
					glVertex3f(-x * tilesize - offset, 0f, -y * tilesize - offset);
					glTexCoord2f(Render.atlas.getCoord(1, true), Render.atlas.getCoord(0, false));
					glVertex3f(-x * tilesize + offset, 0f, -y * tilesize - offset);
					glTexCoord2f(Render.atlas.getCoord(1, true), Render.atlas.getCoord(0, true));
					glVertex3f(-x * tilesize + offset, 0f, -y * tilesize + offset);
					glTexCoord2f(Render.atlas.getCoord(1, false), Render.atlas.getCoord(0, true));
					glVertex3f(-x * tilesize - offset, 0f, -y * tilesize + offset);
					GL11.glEnd();
				}
				if (World.map[x][y] == 2){
					GL11.glBegin(GL11.GL_QUADS);
					glTexCoord2f(Render.atlas.getCoord(3, false), Render.atlas.getCoord(0, false));
					glVertex3f(-x * tilesize - offset, 0f, -y * tilesize - offset);
					glTexCoord2f(Render.atlas.getCoord(3, true), Render.atlas.getCoord(0, false));
					glVertex3f(-x * tilesize + offset, 0f, -y * tilesize - offset);
					glTexCoord2f(Render.atlas.getCoord(3, true), Render.atlas.getCoord(0, true));
					glVertex3f(-x * tilesize + offset, 0f, -y * tilesize + offset);
					glTexCoord2f(Render.atlas.getCoord(3, false), Render.atlas.getCoord(0, true));
					glVertex3f(-x * tilesize - offset, 0f, -y * tilesize + offset);
					GL11.glEnd();
				}
				if (World.map[x][y] == 3){
					GL11.glBegin(GL11.GL_QUADS);
					glTexCoord2f(Render.atlas.getCoord(2, false), Render.atlas.getCoord(0, false));
					glVertex3f(-x * tilesize - offset, 0f, -y * tilesize - offset);
					glTexCoord2f(Render.atlas.getCoord(2, true), Render.atlas.getCoord(0, false));
					glVertex3f(-x * tilesize + offset, 0f, -y * tilesize - offset);
					glTexCoord2f(Render.atlas.getCoord(2, true), Render.atlas.getCoord(0, true));
					glVertex3f(-x * tilesize + offset, 0f, -y * tilesize + offset);
					glTexCoord2f(Render.atlas.getCoord(2, false), Render.atlas.getCoord(0, true));
					glVertex3f(-x * tilesize - offset, 0f, -y * tilesize + offset);
					GL11.glEnd();
				}
			}
		}
		GL11.glEndList();
	}
}
