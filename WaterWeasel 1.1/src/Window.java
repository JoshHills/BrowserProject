import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class models a browser window.
 */
public class Window {
		
	// Main component to be styled and modified.
	private JFrame frame;
	
	/* Nested components. */
	
	// Instance of browser's 'ribbon' for this window.
	private Ribbon ribbon;
	
	// Instance of browser's bookmarks bar.
	private BookmarkBar bmBar;
	
	// List of currently active tabs within window.
	private List<Page> tabs = new ArrayList<Page>();
	
	/**
	 * Constructor sets-up window properties and adds the class' shortcut listener.
	 */
	public Window() {
		
		/* Create and set-up (style) the main window component. */
		
		// Initialise 'JFrame' variable to object (window) with the browser's name at the top.
		frame = new JFrame(Browser.getInstance().getBROWSER_NAME());
		// Set the window's default close action.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set the window's initial size.
		frame.setSize(Browser.getInstance().getxSize(), Browser.getInstance().getySize());
		// Set the window's initial extended state.
		if(Browser.getInstance().isMaximised())
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// Set the window's initial theme.
		frame.getContentPane().setBackground(Browser.getInstance().getTheme());
		
		/* Create child components and add them. */
		
		
		// Add the keyboard shortcut listener.
		
		
	}
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	JFrame component modelled after a browser window.
	 */
	public JFrame getComponent() {
	
		return frame;
		
	}
	
	public List<Page> getTabs() {
		return tabs;
	}

}
