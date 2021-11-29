package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import logic.*;


public class KnightTest {
	Knight k;
	@Test
	public void stateTest1() {
		k = new Knight(0, 0, 0, 0);
		//if holding left arrow key, knight's state is WalkingLeft
		k.setState(MovementState.Idle);
		k.setLeftkey(true);
		k.nextState();
		assertEquals(MovementState.WalkingLeft, k.getState());
	}
	@Test
	public void stateTest2() {
		k = new Knight(0, 0, 0, 0);
		//when both arrow keys released, knight's state is Idle
		k.setLeftkey(false);
		k.nextState();
		assertEquals(MovementState.Idle, k.getState());
	}
	@Test
	public void stateTest3() {
		k = new Knight(0, 0, 0, 0);
		//if holding right arrow key, knight's state is WalkingRight
		k.setRightkey(true);
		k.nextState();
		assertEquals(MovementState.WalkingRight, k.getState());
	}
	@Test
	public void stateTest4() {
		k = new Knight(0, 0, 0, 0);
		//when holding both arrow keys, knight's state is Idle
		k.setLeftkey(true);
		k.setRightkey(true);
		k.nextState();
		assertEquals(MovementState.Idle, k.getState());
	}
	@Test
	public void stateTest5() {
		k = new Knight(0, 0, 0, 0);
		//when the knight is charging, arrow keys have no effect
		k.setSpacekey(true);
		k.setState(MovementState.Charging);
		k.setRightkey(true);
		k.nextState();
		assertEquals(MovementState.Charging, k.getState());
	}
	@Test
	public void stateTest6() {
		//when the knight is jumping and has non 0 Y velocity, arrow keys have no effect
		k = new Knight(0, 0, 0, 1);
		k.setState(MovementState.Jumping);
		k.setSpacekey(false);
		k.setLeftkey(true);
		k.nextState();
		assertEquals(MovementState.Jumping, k.getState());
	}
	@Test
	public void stateTest7() {
		//when the knight is jumping but has 0 Y velocity, knight becomes idle
		k = new Knight(0, 0, 0, 0);
		k.setState(MovementState.Jumping);
		k.setLeftkey(false);
		k.setRightkey(false);
		k.nextState();
		assertEquals(MovementState.Idle, k.getState());
	}
	@Test
	public void stateTest8() {
		k = new Knight(0, 0, 0, 0);
		//when knight is idle(or walking), pressing space bar puts knight in charging state
		k.setState(MovementState.Idle);
		k.setSpacekey(true);
		k.nextState();
		assertEquals(MovementState.Charging, k.getState());
	}
	@Test
	public void stateTest9() {
		k = new Knight(0, 0, 0, 0);
		//when knight is charging, releasing space bar puts knight in jumping state
		k.setState(MovementState.Charging);
		k.setSpacekey(false);
		k.nextState();
		assertEquals(MovementState.Jumping, k.getState());
	}
	
	@Test
	public void updateTest() {
		//testing knight movement in free fall
		k = new Knight(0, 0, 0, 0);
		k.setState(MovementState.Idle);
		k.update();
		//first update only gravity to velocity, state and position updates are 1 tick behind
		assertEquals(0.55, k.getVel().getY(), 0.001);
		assertEquals(MovementState.Idle, k.getState());
		assertEquals(0, k.getPos().getY(), 0.001);
		//second tick updates gravity, state and position
		k.update();
		assertEquals(2*0.55, k.getVel().getY(), 0.001);
		assertEquals(MovementState.Jumping, k.getState());
		assertEquals(0.55, k.getPos().getY(), 0.001);
	}
	@Test
	public void updateVel() {
		k = new Knight(0, 0, 0, 0);
		//when knight is idle, velocity is 0
		k.setState(MovementState.Idle);
		k.updateVel();
		assertEquals(0, k.getVel().getX(), 0.001);
		assertEquals(0, k.getVel().getY(), 0.001);
		
		//when knight is charging, velocity is 0
		k.setVel(0, 0);
		k.setState(MovementState.Charging);
		k.updateVel();
		assertEquals(0, k.getVel().getX(), 0.001);
		assertEquals(0, k.getVel().getY(), 0.001);
		
		//when knight is walking, X velocity is walkingspeed (= 3)
		k.setVel(0, 0);
		k.setState(MovementState.WalkingRight);
		k.updateVel();
		assertEquals(3, k.getVel().getX(), 0.001);
		
		//jumping is handled during the update function and in the knighthandler class, not tested here
	}

}
