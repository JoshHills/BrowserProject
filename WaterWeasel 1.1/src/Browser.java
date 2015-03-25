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
	
	//TODO Make file constants!

	// Browser name is a constant.
	private final String BROWSER_NAME = "WaterWeasel";
	
	/*
	 * The single instance of the 'Browser' application provides overall
	 * management of all expanded hierarchies for consistency in settings.
	 */
	private static Browser instance;
	
	/* Global default settings. */
	
	// Path to settings file.
	private final String SETTINGS_FILE_PATH = "Assets/UserSettings/settings.cfg";
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
		try {
			load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
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
	 * Method loads and initialises fields and objects from necessary files. 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private void load() {
		
		/* Load general user settings. */
		
		// If the settings file does not exist...
		if(!FileMediator.fileExists(SETTINGS_FILE_PATH)) {
			
			
			
		}
		
		// Assert that the settings file (still) exists.
		File settingsFile = FileMediator.assertFile(SETTINGS_FILE_PATH);
		
		// Read properties from file using necessary casting and conversion methods, reverting to defaults if they cannot be found...
		xSize 		= FileMediator.getProperty("width", settingsFile) != null ? 
						Integer.parseInt(FileMediator.getProperty("xSize", settingsFile)) : DEFAULT_SIZE;
		ySize 		= FileMediator.getProperty("height", settingsFile) != null ? 
						Integer.parseInt(FileMediator.getProperty("ySize", settingsFile)) : DEFAULT_SIZE;
		maximised 	= FileMediator.getProperty("fullscreen", settingsFile) != null ?
						Boolean.parseBoolean(FileMediator.getProperty("maximised", settingsFile)) : DEFAULT_MAXIMISED;
		theme		= FileMediator.getProperty("theme", settingsFile) != null ?
						new Color(Integer.parseInt(FileMediator.getProperty("theme", settingsFile))) : DEFAULT_THEME;
		homepage	= FileMediator.getProperty("homepage", settingsFile) != null ?
						makeURL(FileMediator.getProperty("homepage", settingsFile)) : DEFAULT_HOMEPAGE;
								
	}
	
	/**
	 * 
	 */
	public void close() throws FileNotFoundException, IOException {
	
		/* Save general user settings. */
		
		// Assert that the settings file (still) exists.
		File settingsFile = FileMediator.assertFile(SETTINGS_FILE_PATH);
		
		// Write properties to file.
		FileMediator.setProperty("width", xSize, settingsFile);
		FileMediator.setProperty("height", ySize, settingsFile);
		FileMediator.setProperty("fullscreen", maximised, settingsFile);
		FileMediator.setProperty("theme", theme.getRGB(), settingsFile);
		FileMediator.setProperty("homepage", homepage, settingsFile);
		
	}
	
	
	private void initSettings() {
		
		// Create settings file.
		File settingsFile = FileMediator.assertFile(SETTINGS_FILE_PATH);
					
		/* Write default settings. */
					
		FileMediator.setProperty("xSize", DEFAULT_SIZE, settingsFile);
		FileMediator.setProperty("ySize", DEFAULT_SIZE, settingsFile);
		FileMediator.setProperty("maximized", DEFAULT_MAXIMISED, settingsFile);
		FileMediator.setProperty("theme", DEFAULT_THEME, settingsFile);
		FileMediator.setProperty("homepage", DEFAULT_HOMEPAGE, settingsFile);
		
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
