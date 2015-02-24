package eco;

import org.newdawn.slick.opengl.Texture;

public class TextureAtlas {

	public  Texture texture;

	public int textureRes = 8;
	public int size = 4;

	public TextureAtlas(Texture texture) {
		this.texture = texture;
	}

	public float getXCoord(int pos, boolean increase) {
		int pixelPos = pos * textureRes;
		if (increase){
			pixelPos += textureRes;
		}
		return (float) pixelPos / (float) (textureRes * size);
	}

	public float getYCoord(int pos, boolean increase) {
		int pixelPos = pos * textureRes;
		if (increase){
			pixelPos += textureRes;
		}
		return (float) pixelPos / (float) (textureRes * size);
	}

	public float getCoord(int pos, boolean increase) {
		int pixelPos = pos * textureRes;
		if (increase){
			pixelPos += textureRes;
		}
		return (float) pixelPos / (float) (textureRes * size);
	}

	public Texture getTexture() {
		return texture;
	}

}
