package eco.game;

/**
 * A small parameterized class for an object with two values, a 2-tuple
 *
 * @param <X>
 * @param <Y>
 * @author phil
 */

public class Tuple<X, Y> {

    public X x;
    public Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public void setX(X x) {
        this.x = x;
    }

    public Y getY() {
        return y;
    }

    public void setY(Y y) {
        this.y = y;
    }

}
