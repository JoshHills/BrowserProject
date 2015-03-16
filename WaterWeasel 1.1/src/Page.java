import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class models a browser web-page viewport and it's operations.
 */
public class Page implements HyperlinkListener {

	// Wrapper class for main component adds scrolling functionality.
	private JScrollPane scrollPane;
	
	// Main component to be styled and modified.
	private JEditorPane page;
	
	// List of the page's visited URLs.
	private List<URL> visited = new ArrayList<URL>();
	// Index integer for basic iteration.
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
		
		// By initialising 'JScrollPane' object, give page scroll-bars.
		scrollPane = new JScrollPane(page, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// Ensure index defaults to zero.
		index = 0;
		
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
			if(index < (visited.size() - 1)) {
				// Remove the queued links.
				for(int i = 0; i < (visited.size() - index); i++) {
					visited.remove(visited.size() - i);
				}
			}
			
			// Log page in temporary history.
			visited.add(url);
			// Iterate index.
			index++;
			
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
		
		// If forward navigation is possible.
		if(index != (visited.size() - 1)) {
			
			// Increment index by one.
			index++;
			
			// Attempt to display the next web-page.
			try {
				page.setPage(visited.get(index));
			} catch (IOException e) {}
			
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
			
			// Force a null stream description for true refresh.
			Document doc = page.getDocument();
			doc.putProperty(Document.StreamDescriptionProperty, null);
			
			page.setPage(visited.get(index));
		
		} catch (IOException e) {}
		
	}
	
	/* Accessor methods. */
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	JEditorPane component modelled after a browser page.
	 */
	public JScrollPane getComponent() {
		return scrollPane;
	}
	
	/**
	 * Get the private index field.
	 * 
	 * @return	Index of currently active element of visited 'URL' list.
	 */
	public int getIndex() {
		return index;
	}

}
