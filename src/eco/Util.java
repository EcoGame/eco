package eco;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

    }
    
	public static Treble<Float, Float, Float> convertColor(Treble<Float, Float, Float> base){
		return new Treble<Float, Float, Float>(base.x / 255f, base.y / 255f, base.z / 255f);
	}

    public static void readSave(){
         String path = "../saves/save.txt";
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
             //System.out.println(Warrior.wPop);
             PopManager.fPopulation = Integer.valueOf(list.get(1));
             //System.out.println(Farmer.fPop);
             Main.tAcres = Integer.valueOf(list.get(2));
             //System.out.println(Main.tAcres);
          //   Wheat.employedFarmers = Integer.valueOf(list.get(3));
             //System.out.println(Wheat.employedFarmers);
            // Wheat.unemployedFarmers = Integer.valueOf(list.get(4));
             //System.out.println(Wheat.unemployedFarmers);
             Money.tMoney = Integer.valueOf(list.get(5));
             //System.out.println(Money.tMoney);
             Main.wheatPrice = Integer.valueOf(list.get(6));
             //System.out.println(Main.wheatPrice);
             PopManager.uneatenWheat = Integer.valueOf(list.get(7));
             readSuccess();
         } catch(Exception e){
             readError();
         }
         return;
     }

    public static void readError(){
		World.messages.add(new Message("------------------------------------------", 100, 100, 300));
	 	World.messages.add(new Message("Failed to load save!", 100, 130, 300));
	 	World.messages.add(new Message("The file either disappeared or is corrupt!", 100, 160, 300));
	 	World.messages.add(new Message("------------------------------------------", 100, 190, 300));
	}

    public static void readSuccess(){
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

}
