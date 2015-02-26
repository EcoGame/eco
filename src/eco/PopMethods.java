package eco;

public class PopMethods {

		//public static int[] unfilledpops = new int[1000];

		public static void scanPops(int countryCode){
            // System.out.println("efa");
            int x = 0;
            int y = 0;
            while(Main.popArray[countryCode].length > x){
                if((Main.popArray[countryCode][x].people < Main.popSize)&& (Main.popArray[countryCode][x].isUsed == true )){

                    y= Main.popSize- Main.popArray[countryCode][x].people;
                    Main.unfilledpops[countryCode][x] = y;

                }
                x++;
            }
		}

		public static void unusedAcresFarmersAssignment(int countryCode){

            int x = 0;
            int y = 0;
            //int[][] unemployedfarmersArray = new int[1000][200];// put this is in main
            //	System.out.println(x);
            while((Main.popArray[countryCode].length > x) && (PopManager.unusedacres > 5)){
                if(((Main.popArray[countryCode][x].acres < (5*Main.popSize)) || (Main.popArray[countryCode][x].acres > (5*Main.popSize))) && (Main.popArray[countryCode][x].isFarmer == true) /*&& (Main.popArray[x].people*5 > acres/5)*/ && Main.popArray[countryCode][x].isUsed == true ){
                    if(PopManager.unusedacres < (5*Main.popSize)){
                        y = PopManager.unusedacres;
												PopManager.unusedacres = 0;
                    } else if(Main.popArray[countryCode][x].acres > (5*Main.popSize)){
                        y = Main.popArray[countryCode][x].acres - (5*Main.popSize);
                        PopManager.unusedacres =  PopManager.unusedacres + y;
                        y= y*-1;
                    }
                    
                    else {
                        y = (5*Main.popSize);
												PopManager.unusedacres = PopManager.unusedacres - (5*Main.popSize);
                    }
                    Main.popArray[countryCode][x].acres = Main.popArray[countryCode][x].acres + y;
                    y = 0;
                }

            }

		}

		public static void popAssigner(int countryCode){

            int x = 0;
            int y = 0;
            while(Main.popArray[countryCode].length > x){
                if((Main.popArray[countryCode][x].people < Main.popSize)&&(PopManager.unusedpops > 0) && (Main.popArray[countryCode][x].isUsed == true)) {
                    //System.out.println("Kek");
                    y = Main.unfilledpops[countryCode][x];
                    if(PopManager.unusedpops < y){
                        //			System.out.println("Tres");
                        Main.popArray[countryCode][x].people = Main.popArray[countryCode][x].people + PopManager.unusedpops;
												PopManager.unusedpops = 0;
                    }
                    else {

											PopManager.unusedpops = PopManager.unusedpops - y;
                        Main.popArray[countryCode][x].people = Main.popArray[countryCode][x].people + y;}
                        Main.unfilledpops[countryCode][x] = 0;
                        y = 0;
                }
                x++;
            }
		}

		public static int farmertotal(int countryCode){

				int x = 0;
				int y = 0;
				while(Main.popArray[countryCode].length > x) {
                    
                    if(Main.popArray[countryCode][x].isFarmer == true) {
                        y = y + Main.popArray[countryCode][x].people;
                    }
                    x++;
                    
                }
            return y;
		}

		public static int warriortotal(int countryCode) {
            int x = 0;
            int y = 0;
            while(Main.popArray[countryCode].length > x) {
                if(Main.popArray[countryCode][x].isWarrior == true) {
                    y = y + Main.popArray[countryCode][x].people;
                }
                x++;
            }
            return y;
		}

		public static int unemployedFarmerspops(int countryCode) {

            int x = 0;
            int y = 0;
            while(Main.popArray[countryCode].length > x) {
                y = y +Main.popArray[countryCode][x].unemployedfarmers;
                x++;
            }
            return y;
		}

		public static int employedFarmerspops(int countryCode) {

            int x = 0;
            int y = 0;
            while(Main.popArray[countryCode].length > x){

						y = y +Main.popArray[countryCode][x].employedfarmers;
										x++;
								}
		return y;
		}

		public static int usedacres(int countryCode) {
            int x = 0;
            int y = 0;
            while(Main.popArray[countryCode].length > x){
                y = y +Main.popArray[countryCode][x].acres;
                x++;
            }
            return y;
		}

		public static void consumecyclewarrior(int countryCode) {
            //food
            int x = 0;
            int y = 0;
            int p = 0;
						int r = 0;
						int h = 0;
						int u = 0;
            while(Main.popArray[countryCode].length > x) {
                if(Main.popArray[countryCode][x].isWarrior == true){
                    y =	Warrior.wHunger(Main.popArray[countryCode][x].people);
										if((Main.wheatPrice*y)> Main.popArray[countryCode][x].groupmoney){
											r = Main.popArray[countryCode][x].groupmoney/Main.wheatPrice;
											h = y- r;
											u = Warrior.checkStarvation(y, h);
											if( u > 0){
												r = 0;
												h = 0;

												Main.popArray[countryCode][x].people = Main.popArray[countryCode][x].people - u;
												if(Main.popArray[countryCode][x].people < 0){
													Main.popArray[countryCode][x].people = 0;
												}
											}
										}
                    Main.popArray[countryCode][x].groupmoney = Main.popArray[countryCode][x].groupmoney + (Main.wheatPrice*100);
                    PopManager.uneatenwheat = PopManager.uneatenwheat - y;
                }
                x++;
            }
		}

		public static void farmerconsumecycle(int countryCode) {

            int x = 0;
            int y =0;
            int k = 0;
            int r = 0;
            int w = 0;
            int h = 0;
            int m =0;
			//	System.out.println("here1");
            while(Main.popArray[countryCode].length > x){


                if(Main.popArray[countryCode][x].isFarmer == true){
                    y = Wheat.farmPacks(Main.popArray[countryCode][x].acres);
                    k =	Wheat.unemployedFarmers(y, Main.popArray[countryCode][x].people);
                    r = Wheat.employedFarmers(Main.popArray[countryCode][x].people,k);
                    w = Wheat.tWheat(r);
                    h = Farmer.fHunger(Main.popArray[countryCode][x].people);
                    m = Farmer.checkStarvation(h, w);
                    if( m > 0){
                    	w = 0;
                    	h = 0;

                    	Main.popArray[countryCode][x].people = Main.popArray[countryCode][x].people - m;
											if(Main.popArray[countryCode][x].people < 0){
												Main.popArray[countryCode][x].people = 0;
											}
                    }
								//		System.out.println("fstgfd" + Main.popArray[x].people);
                    PopManager.uneatenwheat = PopManager.uneatenwheat + (w-h);
                    Main.popArray[countryCode][x].groupmoney = Main.popArray[countryCode][x].groupmoney + (Main.wheatPrice*w);
                    //	System.out.println(Main.popArray[x].groupmoney+ "kel");
                }
                x++;
            }
		}



		public static void popBuilder(int prefrence, int countryCode){

				boolean iscomplete = false;
				int x = 0;
				int y = 0;
				int l = PopManager.unusedacres;
				int m = 0;
				int r = 0;
				boolean k = false;
				switch(prefrence){
						case 1:
						while(PopManager.unusedpops > 0){
								while(iscomplete == false){
								//	System.out.println("eke");
                                   // System.out.println(PopManager.unusedacres);
										if(PopManager.unusedpops < Main.popSize){
												r= PopManager.unusedpops;
										//		System.out.println("yell");
										}
										if(PopManager.unusedpops >= Main.popSize){
												r = Main.popSize;
										}
										if(PopManager.unusedpops == 0){
												iscomplete = true;
										}
										if(PopManager.unusedacres < (5*Main.popSize)){
												m= PopManager.unusedacres;
										}
										if(PopManager.unusedacres >= (5*Main.popSize)){
												m = (5*Main.popSize);
										}
										PopManager.unusedacres = PopManager.unusedacres - m;
										PopManager.unusedpops = PopManager.unusedpops - r;
										Main.popArray[countryCode][Main.unusedarray[countryCode]].isUsed = true;
										Main.popArray[countryCode][Main.unusedarray[countryCode]].isFarmer = true;
										Main.popArray[countryCode][Main.unusedarray[countryCode]].acres = m;
										Main.popArray[countryCode][Main.unusedarray[countryCode]].people = r;
										Main.popArray[countryCode][Main.unusedarray[countryCode]].groupmoney = 100;
									m = 0;
									r = 0;
									Main.unusedarray[countryCode]++;
										x++;

										if(x == 2){
												iscomplete = true;
											//	System.out.println(iscomplete);
 }
										//iscomplete =false;
									//	x =0;
								}
								iscomplete =false;
								x =0;
								while(iscomplete == false){
										if(PopManager.unusedpops < (Main.popSize)){
												r = PopManager.unusedpops;
										}
										if(PopManager.unusedpops >= (Main.popSize)){
												r = Main.popSize;
										}
										if(PopManager.unusedpops == 0){
												iscomplete = true;
										}
										PopManager.unusedpops = PopManager.unusedpops - r;
										Main.popArray[countryCode][Main.unusedarray[countryCode]].isUsed = true;
										Main.popArray[countryCode][Main.unusedarray[countryCode]].isWarrior = true;
										Main.popArray[countryCode][Main.unusedarray[countryCode]].people = r;
										Main.popArray[countryCode][Main.unusedarray[countryCode]].groupmoney = 100000;
										Main.unusedarray[countryCode]++;
									r =0;
										x++;
										if(x == 1){
												iscomplete = true;
										}
								}
			}
						break;
				}
	}
}
