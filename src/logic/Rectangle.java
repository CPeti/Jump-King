package logic;

public class Rectangle {
	protected Coord pos;
	protected int width;
	protected int height;
	
	public Rectangle(int x, int y, int w, int h) {
		pos = new Coord(x, y);
		width = w;
		height = h;
	}

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Coord getPos() {
		return pos;
	}
}
