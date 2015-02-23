package eco;

import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL15;

public class MeshTask implements Runnable {

	public static float tilesize = Render.tilesize;
	
	@Override
	public void run() {
		
		ArrayList<Float> vertex = new ArrayList<Float>();
		ArrayList<Float> texture = new ArrayList<Float>();
		
		
		for (int x = 0; x < World.mapsize; x++){
			for(int y = 0; y < World.mapsize; y++){
				if (World.map[x][y] == 0){
					vertex = addArrayToList(buildTileVertex((-x) * tilesize, 
							0, (-y) * tilesize), vertex);
					texture = addArrayToList(buildTexCoords(0, 0), texture);
				}
				if (World.map[x][y] == 1){
					vertex = addArrayToList(buildTileVertex((-x) * tilesize, 
							0, (-y) * tilesize), vertex);
					texture = addArrayToList(buildTexCoords(1, 0), texture);
				}
				if (World.map[x][y] == 2){
					vertex = addArrayToList(buildTileVertex((-x) * tilesize, 
							0, (-y) * tilesize), vertex);
					texture = addArrayToList(buildTexCoords(3, 0), texture);
				}
				if (World.structures[x][y] == 1){
					vertex = addArrayToList(buildStructureVertex((-x) * tilesize, 
							1, (-y) * tilesize), vertex);
					texture = addArrayToList(buildTexCoords(3, 1), texture);
				}
				if (World.structures[x][y] == 2){
					vertex = addArrayToList(buildStructureVertex((-x) * tilesize, 
							1, (-y) * tilesize), vertex);
					texture = addArrayToList(buildTexCoords(3, 1), texture);
				}
			}
		}
		
		int buffersize = vertex.size();

		FloatBuffer vertexData = BufferUtils.createFloatBuffer(buffersize);
		
		for (int i = 0; i < buffersize; i++){
			vertexData.put((float) vertex.get(i));
		}
		

		buffersize = texture.size();
		FloatBuffer textureData = BufferUtils.createFloatBuffer(buffersize);
		
		
		for (int i = 0; i < buffersize; i++){
			textureData.put((float) texture.get(i));
		}
		
		

		
		synchronized (ThreadManager.drawable){
			try {
				if (!ThreadManager.drawable.isCurrent()){
					ThreadManager.drawable.makeCurrent();
				}
			} catch (LWJGLException e1) {
				e1.printStackTrace();
			}
		

		vertexData.flip();
		textureData.flip();
		
		
		int texture_handle = glGenBuffers(); 
		int vertex_handle = glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texture_handle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureData,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_handle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		

		
		Render.buffersize = vertexData.capacity();
		Render.texture_handle = texture_handle;
		Render.vertex_handle = vertex_handle;
		try {
			ThreadManager.drawable.releaseContext();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		}
		
	}
	
	public float[] buildTileVertex(float x, float y, float z){
		return new float[]{
			x, y, z,
			x + tilesize, y, z,
			x + tilesize, y, z + tilesize,
			x , y, z + tilesize,
		};
	}
	
	public float[] buildStructureVertex(float x, float y, float z){
		return new float[]{
				x, y, z,
				x + tilesize, y, z,
				x + tilesize, y + tilesize, z,
				x, y + tilesize, z,
			};
	}
	
	public float[] buildTexCoords(int tex, int tey){
		return new float[]{
			Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, false),
			Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, false),	
			Render.atlas.getCoord(tex, true), Render.atlas.getCoord(tey, true),	
			Render.atlas.getCoord(tex, false), Render.atlas.getCoord(tey, true),	
		};
	}
	
	public ArrayList<Float> addArrayToList(float[] array, ArrayList<Float> list){
		for (int i = 0; i < array.length; i++){
			list.add(array[i]);
		}
		return list;
	}

}
