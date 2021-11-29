package tests;

import org.junit.Test;

import static org.junit.Assert.*;
import logic.*;


public class MapTest {

	@Test
	public void buildTest(){
		Map m = new Map(0);
		//first screen contains 7 platforms
		assertEquals(7, m.getPlatformlist().size());
		//second screen contains 7 platforms
		m.build(1);
		assertEquals(7, m.getPlatformlist().size());
		//third screen contains 10 platforms
		m.build(2);
		assertEquals(10, m.getPlatformlist().size());
		//last screen added to the json containing the platform data, but has no platforms added to it
		m.build(9);
		assertEquals(0, m.getPlatformlist().size());
	}
}
