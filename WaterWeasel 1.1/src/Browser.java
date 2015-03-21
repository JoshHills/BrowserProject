import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This program creates a functioning GUI
 * of a web-browser comprising of state
 * management objects and swing components.
 * 
 * The 'Browser' object at the top level hierarchy subsequently
 * handles interpolation with local settings files and the
 * construction of the main windows.
 */
public class Browser {

	// Browser name is a constant.
	private final String BROWSER_NAME = "WaterWeasel";
	
	/*
	 * The single instance of the 'Browser' application provides overall
	 * management of all expanded hierarchies for consistency in settings.
	 */
	private static Browser instance;
	
	/* Global default settings. */

	// Initial window size.
	private final int DEFAULT_SIZE = 500;
	// State of window locking.
	private final boolean DEFAULT_MAXIMISED = true;
	// Overall browser theme.
	private final Color DEFAULT_THEME = new Color(140,210,245);
	// Incognito theme.
	private final Color DEFAULT_PRIVATE_THEME = new Color(95,70,100);
	// 'Homepage' loaded to first page.
	private final URL DEFAULT_HOMEPAGE = makeURL(new File("./Assets/Welcome.html"));
	
	/* Global settings. */
	
	// Saved user initial window size.
	private int xSize, ySize;
	// Saved user state of window locking.
	private boolean maximised;
	// Saved user overall browser theme.
	private Color theme;
	// Saved user 'homepage' loaded to first page.
	private URL homepage;
	
	/* Important context-specific state objects. */
	
	// Declare an object to manage the browser's 'Bookmark' object related operations.
	private BookmarksManager bmManager = new BookmarksManager();
	// Stored collection of user's browser bookmarks.
	private List<Bookmark> bookmarks = new ArrayList<Bookmark>();
	
	// Stored persistent user-history.
	private SortedMap<Date, URL> history = new TreeMap<Date, URL>();
	
	// List of currently active windows.
	private List<Window> windows = new ArrayList<Window>();
	
	/**
	 * Main method assembles components to
	 * load browser.
	 */
	public static void main(String[] args) {
		
		// Add and therefore create a new browser window.
		Browser.getInstance().add();
		
	}
	
	/**
	 * Method models the Singleton design pattern, which itself
	 * provides over-arching management of all open windows.
	 * 
	 * @return	The only available instance of a 'Browser' object.
	 */
	public static Browser getInstance() {
		
		// If one does not exist...
		if(instance == null) {
			// Create one.
			instance = new Browser();
		}
		// Return it regardless.
		return instance;
	}
	
	/**
	 * Constructor is protected; this prevents an inner-component from creating another instance.
	 */
	protected Browser() {
	
		// Attempt to set-up this 'Browser' instance with centralised user-settings.
		load();
			
	}
	
	/**
	 * Method creates a new 'Window' object which displays itself on the screen.
	 */
	public void add() {
		
		// Add a window the 'Browser'.
		Browser.getInstance().getWindows().add(new Window());
		
	}
	
	/**
	 * Overloaded method creates a new 'Window' with a specified privacy state.
	 */
	public void add(boolean incognito) {
		
		// Add a window the 'Browser'.
		Browser.getInstance().getWindows().add(new Window());

		// Set its privacy.
		Browser.getInstance().getWindows().get(
				Browser.getInstance().getWindows().size() - 1).makeIncognito(incognito);
		
	}
		
	/*
	 * Due to the small scope of the project, the interactions with the settings files
	 * are currently quite discrete and specific, however this could be easily generalised
	 * with a 'mediator' class should the program be expanded.
	 * 
	 * As it stands, I have used a variety of appropriate I/O methods to showcase understanding
	 * of their concepts- the general settings do not require encryption but the file format 
	 * lends itself well to iteration, whereas the other personal and dynamic files can be
	 * read/wrote in a more simplified manner, but would require higher levels of protection-
	 * this is reflected in my choice of code.
	 * 
	 * The storage of the elements within the files themselves follows good formatting practice
	 * as defined by the Java documentation- as such, they work to a flexible degree.
	 */
	
	/**
	 * Method decrypts, loads and initialises fields from necessary files. 
	 */
	void load() {
		
		// Store state of errors for dialogue display and perseverance.
		boolean failed;
		
		/* Load general user-settings. */
				
		// Create a properties object to store a reference to the supposed configuration file.
		Properties settingsTxt = new Properties();
		// Attempt to access the file.
		try {
			
			settingsTxt.load(new FileInputStream("Assets/UserSettings/settings.cfg"));
			
			/* Check for settings! */
			
			if(settingsTxt.containsKey("xSize")) {
				// Cast the unknown object value from key to a String to be parsed.
				xSize = Integer.parseInt((String) settingsTxt.get("xSize"));
			}
			else {
				// Log that something failed.
				failed = true;
				// Revert user setting to default.
				xSize = DEFAULT_SIZE;
			}
			
			if(settingsTxt.containsKey("ySize")) {
				// Cast the unknown object value from key to a String to be parsed.
				ySize = Integer.parseInt((String) settingsTxt.get("ySize"));
			}
			else {
				// Log that something failed.
				failed = true;
				// Revert user setting to default.
				ySize = DEFAULT_SIZE;
			}
			
			if(settingsTxt.containsKey("maximised")) {
				// Cast the unknown object value from key to a String to be parsed.
				maximised = Boolean.parseBoolean((String) settingsTxt.get("maximised"));
			}
			else {
				// Log that something failed.
				failed = true;
				// Revert user setting to default.
				maximised = DEFAULT_MAXIMISED;
			}
			
			if(settingsTxt.containsKey("theme")) {
				// Cast the unknown object value from key to a String to be parsed.
				String temp = (String) settingsTxt.get("theme");
				// Split the RGB values into an array.
				String[] tempa = temp.split(",");
				// Assuming the right amount of values...
				if(tempa.length == 3) {
					theme = new Color(Integer.parseInt(tempa[0]),
							Integer.parseInt(tempa[1]),
							Integer.parseInt(tempa[2]));
				}
				// Otherwise log that something failed.
				else {
					failed = true;
					// Revert user setting to default.
					theme = DEFAULT_THEME;
				}
			}
			else {
				// Log that something failed.
				failed = true;
				// Revert user setting to default.
				theme = DEFAULT_THEME;
			}
			
			if(settingsTxt.containsKey("homepage")) {
				// Cast the unknown object value from key to a String to be converted to an URL.
				homepage = makeURL((String) settingsTxt.get("homepage"));
			}
			else {
				// Log that something failed.
				failed = true;
				// Revert user setting to default.
				homepage = DEFAULT_HOMEPAGE;
			}
			
		}
		// If something failed fatally, revert all settings to defaults.
		catch (IOException | NumberFormatException e) {
			System.out.println("AAGH");
		}
		
	}
	
	/**
	 * Method encrypts and saves fields to necessary files.
	 */
	void save() {
		
	}
	
	/**
	 * This method converts a String to an URL object. It is positioned
	 * in this outer-most 'Browser' class for easy access, and serves to
	 * remove boilerplate code when checking for exceptions.
	 * 
	 * @param string	String to attempt to convert to an URL.
	 * @return			A new URL object if successful.
	 */
	public static URL makeURL(String url) {
		
		try {
			// Attempt to return a new URL from the String.
			return new URL(url);
		} catch (java.net.MalformedURLException e) {
			// Otherwise...
			return null;
		}
		
	}
	
	/**
	 * Overloaded method converts a File to an URL object, which allows
	 * the browser to load local web files with extensions such as
	 * '.html'- this allows me to provide a splash page.
	 * 
	 * @param file	File to attempt to convert to an URL.
	 * @return		A new URL object if successful.
	 */
	public static URL makeURL(File file) {
		
		try {
			// Attempt to return a new URL from the File.
			return file.toURI().toURL();
		} catch (java.net.MalformedURLException e) {
			// Otherwise...
			return null;
		}
		
	}

	/* Accessor methods. */
	
	public String getBROWSER_NAME() {
		return BROWSER_NAME;
	}
	
	public URL getDEFAULT_HOMEPAGE() {
		return DEFAULT_HOMEPAGE;
	}
	
	public Color getDEFAULT_PRIVATE_THEME() {
		return DEFAULT_PRIVATE_THEME;
	}

	public int getxSize() {
		return xSize;
	}

	public int getySize() {
		return ySize;
	}

	public boolean isMaximised() {
		return maximised;
	}

	public Color getTheme() {
		return theme;
	}

	public URL getHomepage() {
		return homepage;
	}
	
	public BookmarksManager getBmManager() {
		return bmManager;
	}
	
	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}
	
	public Map<Date, URL> getHistory() {
		return history;
	}
	
	public List<Window> getWindows() {
		return windows;
	}
	
}
