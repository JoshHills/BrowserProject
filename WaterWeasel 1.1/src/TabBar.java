import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Josh Hills
 * 
 * Model for tab-bar button retrieval inspired by a tutorial of 'java2s'
 * at 'http://www.java2s.com/Tutorial/Java/0240__Swing/AddButtontotabbar.htm'.
 *
 * This class models a browser's tab bar for displaying active open pages.
 */
public class TabBar {
	
	/* IMPORTANT NOTE: A USE FOR QUEUES COULD BE FOR TEMPORARY STORAGE OF CLOSED TABS. */
	
	// Parent reference.
	private Window window;
	
	// Main component to be styled and modified.
	private JTabbedPane tabBar;
	
	// List of currently active pages within window.
	private List<Page> pages = new ArrayList<Page>();
	
	/**
	 * Constructor initialises components.
	 * 
	 * @param window	Abstract container.
	 */
	public TabBar(Window window) {
		
		// Set parent reference.
		this.window = window;
		
		// Instantiate the tabbed pane.
		tabBar = new JTabbedPane();
		// Add the first tab to display.
		add();
		// Add a change listener- when the active tab is changed...
		tabBar.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				// Update the text in the address bar.
				updateAddress();
				
			}
			
		});

	}
	
	/**
	 * Method adds a new tab to the screen.
	 */
	public void add() {
		
		// Add a new page to the list.
		pages.add(new Page());
		
		// Display the page in a new tab.
		tabBar.addTab(null, pages.get(pages.size() - 1).getComponent());
		
		/* Set-up the tab's panel. */
		
		// Create a new 'JPanel' to display the tab's title and button.
		JPanel tabPanel = new JPanel();
		// Make it transparent.
		tabPanel.setOpaque(false);
		// Give it a title.
		/* CODE HERE TO GRAB WEB-PAGE <title> TAG CONTENT! TEMPORARY ONE BELOW. */
		tabPanel.add(new JLabel("Tab " + pages.size()));
		// Add the close button.
		JButton closeBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/TabCloseBtn/placeholder.png"));
		// Give it an action listener- when clicked...
		closeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Retrieve the specific source button.
				JButton source = (JButton) e.getSource();
				
				// Loop through the all open tabs.
				for(int i = 0; i < tabBar.getTabCount(); i++) {
					
					// Grab the tab panel of the currently selected tab.
					JPanel pnl = (JPanel) tabBar.getTabComponentAt(i);
					// Grab the button from the panel.
					JButton btn = (JButton) pnl.getComponent(1);
					// If this is the button that has been clicked...
					if(btn == source) {
						
						// Remove the tab from the GUI.
						tabBar.removeTabAt(i);
						// Remove the page from memory.				
						pages.remove(i);
						
						updateAddress();
						
					}
					
				}
				
			}
			
		});
		// Add it to the tab panel.
		tabPanel.add(closeBtn);
		// Apply the tab panel.
		tabBar.setTabComponentAt(tabBar.getTabCount() - 1, tabPanel);
		
		// Set the new tab to be the active one.
		tabBar.setSelectedIndex(tabBar.getTabCount() - 1);
		
		// Get the address bar and set its text to match the page of the newly active tab.
		window.getRibbon().getSearchBar().getAddressField().setText(
			pages.get(tabBar.getSelectedIndex()).getCurrentURL().toString());
		
	}
	
	/**
	 * This method acts as a pseudo change-event, insomuch as it is called in various circumstances
	 * where the tab bar has been updated and therefore the text of the address bar must reflect this-
	 * it removes boilerplate code.
	 */
	public void updateAddress() {
		
		// Get the address bar and set its text to match the page of the newly active tab.
		window.getRibbon().getSearchBar().getAddressField().setText(
			pages.get(tabBar.getSelectedIndex()).getCurrentURL().toString());
		
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
