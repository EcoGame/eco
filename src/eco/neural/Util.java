package eco.game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
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
 * A class that allows neural to save data
 * 
 * @author connor
 * 
 */

public class FileManager {

	public static void createFile() {

		String path = null;
		try {
			path = "txt/" + FILENAME + ".txt";
			if (!Main.isInEclipse) {
				System.out.println("PROBABLE ERROR WITH NEURAL FILES");
			}
			File fOut = new File(path);
			FileOutputStream FOS = new FileOutputStream(fOut);
			BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(FOS));

            //For Single bits of data:
            BW.write(STUFF TO BE SAVED HERE); BW.newLine();
            
            //For arraysof data set up a loop form:
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					BW.write(Short.toString(ARRAY[x][y]));
				}
				BW.newLine();
			}

			BW.close();
		} catch (IOException ex) {
			System.out.println("IOException");
		}

	}

	public static void readSave() {
        
		String path = null;
		File name = null;
		path = "saves/" + FILENAME + ".txt";
		name = new File(FILENAME + ".txt");
		if (!Main.isInEclipse) {
			System.out.println("PROBABLE ERROR IN LOADING NEURAL FILES");
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
				list.add(s.nextLine());
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

            Main.saveName1 = list.get(0);

			PlayerCountry.year = Integer.valueOf(list.get(1));
			PlayerCountry.wheat.settWheat(Integer.valueOf(list.get(2)));
			PlayerCountry.farmer.setfPop(Integer.valueOf(list.get(3)));
			PlayerCountry.warrior.setwPop(Integer.valueOf(list.get(4)));
			int line = 5;
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				for (int y = 0; y < World.mapsize; y++) {
					World.map[x][y] = Short.valueOf(values.substring(y, y + 1));
				}
				line++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			readError();
		}
		return;
	}
}
