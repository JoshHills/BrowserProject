import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class manages the operations relating to the 'Bookmark' objects of the browser.
 */
public class BookmarksManager {
	
	// Collection of loaded icon resources able to be linked to bookmarks.
	Icon[] icons;
	
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
