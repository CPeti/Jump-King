package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import visual.*;

public class FrameTest {
    MainFrame f;

    @Test
    public void panelChangeTest(){
        f = new MainFrame();
        assertEquals(MenuPanel.class, f.getActive().getClass());
        f.changePanel(new GamePanel());
        assertEquals(GamePanel.class, f.getActive().getClass());
    }
}
