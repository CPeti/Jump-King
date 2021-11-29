package logic;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import org.json.simple.*;
import org.json.simple.parser.*;

public class KnightHandler {
	private final Map map;
	private final Knight k;
	private int screenNumber;
	
	public KnightHandler(int x, int y, int xv, int yv, int sn) {
		k = new Knight(x, y, xv, yv);
		map = new Map(sn);
	}
	public int getScreenNumber() {
		return screenNumber;
	}
	public Map getMap() {
		return map;
	}
	public Knight getKnight() {
		return k;
	}

	//prevent knight from leaving screen
	public void constrain() {
		//if knight leaves screen to the sides, it comes back on the other side
		if(k.getPos().getX() > 816 || k.getPos().getX() < 0){
			k.getPos().setX((816 + k.getPos().getX())%816);
		}
		//if knight left screen up or down
		if(k.getPos().getY() < 0 || k.getPos().getY() > 638) {
			//if knight left screen upwards, increase screen number
			if (k.getPos().getY() < 0) {
				screenNumber += 1;
			}
			//if knight left screen upwards, decrease screen number
			else if (k.getPos().getY() > 638) {
				screenNumber -= 1;
			}
			//set screen number between min and max
			if(screenNumber < 0) {
				screenNumber = 0;
			} else if (screenNumber > 42) {
				screenNumber = 42;
			}
			//load the map with the platforms of the new screen
			map.build(screenNumber);
			//normalize Y coordinate of knight
			k.getPos().setY((638 + k.getPos().getY())%638);
		}
		//prevent knight from falling too fast and glitching through platforms
		if(k.getVel().getY() > 30) {
			k.setVel(k.getVel().getX(), 30);
		}
		
	}
	//update knight and apply constraints
	public void update() {
		k.update();
		constrain();
		map.checkCollisions(k);
	}

	//load the the game position from the JSON file given as parameter
	@SuppressWarnings("deprecation")
	public void loadPosition(String s) {
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			//if the save file doesnt exist, load the save of a new game
			try {
				obj = parser.parse(new FileReader(s));
			} catch (FileNotFoundException e) {
				obj = parser.parse(new FileReader("saves/resources/null.json"));
			}

			JSONObject jo = (JSONObject) obj;
			//read params from JSON file
			//x coodrinate of position
			double x = (double)jo.get("x");
			//y coordinate of position
			double y = (double)jo.get("y");
			//x component of velocity
			double vx = (double)jo.get("vx");
			//x component of velocity
			double vy = (double)jo.get("vy");
			//screen number
			int sn = Math.toIntExact((long)jo.get("sn"));

			//set knights values to the loaded values
			k.setPos(x, y);
			k.setVel(vx, vy);
			//load new map
			screenNumber = sn;
			map.build(sn);
		} catch (Exception e) {
			//if save file is incorrectly formatted or any other exceptions happen while loading,
			//load with a new game's values
			k.setPos(400, 500);
			k.setVel(0, 0);
			screenNumber = 0;
			map.build(0);
		}
	}

	//save the game state to the JSON file given
	@SuppressWarnings({"unchecked", "deprecation"})
	public void savePosition(String s) {
		try {
			JSONObject jo = new JSONObject();
			jo.put("x", k.getPos().getX());
			jo.put("y", k.getPos().getY());
			jo.put("vx", k.getVel().getX());
			jo.put("vy", k.getVel().getY());
			jo.put("sn", screenNumber);
			
			PrintWriter pw = new PrintWriter(s);
			pw.write(jo.toJSONString());
			pw.flush();
			pw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//when the control keys are pressed, set their state as true in the knight
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_RIGHT -> k.setRightkey(true);
			case KeyEvent.VK_LEFT -> k.setLeftkey(true);
			case KeyEvent.VK_SPACE -> k.setSpacekey(true);
		}
	}
	//when the control keys are released, set their state as false in the knight
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_RIGHT -> k.setRightkey(false);
			case KeyEvent.VK_LEFT -> k.setLeftkey(false);
			case KeyEvent.VK_SPACE -> k.setSpacekey(false);
		}
	}
}
