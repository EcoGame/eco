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
import java.io.Writer;

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

    Writer writer = null;

    try {
      writer = new BufferedWriter(new OutputStreamWriter(
      new FileOutputStream(Main.currentSave + ".txt"), "utf-8"));
      writer.write((int)Main.fDeathRate);
    }
    catch (IOException ex) {
    // Can add error message if we want.
    }
    finally {
      try {writer.close();} catch (Exception ex) {}
    }

  }

	public static Treble<Float, Float, Float> convertColor(Treble<Float, Float, Float> base) {

		return new Treble<Float, Float, Float>(base.x / 255f, base.y / 255f, base.z / 255f);

	}

  public static void readSave(){
    String path = "../saves/" + Main.currentSave + ".txt";
    Scanner s = null;
    try {
      s = new Scanner(new File(path));
      } catch (FileNotFoundException e){
             readError();
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
             PopManager.wPopulation = Integer.valueOf(list.get(0));
             PopManager.fPopulation = Integer.valueOf(list.get(1));
             Main.tAcres = Integer.valueOf(list.get(2));
             Money.tMoney = Integer.valueOf(list.get(5));
             Main.wheatPrice = Integer.valueOf(list.get(6));
             PopManager.uneatenWheat = Integer.valueOf(list.get(7));
             readSuccess();
         } catch(Exception e){
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

		World.messages.add(new Message("----------------------------------", 100, 100, 30));
	 	World.messages.add(new Message("Loaded game state from save file!", 100, 130, 30));
	 	World.messages.add(new Message("----------------------------------", 100, 160, 30));

	}

	public static int randInt(int max) { //Returns a random number below max.

    return Main.random.nextInt(max);

	}

  public static int randInt(int min, int max) { //Returns a random number between min and max.

    return min + Main.random.nextInt((max + 1)- min);

	}

  public static void takeScreenshot(){
		GL11.glReadBuffer(GL11.GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height= Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );

		DateFormat dateFormat = new SimpleDateFormat("H:mm:ss yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
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
