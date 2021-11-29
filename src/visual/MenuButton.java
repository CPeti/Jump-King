package visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.Serial;
import javax.swing.JButton;
//adding some style to that beautiful swing UI <3
class MenuButton extends JButton{
	@Serial
	private static final long serialVersionUID = 1L;

	MenuButton(String s){
		super(s);
		setFocusPainted(false);
		Dimension menuButtonSize = new Dimension(200, 50);
		setPreferredSize(menuButtonSize);
		Font bigFont = super.getFont().deriveFont(Font.PLAIN, 30f);
		setFont(bigFont);
		setBackground(Color.LIGHT_GRAY);
		setBorderPainted(false);
	}
}