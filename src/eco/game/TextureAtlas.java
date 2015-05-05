package eco.game;

import org.newdawn.slick.opengl.Texture;

/**
 * This class makes is easier to use a texture atlas. It contains methods that
 * return a float position of where a subtexture is located. Has adjustable
 * resolution and size
 * 
 * @author phil
 * 
 */

public class TextureAtlas {

	private Texture texture;

	private int textureRes = 8;
	private int size = 8;

	public TextureAtlas(Texture texture) {
		this.texture = texture;
	}

	public TextureAtlas(Texture texture, int size, int resolution) {
		this.texture = texture;
		this.size = size;
		this.textureRes = resolution;
	}

	public float getXCoord(int pos, boolean increase) {
		int pixelPos = pos * textureRes;
		if (increase) {
			pixelPos += textureRes;
		}
		return (float) pixelPos / (float) (textureRes * size);
	}

	public float getYCoord(int pos, boolean increase) {
		int pixelPos = pos * textureRes;
		if (increase) {
			pixelPos += textureRes;
		}
		return (float) pixelPos / (float) (textureRes * size);
	}

	public float getCoord(int pos, boolean increase) {
		int pixelPos = pos * textureRes;
		if (increase) {
			pixelPos += textureRes;
		}
		return (float) pixelPos / (float) (textureRes * size);
	}

	public Texture getTexture() {
		return texture;
	}

	public void bind() {
		texture.bind();
	}

}
