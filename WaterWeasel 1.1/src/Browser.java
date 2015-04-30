import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	
	// Paths to settings files.
	final static String SETTINGS_FILE_PATH = "Assets/UserSettings/settings.cfg";
	private final String HISTORY_FILE_PATH = "Assets/UserSettings/history.txt";
	private final String BOOKMARKS_FILE_PATH = "Assets/UserSettings/bookmarks.txt";
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
	// Saved user initial window position.
	private int xPos, yPos;
	// Saved user state of window locking.
	private boolean maximised;
	// Saved user overall browser theme.
	private Color theme;
	// Saved user 'homepage' loaded to first page.
	private URL homepage;
	
	/* Important context-specific state objects. */
	
	// Stored collection of user's browser bookmarks.
	private List<Bookmark> bookmarks;
	
	// Stored persistent user-history.
	private SortedMap<Date, URL> history;
	
	// List of currently active windows.
	private List<Window> windows = new ArrayList<Window>();
	
	/**
	 * Main method assembles components to
	 * load browser.
	 */
	public static void main(String[] args) {
		
		// Instantiate the browser with a new window.
		Browser.getInstance().add();
		
	}
	
	/**
	 * Method models the Singleton design pattern, which serves to
	 * provide over-arching management of all open windows.
	 * 
	 * @return	The only available instance of a 'Browser' object.
	 */
	public static Browser getInstance() {
		
		// If one does not exist...
		if(instance == null) {
			// Create one.
			instance = new Browser();
		}
		// Return instance of 'Browser'.
		return instance;
	}
	
	/**
	 * Constructor is protected; this prevents a component from creating another instance.
	 */
	protected Browser() {
	
		// Load local settings into the program.
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
		add();

		// Set its privacy.
		Browser.getInstance().getWindows().get(
				Browser.getInstance().getWindows().size() - 1).makeIncognito(incognito);
		
	}
	
	/**
	 * Method loads and initialises fields and objects from necessary files.
	 */
	@SuppressWarnings("unchecked")
	private void load() {
		
		// Catch-all boolean value stores state of failure.
		boolean somethingWrong = false;
		
		/* Load general user settings. */
		
		// Assert that the settings file (still) exists.
		File settingsFile = FileMediator.assertFile(SETTINGS_FILE_PATH);
		
		// Read properties from file using necessary casting and conversion methods, reverting to defaults if they cannot be found...
		try {
			
			xSize 		= FileMediator.getProperty("width", settingsFile) != null ? 
							Integer.parseInt(FileMediator.getProperty("width", settingsFile)) : DEFAULT_SIZE;
			ySize 		= FileMediator.getProperty("height", settingsFile) != null ? 
							Integer.parseInt(FileMediator.getProperty("height", settingsFile)) : DEFAULT_SIZE;
			xPos 		= FileMediator.getProperty("left", settingsFile) != null ? 
					Integer.parseInt(FileMediator.getProperty("left", settingsFile)) : 0;
			yPos 		= FileMediator.getProperty("top", settingsFile) != null ? 
					Integer.parseInt(FileMediator.getProperty("top", settingsFile)) : 0;
			maximised 	= FileMediator.getProperty("fullscreen", settingsFile) != null ?
							Boolean.parseBoolean(FileMediator.getProperty("fullscreen", settingsFile)) : DEFAULT_MAXIMISED;
			theme		= FileMediator.getProperty("theme", settingsFile) != null ?
							new Color(Integer.parseInt(FileMediator.getProperty("theme", settingsFile))) : DEFAULT_THEME;
			homepage	= FileMediator.getProperty("homepage", settingsFile) != null ?
							makeURL(FileMediator.getProperty("homepage", settingsFile)) : DEFAULT_HOMEPAGE;
		
		} catch (NumberFormatException | IOException e) {
			
			// If an error occurred, revert settings to defaults.
			initSettings();
			// Log that something bad happened!
			somethingWrong = true;
			
		}
									
		/* Load user history. */
		
		// Attempt to load the history collection.
		try {
			
			// Cast the object loaded into the appropriate type.
			history = (TreeMap<Date, URL>) FileMediator.loadObject(HISTORY_FILE_PATH);
		
		} catch (ClassNotFoundException | IOException e) {
			
			// Otherwise default it.
			history = new TreeMap<Date, URL>();
			// Log that something bad happened!
			somethingWrong = true;
		
		}
		
		/* Load user's bookmarks. */
		
		// Attempt to load the bookmark collection.
		try {
			
			// Cast the object loaded into the appropriate type.
			bookmarks = (ArrayList<Bookmark>) FileMediator.loadObject(BOOKMARKS_FILE_PATH);
		
		} catch (ClassNotFoundException | IOException e) {
			
			// Otherwise default it.
			bookmarks = new ArrayList<Bookmark>();
			// Log that something bad happened!
			somethingWrong = true;
			
		}
		
		// If an error occurred during any of the processes, drive relevant method.
		if(somethingWrong) {
			
			// Inform the user via the UI of what has happened.
			alertError();
			
		}
								
	}
	
	/**
	 * Method to be called indeterminately, responsive to user interaction,
	 * saves fields and objects from necessary files.
	 */
	public void save() {
		
		// Catch-all boolean value stores state of failure.
		boolean somethingWrong = false;
		
		/* Save general user settings. */
		
		// Assert that the settings file (still) exists.
		File settingsFile = FileMediator.assertFile(SETTINGS_FILE_PATH);
		
		// Write properties that have likely changed during run-time to file.
		try {
		
			FileMediator.setProperty("width", xSize, settingsFile);
			FileMediator.setProperty("height", ySize, settingsFile);
			FileMediator.setProperty("left", xPos, settingsFile);
			FileMediator.setProperty("top", yPos, settingsFile);
			FileMediator.setProperty("fullscreen", maximised, settingsFile);
			FileMediator.setProperty("theme", theme.getRGB(), settingsFile);
			FileMediator.setProperty("homepage", homepage, settingsFile);
		
		} catch(IOException e) {
			
			// If a fatal error occurs while loading, revert to defaults.
			initSettings();
			
		}
		
		/* Save user history. */
		
		// Attempt to save the history collection.
		try {
			
			FileMediator.saveObject(history, HISTORY_FILE_PATH);
		
		} catch (IOException e) {
		
			// Log that something bad happened!
			somethingWrong = true;
			
		}
		
		/* Save user bookmarks. */
		
		// Attempt to save the bookmarks collection.
		try {
			
			System.out.println("Trying to save bookmarks!");
			FileMediator.saveObject(bookmarks, BOOKMARKS_FILE_PATH);
			
		} catch (IOException e) {
			
			System.out.println("oh shit!");
			// Log that something bad happened!
			somethingWrong = true;
			
		}
		
		// If an error occurred during any of the processes, drive relevant method.
		if(somethingWrong) {
			
			// Inform the user via the UI of what has happened.
			alertError();
			
		}
		
	}
	
	/**
	 * 
	 */
	public void close(JFrame frame) {
			
		// Update state-variables to 'remember' user's preferences.
		xSize = frame.getWidth();
		ySize = frame.getHeight();
		xPos = frame.getX();
		yPos = frame.getY();
		if(frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
			
			maximised = true;
			
		}
		else maximised = false;
		
		// Remove the window from the browser and then dispose of it.
		for(int i = 0; i < windows.size(); i++) {
			
			// Find the correct window based on their component equivalence.
			if(windows.get(i).getComponent() == frame) {
				
				windows.remove(i);
				
			}
			
		}
		
		// Close window properly.
		frame.setVisible(false);
		frame.dispose();
		
		// Save preferences.
		save();
		
		// If there are no more windows left...
		if(windows.size() == 0) {
			
			// Exit program.
			System.exit(0);
			
		}
		
	}
	
	/**
	 * This method constructs a configuration file and writes
	 * program defaults to it to be acted upon.
	 */
	private void initSettings() {
		
		// Create settings file.
		File settingsFile = FileMediator.assertFile(SETTINGS_FILE_PATH);
			
		/* Write default settings. */
		
		/*
		 * By this point, there is no conceivably logical way the program could be throwing errors
		 * as a result of itself- nonetheless, an empty try/catch is necessary here for compilation.
		 */
		try {
			
			FileMediator.setProperty("width", DEFAULT_SIZE, settingsFile);
			FileMediator.setProperty("height", DEFAULT_SIZE, settingsFile);
			FileMediator.setProperty("left", 0, settingsFile);
			FileMediator.setProperty("top", 0, settingsFile);
			FileMediator.setProperty("fullscreen", DEFAULT_MAXIMISED, settingsFile);
			FileMediator.setProperty("theme", DEFAULT_THEME, settingsFile);
			FileMediator.setProperty("homepage", DEFAULT_HOMEPAGE, settingsFile);
		
		} catch (IOException e) {
			
			alertError();
			
		}
		
	}
	
	/**
	 * Handle errors in a browser-specific way; alert the user as to what has happened.
	 */
	private void alertError() {
		
		// Display a dialog alerting the user of the problem.
		JOptionPane.showMessageDialog(null, "Something went wrong while interacting with your browser's local files;\n"
				+ "we've done our best to fix it.", "Oops!", JOptionPane.WARNING_MESSAGE);
		
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

		// TODO: some checking
		
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

	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}
	
	public int getXPosition() {
		return xPos;
	}

	public int getYPosition() {
		return yPos;
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
