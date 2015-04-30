import java.net.URL;
import javax.swing.Icon;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * Class models a browser 'bookmark' with a graphic, name, and link.
 */
public class Bookmark implements java.io.Serializable {

	// Allow writing of Bookmark object to text file.
	private static final long serialVersionUID = -3741349136919119946L;
	
	// Custom icon.
	private Icon ico;
	// User-defined name for identification.
	private String name;
	// Link location.
	private URL url;
	
	/**
	 * Constructor initialises private fields.
	 * 
	 * @param ico	The graphic selected to represent the object.
	 * @param name	The user-defined name of the object.
	 * @param url	The URL the object points to.
	 */
	public Bookmark(Icon ico, String name, URL url) {
		
		this.ico = ico;
		this.name = name;
		this.url = url;
		
	}
	
	/* Accessor methods. */
	
	/**
	 * @return	The graphic associated with the bookmark.
	 */
	public Icon getIcon() {
		return ico;
	}
	
	/**
	 * @return	The human-readable name of the bookmark.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return	The saved URL that the bookmark points to.
	 */
	public URL getURL() {
		return url;
	}

}
