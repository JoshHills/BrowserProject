import java.awt.Dimension;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class manages the operations relating to the 'Bookmark' objects of the browser.
 */
public class BookmarksManager {
	
	// Collection of loaded icon resources able to be linked to bookmarks.
	Icon[] icons;
	
	JFrame bookmarkForm;
	
	/**
	 * As this class relies on the 'Browser' singleton, it will only be instantiated once.
	 * This constructor grabs all 'Bookmark' icons from a source folder and stores them in a hard-
	 * coded manner for use by Swing components (as a visual representation).
	 */
	public BookmarksManager() {
		
		/* Load 'Bookmark' icons. */
		
		// Create an array of file-paths to required resources.
		File[] bmIcons = new File("./Assets/BookmarkIcons").listFiles();
		// Set size of 'icon' array to the number of icons available.
		icons = new Icon[bmIcons.length];
		// Set each 'Icon' reference variable to a new 'ImageIcon' object.
		for(int i = 0; i < icons.length; i++) {
			icons[i] = new ImageIcon(bmIcons[i].getPath());
		}
		
		// Construct the bookmark creation form.
		initBookmarkForm();
		
	}
	
	/**
	 * This method initialises a blueprint for a form
	 * to handle the user-creation of 'Bookmark' objects.
	 */
	private void initBookmarkForm() {
		
		// Create the form window.
		bookmarkForm = new JFrame("Add a new Bookmark...");
		// Set its default close operation.
		bookmarkForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set its size.
		bookmarkForm.setMinimumSize(new Dimension(200,200));
		// Set its layout.
		bookmarkForm.setLayout(new BoxLayout(bookmarkForm.getContentPane(), BoxLayout.Y_AXIS));
		
		/* Comprising components. */
		
		// Name of bookmark.
		JPanel nameSet = new JPanel();
		JTextField name = new JTextField(10);
		nameSet.add(new JLabel("Name:"));
		nameSet.add(name);
		bookmarkForm.add(nameSet);
		
		// Address of bookmark.
		JPanel addressSet = new JPanel();
		JTextField address = new JTextField(10);
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
		JButton saveBtn = new JButton("Save");
		operationSet.add(cancelBtn);
		operationSet.add(saveBtn);
		
		bookmarkForm.add(operationSet);
		
	}
	
	public void displayBookmarkForm() {
		
		bookmarkForm.setVisible(true);
		
	}
	
	/**
	 * Display a page with information about the state of the browser's 'bookmarks'.
	 */
	public void createPage() {
		
		
		
	}
	
	/**
	 * Method displays 'bookmarks' bar.
	 */
	public void showBar() {}
	
	/**
	 * Adds a 'Bookmark' object to the collection and updates JComponent accordingly.
	 */
	public void addBookmark() {
		
	}
	
	/**
	 * Removes a 'Bookmark' object to the collection and updates JComponent accordingly.
	 * 
	 * @param	'Bookmark' object to be removed.
	 */
	public void removeBookmark(Bookmark bm) {
		
	}
	
}
