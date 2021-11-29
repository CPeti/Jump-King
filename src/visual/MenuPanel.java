package visual;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MenuPanel extends JPanel{
	@Serial
	private static final long serialVersionUID = 1L;
	
	protected MenuButton ngButton;
	protected MenuButton loadButton;
	protected MenuButton optButton;
	protected MenuButton exitButton;


	public MenuPanel() {
		//the menu panel uses border layout, so the button panel 
		//and the title panel can be placed above eachother easily
		BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

		//the menu panel contains 2 other panels, one with the buttons and one with the title image
		JPanel buttonPanel = new JPanel();
		//the button panel uses gridbaglayout, because it respects the components' preferred sizes
        buttonPanel.setLayout(new GridBagLayout());
        //add constraint to place buttons above eachother in the grid
        //gridy parameter represents the vertical position of the button in the grid
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        //add padding between the buttons
        c.insets = new Insets(10,0,0,0);  //top padding
        
		ngButton = new MenuButton("New game");
		c.gridy = 0;
		buttonPanel.add(ngButton, c);
		
		loadButton = new MenuButton("Load game");
		c.gridy = 1;
		buttonPanel.add(loadButton, c);
		
		optButton =  new MenuButton("Hitboxes: off");
		c.gridy = 2;
		buttonPanel.add(optButton, c);
		
		exitButton = new MenuButton("Exit");
		c.gridy = 3;
		buttonPanel.add(exitButton, c);
		exitButton.addActionListener(ae -> System.exit(0));

		//set button panel on the bottom of the window
		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setBackground(Color.black);


		JPanel titlePanel = new JPanel();
		//load title image
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
		
		//add border to menu panel to push the button panel up
		setBorder(new EmptyBorder(50, 50, 50, 50));
		setBackground(Color.black);
	}
	
}
