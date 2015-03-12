import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class models a browser web-page and it's operations.
 */
public class Page implements HyperlinkListener {

	// Main component to be styled and modified.
	private JEditorPane page;
	
	// List of the page's visited URLs.
	private List<URL> visited = new ArrayList<URL>();
	// Integer for basic iteration.
	private int index;
	
	/**
	 * Constructor sets-up page properties and adds a hyper-link listener.
	 */
	public Page() {
		
		/* Create and set-up main component. */
		
		page = new JEditorPane();
		// Set the content type to HTML for proper functioning.
		page.setContentType("text/html");
		// Ensure it is not editable.
		page.setEditable(false);
		// Self reference page's implemented listener.
		page.addHyperlinkListener(this);
		// Display the default page.
		show(Browser.getInstance().getHomepage());
		
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
			if(e instanceof HTMLFrameHyperlinkEvent) {
				
				// Cast it to a frame event.
				HTMLFrameHyperlinkEvent frameEvent =
						(HTMLFrameHyperlinkEvent) event;
				
				// Get the current HTML document and process the frame event.
				HTMLDocument doc =
						(HTMLDocument) page.getDocument();
				doc.processHTMLFrameHyperlinkEvent(frameEvent);
				
			}
			// Otherwise, if the event has been activated normally.
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
		
		// Attempt to display the page.
		try {
			
			page.setPage(url);
			
			// If the index is not at the current page.
			if(index < visited.size()) {
				// Remove the queued links.
				for(int i = 0; i < (visited.size() - index); i++) {
					visited.remove(visited.size() - i);
				}
			}
			
			// Log page in temporary history.
			visited.add(url);
			
			//Update buttons.
			
		} catch (IOException e) {}
		
	}
	
	/**
	 * Method displays the previously visited web-page.
	 */
	public void back() {
		
		// If backward navigation is possible.
		if(index != 0) {
			
			// Decrease index by one.
			index--;
			
			// Attempt to display the previous web-page.
			try {
				page.setPage(visited.get(index));
			} catch (IOException e) {}
			
		}
		
	}
	
	/**
	 * Method displays the next visited web-page.
	 */
	public void forward() {
		
		// If backward navigation is possible.
		if(index != visited.size()) {
			
			// Increment index by one.
			index++;
			
			// Attempt to display the next web-page.
			try {
				page.setPage(visited.get(index));
			} catch (IOException e) {}
			
		}
		
	}	
	
	/* Accessor methods. */
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	JEditorPane component modelled after a browser page.
	 */
	public JEditorPane getComponent() {
		
		return page;
		
	}
	
	public int getIndex() {
		
		return index;
		
	}

}
