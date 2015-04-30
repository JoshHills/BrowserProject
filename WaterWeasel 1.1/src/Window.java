import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.net.URL;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	// Instance of browser's history display window.
	private JFrame history;
	
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
				
				// Pass the frame from this window to the close operation.
				Browser.getInstance().close(frame);
				
			}
			
		});
		// Set the window's initial size.
		frame.setSize(Browser.getInstance().getXSize(), Browser.getInstance().getYSize());
		// Set the window's initial position.
		frame.setLocation(Browser.getInstance().getXPosition(), Browser.getInstance().getYPosition());
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
		
		// Create bookmarks bar.
		bmBar = new BookmarkBar(this);
		// Add bookmark bar to window.
		frame.add(bmBar.getComponent(), BorderLayout.EAST);
		
		// Create tab bar (manages tabs and pages).
		tabBar = new TabBar(this);
		// Add tab bar to window.
		frame.add(tabBar.getComponent());
		
		// Add the keyboard shortcut listener.
		// TODO: window shortcuts
		
		// Make the window visible post-initialisation.
		frame.setVisible(true);
		
	}
	
	/**
	 * Method displays user history in a new window.
	 */
	public void showHistory() {
		
		/* Set up defaults for history window. */
		
		// Create the window.
		history = new JFrame("History");
		// Add a window adapter to deal with the closing of this window.
		history.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				
				// Dispose of it properly.
				history.setVisible(false);
				history.dispose();
				
			}
			
		});
		// Set its size.
		history.setSize(500, 500);
		// Set its position.
		history.setLocationRelativeTo(null);
		
		/* Fill with information. */
		
		// Create a panel to store history.
		JPanel historyPanel = new JPanel();
		
		// Iterate over history storage, adding them graphically to the panel. For each...
		for(SortedMap.Entry<Date, URL> entry : Browser.getInstance().getHistory().entrySet()) {
			
			// Create a new label to store the history.
			JPanel historyItem = new JPanel();
			
			// Supply a method of removing individual history items.
			JButton removeHistoryItem = new JButton("X");
			// Add an action listener- if clicked...
			removeHistoryItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					// Remove the graphical representation of this item.
					historyPanel.remove(historyItem);
					history.revalidate();
					history.repaint();
					
					// Remove this specific history item from storage.
					Browser.getInstance().getHistory().remove(entry.getKey(), entry.getValue());
					
				}
				
			});
			// Add this button to the panel.
			historyItem.add(removeHistoryItem);
			
			// Add information about the history item.
			historyItem.add(new JLabel("Date Visited: " + entry.getKey().toString()
					+ " URL: " + entry.getValue().toString()));
			
			historyPanel.add(historyItem);
			
		}
		
		// Add the panel to the window.
		history.add(historyPanel);
		
		// Display the window.
		history.setVisible(true);
		
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
	 * Method returns the ribbon component.
	 * 
	 * @return	This window's 'Ribbon' instance.
	 */
	public Ribbon getRibbon() {
		
		return ribbon;
		
	}
	
	/**
	 * Method returns the bookmark bar component.
	 * 
	 * @return	This window's bookmark bar.
	 */
	public BookmarkBar getBmBar() {
		
		return bmBar;
		
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
	 * Method returns whether the window is set to
	 * record history or not.
	 * 
	 * @return	Boolean value indicating the privacy state of the window.
	 */
	public boolean isIncognito() {
		
		return incognito;
		
	}
	
}
