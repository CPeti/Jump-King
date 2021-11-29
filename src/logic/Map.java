package logic;

import java.util.ArrayList;
import java.io.FileReader;
import java.util.Collections;

import org.json.simple.parser.*;
import org.json.simple.*;

public class Map {
	//the array storing platform data
	private final ArrayList<Platform> platformlist;

	public Map(int i) {
		platformlist = new ArrayList<>();
		build(i);
	}
	public ArrayList<Platform> getPlatformlist() {
		return platformlist;
	}

	//load the patforms of the screen given as parameter
	@SuppressWarnings("deprecation")
	public void build(int screenNumber){
		//delete previous screen's patforms
		platformlist.removeAll(Collections.unmodifiableList(platformlist));
		JSONParser parser = new JSONParser();
		try {
			//create JSONObject from the JSON file with platform data
			Object obj = parser.parse(new FileReader("resources/screens/platforms.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray screenArray = (JSONArray) jsonObject.get("platforms");
			//get the element from JSONArray with the index of the given screen number
			JSONObject platformArray = (JSONObject)screenArray.get(screenNumber);
			//get the array of normal platforms
			JSONArray normalPlatforms = (JSONArray) platformArray.get("normal");
			//load the normal platforms and add them to the plarform list
			for(Object o: normalPlatforms) {
				JSONObject p = (JSONObject) o;
				int x = Math.toIntExact((long)p.get("x"));
				int y = Math.toIntExact((long)p.get("y"));
				int w = Math.toIntExact((long)p.get("width"));
				int h = Math.toIntExact((long)p.get("height"));
				platformlist.add(new RectPlatform(x, y, w, h));
			}

			//load the slippery platforms and add them to the platform list
			JSONArray slopePlatforms = (JSONArray) platformArray.get("slippery");
			for(Object o: slopePlatforms) {
				JSONObject p = (JSONObject) o;
				int x = Math.toIntExact((long)p.get("x"));
				int y = Math.toIntExact((long)p.get("y"));
				int w = Math.toIntExact((long)p.get("width"));
				int h = Math.toIntExact((long)p.get("height"));
				platformlist.add(new SlipperyPlatform(x, y, w, h));
			}
			
		} catch (Exception e) {
			//if unable to load map, close the game
			System.exit(1);
		}  
	}
	//check if knight collides with any platforms in the list
	//the platform hadnles the collision
	public void checkCollisions(Knight k) {
		for(Platform p: platformlist) {
			if(p.collide(k)) {	
				p.handleCollision(k);
			}
		}
	}
}
