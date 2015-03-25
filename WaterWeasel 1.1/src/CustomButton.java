import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This utility class prevents boilerplate code
 * incurred from creating JButtons with
 * custom graphics and/or cleared formatting.
 */
public class CustomButton {
	
	/**
	 * Method essentially creates a proper 'default' JButton.
	 * 
	 * @return	A JButton sans formatting.
	 */
	private static JButton createVanillaBtn() {
		
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
	
	/**
	 * Initialises button with a single image state.
	 * 
	 * @param image	The button's desired constant image.
	 * @return		The formatted button.
	 */
	public static JButton createButton(ImageIcon image) {
		
		// Create button without formatting.
		JButton btn = createVanillaBtn();
		// Set button image.
		btn.setIcon(image);
		
		return btn;
		
	}
	
	/**
	 * Overloaded method initialises a button with general and roll-over image states.
	 * 
	 * @param image		The button's desired static image.
	 * @param imageOver	The button's desired hover-over image.
	 * @return			The formatted button.
	 */
	public static JButton createButton(ImageIcon image, ImageIcon imageOver) {
		
		// Call above method.
		JButton btn = createButton(image);
		// Set button-over image.
		btn.setRolloverIcon(imageOver);
		
		return btn;
		
	}
	
	/**
	 * Overloaded method initialises a button with general, roll-over and down image states.
	 * 
	 * @param image		The button's desired static image.
	 * @param imageOver	The button's desired hover-over image.
	 * @param imageDown	The button's desired pressed image.
	 * @return			The formatted button.
	 */
	public static JButton createButton(ImageIcon image, ImageIcon imageOver, ImageIcon imageDown) {
		
		// Call above method.
		JButton btn = createButton(image, imageOver);
		// Set button-down image.
		btn.setPressedIcon(imageDown);
		
		return btn;
		
	}
	
	/**
	 * Overloaded method initialises a button with general, roll-over and down image states.
	 * 
	 * @param image			The button's desired static image.
	 * @param imageOver		The button's desired hover-over image.
	 * @param imageDown		The button's desired pressed image.
	 * @param imageDisabled	The button's desired disabled image.
	 * @return				The formatted button.
	 */
	public static JButton createButton(ImageIcon image, ImageIcon imageOver, ImageIcon imageDown, ImageIcon imageDisabled) {
		
		// Call above method.
		JButton btn = createButton(image, imageOver, imageDown);
		// Set button-disabled image.
		btn.setDisabledIcon(imageDisabled);
		
		return btn;
		
	}
	
}
