import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class models a custom browser window.
 */
public class Window {
		
	// Root component to be styled and modified.
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
	
	/* Window settings. */
	
	// Stores whether the window's functions will log URL operations- defaulted to false.
	private boolean incognito = false;
	
	/**
	 * Constructor sets-up window properties and adds the class' shortcut listener.
	 */
	public Window() {
		
		/* Create and set-up (style) the main window component. */
		
		// Initialise 'JFrame' variable to an object (window) with the browser's name at the top.
		frame = new JFrame(Browser.getInstance().getBROWSER_NAME());
		// Add a window adapter to deal with the closing of this window.
		frame.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				
				// If this is not the last open window...
				if(Browser.getInstance().getWindows().size() != 1) {
					
					// Dispose of the window properly.
					frame.setVisible(false);
					frame.dispose();					
					// Remove this window.
					Browser.getInstance().getWindows().remove(this);
					
				}
				// If it is...
				else {
				
					// Pass control to the upper management layer to close program properly.
					Browser.getInstance().close();
				
				}
				
			}
			
		});
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
	 * Method compounds operations that enable browser-privacy.
	 * 
	 * @param incognito	State to set the window's privacy to.
	 */
	public void makeIncognito(boolean incognito) {
		
		// If the state is being set to true.
		if(incognito) {
			
			// Adjust the field.
			this.incognito = incognito;
			// Change the window's theme.
			frame.getContentPane().setBackground(Browser.getInstance().getDEFAULT_PRIVATE_THEME());
			
		}
		
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
	
	/**
	 * Method returns the ribbon component.
	 * 
	 * @return	This window's 'Ribbon' instance.
	 */
	public Ribbon getRibbon() {
		
		return ribbon;
		
	}
	
	/**
	 * Method returns whether the window is set to
	 * record history or not.
	 * 
	 * @return	Boolean value indicating the privacy state of the window.
	 */
	public boolean isIncognito() {
		
		return incognito;
		
	}
	
}
