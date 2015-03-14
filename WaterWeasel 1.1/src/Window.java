import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
	
	// Declare a Layout Manager for fine-tuned positioning of inner components.
	private BorderLayout bl;
	
	/* Nested components. */
	
	// Instance of browser's 'ribbon' for this window.
	private Ribbon ribbon;
	
	// Instance of browser's bookmarks bar.
	private BookmarkBar bmBar;
	
	// Instance of browser's tab bar.
	private TabBar tabBar;
	
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
		if(Browser.getInstance().isMaximised()) {
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		// Set the window's initial theme.
		frame.getContentPane().setBackground(Browser.getInstance().getTheme());
		
		// Create layout.
		bl = new BorderLayout();
		// Set layout.
		frame.setLayout(bl);
		
		/* Create child components and add them. */
		
		// Create ribbon (utility tool-bar).
		ribbon = new Ribbon(this);
		// Add ribbon to window.
		frame.add(ribbon.getComponent(), BorderLayout.NORTH);
		
		// Create tab bar (manages tabs and pages).
		tabBar = new TabBar(this);
		// Add tab bar to window.
		frame.add(tabBar.getComponent());				
		
		// Add the keyboard shortcut listener.
		
		// Make the window visible post-initialisation.
		frame.setVisible(true);
		
	}
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	Loaded JFrame component modelled after a browser window.
	 */
	public JFrame getComponent() {
	
		return frame;
		
	}
	
	/**
	 * Method returns the tab bar component.
	 * 
	 * @return	This window's 'TabBar' instance.
	 */
	public TabBar getTabBar() {
		
		return tabBar;
	
	}
	
}
