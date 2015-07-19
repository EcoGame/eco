package eco.game;

public class AggressionScore {

    public int aggressionScore;

    public AggressionScore() {

        aggressionScore = 0;

    }

    public int calculateAggressionScore(int wPop, boolean dead) {
        if (dead) {
            aggressionScore = 0;
            return 0;
        }
        aggressionScore = wPop;
        if (aggressionScore < 0) {
            aggressionScore = 0;
        }
        return aggressionScore;

    }

}
