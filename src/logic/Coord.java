package logic;
//class for storing vector variables
public class Coord {
	private double x;
	private double y;
	public Coord(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	//vector summation
	public Coord add(Coord c) {
		this.x += c.getX();
		this.y += c.getY();
		return this;
	}
}
