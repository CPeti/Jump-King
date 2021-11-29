package visual;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
//same as menu panel...
public class PausedPanel extends JPanel{
	@Serial
	private static final long serialVersionUID = 1L;
		
	protected MenuButton resumeButton;
	protected MenuButton saveButton;
	protected MenuButton optButton;
	protected MenuButton quitButton;


	public PausedPanel() {
		BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

		JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
		//add constraint to place buttons above eachother in the grid
		//gridy parameter represents the vertical position of the button in the grid
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.insets = new Insets(10,0,0,0);  //top padding
        
		resumeButton =   new MenuButton("Resume");
		c.gridy = 0;
		buttonPanel.add(resumeButton, c);
		
		saveButton = new MenuButton("Save");
		c.gridy = 1;
		buttonPanel.add(saveButton, c);
		
		optButton =  new MenuButton("Hitboxes: off");
		c.gridy = 2;
		buttonPanel.add(optButton, c);
		
		quitButton = new MenuButton("Quit");
		c.gridy = 3;
		buttonPanel.add(quitButton, c);
		
		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setBackground(Color.black);
		
		//load title image
		JPanel titlePanel = new JPanel();
		try{
			BufferedImage titleImage = ImageIO.read(new File("resources/menu/title.png"));
			JLabel title = new JLabel(new ImageIcon(titleImage.getScaledInstance(700, 200, Image.SCALE_FAST)));
			title.setOpaque(true);
			titlePanel.add(title);
		}catch(IOException e) {
			System.exit(1);
		}
		
		titlePanel.setBackground(Color.black);
		add(titlePanel, BorderLayout.NORTH);
		
		
		setBorder(new EmptyBorder(50, 50, 50, 50));
		setBackground(Color.black);
	}
	
}
