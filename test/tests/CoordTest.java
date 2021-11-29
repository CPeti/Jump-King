package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import logic.*;

public class CoordTest {
	private Coord c1;
	private Coord c2;
	@Before
	public void init() {
		c1 = new Coord(5, 10);
		c2 = new Coord(-5, 10);
	}
	@Test
	public void addTest() {
		c1.add(c2);
		assertEquals(0, c1.getX(), 0.001);
		assertEquals(20, c1.getY(), 0.001);
		c2.add(c1).add(c1);
		assertEquals(-5, c2.getX(), 0.001);
		assertEquals(50, c2.getY(), 0.001);
	}

}
