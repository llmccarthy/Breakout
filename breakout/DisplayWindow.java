package breakout;

import java.awt.*;
import javax.swing.*;

/*
 * DisplayWindow puts a graphic window in the display
 */

public class DisplayWindow extends JFrame{
	public DisplayWindow(){
		add(new Display());
		
		setSize(Display.getPanelWidth(), Display.getPanelHeight());
		setResizable(false);
		
		setTitle("Breakout");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
            	DisplayWindow ex = new DisplayWindow();
                ex.setVisible(true);
            }
        });
    }
}
