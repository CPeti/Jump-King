package logic;

import javax.swing.*;
import java.awt.*;

public class Knight extends Rectangle{
	//velocity
	private final Coord vel;
	//width and height of the knight
	private static final int size = 50;
	private static final Coord gravity = new Coord(0, 0.55);
	//knight's X speed when walking
	private static final int walkSpeed = 3;
	//knight's X speed when jumping
	private static final int jumpSpeed = 6;
	//maximum Y speed of a jump is maxJump/0.33
	private static final int maxJump = 60;
	//maximum Y speed of a jump is minJump/0.33
	private static final int minJump = 10;
	private MovementState state;
	//image for every movement state
	private static final Image idleImage = new ImageIcon("resources/knight/knightRight.png").getImage().getScaledInstance(size, size, Image.SCALE_FAST);
	private static final Image walkLeftImage = new ImageIcon("resources/knight/knightLeft.png").getImage().getScaledInstance(size, size, Image.SCALE_FAST);
	private static final Image walkRightImage = new ImageIcon("resources/knight/knightRight.png").getImage().getScaledInstance(size, size, Image.SCALE_FAST);
	private static final Image chargingImage = new ImageIcon("resources/knight/knightCharging.png").getImage().getScaledInstance(size, size, Image.SCALE_FAST);
	private static final Image jumpingImage = new ImageIcon("resources/knight/knightRight.png").getImage().getScaledInstance(size, size, Image.SCALE_FAST);
	//states of the control keys, true = pressed
	private boolean leftkey = false;
	private boolean rightkey = false;
	private boolean spacekey = false;
	//initialize the variable storing the power of next jump
	private int chargeTime = minJump;

	public Knight(int x, int y, int xv, int yv) {
		super(x, y, size, size);
		vel = new Coord(xv, yv);
		state = MovementState.Jumping;
	}

	public MovementState getState() {
		return state;
	}
	public void setState(MovementState s) {
		state = s;
	}
	public void setPos(double x, double y) {
		pos.setX(x);
		pos.setY(y);
	}
	public Coord getVel() {
		return vel;
	}
	public void setVel(double x, double y) {
		vel.setX(x);
		vel.setY(y);
	}
	public Image getImage() {
		return switch (state) {
			case WalkingLeft -> walkLeftImage;
			case WalkingRight -> walkRightImage;
			case Charging -> chargingImage;
			case Jumping -> jumpingImage;
			default -> idleImage;
		};
	}
	public void setLeftkey(boolean leftkey) {
		this.leftkey = leftkey;
	}
	public void setRightkey(boolean rightkey) {
		this.rightkey = rightkey;
	}
	public void setSpacekey(boolean spacekey) {
		this.spacekey = spacekey;
	}

	//update the knight's velocity by its state
	public void updateVel() {
		switch(state) {
			//when knight is idle or charging, its not moving
			case Idle:
			case Charging:
				setVel(0, 0);
				break;
			//when knight is walking, Y velocity is 0, X is walkingSpeed in the given direction
			case WalkingLeft:
				setVel(-walkSpeed, 0);
				break;
			case WalkingRight:
				setVel(walkSpeed, 0);
				break;
			//when the knight is jumping, only gravity is applied, no need to change velocity
			case Jumping:
				//
				break;
		}
	}
	//update knight's state field
	public void nextState() {
		//check if knight is in a controlled state
		if(state == MovementState.Idle || state == MovementState.WalkingLeft || state == MovementState.WalkingRight) {
			//if knight has Y velocity, set state to Jumping
			if(vel.getY() != 0) {
				state = MovementState.Jumping;
			}
			//if both arrowkeys are held down, set state to idle
			else if(leftkey && rightkey || (!leftkey && !rightkey)) {
				state = MovementState.Idle;
			}
			//if only left arrow key is held down, knight is walking left
			else if(leftkey){
				state = MovementState.WalkingLeft;
			}
			//if only right arrow key is held down, knight is walking right
			else {
				state = MovementState.WalkingRight;
			}
			//if spacekey is held down, knight begins charging a jump
			if(spacekey) {
				state = MovementState.Charging;
			}
		}
		//check if knight is charging a jump
		else if(state == MovementState.Charging) {
			//increment jump power
			chargeTime += 1;
			//if space key is released or the charged jump has reached maximum power, knight stops charging and jumps
			if(!spacekey || chargeTime == maxJump) {
				double jumpForce = -chargeTime/3.337;
				//if only left arrow key is held down when jumping, jump to the left with jumpSpeed
				if(leftkey && !rightkey) {
					setVel(-jumpSpeed, jumpForce);
				}
				//if only right arrow key is held down when jumping, jump to the right with jumpSpeed
				else if(rightkey && !leftkey) {
					setVel(jumpSpeed, jumpForce);
				}
				//otherwise jump straight up
				else {
					setVel(0, jumpForce);
				}
				state = MovementState.Jumping;
			}
		}
		//check if knight is jumping
		else if(state == MovementState.Jumping) {
			//reset jump power
			chargeTime = 10;
			//if Y velocity is 0, set the knight back to a controllable state
			if(vel.getY() == 0) {
				if(leftkey && !rightkey){
					state = MovementState.WalkingLeft;
				} else if(rightkey && !leftkey) {
					state = MovementState.WalkingRight;
				} else {
					state = MovementState.Idle;
				}
			}
		}
	}
	//update knight
	//when updating, velocity is always 1 tick behind position updates
	public void update() {
		nextState();
		updateVel();
		//apply velocity to position
		pos.add(vel);
		//apply gravity to velocity
		vel.add(gravity);
	}
}
