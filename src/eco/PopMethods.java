package eco;

public class PopMethods {

		//public static int[] unfilledpops = new int[1000];

		public static void scanPops(int countrycode){
            // System.out.println("efa");
            int x = 0;
            int y = 0;
            while(Main.popArray[countrycode].length > x){
                if((Main.popArray[countrycode][x].people < Main.popSize)&& (Main.popArray[countrycode][x].isUsed == true )){

                    y= Main.popSize- Main.popArray[countrycode][x].people;
                    Main.unfilledpops[countrycode][x] = y;
                    //	System.out.println(Main.popArray[x].isFarmer);
                    //	System.out.println(y);
                    //System.out.println("Wdfe");

                }
                x++;
            }
		}

		public static void unusedAcresFarmersAssignment(int countrycode){

            int x = 0;
            int y = 0;
            //int[][] unemployedfarmersArray = new int[1000][200];// put this is in main
            //	System.out.println(x);
            while((Main.popArray[countrycode].length > x) && (PopManager.unusedAcres > 5)){
                if(((Main.popArray[countrycode][x].acres < (5*Main.popSize)) || (Main.popArray[countrycode][x].acres > (5*Main.popSize))) && (Main.popArray[countrycode][x].isFarmer == true) /*&& (Main.popArray[x].people*5 > acres/5)*/ && Main.popArray[countrycode][x].isUsed == true ){
                    if(PopManager.unusedAcres < (5*Main.popSize)){
                        y = PopManager.unusedAcres;
												PopManager.unusedAcres = 0;
                    } else if(Main.popArray[countrycode][x].acres > (5*Main.popSize)){
                        y = Main.popArray[countrycode][x].acres - (5*Main.popSize);
                        PopManager.unusedAcres =  PopManager.unusedAcres + y;
                        y= y*-1;
                    }

                    else {
                        y = (5*Main.popSize);
												PopManager.unusedAcres = PopManager.unusedAcres - (5*Main.popSize);
                    }
                    Main.popArray[countrycode][x].acres = Main.popArray[countrycode][x].acres + y;
                    y = 0;
                    //System.out.println("fsdf");
                }
                x++;
                //	System.out.println(x);
            }
            // System.out.println("efsf");
		}

		public static void popAssigner(int countrycode){

            int x = 0;
            int y = 0;
            while(Main.popArray[countrycode].length > x){
                if((Main.popArray[countrycode][x].people < Main.popSize)&&(PopManager.unusedPops > 0) && (Main.popArray[countrycode][x].isUsed == true)) {
                    //System.out.println("Kek");
                    y = Main.unfilledpops[countrycode][x];
                    if(PopManager.unusedPops < y){
                        //			System.out.println("Tres");
                        Main.popArray[countrycode][x].people = Main.popArray[countrycode][x].people + PopManager.unusedPops;
												PopManager.unusedPops = 0;
                    }
                    else {

											PopManager.unusedPops = PopManager.unusedPops - y;
                        Main.popArray[countrycode][x].people = Main.popArray[countrycode][x].people + y;}
                        Main.unfilledpops[countrycode][x] = 0;
                        y = 0;
                }
                x++;
            }
		}

		public static int farmertotal(int countrycode){

				int x = 0;
				int y = 0;
				while(Main.popArray[countrycode].length > x) {
                    if(Main.popArray[countrycode][x].isFarmer == true) {
                        y = y +Main.popArray[countrycode][x].people;
						//		System.out.println("kejk" +y);
                    }
                    x++;
                }
            return y;
		}

		public static int warriortotal(int countrycode) {
            int x = 0;
            int y = 0;
            while(Main.popArray[countrycode].length > x) {
                if(Main.popArray[countrycode][x].isWarrior == true) {
                    y = y + Main.popArray[countrycode][x].people;
                }
                x++;
            }
            return y;
		}

		public static int unemployedFarmerspops(int countrycode) {

            int x = 0;
            int y = 0;
            while(Main.popArray[countrycode].length > x) {
                y = y +Main.popArray[countrycode][x].unemployedfarmers;
                x++;
            }
            return y;
		}

		public static int employedFarmerspops(int countrycode) {

            int x = 0;
            int y = 0;
            while(Main.popArray[countrycode].length > x){

						y = y +Main.popArray[countrycode][x].employedfarmers;
										x++;
								}
		return y;
		}

		public static int usedacres(int countrycode) {
            int x = 0;
            int y = 0;
            while(Main.popArray[countrycode].length > x){
                y = y +Main.popArray[countrycode][x].acres;
                x++;
            }
            return y;
		}

		public static void consumecyclewarrior(int countrycode) {
            //food
            int x = 0;
            int y = 0;
            int p = 0;
						int r = 0;
						int h = 0;
						int u = 0;
            while(Main.popArray[countrycode].length > x) {
                if(Main.popArray[countrycode][x].isWarrior == true){
                    y =	Warrior.wHunger(Main.popArray[countrycode][x].people);
										if((Main.wheatPrice*y)> Main.popArray[countrycode][x].groupmoney){
											r = Main.popArray[countrycode][x].groupmoney/Main.wheatPrice;
											h = y- r;
											u = Warrior.checkStarvation(y, h);
											if( u > 0){
												r = 0;
												h = 0;
											//	System.out.println("Warriors be startving" + x);
												Main.popArray[countrycode][x].people = Main.popArray[countrycode][x].people - u;
												if(Main.popArray[countrycode][x].people < 0){
													Main.popArray[countrycode][x].people = 0;
												}
											}
										}
                    Main.popArray[countrycode][x].groupmoney = Main.popArray[countrycode][x].groupmoney + (Main.wheatPrice*100);
                    PopManager.uneatenWheat = PopManager.uneatenWheat - y;
                }
                x++;
            }
		}

		public static void farmerconsumecycle(int countrycode) {

            int x = 0;
            int y =0;
            int k = 0;
            int r = 0;
            int w = 0;
            int h = 0;
            int m =0;
			//	System.out.println("here1");
            while(Main.popArray[countrycode].length > x){


                if(Main.popArray[countrycode][x].isFarmer == true){
                    y = Wheat.farmPacks(Main.popArray[countrycode][x].acres);
                    k =	Wheat.unemployedFarmers(y, Main.popArray[countrycode][x].people);
                    Main.unemployedFarmers = Main.unemployedFarmers + k;
                    r = Wheat.employedFarmers(Main.popArray[countrycode][x].people,k);
                    Main.employedFarmers = Main.employedFarmers + r;
                    w = Wheat.tWheat(r);
									//	System.out.println("eaf" +w);
                    h = Farmer.fHunger(Main.popArray[countrycode][x].people);
									//	System.out.println("gah" + h);
                    m = Farmer.checkStarvation(h, w);
                    if( m > 0){
                    	w = 0;
                    	h = 0;
											//System.out.println("Farmers be startving"+ x );
                    	Main.popArray[countrycode][x].people = Main.popArray[countrycode][x].people - m;
											if(Main.popArray[countrycode][x].people < 0){
												Main.popArray[countrycode][x].people = 0;
											}
                    }
								//		System.out.println("fstgfd" + Main.popArray[x].people);
                    PopManager.uneatenWheat = PopManager.uneatenWheat + (w-h);
                    Main.popArray[countrycode][x].groupmoney = Main.popArray[countrycode][x].groupmoney + (Main.wheatPrice*w);
                    //	System.out.println(Main.popArray[x].groupmoney+ "kel");
                }
                x++;
            }
		}



		public static void popBuilder(int prefrence, int countrycode){

				boolean iscomplete = false;
				int x = 0;
				int y = 0;
				int l = PopManager.unusedAcres;
				int m = 0;
				int r = 0;
				boolean k = false;
				switch(prefrence){
						case 1:
						while(PopManager.unusedPops > 0){
								while(iscomplete == false){
								//	System.out.println("eke");
                                   // System.out.println(PopManager.unusedAcres);
										if(PopManager.unusedPops < Main.popSize){
												r= PopManager.unusedPops;
										//		System.out.println("yell");
										}
										if(PopManager.unusedPops >= Main.popSize){
												r = Main.popSize;
										}
										if(PopManager.unusedPops == 0){
												iscomplete = true;
										}
										if(PopManager.unusedAcres < (5*Main.popSize)){
												m= PopManager.unusedAcres;
										//		Main.unusedAcres = Main.unusedAcres - m;
										}
										if(PopManager.unusedAcres >= (5*Main.popSize)){
												m = (5*Main.popSize);
										//		Main.unusedAcres = 0;
										}
                                 //   System.out.println(m);
										PopManager.unusedAcres = PopManager.unusedAcres - m;
										PopManager.unusedPops = PopManager.unusedPops - r;
										Main.popArray[countrycode][Main.unusedarray[countrycode]].isUsed = true;
										Main.popArray[countrycode][Main.unusedarray[countrycode]].isFarmer = true;
										Main.popArray[countrycode][Main.unusedarray[countrycode]].acres = m;
										Main.popArray[countrycode][Main.unusedarray[countrycode]].people = r;
										Main.popArray[countrycode][Main.unusedarray[countrycode]].groupmoney = 100;
									m = 0;
									r = 0;
									Main.unusedarray[countrycode]++;
                                    //System.out.println(Main.unusedarray[countrycode]);
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
								//	System.out.println("eke2");
										if(PopManager.unusedPops < (Main.popSize)){
												r= PopManager.unusedPops;
									//			Main.unusedPops; =
										}
										if(PopManager.unusedPops >= (Main.popSize)){
												r = Main.popSize;
										}
										if(PopManager.unusedPops == 0){
												iscomplete = true;
										}
										PopManager.unusedPops = PopManager.unusedPops - r;
										Main.popArray[countrycode][Main.unusedarray[countrycode]].isUsed = true;
										Main.popArray[countrycode][Main.unusedarray[countrycode]].isWarrior = true;
										Main.popArray[countrycode][Main.unusedarray[countrycode]].people = r;
										Main.popArray[countrycode][Main.unusedarray[countrycode]].groupmoney = 100000;
										Main.unusedarray[countrycode]++;
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
