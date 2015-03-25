import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class models a browser web-page viewport and it's operations;
 * thus it implements a listener for events pertaining to 'Hyperlinks'.
 */
public class Page implements HyperlinkListener {
	
	// Parent reference.
	private Window window;
	
	// Wrapper class for main component adds scrolling functionality.
	private JScrollPane scrollPane;
	
	// Main component to be styled and modified.
	private JEditorPane page;
	
	// List of the page's visited URLs.
	private List<URL> visited = new ArrayList<URL>();
	// Iterator for list navigation.
	private ListIterator<URL> iterator = visited.listIterator();
	
	// Store current URL- bypasses overhead of Swing component's 'getURL' method.
	private URL currentURL;
	
	/**
	 * Constructor sets-up page properties and adds a hyper-link listener.
	 */
	public Page(Window window) {

		// Set parent reference.
		this.window = window;
		
		/* Create and set-up main component. */
		
		// Create component.
		page = new JEditorPane();
		// Set the content type to HTML for proper functioning.
		page.setContentType("text/html");
		// Ensure it is not editable.
		page.setEditable(false);
		// Self reference page's implemented listener method.
		page.addHyperlinkListener(this);
		// Display the default page.
		show(Browser.getInstance().getHomepage());
		
		// By initialising 'JScrollPane' object, give page appropriate scroll-bar functionality.
		scrollPane = new JScrollPane(page, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
	}
	
	/**
	 * Method receives 'HyperlinkEvent' objects and alters the page's
	 * contents accordingly for key browser functionality.
	 * 
	 * @Override	Overrides the interface method that receives events.
	 * @param e		Event passed to the listener to be evaluated. 
	 */
	public void hyperlinkUpdate(HyperlinkEvent event) {
		
		// Get the type of event for inspection.
		HyperlinkEvent.EventType type = event.getEventType();
		
		// If a hyperlink has been activated...
		if(type == HyperlinkEvent.EventType.ACTIVATED) {
			
			// If the event has been activated from within a frame in the page...
			if(event instanceof HTMLFrameHyperlinkEvent) {
				
				// Cast it to a frame event.
				HTMLFrameHyperlinkEvent frameEvent =
						(HTMLFrameHyperlinkEvent) event;
				
				// Get the current HTML document and process the frame event.
				HTMLDocument doc =
						(HTMLDocument) page.getDocument();
				doc.processHTMLFrameHyperlinkEvent(frameEvent);
				
			}
			// Otherwise, if the event has been activated normally...
			else {
				
				// Show the new page.
				show(event.getURL());
				
			}
				
		}
			
	}
	
	/**
	 * Method displays the web-page the specified URL points to
	 * and updates state-management objects accordingly.
	 * 
	 * @param url	The URL to display on the 'JEditorPane'.
	 */
	public void show(URL url) {
		
		// Attempt to perform the actions relevant to loading a new web-page...
		try {
			
			// If the page is already being displayed...
			if(url == page.getPage()) {
				
				// Refresh instead.
				refresh();
				
			}
			else {
			
				// Display the page in the viewport.
				page.setPage(url);
				
				// If a 'back' operation has just occurred, reposition the iterator.
				if(iterator.hasNext() && visited.get(iterator.nextIndex()).equals(currentURL)) {
					iterator.next();
				}
				
				// If the index is not at the current page, remove the following visited links.
				while(iterator.hasNext()) {
					
					// Advance the iterator.
					iterator.next();
					
					// Remove the element.
					iterator.remove();
					
				}
				
				/* Log and update. */
				
				// Log page in temporary history.
				iterator.add(url);
				// Log page in browser history.
				logURL(url);
				// Update current URL.
				currentURL = url;
				// Update address bar post-initialisation.
				try {
					window.getRibbon().getSearchBar().updateAddress();
				} catch(NullPointerException e) {}
			
			}
				
		} catch (IOException e) {
			
			// Handle any errors appropriately.
			handleException();
		
		}
		
	}
	
	/**
	 * Method displays the previously visited web-page.
	 */
	public void back() {
		
		// So long as any navigation is possible...
		if(visited.size() > 1) {
			
			// If the iterator is hovering at the outermost position...
			if(iterator.hasPrevious() && !iterator.hasNext()) {
				
				// Move it inward, ignoring the page that is already being displayed.
				iterator.previous();
				
			}
			
			// If backward navigation is possible...
			if(iterator.hasPrevious()) {
				
				// Attempt to display the previous web-page.
				try {
					
					currentURL = iterator.previous();
					page.setPage(currentURL);
				
				} catch (IOException e) {
					
					// Handle any errors appropriately.
					handleException();
				
				}
				
			}
			
		}
		
	}
	
	/**
	 * Method displays the next visited web-page.
	 */
	public void forward() {
		
		// So long as any navigation is possible...
		if(visited.size() > 1) {
			
			// If the iterator is hovering at the outermost position...
			if(iterator.hasNext() && !iterator.hasPrevious()) {
				
				// Move it inward, ignoring the page that is already being displayed.
				iterator.next();
			
			}
			
			// If forward navigation is possible...
			if(iterator.hasNext()) {
				
				// Attempt to display the next web-page.
				try {
					
					currentURL = iterator.next();
					page.setPage(currentURL);
				
				} catch (IOException e) {
					
					// Handle any errors appropriately.
					handleException();
				
				}
			
			}
		
		}
		
	}	
	
	/**
	 * Method refreshes the current web-page- the Java Documentation explains:
	 * "If the desired URL is the one currently being displayed, the document
	 * will not be reloaded. To force a document reload it is necessary to clear
	 * the stream description"- this explains the extra code below.
	 */
	public void refresh() {
		
		// Attempt to display the current web-page again.
		try {
			
			// Store the URL temporarily.
			URL current = page.getPage();
			
			// Force a null stream description for true refresh.
			Document doc = page.getDocument();
			doc.putProperty(Document.StreamDescriptionProperty, null);
			
			// Set the page again.
			page.setPage(current);
		
		} catch (IOException e) {
			
			// Handle any errors appropriately.
			handleException();
		
		}
		
	}
	
	/**
	 * This method handles the storage of browser history.
	 */
	private void logURL(URL url) {
		
		// As long as the window is not set to private.
		if(!window.isIncognito()) {
			
			// Add the URL to the history map, linked by the current time.
			Browser.getInstance().getHistory().put(new Date(), url);
			
		}
		
	}
	
	/**
	 * This method handles any errors thrown while performing 'Page' functions
	 * in a context-bound manner.
	 * 
	 * In this instance, it is appropriate to display a dialogue prompt to warn
	 * the user of the mishap.
	 */
	private void handleException() {
		
	}
	
	/* Accessor methods. */
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	JScrollPane component wrapping the page.
	 */
	public JScrollPane getComponent() {
		
		return scrollPane;
	
	}
	
	/**
	 * Method returns the iterator for the temporary list store of
	 * visited pages.
	 * 
	 * @return	ListIterator for the page's URL ArrayList.
	 */
	public ListIterator<URL> getIterator() {
		
		return iterator;
		
	}
	
	/**
	 * Method returns the current URL being displayed by the page.
	 * 
	 * @return	URL of the page in its current state.
	 */
	public URL getCurrentURL() {
		
		return currentURL;
		
	}
	
}
