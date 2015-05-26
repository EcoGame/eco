package eco.neural;
import eco.game.*;
public class GeneticMaster {

	public static	int[] fitness = new int[10];
	public static int highfit = 0;
	public static int ndhighfit = 0;
	public static int numhighfit = 0;
	public static int ndnumhighfit = 0;
	public static eco.game.Country Counties[] = new eco.game.Country[10];
	public static void geneMaster() {
		System.out.println("yay for polan");
		Counties = eco.game.Util.getCountries();
		selectParents();
		System.out.println(numhighfit +" gof "+ ndnumhighfit);
		attributeBreeder(numhighfit, ndnumhighfit);
		connectionBreeder(numhighfit, ndnumhighfit);
		NeuronReader.generation++;
		Util.createConnectionsFile();
		Util.createNeuralFile();
		Util.createCurrentState();
	}

	public static void attributeBreeder(int network1, int network2) {
boolean iscomplete = false;
//int type = 0;
for(int k = 0; k < Main.workingnetworks.length; k++){
	int type = 0;
	iscomplete = false;
	while(iscomplete == false){
switch(type){
	case 0:
	for(int x = 0; x < Main.inputNeuralArray[k].length; x++){
		Main.neuronArrayprint[k][type][x][0]= GeneticMixer.spawnChildChromosome(Main.neuronArrayfill[network1][type][x][0], Main.neuronArrayfill[network2][type][x][0]);
		Main.highOrLowtoprint[k][x][0] = GeneticMixer.spawnChildChromosome(Main.highOrLow[network1][x][0], Main.highOrLow[network1][x][0]);
		Main.neuronArrayprint[k][type][x][1] =Main.neuronArrayfill[k][type][x][1];
		}
		type++;
	break;
	case 1:
	for(int q = 0; q < Main.outputNeuralArray[k].length; q++){
		Main.neuronArrayprint[k][type][q][0]= GeneticMixer.spawnChildChromosome(Main.neuronArrayfill[network1][type][q][0], Main.neuronArrayfill[network2][type][q][0]);
		Main.neuronArrayprint[k][type][q][1] =Main.neuronArrayfill[k][type][q][1];
		}
		type++;
		break;
		case 2:
		for(int e = 0; e < Main.neuralArray[k].length; e++){
			Main.neuronArrayprint[k][type][e][0]= GeneticMixer.spawnChildChromosome(Main.neuronArrayfill[network1][type][e][0], Main.neuronArrayfill[network2][type][e][0]);
			Main.neuronArrayprint[k][type][e][1] =Main.neuronArrayfill[k][type][e][1];
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
for(int k = 0; k < Main.workingnetworks.length; k++){
	iscomplete = false;
	while(iscomplete == false){
	switch(type){
		case 0:
		for(int x = 0; x < Main.inputNeuralArray[k].length; x++){
			for(int m = 0; m < Main.axonArraytofill[k][type][x].length; m++){
				for(int g = 0; g < Main.axonArraytofill[k][type][x][m].length; g++){
					Main.axonArraytoprint[k][type][x][m][g] = GeneticMixer.spawnChildChromosome(Main.axonArraytofill[network1][type][x][m][g],Main.axonArraytofill[network2][type][x][m][g]);
				}
			}
			}
			type++;
		break;
		case 1:
		for(int q = 0; q < Main.outputNeuralArray[k].length; q++){
			for(int y = 0; y < Main.axonArraytofill[k][type][q].length; y++){
				for(int p = 0; p < Main.axonArraytofill[k][type][q][y].length; p++){
					Main.axonArraytoprint[k][type][q][y][p] = GeneticMixer.spawnChildChromosome(Main.axonArraytofill[network1][type][q][y][p],Main.axonArraytofill[network2][type][q][y][p]);
				}
			}
			}
			type++;
			break;
			case 2:
			for(int e = 0; e < Main.neuralArray[k].length; e++){
				for(int b = 0; b < Main.axonArraytofill[k][type][e].length; b++){
					for(int a = 0; a < Main.axonArraytofill[k][type][e][b].length; a++){
						Main.axonArraytoprint[k][type][e][b][a] = GeneticMixer.spawnChildChromosome(Main.axonArraytofill[network1][type][e][b][a],Main.axonArraytofill[network2][type][e][b][a]);
					}
				}
			}
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
		peak[x] =	Counties[x].score.peakScore;
		total[x] =	Counties[x].score.totalScore;
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
