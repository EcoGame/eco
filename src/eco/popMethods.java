package eco;

public class popMethods {

		//public static int[] unfilledpops = new int[1000];

		public static void scanPops(){
            // System.out.println("efa");
            int x = 0;
            int y = 0;
            while(Main.popArray.length > x){
                if((Main.popArray[x].people < 100)&& (Main.popArray[x].isUsed == true )){
                    
                    y= 100- Main.popArray[x].people;
                    Main.unfilledpops[x] = y;
                    //	System.out.println(Main.popArray[x].isFarmer);
                    //	System.out.println(y);
                    //System.out.println("Wdfe");
                    
                }
                x++;
            }
		}

		public static void unusedAcresFarmersAssignment(){
            
            int x = 0;
            int y = 0;
            //int[][] unemployedfarmersArray = new int[1000][200];// put this is in main
            //	System.out.println(x);
            while((Main.popArray.length > x) && (Main.unusedacres > 5)){
                if((Main.popArray[x].acres < 500) && (Main.popArray[x].isFarmer == true) /*&& (Main.popArray[x].people*5 > acres/5)*/ && Main.popArray[x].isUsed == true ){
                    if(Main.unusedacres < 500){
                        y = Main.unusedacres;
                        Main.unusedacres = 0;
                    }
                    else {
                        y = 500;
                        Main.unusedacres = Main.unusedacres - 500;
                    }
                    Main.popArray[x].acres = Main.popArray[x].acres + y;
                    y = 0;
                    //System.out.println("fsdf");
                }
                x++;
                //	System.out.println(x);
            }
            // System.out.println("efsf");
		}

		public static void popAssigner(){

            int x = 0;
            int y = 0;
            while(Main.popArray.length > x){
                if((Main.popArray[x].people < 100)&&(Main.unusedpops > 0) && (Main.popArray[x].isUsed == true)) {
                    //System.out.println("Kek");
                    y = Main.unfilledpops[x];
                    if(Main.unusedpops < y){
                        //			System.out.println("Tres");
                        Main.popArray[x].people = Main.popArray[x].people + Main.unusedpops;
                        Main.unusedpops = 0;
                    }
                    else {
                        
                        Main.unusedpops = Main.unusedpops - y;
                        Main.popArray[x].people = Main.popArray[x].people + y;}
                        Main.unfilledpops[x] = 0;
                        y = 0;
                }
                x++;
            }
		}

		public static int farmertotal(){

				int x = 0;
				int y = 0;
				while(Main.popArray.length > x) {
                    if(Main.popArray[x].isFarmer == true) {
                        y = y +Main.popArray[x].people;
						//		System.out.println("kejk" +y);
                    }
                    x++;
                }
            return y;
		}

		public static int warriortotal() {
            int x = 0;
            int y = 0;
            while(Main.popArray.length > x) {
                if(Main.popArray[x].isWarrior == true) {
                    y = y + Main.popArray[x].people;
                }
                x++;
            }
            return y;
		}

		public static int unemployedFarmerspops() {
            
            int x = 0;
            int y = 0;
            while(Main.popArray.length > x) {
                y = y +Main.popArray[x].unemployedfarmers;
                x++;
            }
            return y;
		}

		public static int employedFarmerspops() {
				
            int x = 0;
            int y = 0;
            while(Main.popArray.length > x){

						y = y +Main.popArray[x].employedfarmers;
										x++;
								}
		return y;
		}

		public static int usedacres() {
            int x = 0;
            int y = 0;
            while(Main.popArray.length > x){
                y = y +Main.popArray[x].acres;
                x++;
            }
            return y;
		}

		public static void consumecyclewarrior() {
            //food
            int x = 0;
            int y = 0;
            int p = 0;
            while(Main.popArray.length > x) {
                if(Main.popArray[x].isWarrior == true){
                    y =	Warrior.wHunger(Main.popArray[x].people);
                    Main.popArray[x].groupmoney = Main.popArray[x].groupmoney + (Main.wheatPrice*100);
                    Main.uneatenwheat = Main.uneatenwheat - y;
                }
                x++;
            }
		}

		public static void farmerconsumecycle() {
            
            int x = 0;
            int y =0;
            int k = 0;
            int r = 0;
            int w = 0;
            int h = 0;
            int m =0;
			//	System.out.println("here1");
            while(Main.popArray.length > x){
                if(Main.popArray[x].isFarmer == true){
                    y = Wheat.farmPacks(Main.popArray[x].acres);
                    k =	Wheat.unemployedFarmers(y, Main.popArray[x].people);
                    r = Wheat.employedFarmers(Main.popArray[x].people,k);
                    w = Wheat.tWheat(r);
                    h = Farmer.fHunger(Main.popArray[x].people);
                    m = checkStarvation(h, w);
                    if( m > 0){
                    	w = 0;
                    	h = 0;
                    	Main.popArray[x].people = Main.popArray[x].people - m;
                    }
                    Main.uneatenwheat = Main.uneatenwheat + (w-h);
                    Main.popArray[x].groupmoney = Main.popArray[x].groupmoney + (Main.wheatPrice*w);
                    //	System.out.println(Main.popArray[x].groupmoney+ "kel");
                }
                x++;
            }
		}



		public static void popBuilder(int prefrence){
            
				boolean iscomplete = false;
				int x = 0;
				int y = 0;
				int l = Main.unusedacres;
				int m = 0;
				int r = 0;
				boolean k = false;
				switch(prefrence){
						case 1:
						while(Main.unusedpops > 0){
								while(iscomplete == false){
								//	System.out.println("eke");
										if(Main.unusedpops < 100){
												r= Main.unusedpops;
										//		System.out.println("yell");
										}
										if(Main.unusedpops >= 100){
												r = 100;
										}
										if(Main.unusedpops == 0){
												iscomplete = true;
										}
										if(Main.unusedacres < 500){
												m= Main.unusedacres;
										//		Main.unusedacres = Main.unusedacres - m;
										}
										if(Main.unusedacres >= 500){
												m = 500;
										//		Main.unusedacres = 0;
										}
										Main.unusedacres = Main.unusedacres - m;
										Main.unusedpops = Main.unusedpops - r;
										Main.popArray[Main.unusedarray].isUsed = true;
										Main.popArray[Main.unusedarray].isFarmer = true;
										Main.popArray[Main.unusedarray].acres = m;
										Main.popArray[Main.unusedarray].people = r;
										Main.popArray[Main.unusedarray].groupmoney = 100;
									m = 0;
									r = 0;
										Main.unusedarray++;
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
										if(Main.unusedpops < 100){
												r= Main.unusedpops;
									//			Main.unusedpops; =
										}
										if(Main.unusedpops >= 100){
												r = 100;
										}
										if(Main.unusedpops == 0){
												iscomplete = true;
										}
										Main.unusedpops = Main.unusedpops - r;
										Main.popArray[Main.unusedarray].isUsed = true;
										Main.popArray[Main.unusedarray].isWarrior = true;
										Main.popArray[Main.unusedarray].people = r;
										Main.popArray[Main.unusedarray].groupmoney = 100000;
										Main.unusedarray++;
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
