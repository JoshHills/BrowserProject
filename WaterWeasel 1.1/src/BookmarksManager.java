import java.util.HashMap;
import java.util.Map;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class manages the 'Bookmark' objects relating to the browser.
 */
public class BookmarksManager {
	
	// Collection of users' bookmarks.
	private Map<String, Bookmark> bookmarks = new HashMap<String, Bookmark>();
	
	
	// Component models custom tool-bar to display collection of 'Bookmark' objects.
	
	
	/**
	 * Constructor assembles bar itself from the load of configuration file.
	 */
	public BookmarksManager() {
	
		// Load from setup.
		
	}
	
	/**
	 * Display a page with information about the state of the browser's 'bookmarks'.
	 */
	public void display() {
		
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
