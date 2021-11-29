package visual;

import logic.*;

import javax.swing.*;




import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;

public class GamePanel extends JPanel implements ActionListener{
	@Serial
	private static final long serialVersionUID = 1L;
	//controller handling the movement of the knight
	private final KnightHandler knighthandler;
	
	private Image backgroundImage;
	private int currentScreen;
	private final Timer timer;
	//hitboxes are invisible by default
	private boolean hitboxes = false;
	
	public GamePanel() {
		setFocusable(true);
		addKeyListener(new TAdapter());
		//knights default position is (300, 300) on the first screen
		knighthandler = new KnightHandler(300, 300, 0, 0, 0);
		BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        //load background image
		currentScreen = 0;
		backgroundImage = new ImageIcon("resources/screens/" + currentScreen + ".png").getImage();
		//set timer
		int DELAY = 10; //10 ms -> 100 fps
		timer = new Timer(DELAY, this);
        
	}
	public boolean getHitboxes() {
		return hitboxes;
	}
	public void setHitboxes(boolean b) {
		hitboxes = b;
	}
	//knighthandler handles loading
	public void loadPosition(String s) {
		knighthandler.loadPosition(s);
	}
	//knighthandler handles saving
	public void savePosition(String s) {
		knighthandler.savePosition(s);
	}
	
	//knighthandler handles processing of key actions
	private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
        	knighthandler.keyPressed(e);
        }
        @Override
        public void keyReleased(KeyEvent e) {
        	knighthandler.keyReleased(e);
        }
    }
	
	//gets called on every tick of the timer
	@Override
	public void actionPerformed(ActionEvent e) {
		update();
	}
	//pauses the timer
	public void stop() {
		timer.stop();
	}
	//starts timer
	public void start() {
		timer.start();
	}
	
	//gets called on every tick of the timer
	public void update() {
		//knighthandler updates the knight
		knighthandler.update();
		//checks if background screen changed
		//if it changed, loads and displays new background image
		if(currentScreen != knighthandler.getScreenNumber()) {
			currentScreen = knighthandler.getScreenNumber();
			backgroundImage = new ImageIcon("resources/screens/" + currentScreen + ".png").getImage();
		}
		repaint();
	}
	
	//paint the game components
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //draw background image
        g2d.drawImage(backgroundImage, 0, 0, null);
        //draw the knight
        g2d.drawImage(knighthandler.getKnight().getImage(), (int)knighthandler.getKnight().getPos().getX(), (int)knighthandler.getKnight().getPos().getY(), null);
        //if enabled, drow platform hitboxes
        if(hitboxes) {
        	for(Platform r: knighthandler.getMap().getPlatformlist()) {
        		r.draw(g2d);
        	}
        }
        Toolkit.getDefaultToolkit().sync();
    }
}
