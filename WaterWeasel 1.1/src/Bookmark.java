import java.net.URL;
import javax.swing.Icon;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * Class models a browser 'bookmark' with a graphic, name, and link.
 */
public class Bookmark {

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
	
	public Icon getIcon() {
		return ico;
	}
	
	public void setIcon(Icon ico) {
		this.ico = ico;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public URL getURL() {
		return url;
	}
	
	public void setURL(URL url) {
		this.url = url;
	}
	
}
