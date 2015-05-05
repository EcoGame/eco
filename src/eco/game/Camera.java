package eco.game;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 * A class that represents a camera for use with the openGl projection matrix.
 * Has methods for movement in six directions, along with a method look() that
 * will apply the matrix transformations that make the camera function. Defaults
 * to a 30 degree pitch and 0 degree yaw
 * 
 * @author phil
 * 
 */

public class Camera {

	private Vector3f position;
	private float yaw = 0.0f;
	private float pitch = 30.0f;

	public Camera(float x, float y, float z) {
		position = new Vector3f(x, y, z);
	}

	public void moveForward(float distance) {
		position.x += distance * (float) Math.sin(Math.toRadians(yaw));
		position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
	}

	public void moveBack(float distance) {
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw));
	}

	public void moveLeft(float distance) {
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
	}

	public void moveRight(float distance) {
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
	}

	public void moveUp(float distance) {
		position.y -= distance;
	}

	public void moveDown(float distance) {
		position.y += distance;
	}

	public void look() {
		GL11.glRotatef(yaw, 0f, 0f, 1f);
		GL11.glRotatef(pitch, 1f, 0f, 0f);
		GL11.glTranslatef(-position.x, position.y, -position.z);
	}

}
