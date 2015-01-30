package eco;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;




public class Perlin {

	public static int maxheight = 1;
	

	
	public static int octaveCount = 7;
	
	public static int stoneheight =  2 * maxheight / 3;
	
	public static int heightGradients = 1;
	public static boolean applyHeightMask = true;
	
	public static  Random random = new Random();
	
	public static int width = 128;
	public static int height = 128;
	public static int size = 7;
	
	public static void main(String[] args){
		float[][] noise = newWorld(size);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        for(int y = 0; y < height; y++){
	            for(int x = 0; x < width; x++){
	            	float c = noise[x][y];

	                c *= 128.0;
	                c += 127.0;
	                if(c > 255.0){
	                    c = 255.0f;
	                }
	                if(c < 0.0){
	                    c = 0.0f;
	                }

	                int r = (int) c;
	                int g = (int) c;
	                int b = (int) c;




	                int color = new Color(r, g, b).getRGB();

	                image.setRGB(x, y, color);
	            }
	        }

	        File fileImage = new File("noise.png");
	        try {
	            ImageIO.write(image, "png", fileImage);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public static void setSeed(long seed){
		random.setSeed(seed);
	}
	
	public static void setMaxHeight(int height){
		maxheight = height;
	}
	
	public static void setSeaLevel(int sealevel){
		
	}
	
	public static float[][] newWorld(int size){
		//double[][] noisebase = NoiseGen.getMap(size);
		
		int[][] noisebase = new int[(int) Math.pow(2, size)][(int) Math.pow(2, size)];
		float[][] heightbase = new float[(int) Math.pow(2, size)][(int) Math.pow(2, size)];
		
		int mapsize = (int) Math.pow(2, size);
		
		float[][] gradients = new float[mapsize][mapsize];
		
		for (int x = 0; x < mapsize; x++){
			for (int y = 0; y < mapsize; y++){
				gradients[x][y] = maxheight;
			}
		}
		
		for (int i = 0; i < heightGradients; i++){
			gradients = addGradient(gradients, mapsize);
		}
		
		
		random.setSeed(System.currentTimeMillis());
		float[][][] noiseOctaves = new float[octaveCount][mapsize][mapsize];
		

		
		for (int i = 0; i < octaveCount; i++){
			noiseOctaves[i] = GenerateSmoothNoise(genRandomNoise(mapsize), i);
		} 
		
		float[][] perlinNoise = new float[mapsize][mapsize];
		for (int i = 0; i < heightbase.length; i++){
			for (int k = 0; k < heightbase.length; k++){
				perlinNoise[i][k] = 0;
			}
		}
		
	    float amplitude = 5.5f;
	    float totalAmplitude = 0.0f;
	    float persistance = 0.525f;
	 
	    for (int octave = octaveCount - 1; octave >= 0; octave--)
	    {
	       amplitude *= persistance;
	       totalAmplitude += amplitude;
	 
	       for (int i = 0; i < mapsize; i++)
	       {
	          for (int j = 0; j < mapsize; j++)
	          {
	             perlinNoise[i][j] += (noiseOctaves[octave][i][j] * amplitude);
	          }
	       }
	    }
	   for (int i = 0; i < mapsize; i++)
	   {
	      for (int j = 0; j < mapsize; j++)
	      {
	         perlinNoise[i][j] /= totalAmplitude;
	      }	
	   }
		
		

		for (int i = 0; i < noisebase.length; i++){
			for (int k = 0; k < noisebase.length; k++){
				if (perlinNoise[i][k] >= maxheight){
					perlinNoise[i][k] = (float) (maxheight - 0.01);
				}
				if (applyHeightMask){
					perlinNoise[i][k] -= gradients[i][k];
				}
				perlinNoise[i][k] *= 128;
				perlinNoise[i][k] += 128;

			}
		}

		
	
		
		return perlinNoise;
	}
	
	public static float[][] genRandomNoise(int size){
		float[][] result = new float[size][size];
		for (int i = 0; i < size; i++){
			for (int k = 0; k < size; k++){
				result[i][k] = random.nextFloat() - random.nextInt(2);
			}
		}
		return result;
	}
	
	
	
	static float[][] GenerateSmoothNoise(float[][] baseNoise, int octave)
	{
	   int width = baseNoise.length;
	   int height = baseNoise[0].length;
	 
	   float[][] smoothNoise = new float[width][height];
	 
	   int samplePeriod = (int) Math.pow(2, octave);
	   float sampleFrequency = 1.0f / samplePeriod;
	 
	   for (int i = 0; i < width; i++)
	   {
	      int sample_i0 = (i / samplePeriod) * samplePeriod;
	      int sample_i1 = (sample_i0 + samplePeriod) % width; 
	      float horizontal_blend = (i - sample_i0) * sampleFrequency;
	 
	      for (int j = 0; j < height; j++)
	      {
	         int sample_j0 = (j / samplePeriod) * samplePeriod;
	         int sample_j1 = (sample_j0 + samplePeriod) % height;
	         float vertical_blend = (j - sample_j0) * sampleFrequency;
	 
	         float top = Interpolate(baseNoise[sample_i0][sample_j0],
	            baseNoise[sample_i1][sample_j0], horizontal_blend);
	 
	         float bottom = Interpolate(baseNoise[sample_i0][sample_j1],
	            baseNoise[sample_i1][sample_j1], horizontal_blend);
	 
	         smoothNoise[i][j] = Interpolate(top, bottom, vertical_blend);
	      }
	   }
	 
	   return smoothNoise;
	}
	
	static float Interpolate(float x0, float x1, float alpha)
	{
	   return x0 * (1 - alpha) + alpha * x1;
	}
	
	public static float[][] addGradient(float[][] map, int radius){
		float xpos = (float) ((float) (map.length / 2));
		float ypos = (float) ((float) (map.length / 2));
		
		
		for (int x = 0; x < map.length; x++){
			for (int y = 0; y < map.length; y++){
		        float distanceX = (xpos - x) * (xpos - x);
		        float distanceY = (ypos - y) * (ypos - y);

		        float distanceToCenter = (float) Math.sqrt(distanceX + distanceY);
		        
		        if (distanceToCenter <= radius){
		        	distanceToCenter = (distanceToCenter) / (128.0f);
		        	if (map[x][y] == maxheight){
		        	map[x][y] = distanceToCenter;}
		        	else{
		        		map[x][y] = (distanceToCenter + map[x][y]) / 2f;
		        	}
		        }


			}
		}
		
		return map;

	}
	
	public static float findGreatest(float[][] map){
		float greatest = 0.0f;
		for (int x = 0; x < map.length; x++){
			for (int y = 0; y < map[0].length; y++){
				greatest = Math.max(greatest, map[x][y]);
			}
		}
		return greatest;
	}
	
	public static void printNoise(float[][] map){
		for (int i = 0; i < map.length; i++){
			for (int k = 0; k < map.length; k++){
				System.out.print(map[i][k]);
				System.out.print(",");
			}
			System.out.println();
		}
	}
	
}
