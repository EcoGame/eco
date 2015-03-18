package eco;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL15;

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

	@Override
	public void run() {

		ArrayList<Float> vertex = new ArrayList<Float>();
		ArrayList<Float> texture = new ArrayList<Float>();

		ArrayList<Float> structureVertex = new ArrayList<Float>();
		ArrayList<Float> structureTexture = new ArrayList<Float>();

		for (int x = 0; x < World.mapsize; x++) {
			for (int y = 0; y < World.mapsize; y++) {
				if (World.map[x][y] == 0) {
					vertex = addArrayToList(
							buildTileVertex((-x) * tilesize,
									48 * heightConstant, (-y) * tilesize, false),
							vertex);
					texture = addArrayToList(buildTexCoords(0, 0, false),
							texture);
				}
				if (World.map[x][y] == 1) {
					vertex = addArrayToList(
							buildTileVertex((-x) * tilesize, World.noise[x][y]
									* heightConstant, (-y) * tilesize, true),
							vertex);
					texture = addArrayToList(buildTexCoords(1, 0, true),
							texture);
				}
				if (World.map[x][y] == 2) {
					vertex = addArrayToList(
							buildTileVertex((-x) * tilesize, World.noise[x][y]
									* heightConstant, (-y) * tilesize, true),
							vertex);
					texture = addArrayToList(buildTexCoords(3, 0, true),
							texture);
				}
				if (World.map[x][y] == 3) {
					vertex = addArrayToList(
							buildTileVertex((-x) * tilesize, World.noise[x][y]
									* heightConstant, (-y) * tilesize, true),
							vertex);
					texture = addArrayToList(buildTexCoords(2, 0, true),
							texture);
				}
				if (Render.multiThreadStructures) {
					if (World.structures[x][y] == 1) {
						vertex = addArrayToList(
								buildStructureVertex((-x) * tilesize,
										World.noise[x][y] * heightConstant,
										(-y) * tilesize), structureVertex);
						texture = addArrayToList(buildTexCoords(0, 1, false),
								structureTexture);
					}
					if (World.structures[x][y] == 2) {
						vertex = addArrayToList(
								buildStructureVertex((-x) * tilesize,
										World.noise[x][y] * heightConstant,
										(-y) * tilesize), structureVertex);
						texture = addArrayToList(buildTexCoords(1, 1, false),
								structureTexture);
					}
				}
			}
		}

		int buffersize = vertex.size();

		FloatBuffer vertexData = BufferUtils.createFloatBuffer(buffersize);

		for (int i = 0; i < buffersize; i++) {
			vertexData.put((float) vertex.get(i));
		}

		buffersize = texture.size();
		FloatBuffer textureData = BufferUtils.createFloatBuffer(buffersize);

		for (int i = 0; i < buffersize; i++) {
			textureData.put((float) texture.get(i));
		}

		if (Render.multiThreadStructures) {
			buffersize = structureVertex.size();
			FloatBuffer structureVertexData = BufferUtils
					.createFloatBuffer(buffersize);

			for (int i = 0; i < buffersize; i++) {
				structureVertexData.put((float) vertex.get(i));
			}
			buffersize = structureTexture.size();

			FloatBuffer structureTextureData = BufferUtils
					.createFloatBuffer(buffersize);

			for (int i = 0; i < buffersize; i++) {
				structureTextureData.put((float) texture.get(i));
			}
		}

		vertexData.flip();
		textureData.flip();

		synchronized (ThreadManager.drawable) {
			try {
				if (!ThreadManager.drawable.isCurrent()) {
					ThreadManager.drawable.makeCurrent();
				}
			} catch (LWJGLException e1) {
				e1.printStackTrace();
			}

			Render.shouldRender = false;
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, Render.texture_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureData,
					GL15.GL_STATIC_DRAW);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, Render.vertex_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData,
					GL15.GL_STATIC_DRAW);

			Render.buffersize = vertexData.capacity();
			Render.shouldRender = true;
			try {
				ThreadManager.drawable.releaseContext();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
		}

	}

	private float[] buildTileVertex(float x, float y, float z, boolean height) {
		if (!height) {
			return new float[] { x, y, z, x + tilesize, y, z, x + tilesize, y,
					z + tilesize, x, y, z + tilesize, };
		} else {
			return new float[] { x, y, z, x + tilesize, y, z, x + tilesize, y,
					z + tilesize, x, y, z + tilesize,

					x, 0, z, x + tilesize, 0, z, x + tilesize, y, z, x, y, z,

					x, 0, z + tilesize, x + tilesize, 0, z + tilesize,
					x + tilesize, y, z + tilesize, x, y, z + tilesize,

					x, 0, z, x, 0, z + tilesize, x, y, z + tilesize, x, y, z,

					x + tilesize, 0, z, x + tilesize, 0, z + tilesize,
					x + tilesize, y, z + tilesize, x + tilesize, y, z,

			};
		}
	}

	private float[] buildStructureVertex(float x, float y, float z) {
		return new float[] { x, y, z, x + tilesize, y, z, x + tilesize,
				y + tilesize, z, x, y + tilesize, z, };
	}

	private float[] buildTexCoords(int tex, int tey, boolean height) {
		if (!height) {
			return new float[] { Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, false),
					Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, false), };
		} else {
			return new float[] { Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, false),
					Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, false),

					Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, false),
					Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, false),

					Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, false),
					Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, false),

					Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, false),
					Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, false),

					Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, true),
					Render.atlas.getCoord(tex, true),
					Render.atlas.getCoord(tey, false),
					Render.atlas.getCoord(tex, false),
					Render.atlas.getCoord(tey, false), };
		}
	}

	private ArrayList<Float> addArrayToList(float[] array, ArrayList<Float> list) {
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}

}
