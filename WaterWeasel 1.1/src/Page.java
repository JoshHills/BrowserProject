import javax.swing.JEditorPane;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class models a browser web-page.
 */
public class Page {

	// Main component to be styled and modified.
	private JEditorPane page;
	
	/**
	 * Constructor sets-up page properties and adds a hyper-link listener.
	 */
	public Page() {
		
		// Create and set-up.
		
		// Add the hyper-link listener.
		
	}
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	JEditorPane component modelled after a browser page.
	 */
	public JEditorPane getComponent() {
		
		return page;
		
	}
	
}
