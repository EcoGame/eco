package eco.neural;
import eco.game.*;
public class GeneticMaster {

    public static    int[] fitness = new int[10];
    public static int highfit = 0;
    public static int ndhighfit = 0;
    public static int numhighfit = 0;
    public static int ndnumhighfit = 0;
    public static NPCCountry Counties[] = new NPCCountry[10];
    public static void geneMaster() {
        Counties = eco.game.Util.getCountries();
        selectParents();
        attributeBreeder(numhighfit, ndnumhighfit);
        connectionBreeder(numhighfit, ndnumhighfit);
        NeuronReader.generation++;
        for(int p = 0; p< 10; p++){
        Util.createConnectionsFile(p);
        Util.createNeuralFile(p);
    }
        Util.createCurrentState();
    }

    public static void attributeBreeder(int network1, int network2) {
boolean iscomplete = false;

for(int k = 0; k < 10; k++){
    int randInt = MathUtil.randInt(0, 20);
    int type = 0;
    iscomplete = false;
    while(iscomplete == false){
switch(type){
    case 0:
    for(int x = 0; x < Main.inputNeuralArray[k].length; x++){
        if((randInt ==5) && (x == MathUtil.randInt(0, 9))){
            Main.neuronArrayprint[k][type][x][0] = MathUtil.randInt(1000);
            Main.highOrLowtoprint[k][x][0] = MathUtil.randInt(0, 1);
            Main.neuronArrayprint[k][type][x][1] =Main.neuronArrayfill[k][type][x][1];
    } else {
        Main.neuronArrayprint[k][type][x][0]= GeneticMixer.spawnChildChromosome1(Main.neuronArrayfill[network1][type][x][0], Main.neuronArrayfill[network2][type][x][0]);
        Main.highOrLowtoprint[k][x][0] = GeneticMixer.spawnChildChromosome1(Main.highOrLow[network1][x][0], Main.highOrLow[network1][x][0]);
        Main.neuronArrayprint[k][type][x][1] =Main.neuronArrayfill[k][type][x][1];

        }
 }
        type++;
    break;
    case 1:
    for(int q = 0; q < Main.outputNeuralArray[k].length; q++){
        if((randInt == 9) && (q == MathUtil.randInt(0, 9))){
            Main.neuronArrayprint[k][type][q][0] = MathUtil.randInt(1000);
            Main.neuronArrayprint[k][type][q][1] =Main.neuronArrayfill[k][type][q][1];
    }else{
        Main.neuronArrayprint[k][type][q][0]= GeneticMixer.spawnChildChromosome1(Main.neuronArrayfill[network1][type][q][0], Main.neuronArrayfill[network2][type][q][0]);
        Main.neuronArrayprint[k][type][q][1] =Main.neuronArrayfill[k][type][q][1];
    }
        }
        type++;
        break;
        case 2:
        for(int e = 0; e < Main.neuralArray[k].length; e++){
            if((randInt == 13) &&(e == MathUtil.randInt(0, 9))){
                Main.neuronArrayprint[k][type][e][0] = MathUtil.randInt(1000);
            Main.neuronArrayprint[k][type][e][1] =Main.neuronArrayfill[k][type][e][1];
}else{
    Main.neuronArrayprint[k][type][e][0]= GeneticMixer.spawnChildChromosome1(Main.neuronArrayfill[network1][type][e][0], Main.neuronArrayfill[network2][type][e][0]);
    Main.neuronArrayprint[k][type][e][1] =Main.neuronArrayfill[k][type][e][1];
}
        }
        iscomplete = true;
            break;
 }
}
}
    }

    public static void connectionBreeder(int network1, int network2) {
int type = 0;
boolean iscomplete = false;
for(int k = 0; k < 10; k++){
//System.out.println("k"+k);
    int randInt = MathUtil.randInt(0, 20);
    type = 0;
    iscomplete = false;
    while(iscomplete == false){
    switch(type){
        case 0:
        for(int x = 0; x < Main.inputNeuralArray[k].length; x++){
            for(int m = 0; m < Main.axonArraytofill[k][type][x].length; m++){
                for(int g = 0; g < Main.axonArraytofill[k][type][x][m].length; g++){
                    if((randInt == 15) && (x == MathUtil.randInt(0, 29))){
                        Main.axonArraytoprint[k][type][x][m][g] = GeneticMixer.mutator(0, Main.axonArraytofill[network1][type].length);
                    }else{
                    Main.axonArraytoprint[k][type][x][m][g] = GeneticMixer.spawnChildChromosome(Main.axonArraytofill[network1][type][x][m][g],Main.axonArraytofill[network2][type][x][m][g], 0, Main.axonArraytofill[network1][type].length);
                }
                }
            }
            }
            type++;
        break;
        case 1:
        for(int q = 0; q < Main.outputNeuralArray[k].length; q++){
            for(int y = 0; y < Main.axonArraytofill[k][type][q].length; y++){
                for(int p = 0; p < Main.axonArraytofill[k][type][q][y].length; p++){
                    if((randInt == 7) && (q == MathUtil.randInt(0, 19))){
                        Main.axonArraytoprint[k][type][q][y][p] = GeneticMixer.mutator(0, Main.axonArraytofill[network1][type].length);
                    }else{
                    Main.axonArraytoprint[k][type][q][y][p] = GeneticMixer.spawnChildChromosome(Main.axonArraytofill[network1][type][q][y][p],Main.axonArraytofill[network2][type][q][y][p], 0, Main.axonArraytofill[network1][type].length);
                }}
            }
            }
            type++;
            break;
            case 2:
            for(int e = 0; e < Main.neuralArray[k].length; e++){
                for(int b = 0; b < Main.axonArraytofill[k][type][e].length; b++){
                    for(int a = 0; a < Main.axonArraytofill[k][type][e][b].length; a++){
                        if((randInt == 19) && (e == MathUtil.randInt(0, 49))){
                            Main.axonArraytoprint[k][type][e][b][a] = GeneticMixer.mutator(0, Main.axonArraytofill[network1][type].length);
                        }else{
                        Main.axonArraytoprint[k][type][e][b][a] = GeneticMixer.spawnChildChromosome(Main.axonArraytofill[network1][type][e][b][a],Main.axonArraytofill[network2][type][e][b][a], 0, Main.axonArraytofill[network1][type].length);
                    }
                }
            }}
            iscomplete = true;
                break;
    }
}

}
    }

    public static void selectParents() {


        int[] total = new int[10];
        int hightotal = 0;
        int ndhightotal = 0;
        int numhightotal = 0;
        int ndnumhightotal = 0;
        int[] peak =new int[10];
        int highpeak = 0;
        int ndhighpeak = 0;
        int numhighpeak = 0;
        int ndnumhighpeak = 0;
        int[] avgGrowth = new int[10];
        int highavg = 0;
        int ndhighavg = 0;
        int numhighavg = 0;
        int ndnumhighavg = 0;

        for (int x = 0; x < Main.workingnetworks.length; x++) {
        peak[x] =    Counties[x].score.peakScore;
        total[x] =    Counties[x].score.totalScore;
        avgGrowth[x] = Counties[x].score.avgScore;
        }

        for(int h = 0; h < fitness.length; h++){
            if(peak[h] > highpeak){
                ndhighpeak = highpeak;
                ndnumhighpeak = numhighpeak;
                highpeak = peak[h];
                numhighpeak = h;
            }

        }
        fitness[numhighpeak] = fitness[numhighpeak] + 30;
        fitness[ndnumhighpeak] = fitness[ndnumhighpeak] + 15;

        for(int q = 0; q < fitness.length; q++){
            if(total[q] > hightotal){
                ndhightotal = hightotal;
                ndnumhightotal = numhightotal;
                hightotal = total[q];
                numhightotal = q;
            }

        }
        fitness[numhightotal] = fitness[numhightotal] + 30;
        fitness[ndnumhightotal] = fitness[ndnumhightotal] + 15;

        for(int e = 0; e < fitness.length; e++){
            if(avgGrowth[e] > highavg){
                ndhighavg = highavg;
                ndnumhighavg = numhighavg;
                highavg = avgGrowth[e];
                numhighavg = e;
            }

        }
        fitness[numhighavg] = fitness[numhighavg] + 30;
        fitness[ndnumhighavg] = fitness[ndnumhighavg] + 15;
        for(int f = 0; f < fitness.length; f++){
            if(fitness[f] > highfit){
                ndhighfit = highfit;
                ndnumhighfit = numhighfit;
                highfit = fitness[f];
                numhighfit = f;
            }

        }

    }
}
