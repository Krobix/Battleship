package battleship;

public class Coordinate {
	private int x;
	private int y;
	private char shipType;
	private char hit;
	
	public Coordinate(int a, int b, char type) {
		x = a;
		y = b;
		shipType = type;
		hit = '~';
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public char getShipType() {
		return shipType;
	}
	
	public void setShipType(char t) {
		shipType = t;
	}
	
	public char getHitStatus() {
		return hit;
	}
	
	public void setHitStatus(char h) {
		hit = h;
	}
}
