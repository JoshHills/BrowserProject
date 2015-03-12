import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author Josh Hills
 *
 * This class models a browser's tab bar
 * for displaying active open pages.
 */
public class TabBar {
	
	/* IMPORTANT NOTE: A USE FOR QUEUES COULD BE FOR TEMPORARY STORAGE OF CLOSED TABS. */
	
	// Parent reference.
	private Window window;
	
	// Main component to be styled and modified.
	private JTabbedPane tabBar;
	
	// List of currently active pages within window.
	private List<Page> pages = new ArrayList<Page>();
	
	// Store amount of open tabs.
	private int openTabs = 0;
	
	/**
	 * Constructor initialises components.
	 * 
	 * @param window	Abstract container.
	 */
	public TabBar(Window window) {
		
		// Set parent reference.
		this.window = window;
		// Instantiate.
		tabBar = new JTabbedPane();
		
		// Add the first tab to display.
		add();
		
	}

	/**
	 * Method adds a new tab to the screen.
	 */
	public void add() {
		
		pages.add(new Page());
		
		tabBar.addTab("Tab " + pages.size(), pages.get(pages.size() - 1).getComponent());

		openTabs++;
		
	}
	
	/* Accessor methods. */
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	JTabbedPane component modelled after a browser page.
	 */
	public JTabbedPane getComponent() {
		return tabBar;
	}
	
	public List<Page> getPages() {
		return pages;
	}

	
}
