import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Josh Hills
 *
 * This class models a browser's tab bar
 * for displaying active open pages.
 */
public class TabBar {
	
	// Parent reference.
	private Window window;
	
	// Main component to be styled and modified.
	private JPanel tabBar;
	
	// List of currently active pages within window.
	private List<Page> pages = new ArrayList<Page>();	
	
	/**
	 * Constructor initialises 'tabBar' and calls 'refresh' method for first time.
	 * 
	 * @param window	Abstract container.
	 */
	public TabBar(Window window) {
		
		// Set parent reference.
		this.window = window;
		// Instantiate.
		tabBar = new JPanel();
		// Apply relevant layout manager.
		tabBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// Call refresh to add and display components.
		refresh();
		
	}

	/**
	 * Method creates a formatted JLabel from user bookmarks.
	 */
	public void refresh() {
		
		// Remove all existing components (some may have been removed or added since).
		tabBar.removeAll();
		
		/* Produce a visual representation for each 'Page' object using Swing components. */
		
		// Create a temporary list of 'JButton's.
		List<JButton> activeTabs = new ArrayList<JButton>();
		
		for(Page page : Browser.getInstance().getWindows().get(0).getTabs()) {
			
			activeTabs.add(new JButton("Tab" + (activeTabs.size() + 1)));
			
			
		}
		
		
	}
	
	/* Accessor methods. */
	
	public List<Page> getTabs() {
		return pages;
	}

	
}
