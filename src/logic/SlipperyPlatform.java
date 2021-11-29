package logic;

import java.awt.Color;
import java.awt.Graphics;

public class SlipperyPlatform extends Rectangle implements Platform{
	public SlipperyPlatform(int x, int y, int w, int h){
		super(x, y, w, h);
	}
	//check if knight overlaps with platform
	public boolean collide(Knight k) {
		double x = pos.getX();
		double y = pos.getY();
		double kx = k.pos.getX();
		double ky = k.pos.getY();
		return (x < kx+k.width && x+width > kx && y < ky+k.height && y+height > ky);
	}

	public void handleCollision(Knight k) {
		MovementState s = k.getState();
		//local variables store knights values to avoid using getters all the time
		double x = pos.getX();
		double y = pos.getY();
		double kx = k.pos.getX();
		double ky = k.pos.getY();
		double kvx = k.getVel().getX();
		double kvy = k.getVel().getY();

		//y coordinate of knight's bottom
		double knightBottom = ky + k.getHeight();
		//y coordinate of platform's bottom
		double platformBottom = y + height;
		//x coordinate of knight's right side
		double knightRight = kx + k.getWidth();
		//x coordinate of platform's right side
		double platformRight = x + width;

		//difference between platform's bottom and knight's top Y coordinates
		double bCollision = platformBottom - ky;
		//difference between platform's top and knights bottom Y coordinates
		double tCollision = knightBottom - y;
		//difference between platform's left and knight's right X coordinates
		double lCollision = knightRight - x;
		//difference between platform's right and knights left Y coordinates
		double rCollision = platformRight - kx;

		//set bouncyness of side collisions
		double bouncyness = 0.75;
		//Top collision
		if (tCollision < bCollision && tCollision < lCollision && tCollision < rCollision ){
			//check if the knight is moving towards the platform
			if(kvy > 0) {
				//if the knight was jumping, invert its Y velocity and multiply it by 0.5
				//when landing on top of a slippery platform, the knight boucnes and slides until its Y velocity slows below 2
				if(s == MovementState.Jumping) {
					if(k.getVel().getY() > 2){
						k.setVel(k.getVel().getX(), 0.5 * -k.getVel().getY());
					} else {
						k.setVel(0, 0);
					}
				} else {
					k.setVel(0, 0);
				}
				//set knight outside the platform so they dont glitch through eachother
				double yDiff = ky + k.getHeight() - y -1;
				k.setPos(kx, ky - yDiff);
			}
		}
		//bottom collision
		else if (bCollision < tCollision && bCollision < lCollision && bCollision < rCollision){
			//check if knight is moving towards the platform, then invert the Y velocity
			if(kvy < 0){
				k.setVel(kvx, -kvy * bouncyness);
			}
		}
		//Left collision
		else if (lCollision < rCollision && lCollision < tCollision && lCollision < bCollision){
			//check if knight is moving towards the platform
			if(kvx > 0) {
				//if the knight was walking right, stop it
				if(s == MovementState.WalkingRight) {
					k.setVel(0, 0);
				}
				//otherwise invert the X velocity
				else {
					k.setVel(-kvx * bouncyness, kvy);
				}

				double xDiff = kx + k.getWidth() - x -2;
				k.setPos(kx - xDiff, ky);
			}
		}
		//Right collision
		else if (rCollision < lCollision && rCollision < tCollision && rCollision < bCollision ){
			//check if knight is moving towards the platform
			if(kvx < 0) {
				//if the knight was walking left, stop it
				if(s == MovementState.WalkingLeft){
					k.setVel(0, 0);
				}
				//otherwise invert the X velocity
				else {
					k.setVel(-kvx * bouncyness, kvy);
				}
				//set knight outside platform
				double xDiff = x + width - kx  -2;
				k.setPos(kx + xDiff, ky);
			}
		}
	}
	//draw the platform as a blue rectangle
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect((int)getPos().getX(), (int)getPos().getY(), getWidth(), getHeight());
	}
}
