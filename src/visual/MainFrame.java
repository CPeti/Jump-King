package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serial;

public class MainFrame extends JFrame{
	@Serial
	private static final long serialVersionUID = 1L;
	//main menu panel
	private final MenuPanel menu = new MenuPanel();
	//panel where the game is drawn
	private final GamePanel game = new GamePanel();
	//panel where the menu is drawn when the game is paused
	private final PausedPanel pmenu = new PausedPanel();
	//sores the players name, used for loading and saving gamestate
	private String playerName = null;
	//current panel shown inside the main frame
	private JPanel active;

	public JPanel getActive() {
		return active;
	}
	//initialize panels
	private void InitComponents() {
		//when the application is launched, menu panel is visible
		add(menu, BorderLayout.CENTER);
		active = menu;
		
		//add action listener to main menu's new game button
		menu.ngButton.addActionListener(e -> {
			//reset player name
			playerName = null;
			//load the save file containing the parameters of a new game
			game.loadPosition("resources/saves/null.json");
			//change the active panel to game panel
			changePanel(game);
			//start the timer of game panel
			game.start();
		});
		
		//add action listener to main menu's load button
		menu.loadButton.addActionListener(e -> {
			//ask for player's name using a dialog box
			playerName = JOptionPane.showInputDialog(menu, "What is your name?", playerName);
			//check if player entered a valid name
			if(playerName != null && playerName.length() > 0) {
				//loads the save file with the name given
				//if no such file exists, loads a new game
				game.loadPosition("resources/saves/" + playerName +".json");
				//change active panel to game panel
				changePanel(game);
				//start game panel's timer
				game.start();
			}
		});
		
		//add action listener to main menu's option button
		menu.optButton.addActionListener(e -> {
			//check if the game has visible hitboxes turned on
			//adjusts button texts accordingly
			if(game.getHitboxes()) {
				menu.optButton.setText("Hitboxes: off");
				pmenu.optButton.setText("Hitboxes: off");
			} else {
				menu.optButton.setText("Hitboxes: on");
				pmenu.optButton.setText("Hitboxes: on");
			}
			//inverts the variable holding hitbox state
			game.setHitboxes(!game.getHitboxes());
		});
		//add action listener to the pause menu's option button
		pmenu.optButton.addActionListener(e -> {
			//check if the game has visible hitboxes turned on
			//adjusts button texts accordingly
			if(game.getHitboxes()) {
				menu.optButton.setText("Hitboxes: off");
				pmenu.optButton.setText("Hitboxes: off");
			} else {
				menu.optButton.setText("Hitboxes: on");
				pmenu.optButton.setText("Hitboxes: on");
			}
			//inverts the variable holding hitbox state
			game.setHitboxes(!game.getHitboxes());
		});
		//add action listener to the pause menu's resume button
		pmenu.resumeButton.addActionListener(e -> {
			//changes the active panel back to the game panel
			changePanel(game);
			//starts the game panel's timer
			game.start();
		});
		//add action listener to the pause menu's save button
		pmenu.saveButton.addActionListener(e -> {
			//if player name is unknown, asks for input with a dialog box
			if(playerName == null)
				playerName = JOptionPane.showInputDialog(pmenu, "What is your name?", null);
			//if player name is known, saves the game state in the save file with the same name
			if(playerName != null && playerName.length() > 0) {
				game.savePosition("resources/saves/" + playerName +".json");
				//shows dialong informing of successful save
				JOptionPane.showMessageDialog(pmenu, "Saved progress of " + playerName + "!");
			}
		});
		//add action listener to the pause menu's quit button
		pmenu.quitButton.addActionListener(e -> {
			//change active panel to main menu and stop game timer
			changePanel(menu);
			game.stop();
		});
		//add a key listener to the game panel
		game.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e) {
		    }
		    public void keyPressed(KeyEvent e) {
		    	//when escape is pressed, open the paused menu and stop timer
		    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		    		game.stop();
		    		changePanel(pmenu);
		    	}
		    }
		    public void keyReleased(KeyEvent e) {
		    }
		});
	}
	
	public MainFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Jump King");
		setIconImage(new ImageIcon("resources/menu/icon.jpg").getImage());
		//swing resized 800x600 images to 816x638, so had to use this ratio to avoid covering the bacground
		setSize(816, 638);
		setResizable(false);
		
		InitComponents();
	}
	//change the current panel to the one given as parameter
	public void changePanel(JPanel panel) {
		//add new active panel to frame
		add(panel);
		//remove old panel
	    remove(active);
	    //set new panel as active
	    active = panel;
	    doLayout();	
	    update(getGraphics());
	    revalidate();
	    repaint();
	}
	
	public static void main(String[] args) {
		//posts an event to the main frame at the end of swings event list
		EventQueue.invokeLater(() -> {
			JFrame frame = new MainFrame();
			frame.setVisible(true);
        });
		
	}
}
