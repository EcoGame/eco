package eco.game;

import eco.ui.Menu;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class does all the saving
 *
 * @author phil
 */
public class SaveUtil {
    public static void createSave(PlayerCountry country) {

        if (Main.currentSave == -1) {
            return;
        }

        if (Main.currentSave == 1) {
            if (Main.saveName1.contains(" ")) {
                Main.saveName1 = Main.saveName1.replace(" ", "@");
            }
        }
        if (Main.currentSave == 2) {
            if (Main.saveName2.contains(" ")) {
                Main.saveName2 = Main.saveName2.replace(" ", "@");
            }
        }
        if (Main.currentSave == 3) {
            if (Main.saveName3.contains(" ")) {
                Main.saveName3 = Main.saveName3.replace(" ", "@");
            }
        }
        if (Main.currentSave == 4) {
            if (Main.saveName4.contains(" ")) {
                Main.saveName4 = Main.saveName4.replace(" ", "@");
            }
        }
        if (Main.currentSave == 5) {
            if (Main.saveName5.contains(" ")) {
                Main.saveName5 = Main.saveName5.replace(" ", "@");
            }
        }

        String path;
        try {
            path = "../saves/" + Main.currentSave + ".txt";
            File fOut = new File(path);
            FileOutputStream FOS = new FileOutputStream(fOut);
            BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(FOS));

            // Data Being Saved:
            if (Main.currentSave == 1) {
                BW.write(Main.saveName1);
                BW.newLine();
            }
            if (Main.currentSave == 2) {
                BW.write(Main.saveName2);
                BW.newLine();
            }
            if (Main.currentSave == 3) {
                BW.write(Main.saveName3);
                BW.newLine();
            }
            if (Main.currentSave == 4) {
                BW.write(Main.saveName4);
                BW.newLine();
            }
            if (Main.currentSave == 5) {
                BW.write(Main.saveName5);
                BW.newLine();
            }

            BW.write(Integer.toString(Country.year));
            BW.newLine();
            BW.write(Integer.toString(country.wheat.getAmount()));
            BW.newLine();
            BW.write(Integer.toString(country.farmer.getPop()));
            BW.newLine();
            BW.write(Integer.toString(country.warrior.getPop()));
            BW.newLine();
            BW.write(Integer.toString(country.land.getLand()));
            BW.newLine();
            BW.write(Integer.toString(country.land.getPop()));
            BW.newLine();
            BW.write(Integer.toString(country.wood.getAmount()));
            BW.newLine();
            BW.write(Integer.toString(country.stone.getAmount()));
            BW.newLine();
            BW.write(Integer.toString(country.aggression.aggressionScore));
            BW.newLine();
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                BW.write(String.valueOf(PlayerCountry.countries.get(x).farmer
                        .getPop()) + ",");
            }
            BW.newLine();
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                BW.write(String.valueOf(PlayerCountry.countries.get(x).warrior
                        .getPop()) + ",");
            }
            BW.newLine();
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                BW.write(String.valueOf(PlayerCountry.countries.get(x).name)
                        + ",");
            }
            BW.newLine();
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                BW.write(String.valueOf(PlayerCountry.countries.get(x).wheat
                        .getAmount()) + ",");
            }
            BW.newLine();
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                BW.write(String.valueOf(PlayerCountry.countries.get(x).economy
                        .getTreasury()) + ",");
            }
            BW.newLine();
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                BW.write(String.valueOf(PlayerCountry.countries.get(x).land
                        .getLand()) + ",");
            }
            BW.newLine();
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                BW.write(String.valueOf(PlayerCountry.countries.get(x).land
                        .getPop()) + ",");
            }
            BW.newLine();
            /*
             * Use: BW.write(STUFF TO BE SAVED HERE); BW.newLine();
             *
             * Unless it needs to use loops in which case see the loops above.
             */

            BW.close();
        } catch (IOException ex) {
            System.out.println("IOException");
        }

    }

    public static void readSave() {

        if (Main.currentSave == 1) {
            if (Main.saveName1.contains("@")) {
                Main.saveName1 = Main.saveName1.replace("@", " ");
            }
        }
        if (Main.currentSave == 2) {
            if (Main.saveName2.contains("@")) {
                Main.saveName2 = Main.saveName1.replace("@", " ");
            }
        }
        if (Main.currentSave == 3) {
            if (Main.saveName3.contains("@")) {
                Main.saveName3 = Main.saveName3.replace("@", " ");
            }
        }
        if (Main.currentSave == 4) {
            if (Main.saveName4.contains("@")) {
                Main.saveName4 = Main.saveName4.replace("@", " ");
            }
        }
        if (Main.currentSave == 5) {
            if (Main.saveName5.contains("@")) {
                Main.saveName5 = Main.saveName1.replace("@", " ");
            }
        }
        String path;
        @SuppressWarnings("unused")
        File name = null;
        path = "../saves/" + Main.currentSave + ".txt";
        name = new File(Main.saveName1 + ".txt");
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
            Util.readError();
            return;
        }
        try {
            for (String str : list) {
                str = str.replace(System.getProperty("line.separator"), "");
            }

            // Information being loaded:
            if (Main.currentSave == 1) {
                Main.saveName1 = list.get(0);
            }
            if (Main.currentSave == 2) {
                Main.saveName2 = list.get(0);
            }
            if (Main.currentSave == 3) {
                Main.saveName3 = list.get(0);
            }
            if (Main.currentSave == 4) {
                Main.saveName4 = list.get(0);
            }
            if (Main.currentSave == 5) {
                Main.saveName5 = list.get(0);
            }
            PlayerCountry playerCountry = new PlayerCountry();
            PlayerCountry.year = Integer.valueOf(list.get(1));
            playerCountry.wheat.setAmount(Integer.valueOf(list.get(2)));
            playerCountry.farmer.setPop(Integer.valueOf(list.get(3)));
            playerCountry.warrior.setPop(Integer.valueOf(list.get(4)));
            playerCountry.land.setLand(Integer.valueOf(list.get(5)));
            playerCountry.land.setPop(Integer.valueOf(list.get(6)));
            playerCountry.wood.setAmount(Integer.valueOf(list.get(7)));
            playerCountry.stone.setAmount(Integer.valueOf(list.get(8)));
            playerCountry.aggression.aggressionScore = Integer.valueOf(list.get(9));
            int line = 10;
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                String values = list.get(line);
                String[] parts = values.split(",");
                PlayerCountry.countries.get(x).farmer.setPop(Integer
                        .valueOf(parts[x]));
            }
            line++;
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                String values = list.get(line);
                String[] parts = values.split(",");
                PlayerCountry.countries.get(x).warrior.setPop(Integer
                        .valueOf(parts[x]));
            }
            line++;
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                String values = list.get(line);
                String[] parts = values.split(",");
                PlayerCountry.countries.get(x).name = (parts[x]);
            }
            line++;
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                String values = list.get(line);
                String[] parts = values.split(",");
                PlayerCountry.countries.get(x).wheat.setAmount(Integer
                        .valueOf(parts[x]));
            }
            line++;
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                String values = list.get(line);
                String[] parts = values.split(",");
                PlayerCountry.countries.get(x).economy.setTreasury(Integer
                        .valueOf(parts[x]));
            }
            line++;
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                String values = list.get(line);
                String[] parts = values.split(",");
                PlayerCountry.countries.get(x).land.setLand(Integer
                        .valueOf(parts[x]));
            }
            line++;
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                String values = list.get(line);
                String[] parts = values.split(",");
                PlayerCountry.countries.get(x).land.setPop(Integer
                        .valueOf(parts[x]));
            }
            line++;
            for (int x = 0; x < PlayerCountry.countries.size(); x++) {
                String values = list.get(line);
                String[] parts = values.split(",");
               /* PlayerCountry.countries.get(x).landsize = (Integer
                        .valueOf(parts[x]));*/
            }
            line++;
            // Set the variable that the information will become
            // To the end here.

            // readSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            Util.readError();
        }
        return;
    }

    public static boolean doesSaveExist(int currentSave) {
        if (currentSave == -1) {
            return false;
        }
        String path = "";
        File name = null;
        path = "../saves/" + currentSave + ".txt";
        name = new File(currentSave + ".txt");
        name = new File(path);
        if (name.exists()) {
            return true;
        }
        return false;
    }

    public static String loadSaveName(int currentSave) {
        String path = "";
        @SuppressWarnings("unused")
        File name = null;
        path = "../saves/" + currentSave + ".txt";
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
            Util.readError();
            return "";
        }
        try {
            for (String str : list) {
                str = str.replace(System.getProperty("line.separator"), "");
            }

            // Information being loaded:
            /*
             * if (currentSave == 1){ Main.saveName1 = list.get(0); } if
             * (currentSave == 2){ Main.saveName2 = list.get(0); } if
             * (currentSave == 3){ Main.saveName3 = list.get(0); } if
             * (currentSave == 4){ Main.saveName4 = list.get(0); } if
             * (currentSave == 5){ Main.saveName5 = list.get(0); }
             */
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            Util.readError();
        }
        return "";
    }

    public static void deleteSave(int save) {
        String path = "";
        File name = null;
        path = "../saves/" + save + ".txt";
        name = new File(path);
        if (name.exists()) {
            name.delete();
            Menu.initMenu();
        }
    }
}
