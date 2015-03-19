import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class models a browser 'ribbon'.
 */
public class Ribbon {
	
	// Parent reference.
	private Window window;
	
	// Main component to be styled and modified.
	private JPanel ribbon;
	
	// Declare a Layout Manager for fine-tuned positioning of inner components.
	private FlowLayout fl;
	
	// Buttons to be placed on the ribbon.
	private JButton logoBtn, backBtn, forwardBtn, refreshBtn, goBtn, homeBtn, optsBtn;
	
	// Search bar.
	private SearchBar searchBar;
	
	// Options menu.
	
	
	/**
	 * Constructor sets-up the ribbon tool-bar by creating and compiling relevant components.
	 * 
	 * @param window	Abstract container.
	 */
	public Ribbon(Window window) {
		
		/* Set-up ribbon with properties. */
		
		// Create layout.
		fl = new FlowLayout();		
		// Create ribbon.
		ribbon = new JPanel(fl);
		
		/* Logo (about) button. */
		
		// Create the logo button.
		logoBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/LogoBtn/placeholder.png"));
		// Add it to the ribbon.
		ribbon.add(logoBtn);
		
		/* Back button. */
		
		// Create a 'back' button.
		backBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/BackBtn/placeholder.png"));
		// Add an action listener- if clicked...
		backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Conduct operation on the correct page based on the actively selected tab.
				window.getTabBar().getPages().get(
						window.getTabBar().getComponent().getSelectedIndex()).back();
				// Update address bar accordingly.
				window.getTabBar().updateAddress();
				
			}
			
		});
		// Add it to the ribbon.
		ribbon.add(backBtn);	
		
		/* Forward button. */
		
		// Create a 'forward' button.
		forwardBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/ForwardBtn/placeholder.png"));
		// Add an action listener- if clicked...
		forwardBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Conduct operation on the correct page based on the actively selected tab.
				window.getTabBar().getPages().get(
						window.getTabBar().getComponent().getSelectedIndex()).forward();
				// Update address bar accordingly.
				window.getTabBar().updateAddress();
				
			}
			
		});
		// Add it to the ribbon.
		ribbon.add(forwardBtn);	
		
		/* Refresh button. */
		
		// Create a 'refresh' button.
		refreshBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/RefreshBtn/placeholder.png"));
		// Add an action listener- if clicked...
		refreshBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Conduct operation on the correct page based on the actively selected tab.
				window.getTabBar().getPages().get(
						window.getTabBar().getComponent().getSelectedIndex()).refresh();
				
			}
			
		});
		// Add it to the ribbon.
		ribbon.add(refreshBtn);
		
		/* Search bar. */
		
		// Create a 'search bar'.
		searchBar = new SearchBar(window);
		// Add it to the ribbon.
		ribbon.add(searchBar.getComponent());
		
		/* Go button. */
		
		// Create a 'go' button.
		goBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/GoBtn/placeholder.png"));
		// Add an action listener- if clicked...
		goBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				searchBar.fire();
				
			}
			
		});
		// Add it to the ribbon.
		ribbon.add(goBtn);
		
		/* Home button. */
		
		// Create a 'home' button.
		homeBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/HomeBtn/placeholder.png"));
		// Add an action listener- if clicked...
		homeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// If the active tab is already at the homepage.
				if(searchBar.getAddressField().getText().equals(Browser.getInstance().getHomepage().toString())) {
					
					// Make it refresh to check for updates.
					window.getTabBar().getPages().get(
							window.getTabBar().getComponent().getSelectedIndex()).refresh();
					
				}
				// Otherwise revert the active-tab to the browser's homepage.
				else {
					
					window.getTabBar().getPages().get(
						window.getTabBar().getComponent().getSelectedIndex()).show(
								Browser.getInstance().getHomepage());
					// Ensure the text in the address field updates correctly.
					searchBar.getAddressField().setText(Browser.getInstance().getHomepage().toString());	
				
				}
				
			}
			
		});
		// Add it to the ribbon.
		ribbon.add(homeBtn);
		
		/* Options Menu. */
		
		// Create a pop-up menu.
		JPopupMenu optsMenu = new JPopupMenu();
		
		/* Add options. */
		
		// Create new tab item.
		JMenuItem newTab = new JMenuItem("New Tab");
		// Set its mnemonic.
		newTab.setMnemonic(KeyEvent.VK_N);
		// Add its action listener- if clicked...
		newTab.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				window.getTabBar().add();
				
			}
						
		});
		// Add it to the menu.
		optsMenu.add(newTab);
		
		// Create a new window item.
		JMenuItem newWindow = new JMenuItem("New Window");
		// Set its mnemonic.
		newWindow.setMnemonic(KeyEvent.VK_W);
		// Add its action listener- if clicked...
		newWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Open a new window.
				Browser.getInstance().add();
				
			}
			
		});
		// Add it to the menu.
		optsMenu.add(newWindow);
		
		// Create a new incognito window item.
		JMenuItem newIncognitoWindow = new JMenuItem("New Incognito Window");
		// Set its mnemonic.
		newIncognitoWindow.setMnemonic(KeyEvent.VK_I);
		// Add it's action listener- if clicked...
		/* CODE HERE */
		// Add it to the menu.
		optsMenu.add(newIncognitoWindow);
		
		// Add a separator.
		optsMenu.addSeparator();
		
// Bookmark.
		
		// Create an 'options' button.
		optsBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/OptsBtn/placeholder.png"));
		// Add an action listener- if clicked...
		optsBtn.addActionListener(new ActionListener() {

			/**
			 * This method handles the displaying of the pop-up settings
			 * menu upon clicking the options button.
			 * 
			 * @Override
			 */
			public void actionPerformed(ActionEvent e) {
				
				// Get the source component of the event.
				JComponent comp = (JComponent) e.getSource();
				
				// Get the location of the point on screen.
				Point point = comp.getLocationOnScreen();
				
				// Show the options menu.
				optsMenu.show(comp, 0, 0);
				
				// Anchor the options menu to the calling component.
				optsMenu.setLocation(point.x - (optsMenu.getWidth() - optsBtn.getWidth()), point.y + comp.getHeight());
				
			}

		});
		// Add it to the ribbon.
		ribbon.add(optsBtn);
		
	}
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	JPanel component that composes the browser 'ribbon'.
	 */
	public JPanel getComponent() {
		
		return ribbon;
		
	}
	
	/**
	 * Method returns the search bar component of the ribbon.

	 * @return	SearchBar object that composes the browser's search bar.
	 */
	public SearchBar getSearchBar() {
		
		return searchBar;
		
	}
	
}
