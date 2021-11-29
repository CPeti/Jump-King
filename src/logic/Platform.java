package logic;
import java.awt.Graphics;

public interface Platform {
	boolean collide(Knight k);
	void handleCollision(Knight k);
	void draw(Graphics g);
}
