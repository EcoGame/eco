package eco;

import java.io.File;
import java.io.FileNotFoundException;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.Writer;
import java.io.File;
import java.nio.file.NoSuchFileException;

public class Util {

	/*
	Lines of the save file:
		wPop
		fPop
		tAcres
		employedFarmers
		unemployedFarmers
		tMoney
		wheatPrice
		tWheat

	*/
    public static void createSave(){

    String path = null;
    try {
        if(Main.currentSave == 1) {
            path = "../saves/" + Main.saveName1 + ".txt";
        }
        if(Main.currentSave == 2) {
            path = "../saves/" + Main.saveName2 + ".txt";
        }
        if(Main.currentSave == 3) {
            path = "../saves/" + Main.saveName3 + ".txt";
        }
        if(Main.currentSave == 4) {
            path = "../saves/" + Main.saveName4 + ".txt";
        }
        if(Main.currentSave == 5) {
            path = "../saves/" + Main.saveName5 + ".txt";
        }
        File fOut = new File(path);
        FileOutputStream FOS = new FileOutputStream(fOut);
        BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(FOS));
        BW.write(Integer.toString(Main.year));
        BW.newLine();
        BW.write(Integer.toString(Wheat.tWheat(Farmer.fPop())));
        BW.newLine();
        BW.write(Integer.toString(Farmer.fPop()));
        BW.newLine();
        BW.write(Integer.toString(Warrior.wPop()));
        BW.newLine();
        for (int x = 0; x < World.mapsize; x++){
            for (int y = 0; y < World.mapsize; y++){
                BW.write(Short.toString(World.map[x][y]));
            }
            BW.newLine();
        }
        for (int x = 0; x < World.mapsize; x++){
            for (int y = 0; y < World.mapsize; y++){
                BW.write(Float.toString(World.noise[x][y])+",");
            }
            BW.newLine();
        }
        BW.close();
    }
    catch (IOException ex) {
        System.out.println("IOException");
    }

  }

	public static Treble<Float, Float, Float> convertColor(Treble<Float, Float, Float> base) {

		return new Treble<Float, Float, Float>(base.x / 255f, base.y / 255f, base.z / 255f);

	}

  public static void readSave() {
    String path = "";
    File name = null;
    if(Main.currentSave == 1) {
        path = "../saves/" + Main.saveName1 + ".txt";
        name = new File(Main.saveName1 + ".txt");
    }
    if(Main.currentSave == 2) {
        path = "../saves/" + Main.saveName2 + ".txt";
        name = new File(Main.saveName2 + ".txt");
    }
    if(Main.currentSave == 3) {
        path = "../saves/" + Main.saveName3 + ".txt";
        name = new File(Main.saveName3 + ".txt");
    }
    if(Main.currentSave == 4) {
        path = "../saves/" + Main.saveName4 + ".txt";
        name = new File(Main.saveName4 + ".txt");
    }
    if(Main.currentSave == 5) {
        path = "../saves/" + Main.saveName5 + ".txt";
        name = new File(Main.saveName5 + ".txt");
    }
    Scanner s = null;
    try {
      s = new Scanner(new File(path));
      } catch (FileNotFoundException e){
             System.out.println("File Not Found");
             return;
      }
      ArrayList<String> list = new ArrayList<String>();
      try{
          while (s.hasNext()){
              list.add(s.next());
      }
             s.close();
         }
         catch (Exception e){
             readError();
             return;
         }
         try{
            for (String str : list){
            str = str.replace(System.getProperty("line.separator"), "");
            }
             Main.year = Integer.valueOf(list.get(0));
             Wheat.tWheat = Integer.valueOf(list.get(1));
             Farmer.fPop = Integer.valueOf(list.get(2));
             Warrior.wPop = Integer.valueOf(list.get(3));
            
             int line = 4;
             for (int x = 0; x < World.mapsize; x++){
                 String values = list.get(line);
                 for (int y = 0; y < World.mapsize; y++){
                     World.map[x][y] = Short.valueOf(values.substring(y, y + 1));
                 }
                 line++;
             }
             for (int x = 0; x < World.mapsize; x++){
                 String values = list.get(line);
                 String[] parts = values.split(",");
                 for (int y = 0; y < World.mapsize; y++){
                     World.noise[x][y] = Float.valueOf((parts[y]));
                 }
                 line++;
             }
            readSuccess();
         } catch(Exception e){
             e.printStackTrace();
            readError();
         }
         return;
     }

    public static void readError() {

		World.messages.add(new Message("------------------------------------------", 100, 100, 300));
	 	World.messages.add(new Message("Failed to load save!", 100, 130, 300));
	 	World.messages.add(new Message("The file either disappeared or is corrupt!", 100, 160, 300));
	 	World.messages.add(new Message("------------------------------------------", 100, 190, 300));

	}

  public static void readSuccess() {

      World.messages.add(new Message("----------------------------------", 100, 100, 300));
      World.messages.add(new Message("Loaded game state from save file!", 100, 130, 300));
      World.messages.add(new Message("----------------------------------", 100, 160, 300));

	}

	public static int randInt(int max) { //Returns a random number below max.

    return Main.random.nextInt(max);

	}

  public static int randInt(int min, int max) { //Returns a random number between min and max.

    return min + Main.random.nextInt((max + 1)- min);

	}

  public static void takeScreenshot(){

        DateFormat dateFormat = new SimpleDateFormat("H:mm:ss yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
		GL11.glReadBuffer(GL11.GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height= Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );
		File file = new File("../screenshots/"+dateFormat.format(date)); // The file to save to.
        file.mkdirs();
		String format = "PNG";
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for(int x = 0; x < width; x++)
		{
		    for(int y = 0; y < height; y++)
		    {
		        int i = (x + (width * y)) * bpp;
		        int r = buffer.get(i) & 0xFF;
		        int g = buffer.get(i + 1) & 0xFF;
		        int b = buffer.get(i + 2) & 0xFF;
		        image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
		    }
		}

		try {
		    ImageIO.write(image, format, file);
		} catch (IOException e) { e.printStackTrace(); }
	}

  public static int computeTotalHunger(){

    return Farmer.totalHunger + Warrior.totalHunger + ((int) (Farmer.fHunger * World.displacedPeople / 2f));

  }

  public static String getWheatRateForDisplay(){

    int hunger = computeTotalHunger();
    int input = Farmer.wheatPerFarmer * Farmer.fPop;
    int total = input - hunger;
    if (total < 0){
      return "dW/dT: "+String.valueOf(total)+" Bushels";
    }
    else if (total == 0){
      return "dW/dT: "+"0 Bushels";
    }
    else{
      return "dW/dT: +"+String.valueOf(total)+" Bushels";
    }
  }

  public static int getWheatRate(){
    int hunger = computeTotalHunger();
    int input = Farmer.wheatPerFarmer * Farmer.fPop;
    int total = input - hunger;
    return total;
  }

}
