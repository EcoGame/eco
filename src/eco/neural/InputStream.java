package eco.neural;

public class InputStream {
    static int oldgold = 0;
    static int oldfPop = 0;
    static int oldwPop = 0;
    static int oldwheat = 0;
    public static int readInput(int currentnetwork, int id) {
        int k = 0;
        int q = 0;
        switch (id) {

        case 0:
               // System.out.println(currentnetwork +"fasd");
            //
            k =goldchange(currentnetwork);
          //  System.out.println(NeuralManager.Countries[currentnetwork].farmer.//getfPop()+" af");
            break;

        case 1:
        //    k = NeuralManager.Countries[currentnetwork].warrior.getwPop();
            k =goldchange(currentnetwork);
            break;
        case 2:
        //    k = NeuralManager.Countries[currentnetwork].wheat.gettWheat();
        k = fPopchange(currentnetwork);
            break;

        case 3:
   k = fPopchange(currentnetwork);
            break;

        case 4:
     k = wPopchange(currentnetwork);
        break;
     case 5:
    k = wPopchange(currentnetwork);
    break;

        case 6:
        k = wheatChange(currentnetwork);
        break ;

        case 7:
        k = wheatChange(currentnetwork);
        break ;
        case 8:
        q = 0;
        k = getagression(currentnetwork , q);
        break;
        case 9:
        q = 1;
        k = getagression(currentnetwork , q);
        break;
        case 10:
        q = 2;
        k = getagression(currentnetwork , q);
        break;
        case 11:
        q = 3;
        k = getagression(currentnetwork , q);
        break;
        case 12:
        q = 4;
        k = getagression(currentnetwork , q);
        break;
        case 13:
        q = 5;
        k = getagression(currentnetwork , q);
        break;
        case 14:
        q = 6;
        k = getagression(currentnetwork , q);
        break;
        case 15:
        q = 7;
        k = getagression(currentnetwork , q);
        break;
        case 16:
        q = 8;
        k = getagression(currentnetwork , q);
        break;
        case 17:
        q = 9;
        k = getagression(currentnetwork , q);
        break;


    case 18:
    q = 0;
    k = warriorratio(currentnetwork, q);
 break;
    case 19:
    q = 1;
    k = warriorratio(currentnetwork, q);
    break;
    case 20:
    q = 2;
    k = warriorratio(currentnetwork, q);
    break;
    case 21:
    q = 3;
    k = warriorratio(currentnetwork, q);
    break;
    case 22:
    q = 4;
    k = warriorratio(currentnetwork, q);
    break;
    case 23:
    q = 5;
    k = warriorratio(currentnetwork, q);
    break;
    case 24:
    q = 6;
    k = warriorratio(currentnetwork, q);
    break;
    case 25:
    q = 7;
    k = warriorratio(currentnetwork, q);
    break;
    case 26:
    q = 8;
    k = warriorratio(currentnetwork, q);
    break;
    case 27:
    q = 9;
    k = warriorratio(currentnetwork, q);
    break;
    case 28:
    k = woodlevels(currentnetwork);
    break;
    case 29:
    k =stonelevels(currentnetwork);
    break;

    }
    return k;
}
    public static int goldchange(int currentnetwork){
        int currentgold = 0;
        int ret =0;
        currentgold= NeuralManager.Countries[currentnetwork].economy.getTreasury();
        ret= currentgold-oldgold;
        oldgold = currentgold;
        return ret;
    }
    public static int fPopchange(int currentnetwork){
        int currentfPop =0;
        int ret =0;
     currentfPop = NeuralManager.Countries[currentnetwork].farmer.getPop();
    ret =currentfPop - oldfPop;
    oldfPop = currentfPop;
return ret;
    }
    public static int wPopchange(int currentnetwork){
        int currentwPop = 0;
        int ret = 0;
        currentwPop = NeuralManager.Countries[currentnetwork].warrior.getPop();
        ret = oldwPop - currentwPop;
        return ret;
    }
    public static int wheatChange(int currentnetwork){
        int currentwheat = 0;
        int ret = 0;
        currentwheat = NeuralManager.Countries[currentnetwork].warrior.getPop();
        ret  = currentwheat - oldwheat;
        oldwheat = currentwheat;
        return ret;
    }
    public static int agression(int currentnetwork){
    int ret = 0;
    ret = NeuralManager.Countries[currentnetwork].aggression.aggressionScore;
    return ret;
}
    public static int getagression(int currentnetwork, int q){
        int ret = 0;
        if(currentnetwork == q){
            ret=NeuralManager.Countries[currentnetwork].aggression.aggressionScore;

        }else{
            ret=NeuralManager.Countries[q].aggression.aggressionScore;

        }
        return ret;

    }
    public static int warriorratio( int currentnetwork, int q){
    double ratio = 0;
    int selfwPop =0;
    int otherwPop =0;
    int ret = 0;
    if(currentnetwork == q){
        return 0;

    }else{
        selfwPop =NeuralManager.Countries[currentnetwork].warrior.getPop();
        otherwPop = NeuralManager.Countries[currentnetwork].warrior.getPop();
        if(otherwPop == 0){
            otherwPop = 1;
        }
        ratio = selfwPop/otherwPop;

        ratio= ratio*100;
        ret = (int)(ratio);
        return ret;
    }

}
    public static int woodlevels(int currentnetwork){
        int ret = 0;
        ret = NeuralManager.Countries[currentnetwork].wood.getWood();
        return ret;
    }
    public static int stonelevels(int currentnetwork){
        int ret = 0;
        ret = NeuralManager.Countries[currentnetwork].stone.getStone();
        return ret;
    }
}
