package eco;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * A class that contains various utilities and convience methods
 * 
 * @author phil, nate, connor, will
 * 
 */

public class Util {

	public static void createSave() {

		String path = null;
		try {
			path = "saves/" + Main.currentSave + ".txt";
			if (!Main.isInEclipse) {
				path = "../" + path;
			}
			File fOut = new File(path);
			FileOutputStream FOS = new FileOutputStream(fOut);
			BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(FOS));
            
            //Data Being Saved:
			if (Main.currentSave == 1){			
				BW.write(Main.saveName1);
				BW.newLine();
			}
			if (Main.currentSave == 2){			
				BW.write(Main.saveName2);
				BW.newLine();
			}
			if (Main.currentSave == 3){			
				BW.write(Main.saveName3);
				BW.newLine();
			}
			if (Main.currentSave == 4){			
				BW.write(Main.saveName4);
				BW.newLine();
			}
			if (Main.currentSave == 5){			
				BW.write(Main.saveName5);
				BW.newLine();
			}

			BW.write(Integer.toString(Main.year));
			BW.newLine();
			BW.write(Integer.toString(Wheat.tWheat(Farmer.fPop())));
			BW.newLine();
			BW.write(Integer.toString(Farmer.fPop()));
			BW.newLine();
			BW.write(Integer.toString(Warrior.wPop()));
			BW.newLine();
			for (int x = 0; x < World.mapsize; x++) {
				for (int y = 0; y < World.mapsize; y++) {
					BW.write(Short.toString(World.map[x][y]));
				}
				BW.newLine();
			}
			for (int x = 0; x < World.mapsize; x++) {
				for (int y = 0; y < World.mapsize; y++) {
					BW.write(Float.toString(World.noise[x][y]) + ",");
				}
				BW.newLine();
			}
			for (int x = 0; x < World.mapsize; x++) {
				for (int y = 0; y < World.mapsize; y++) {
					BW.write(Short.toString(World.structures[x][y]) + ",");
				}
				BW.newLine();
			}
			for (int x = 0; x < World.mapsize; x++) {
				for (int y = 0; y < World.mapsize; y++) {
					BW.write(Short.toString(World.popmap[x][y]) + ",");
				}
				BW.newLine();
			}
			for (int x = 0; x < World.mapsize; x++) {
				for (int y = 0; y < World.mapsize; y++) {
					BW.write(Short.toString(World.popdensity[x][y]) + ",");
				}
				BW.newLine();
			}
            /* Use:
             * BW.write(STUFF TO BE SAVED HERE);
             * BW.newLine();
             *
             * Unless it needs to use loops in which case see the loops above. 
             */
            
			BW.close();
		} catch (IOException ex) {
			System.out.println("IOException");
		}

	}

	public static void readSave() {
		String path = "";
		@SuppressWarnings("unused")
		File name = null;
			path = "saves/" + Main.currentSave + ".txt";
			name = new File(Main.saveName1 + ".txt");
		if (!Main.isInEclipse) {
			path = "../" + path;
		}
		Scanner s = null;
		try {
			s = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			return;
		}
		ArrayList<String> list = new ArrayList<String>();
		try {
			while (s.hasNext()) {
				list.add(s.next());
			}
			s.close();
		} catch (Exception e) {
			readError();
			return;
		}
		try {
			for (String str : list) {
				str = str.replace(System.getProperty("line.separator"), "");
			}
            
            // Information being loaded:
			if (Main.currentSave == 1){
				Main.saveName1 = list.get(0);			
			}
			if (Main.currentSave == 2){
				Main.saveName2 = list.get(0);			
			}
			if (Main.currentSave == 3){
				Main.saveName3 = list.get(0);			
			}
			if (Main.currentSave == 4){
				Main.saveName4 = list.get(0);			
			}
			if (Main.currentSave == 5){
				Main.saveName5 = list.get(0);			
			}
			Main.year = Integer.valueOf(list.get(1));
			Wheat.settWheat(Integer.valueOf(list.get(2)));
			Farmer.setfPop(Integer.valueOf(list.get(3)));
			Warrior.setwPop(Integer.valueOf(list.get(4)));
			int line = 5;
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				for (int y = 0; y < World.mapsize; y++) {
					World.map[x][y] = Short.valueOf(values.substring(y, y + 1));
				}
				line++;
			}
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				String[] parts = values.split(",");
				for (int y = 0; y < World.mapsize; y++) {
					World.noise[x][y] = Float.valueOf((parts[y]));
				}
				line++;
			}
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				String[] parts = values.split(",");
				for (int y = 0; y < World.mapsize; y++) {
					World.structures[x][y] = Short.valueOf((parts[y]));
				}
				line++;
			}
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				String[] parts = values.split(",");
				for (int y = 0; y < World.mapsize; y++) {
					World.popmap[x][y] = Short.valueOf((parts[y]));
				}
				line++;
			}
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				String[] parts = values.split(",");
				for (int y = 0; y < World.mapsize; y++) {
					World.popdensity[x][y] = Short.valueOf((parts[y]));
				}
				line++;
			}
            // Set the variable that the information will become
            // To the end here.
            
			readSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			readError();
		}
		return;
	}

	public static void readError() {
		Message.addMessage(new Message(
				"------------------------------------------", 100, 100, 300));
		Message.addMessage(new Message("Failed to load save!", 100, 130, 300));
		Message.addMessage(new Message(
				"The file either disappeared or is corrupt!", 100, 160, 300));
		Message.addMessage(new Message(
				"------------------------------------------", 100, 190, 300));
	}

	public static void readSuccess() {
		Message.addMessage(new Message("----------------------------------",
				100, 100, 300));
		Message.addMessage(new Message("Loaded game state from save file!",
				100, 130, 300));
		Message.addMessage(new Message("----------------------------------",
				100, 160, 300));
	}

	public static int randInt(int max) { // Returns a random number below max.

		return Main.random.nextInt(max);

	}

	public static int randInt(int min, int max) { // Returns a random number
													// between min and max.

		return min + Main.random.nextInt((max + 1) - min);

	}

	public static void takeScreenshot() {
		GL11.glReadBuffer(GL11.GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();
		int bpp = 4;
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, buffer);

		DateFormat dateFormat = new SimpleDateFormat("H:mm:ss yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		File file = new File("../screenshots/" + dateFormat.format(date));
		file.mkdirs();
		String format = "PNG";
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int i = (x + (width * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16)
						| (g << 8) | b);
			}
		}

		try {
			ImageIO.write(image, format, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int computeTotalHunger() {
		return Farmer.getTotalHunger() + Warrior.getTotalHunger()
				+ ((int) (Farmer.getfHunger() * World.displacedPeople / 2f));
	}

	public static String getWheatRateForDisplay() {
		int hunger = computeTotalHunger();
		int input = Farmer.getWheatProductionRate() * Farmer.getfPop();
		int total = input - hunger;
		if (total < 0) {
			return "dW/dT: " + String.valueOf(total) + " Bushels";
		} else if (total == 0) {
			return "dW/dT: " + "0 Bushels";
		} else {
			return "dW/dT: +" + String.valueOf(total) + " Bushels";
		}
	}

	public static int getWheatRate() {
		int hunger = computeTotalHunger();
		int input = Farmer.getWheatProductionRate() * Farmer.getfPop();
		int total = input - hunger;
		return total;
	}

	public static float getTotalPopf() {
		return Warrior.getFloatWPop() + Farmer.getFloatFPop();
	}

	public static int getTotalPop() {
		return Warrior.getwPop() + Farmer.getfPop();
	}

	public static boolean doesSaveExist(int currentSave) {
		String path = "";
		File name = null;
			path = "saves/" + currentSave + ".txt";
			name = new File(currentSave + ".txt");
		if (!Main.isInEclipse) {
			path = "../" + path;
		}
		name = new File(path);
		if (name.exists()) {
			return true;
		}
		return false;
	}

	public static Treble<Float, Float, Float> convertColor(
			Treble<Float, Float, Float> base) {
		return new Treble<Float, Float, Float>(base.x / 255f, base.y / 255f,
				base.z / 255f);
	}

	public static String loadSaveName(int currentSave){
		String path = "";
		@SuppressWarnings("unused")
		File name = null;
			path = "saves/" + currentSave + ".txt";
		if (!Main.isInEclipse) {
			path = "../" + path;
		}
		Scanner s = null;
		try {
			s = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			return "";
		}
		ArrayList<String> list = new ArrayList<String>();
		try {
			while (s.hasNext()) {
				list.add(s.next());
			}
			s.close();
		} catch (Exception e) {
			readError();
			return "";
		}
		try {
			for (String str : list) {
				str = str.replace(System.getProperty("line.separator"), "");
			}
            
            // Information being loaded:
			/*if (currentSave == 1){
				Main.saveName1 = list.get(0);			
			}
			if (currentSave == 2){
				Main.saveName2 = list.get(0);			
			}
			if (currentSave == 3){
				Main.saveName3 = list.get(0);			
			}
			if (currentSave == 4){
				Main.saveName4 = list.get(0);			
			}
			if (currentSave == 5){
				Main.saveName5 = list.get(0);			
			}*/
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			readError();
		}
		return "";
	}

}
