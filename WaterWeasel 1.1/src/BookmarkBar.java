import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * @author Josh Hills
 * 
 * This class models a browser's 'bookmarks' bar.
 */
public class BookmarkBar {

	// Parent container.
	private Window window;
	
	// Collection of loaded icon resources able to be linked to bookmarks.
	private Icon[] icons;
	
	// Main component to be styled and modified.
	private JPanel bookmarkBar;
	
	// Wrapper to add scrolling functionality.
	private JScrollPane bookmarkBarScroll;
	
	// Form to display which lets the user create a bookmark.
	private JFrame bookmarkForm;
	
	public BookmarkBar(Window window) {
		
		// Set parent container.
		this.window = window;
		
		/* Load 'Bookmark' icons. */
		
		// Create an array of file-paths to required resources.
		File[] bmIcons = new File("./Assets/BookmarkIcons").listFiles();
		// Set size of 'icon' array to the number of icons available.
		icons = new Icon[bmIcons.length];
		// Set each 'Icon' reference variable to a new 'ImageIcon' object.
		for(int i = 0; i < icons.length; i++) {
			icons[i] = new ImageIcon(bmIcons[i].getPath());
		}
		
		// Create panel.
		bookmarkBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// Make it transparent.
		bookmarkBar.setOpaque(false);
		
		// Initialise it with pre-existing bookmarks.
		initBookmarkBar();
		
		// Wrap everything in a scrollable pane.
		bookmarkBarScroll = new JScrollPane(bookmarkBar, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// Allow some padding inside to accommodate for dynamically appearing scrollbar.
		bookmarkBarScroll.setMinimumSize(new Dimension(0,55));
		// Remove it's styling.
		bookmarkBarScroll.setBorder(null);
		bookmarkBarScroll.setOpaque(false);
		bookmarkBarScroll.getViewport().setOpaque(false);
				
	}
	
	private void initBookmarkBar() {
		
		// Add a button that allows the user to add a bookmark.
		JButton addBm = new JButton("+");
		// Add an action listener- if clicked...
		addBm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Open bookmark creation form.
				initBookmarkForm();
				
			}
			
		});
		// Remove unwanted styling.
		addBm.setFocusable(false);
		// Add it to the bar.
		bookmarkBar.add(addBm);
		
		// For every bookmark that the browser has stored...
		for(int i = 0; i < Browser.getInstance().getBookmarks().size(); i++) {
			
			// Display each graphically.
			addBookmark(Browser.getInstance().getBookmarks().get(i));
			
		}
		
	}
	
	/**
	 * Method adds a bookmark to the GUI and logs it in browser storage.
	 * The view incorporates related functionality such as the ability to
	 * click to visit the link, and right-click to delete.
	 * 
	 * @param bookmark	The bookmark to display and store.
	 */
	private void addBookmark(Bookmark bookmark) {
		
		/* Create a new button to perform operations upon. */
		
		JButton bookmarkButton = new JButton();
		// Give it an icon.
		bookmarkButton.setIcon(bookmark.getIcon());
		// Give it some text.
		bookmarkButton.setText(bookmark.getName());
		// Add an action listener- if clicked...
		bookmarkButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Change the active page to the bookmark location.
				window.getTabBar().getPages().get(
						window.getTabBar().getComponent().getSelectedIndex()).show(
								bookmark.getURL());
				
			}

		});
		// Remove unwanted styling.
		bookmarkButton.setFocusable(false);
		
		/* Add a method of removing the bookmark. */
		
		JPopupMenu bookmarkMenu = new JPopupMenu();
		JMenuItem deleteBookmark = new JMenuItem("Delete");
		deleteBookmark.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Remove the bookmark from display.
				bookmarkBar.remove(bookmarkButton);
				
				// Update the GUI.
				bookmarkBar.revalidate();
				bookmarkBar.repaint();
				
				// Remove the bookmark from storage.
				Browser.getInstance().getBookmarks().remove(bookmark);
				
			}
			
		});
		bookmarkMenu.add(deleteBookmark);
		bookmarkButton.setComponentPopupMenu(bookmarkMenu);
		
		// Add the bookmark to the bar.
		bookmarkBar.add(bookmarkButton);
		
		// Update the GUI.
		bookmarkBar.revalidate();
		
	}
	
	/**
	 * This method initialises a blueprint for a form
	 * to handle the user-creation of 'Bookmark' objects.
	 */
	public void initBookmarkForm() {
		
		// Create the form window.
		bookmarkForm = new JFrame("Add a new Bookmark...");
		// Add a window adapter to deal with the closing of this window.
		bookmarkForm.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				
				// Dispose of it properly.
				bookmarkForm.setVisible(false);
				bookmarkForm.dispose();
				
			}
			
		});
		// Set its size.
		bookmarkForm.setMinimumSize(new Dimension(300,200));
		bookmarkForm.setResizable(false);
		// Centre its position.
		bookmarkForm.setLocationRelativeTo(null);
		// Set its layout.
		bookmarkForm.setLayout(new BoxLayout(bookmarkForm.getContentPane(), BoxLayout.Y_AXIS));
		
		/* Comprising components. */
		
		// Name of bookmark.
		JPanel nameSet = new JPanel();
		JTextField name = new JTextField(10);
		// Smart-auto-fill the field by grabbing the title of the current webpage.
		// TODO: make a utility method for this
		if(window.getTabBar().getPages().get(
				window.getTabBar().getComponent().getSelectedIndex())
					.getInnerComponent().getDocument().getProperty("title") != null) {
			
			name.setText((String) window.getTabBar().getPages().get(
				window.getTabBar().getComponent().getSelectedIndex())
					.getInnerComponent().getDocument().getProperty("title"));
			
		}
		else {
			
			name.setText("New Bookmark");
			
		}
		nameSet.add(new JLabel("Name:"));
		nameSet.add(name);
		bookmarkForm.add(nameSet);
		
		// Address of bookmark.
		JPanel addressSet = new JPanel();
		JTextField address = new JTextField(10);
		address.setText(window.getTabBar().getPages().get(
				window.getTabBar().getComponent().getSelectedIndex())
					.getCurrentURL().toString());
		addressSet.add(new JLabel("URL:"));
		addressSet.add(address);
		bookmarkForm.add(addressSet);
		
		// Icon of bookmark.
		JPanel iconSet = new JPanel();
		JComboBox<Icon> iconBox = new JComboBox<Icon>(icons);
		iconSet.add(new JLabel("Select an icon:"));
		iconSet.add(iconBox);
		bookmarkForm.add(iconSet);
		
		// Close/Save
		JPanel operationSet = new JPanel();
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Close window.
				bookmarkForm.setVisible(false);
				bookmarkForm.dispose();
				
			}
			
		});
		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				/* User form information to build a bookmark! */
				
				// If any of the fields are blank...
				if(name.getText().equals("") || address.getText().equals("")) {
					
					// Display a dialog alerting the user of the problem.
					JOptionPane.showMessageDialog(null, "One or more fields not set!\n"
							+ "Please make sure you've filled the text-boxes.", "Oops!", JOptionPane.ERROR_MESSAGE);
					
				}
				// Or if the link does not validate...
				else if(Browser.makeURL(address.getText()) == null){
					
					// Display a dialog alerting the user of the problem.
					JOptionPane.showMessageDialog(null, "The URL you provided was not valid!\n"
							+ "Perhaps make sure you wrote it in right?", "Oops!", JOptionPane.ERROR_MESSAGE);
					
				}
				else {
					
					// Create a bookmark using the user's selected fields.
					Bookmark thisBm = new Bookmark((Icon) iconBox.getSelectedItem(),
							name.getText(), Browser.makeURL(address.getText()));
					
					// Add the new bookmark.
					addBookmark(thisBm);
					
					// Add it to the list of bookmarks.
					Browser.getInstance().getBookmarks().add(thisBm);
					
					// Close window.
					bookmarkForm.setVisible(false);
					bookmarkForm.dispose();
					
				}
				
			}

		});
		operationSet.add(cancelBtn);
		operationSet.add(saveBtn);
		bookmarkForm.add(operationSet);
		
		// Make the window visible.
		bookmarkForm.setVisible(true);
		
	}
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	JScrollPane component containing functioning browser bookmarks bar.
	 */
	public JScrollPane getComponent() {
		
		return bookmarkBarScroll;
		
	}
		
	/**
	 * Method returns the bookmark form.
	 * 
	 * @return	JFrame component containing form to create bookmarks.
	 */
	public JFrame getBookmarkForm() {
		
		return bookmarkForm;
		
	}
	
}