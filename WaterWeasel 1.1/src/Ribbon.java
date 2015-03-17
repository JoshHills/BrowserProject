import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

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
	private JButton logoBtn, backBtn, forwardBtn, refreshBtn, goBtn, homeBtn;
	
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
		
		
	}
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	JPanel component that composes the browser 'ribbon'.
	 */
	public JPanel getComponent() {
		
		return ribbon;
		
	}
	
}
