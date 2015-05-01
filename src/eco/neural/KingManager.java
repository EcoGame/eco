package eco.neural;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class KingManager{
    public static int currentnetwork;
    
    public static void overallmanager(){
        boolean iscomplete = false;
        for(int x = 0; (x< Main.workingnetworks.length) && (iscomplete == false); x++){
            if(Main.workingnetworks[x] == true){
                currentnetwork = x;
                Main.workingnetworks[x] = false;
                iscomplete = true;
            }
        }
        iscomplete = false;
        
        NeuronReader.connectionreader(currentnetwork);
        
        NeuronReader.attributereader(currentnetwork);
        
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public static void statusreader(){
        char code;
        int x;
        int generation;
        int success;
		int filler;
		try {
			File file = new File("./neural/currentstates.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				code = line.charAt(0);
                
				String[] tokenSpace = line.split(" ");
 				
                switch(code){
                        
                    case 'g':
                    generation= Integer.parseInt(tokenSpace[1]);
                        break;
                    case 'y':
                        x = Integer.parseInt(tokenSpace[1]);
                        Main.workingnetworks[x] = true;
                        break;
                    case 'n':
                        x = Integer.parseInt(tokenSpace[1]);
                        Main.workingnetworks[x] = false;
                        break;

                    case 'q':
                        break;
                }
			}
			fileReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        
        
    }




}
