import javax.swing.JLabel;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class models a browser 'ribbon'.
 */
public class Ribbon {
	
	// Main component to be styled and modified.
	JLabel ribbon;
	
	/**
	 * Constructor sets-up the ribbon tool-bar by creating and compiling relevant components.
	 */
	public Ribbon() {
		
		// Set-up ribbon with properties and create and add components.
		
	}
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	JLabel component that composes the browser 'ribbon'.
	 */
	public JLabel getComponent() {
		
		return ribbon;
		
	}
	
}
