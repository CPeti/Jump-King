package tests;

import org.junit.Test;

import static org.junit.Assert.*;
import logic.*;

public class PlatformTest {
	private RectPlatform r1;
	private SlipperyPlatform r2;
	private Knight k; //knight has dimensions 50x50, rectangle collisions are tested accordingly
	@Test
	public void collideTest() {
		//overlap
		k = new Knight(0, 0, 0, 0);
		r1 = new RectPlatform(25, 25, 50, 50);
		assertTrue(r1.collide(k));
		
		//edges touching
		r1 = new RectPlatform(50, 50, 50, 50);
		assertFalse(r1.collide(k));
		
		//1 px overlap
		r1 = new RectPlatform(49, 49, 50, 50);
		assertTrue(r1.collide(k));
		
		//full overlap
		r1 = new RectPlatform(0, 0, 50, 50);
		assertTrue(r1.collide(k));
	}
	@Test
	public void topCollisionTest() {
		//knight collides with normal platform from above while moving down
		//platform sets knights x and y velocities to 0
		k = new Knight(20, 0, -5, 3);
		r1 = new RectPlatform(0, 60, 100, 50);
		r1.handleCollision(k);
		assertEquals(0, k.getVel().getY(), 0.001);
		assertEquals(0, k.getVel().getX(), 0.001);
		
		//knight collides with normal platform from above while moving up
		//platform doesnt reset velocities as knight is leaving collision state
		k = new Knight(20, 0, -5, -3);
		r1 = new RectPlatform(0, 60, 100, 50);
		r1.handleCollision(k);
		assertNotEquals(0, k.getVel().getY(), 0.001);
		assertNotEquals(0, k.getVel().getX(), 0.001);
		assertEquals(-3, k.getVel().getY(), 0.001);
		assertEquals(-5, k.getVel().getX(), 0.001);
		
		
	}
	@Test
	public void sideCollisionTest() {
		//knight collides with normal platform from the right while moving towards the platform and moving on Y axis
		//platform inverts X velocity and multiplies by constant
		k = new Knight(80, 0, -5, 3);
		r1 = new RectPlatform(0, 0, 50, 50);
		r1.handleCollision(k);
		assertEquals(3, k.getVel().getY(), 0.001);
		assertEquals(-5 * -0.75, k.getVel().getX(), 0.001);
		
		//knight collides with normal platform from the left while moving away from the platform and moving on Y axis
		//platform doesnt reset velocities as knight is leaving collision state
		k = new Knight(80, 0, -5, 3);
		r1 = new RectPlatform(100, 0, 50, 50);
		r1.handleCollision(k);
		assertEquals(-5, k.getVel().getX(), 0.001);
		assertEquals(3, k.getVel().getY(), 0.001);
		
		//knight collides with normal platform from the left while moving towards the platform
		//and NOT moving on Y axis, knight is also in walking state towards platform
		//platform sets velocities to 0 to prevent further overlap
		k = new Knight(80, 0, -5, 0);
		k.setState(MovementState.WalkingLeft);
		r1 = new RectPlatform(0, 0, 50, 50);
		r1.handleCollision(k);
		assertEquals(0, k.getVel().getX(), 0.001);
		assertEquals(0, k.getVel().getY(), 0.001);	
	}
	
	@Test
	public void slipperyCollideTest() {
		//testing collision of slippery platforms
		//overlap
		k = new Knight(0, 0, 0, 0);
		r2 = new SlipperyPlatform(25, 25, 50, 50);
		assertTrue(r2.collide(k));
		
		//edges touching
		r2 = new SlipperyPlatform(50, 50, 50, 50);
		assertFalse(r2.collide(k));
		
		//1 px overlap
		r2 = new SlipperyPlatform(49, 49, 50, 50);
		assertTrue(r2.collide(k));
		
		//full overlap
		r2 = new SlipperyPlatform(0, 0, 50, 50);
		assertTrue(r2.collide(k));
	}
	@Test
	public void SlipperyTopCollisionTest() {
		//knight collides with slippery platform from above while moving down
		//platform causes knight to bounce and slide, inverting Y speed
		k = new Knight(20, 0, -5, 3);
		r2 = new SlipperyPlatform(0, 60, 100, 50);
		r2.handleCollision(k);
		assertEquals(-5, k.getVel().getX(), 0.001);
		assertEquals(3 * -0.5, k.getVel().getY(), 0.001);
		
		//knight collides with normal platform from above while moving up
		//platform doesnt reset velocities as knight is leaving collision state
		k = new Knight(20, 0, -5, -3);
		r2 = new SlipperyPlatform(0, 60, 100, 50);
		r2.handleCollision(k);
		assertNotEquals(0, k.getVel().getY(), 0.001);
		assertNotEquals(0, k.getVel().getX(), 0.001);
		assertEquals(-3, k.getVel().getY(), 0.001);
		assertEquals(-5, k.getVel().getX(), 0.001);
		
		
	}
	
}
