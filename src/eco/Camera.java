package eco;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	Vector3f position;
	float yaw = 0.0f;
	float pitch = 0.0f;

	float dx = 0.0f;
	float dy = 0.0f;
	float dt = 0.0f;
	float lastTime = 0.0f;
	float time = 0.0f;

	float mouseSensitivity = 0.15f;
	float movementSpeed = 10.0f;

	public boolean markForUpdate = false;

	public Camera(float x, float y, float z){
		position = new Vector3f(x, y, z);
		pitch = 0f;	
	}

	public void moveForward(float distance){
		position.x += distance * (float) Math.sin(Math.toRadians(yaw));
		position.z -= distance * (float) Math.cos(Math.toRadians(yaw));	
	}

	public void moveBack(float distance){
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw));	
	}

	public void moveLeft(float distance)
	{
	    position.x -= distance * (float)Math.sin(Math.toRadians(yaw-90));
	    position.z += distance * (float)Math.cos(Math.toRadians(yaw-90));
	}

	public void moveRight(float distance)
	{
	    position.x -= distance * (float)Math.sin(Math.toRadians(yaw+90));
	    position.z += distance * (float)Math.cos(Math.toRadians(yaw+90));
	}

	public void moveUp(float distance){
		position.y -= distance;	
	}
	public void moveDown(float distance){
		position.y += distance;	
	}
	public void look(){
		GL11.glRotatef(yaw, 0f, 0f, 1f);
		GL11.glRotatef(30, 1f, 0f, 0f);
		GL11.glTranslatef(-position.x, position.y, -position.z);
	}

}
