import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

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
	private BoxLayout bl;
	
	// Buttons to be placed on the ribbon.
	private JButton logoBtn, backBtn, forwardBtn, refreshBtn, goBtn, homeBtn, optsBtn;
	
	// Search bar.
	private SearchBar searchBar;
	
	// Options menu.
	private JPopupMenu optsMenu;
	
	/**
	 * Constructor sets-up the ribbon tool-bar by creating and compiling relevant components.
	 * 
	 * @param window	Abstract container.
	 */
	public Ribbon(Window window) {
		
		/* Set-up ribbon with properties. */
		
		// Create ribbon.
		ribbon = new JPanel();
		// Create layout.
		bl = new BoxLayout(ribbon, BoxLayout.LINE_AXIS);
		// Set the ribbon's layout manager.
		ribbon.setLayout(bl);
		// Give the ribbon some padding.
		ribbon.setBorder(new EmptyBorder(5,5,5,5));
		// Make it transparent.
		ribbon.setOpaque(false);
		
		/* Logo (about) button. */
		
		// Create the logo button.
		logoBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/LogoBtn/logo.png"));
		// Add it to the ribbon.
		ribbon.add(logoBtn);
		// Add some spacing.
		ribbon.add(Box.createRigidArea(new Dimension(5,0)));
		
		/* Back button. */
		
		// Create a 'back' button.
		backBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/BackBtn/BackBtn.png"),
				new ImageIcon("./Assets/ButtonIcons/BackBtn/BackBtnOver.png"),
				new ImageIcon("./Assets/ButtonIcons/BackBtn/BackBtnDn.png"),
				new ImageIcon("./Assets/ButtonIcons/BackBtn/BackBtnDisabled.png"));
		// Add an action listener- if clicked...
		backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Conduct operation on the correct page based on the actively selected tab.
				window.getTabBar().getPages().get(
						window.getTabBar().getComponent().getSelectedIndex()).back();
				// Update address bar accordingly.
				searchBar.updateAddress();
				
			}
			
		});
		// Add it to the ribbon.
		ribbon.add(backBtn);	
		
		/* Forward button. */
		
		// Create a 'forward' button.
		forwardBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/ForwardBtn/ForwardBtn.png"),
				new ImageIcon("./Assets/ButtonIcons/ForwardBtn/ForwardBtnOver.png"),
				new ImageIcon("./Assets/ButtonIcons/ForwardBtn/ForwardBtnDn.png"),
				new ImageIcon("./Assets/ButtonIcons/ForwardBtn/ForwardBtnDisabled.png"));
		// Add an action listener- if clicked...
		forwardBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Conduct operation on the correct page based on the actively selected tab.
				window.getTabBar().getPages().get(
						window.getTabBar().getComponent().getSelectedIndex()).forward();
				// Update address bar accordingly.
				searchBar.updateAddress();
				
			}
			
		});
		// Add it to the ribbon.
		ribbon.add(forwardBtn);
		// Add some spacing.
		ribbon.add(Box.createRigidArea(new Dimension(5,0)));
		
		/* Refresh button. */
		
		// Create a 'refresh' button.
		refreshBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/RefreshBtn/RefreshBtn.png"),
				new ImageIcon("./Assets/ButtonIcons/RefreshBtn/RefreshBtnOver.png"),
				new ImageIcon("./Assets/ButtonIcons/RefreshBtn/RefreshBtnDn.png"));
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
		// Add some spacing.
		ribbon.add(Box.createRigidArea(new Dimension(5,0)));
		
		/* Search bar. */
		
		// Create a 'search bar'.
		searchBar = new SearchBar(window);
		// Add it to the ribbon.
		ribbon.add(searchBar.getComponent());
		// Add some spacing.
		ribbon.add(Box.createRigidArea(new Dimension(5,0)));
		
		/* Go button. */
		
		// Create a 'go' button.
		goBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/GoBtn/GoBtn.png"),
				new ImageIcon("./Assets/ButtonIcons/GoBtn/GoBtnOver.png"),
				new ImageIcon("./Assets/ButtonIcons/GoBtn/GoBtnDn.png"));
		// Add an action listener- if clicked...
		goBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				searchBar.fire();
				
			}
			
		});
		// Add it to the ribbon.
		ribbon.add(goBtn);
		// Add some spacing.
		ribbon.add(Box.createRigidArea(new Dimension(5,0)));
		
		/* Home button. */
		
		// Create a 'home' button.
		homeBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/HomeBtn/HomeBtn.png"),
				new ImageIcon("./Assets/ButtonIcons/HomeBtn/HomeBtnOver.png"),
				new ImageIcon("./Assets/ButtonIcons/HomeBtn/HomeBtnDn.png"));
		// Add an action listener- if clicked...
		homeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				// Load the homepage to the active tab.
				window.getTabBar().getPages().get(
					window.getTabBar().getComponent().getSelectedIndex()).show(
							Browser.getInstance().getHomepage());
				// Ensure the text in the address field updates correctly.
				searchBar.getAddressField().setText(Browser.getInstance().getHomepage().toString());
				
			}
			
		});
		// Add it to the ribbon.
		ribbon.add(homeBtn);
		// Add some spacing.
		ribbon.add(Box.createRigidArea(new Dimension(5,0)));
		
		// Create a pop-up menu.
		optsMenu = new JPopupMenu();
		
		/* Add options. */
		
		// Create new tab item.
		JMenuItem newTab = new JMenuItem("New Tab");
		// Set its mnemonic.
		newTab.setMnemonic(KeyEvent.VK_N);
		// Add its action listener- if clicked...
		newTab.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Open a new tab.
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
			public void actionPerformed(ActionEvent e) {
				
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
		// Add its action listener- if clicked...
		newIncognitoWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Open a new window.
				Browser.getInstance().add(true);
				
			}
			
		});
		// Add it to the menu.
		optsMenu.add(newIncognitoWindow);
		
		// Add a separator to the menu.
		optsMenu.addSeparator();
		
		// Add an option for the user to change their homepage.
		JMenuItem changeHomepage = new JMenuItem("Change Homepage");
		// Set it's mnemonic.
		changeHomepage.setMnemonic(KeyEvent.VK_C);
		// Add its action listener- if clicked...
		changeHomepage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Display a form to enter a new homepage.
				String newHomepage = JOptionPane.showInputDialog(
						"Enter a new homepage:", Browser.getInstance().getHomepage());
				
				// If the homepage validates...
				if(Browser.makeURL(newHomepage) != null) {
					
					// Set the homepage to the new page.
					Browser.getInstance().setHomepage(Browser.makeURL(newHomepage));
					
				}
				
			}
			
		});
		// Add it to the window.
		optsMenu.add(changeHomepage);
		
		// Add a separator to the menu.
		optsMenu.addSeparator();
		
		// Create a new bookmark item.
		JMenuItem addBookmark = new JMenuItem("Add Bookmark");
		// Set it's mnemonic.
		addBookmark.setMnemonic(KeyEvent.VK_A);
		// Add its action listener- if clicked...
		addBookmark.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Open the form to create a new bookmark.
				window.getBmBar().initBookmarkForm();
				
			}
			
		});
		// Add it to the menu.
		optsMenu.add(addBookmark);
		
		// Create an option to toggle the bookmarks bar.
		JMenuItem showhideBookmarks = new JMenuItem("Show/Hide Bookmarks Bar");
		// Set its mnemonic.
		showhideBookmarks.setMnemonic(KeyEvent.VK_B);
		// Add its action listener- if clicked...
		showhideBookmarks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Toggle visibility of bookmarks bar.
				if(window.getBmBar().getComponent().isVisible()) {
					
					window.getBmBar().getComponent().setVisible(false);
					window.getComponent().revalidate();
					window.getComponent().repaint();
					
					// Log user preference.
					Browser.getInstance().setBmsVisible(false);
					
				}
				else {
					
					window.getBmBar().getComponent().setVisible(true);
					window.getComponent().revalidate();
					window.getComponent().repaint();
					
					// Log user preference.
					Browser.getInstance().setBmsVisible(true);
					
				}
				
			}
			
		});
		// Add it to the menu.
		optsMenu.add(showhideBookmarks);
		
		// Add another separator to the menu.
		optsMenu.addSeparator();
		
		// Create an option to change the user's homepage.
		
		// Create an option to view the user's browsing history.
		JMenuItem viewHistory = new JMenuItem("View History");
		// Set its mnemonic.
		viewHistory.setMnemonic(KeyEvent.VK_H);
		// Add its action listener- if clicked...
		viewHistory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Open a window displaying the user's history.
				window.showHistory();
				
			}
			
		});
		// Add it to the menu.
		optsMenu.add(viewHistory);
		
		// Create an 'options' button.
		optsBtn = CustomButton.createButton(new ImageIcon("./Assets/ButtonIcons/OptsBtn/placeholder.png"));
		// Add an action listener- if clicked...
		optsBtn.addActionListener(new ActionListener() {

			/**
			 * This method handles the displaying and positioning of the
			 * pop-up settings menu upon clicking the options button.
			 */
			@Override
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
