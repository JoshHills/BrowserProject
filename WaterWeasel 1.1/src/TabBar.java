import java.awt.Color;
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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

/**
 * @author Josh Hills
 *
 * This class models a browser's tab bar for displaying active open pages.
 * 
 * Model for adding a close button to the tab-bar inspired by a tutorial of 'java2s'
 * at 'http://www.java2s.com/Tutorial/Java/0240__Swing/AddButtontotabbar.htm'.
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
		// Make the tabs scroll horizontally when there are too many.
		tabBar.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		// Add the first tab to display.
		add();
		// Add a change listener- when the active tab is changed...
		tabBar.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				// Update the text in the address bar.
				window.getRibbon().getSearchBar().updateAddress();
				
			}
			
		});

	}
	
	/**
	 * Method adds a new tab to the screen.
	 */
	public void add() {
		
		// Add a new page to the list.
		pages.add(new Page(window));
		
		// Display the page in a new tab.
		tabBar.addTab(null, pages.get(pages.size() - 1).getComponent());
		
		// Set the new tab to be the active one.
		tabBar.setSelectedIndex(tabBar.getTabCount() - 1);

		// Create and apply a tab panel.
		tabBar.setTabComponentAt(tabBar.getTabCount() - 1, createTabPanel());
		
	}
	
	/**
	 * Method handles the initialisation of the inner components of a new tab-
	 * this includes creating and adding a title and a close button.
	 */
	private JPanel createTabPanel() {
		
		/* Create and style component. */
		
		// Create a new 'JPanel' to display the tab's title and button.
		JPanel tabPanel = new JPanel();
		// Make it transparent.
		tabPanel.setOpaque(false);
		
		/* Tab title. */
		
		// Add a temporary title to be updated by the change listener applied to the page.
		tabPanel.add(new JLabel("Loading..."));
		
		/* Close button. */
		
		// Add the close button.
		JButton closeBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/TabCloseBtn/placeholder.png"));
		// Give it an action listener- when clicked...
		closeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// If the user is closing the last tab.
				if(tabBar.getTabCount() == 1) {
					
					Browser.getInstance().close(window.getComponent());
					
				}
				else {
				
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
							
							// Update the text in the address field.
							window.getRibbon().getSearchBar().updateAddress();
							
							// Now finished, end loop.
							break;
							
						}
						
					}
					
				}
				
			}
			
		});
		// Add it to the tab panel.
		tabPanel.add(closeBtn);
		
		// Return the newly created panel.
		return tabPanel;
		
	}
	
	/**
	 * This method updates the title of the tab.
	 * 
	 * @param page	The page that has finished loading.
	 */
	public void updateTab(JEditorPane page) {
		
		// Loop through the all open tabs.
		for(int i = 0; i < tabBar.getTabCount(); i++) {
			
			// Temporarily select and cast the component being displayed in the tab.
			JScrollPane componentInTab = (JScrollPane) tabBar.getComponentAt(i);
			
			if(page.equals(componentInTab.getViewport().getComponent(0))) {
				
				// Through typecasting, target the text portion of the tab.
				JPanel tabHeader = (JPanel) tabBar.getTabComponentAt(i);
				JLabel tabLabel = (JLabel) tabHeader.getComponent(0);
				
				/* Scrape the HTML content of the web-page to find the content of the 'title' node. */
				
				
				
				// Now finished, end loop.
				break;
				
			}
			
		}
		
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
	
	/**
	 * Method returns a list of the window's open pages.
	 * 
	 * @return	A list of the window's open pages.
	 */
	public List<Page> getPages() {
		
		return pages;
		
	}
	
}
