import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class prevents boilerplate code
 * incurred from creating JButtons with
 * custom graphics.
 */
public class CustomButton {
	
	// Base method initialises button with a single image state.
	public static JButton createButton(ImageIcon image) {
		
		// Create button without formatting.
		JButton btn = vanillaBtn();
		// Set button image.
		btn.setIcon(image);
		
		return btn;
		
	}
	
	// Overloaded method initialises a button with general and roll-over image states.
	public static JButton createButton(ImageIcon image, ImageIcon imageOver) {
		
		// Create button without formatting.
		JButton btn = vanillaBtn();
		// Set button image.
		btn.setIcon(image);
		// Set button-over image.
		btn.setRolloverIcon(imageOver);
		
		return btn;
		
	}
	
	// Overloaded method initialises a button with general, roll-over and down image states.
	public static JButton createButton(ImageIcon image, ImageIcon imageOver, ImageIcon imageDown) {
		
		// Create button without formatting.
		JButton btn = vanillaBtn();
		// Set button image.
		btn.setIcon(image);
		// Set button-over image.
		btn.setRolloverIcon(imageOver);
		// Set button-down image.
		btn.setPressedIcon(imageDown);
		
		return btn;
		
	}
	
	// Method essentially creates a different 'default' JButton.
	private static JButton vanillaBtn() {
		
		// Create button.
		JButton btn = new JButton();
		
		/* Clear default formatting. */
		
		// Remove colour-fill.
		btn.setContentAreaFilled(false);		
		// Remove margin.
		btn.setMargin(new Insets(0, 0, 0, 0));
		// Remove border.
		btn.setBorder(null);
		btn.setBorderPainted(false);
		
		return btn;
		
	}
	
}
