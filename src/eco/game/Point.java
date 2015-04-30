package eco.game;

import java.util.Objects;

/**
 * A class that represents a 2D point, and has overridden <i>hashCode</i> and
 * <i>equals</i> methods for use in hashmaps. Maintains the hashcode-equals
 * contract.
 * 
 * @author phil
 * 
 */

public class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		int hash = Objects.hash(x, y);
		return hash;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (getClass() != o.getClass())
			return false;

		final Point other = (Point) o;
		return x == other.x && y == other.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	 
}
