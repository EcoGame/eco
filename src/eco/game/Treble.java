package eco.game;

/**
 * A small parameterized class for an object with three values, a 3-tuple
 * 
 * @author phil
 * 
 * @param <X>
 * @param <Y>
 * @param <Z>
 */

public class Treble<X, Y, Z> {

	public X x;
	public Y y;
	public Z z;

	public Treble(X x, Y y, Z z) {
		this.x = x;
		this.y = y;
		this.z = z;
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

	public Z getZ() {
		return z;
	}

	public void setZ(Z z) {
		this.z = z;
	}

}
