package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import logic.*;

public class KnightHandlerTest {
	KnightHandler kh;
	@Test
	public void constrainTest1() {
		//when knight is inside the bounds of the window, constrain has no effects
		kh = new KnightHandler(100, 200, 0, 0, 3);
		assertEquals(100, kh.getKnight().getPos().getX(), 0.001);
		assertEquals(200, kh.getKnight().getPos().getY(), 0.001);
		kh.constrain();
		assertEquals(100, kh.getKnight().getPos().getX(), 0.001);
		assertEquals(200, kh.getKnight().getPos().getY(), 0.001);
	}
	@Test
	public void constrainTest2() {
		//when knight is outside the window, it returns on the other side of window
		kh = new KnightHandler(-100, -200, 0, 0, 3);
		assertEquals(-100, kh.getKnight().getPos().getX(), 0.001);
		assertEquals(-200, kh.getKnight().getPos().getY(), 0.001);
		kh.constrain();
		assertEquals(816 - 100, kh.getKnight().getPos().getX(), 0.001);
		assertEquals(638 - 200, kh.getKnight().getPos().getY(), 0.001);
	}
	@Test
	public void constrainTest3() {
		//when knight is outside the window, it returns on the other side of window
		kh = new KnightHandler(900, 600, 0, 0, 9);
		assertEquals(900, kh.getKnight().getPos().getX(), 0.001);
		assertEquals(600, kh.getKnight().getPos().getY(), 0.001);
		kh.constrain();
		assertEquals(900-816, kh.getKnight().getPos().getX(), 0.001);
		assertEquals(600, kh.getKnight().getPos().getY(), 0.001);
	}

}
