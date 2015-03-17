import java.awt.Color;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author Josh Hills
 * @version 1.1
 * 
 * This class models a browser search bar.
 */
public class SearchBar {
	
	// Parent container.
	private Window window;
	
	// Main component to be styled and modified.
	private JPanel searchBar;
	
	// Useful sub-components of search bar.
	private JTextField predictiveField, addressField;
	
	/**
	 * Constructor sets-up the search-bar by creating and compiling relevant components.
	 */
	public SearchBar(Window window) {
		
		// Set parent container.
		this.window = window;
		
		/* Create search bar with components. */
		
		// Initialise panel.
		searchBar = new JPanel();
		// Give it a background colour.
		searchBar.setBackground(Color.WHITE);
		
		/* Predictive box. */
		
		// Create a predictive text field for user-direction.
		predictiveField = new JTextField("Enter:");
		// Remove border.
		predictiveField.setBorder(null);
		// Ensure it is not editable.
		predictiveField.setEditable(false);
		// Add it to the search-bar.
		searchBar.add(predictiveField);
		
		/* Address box. */
		
		// Create an editable text field for URL input, default text to homepage.
		addressField = new JTextField(Browser.getInstance().getHomepage().toString());
		// Remove border.
		addressField.setBorder(null);
		/*
		 * Add listener to 'document' of the text-field
		 * to allow an action to happen upon edit-
		 * in this scenario, the contents of 'predictive' will
		 * change depending on what the user enters in the URL field.
		 */
		addressField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				handleEvent(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				handleEvent(e);				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				handleEvent(e);
			}
			
			private void handleEvent(DocumentEvent e) {
				
				// If the address field is empty, tell the user to enter something.
				if(addressField.getText().equals("")) {
					predictiveField.setText("Enter:");
				}
				// If the address field looks like a link...
				else if(addressField.getText().contains("://") ||
					addressField.getText().contains("www.")) {
					predictiveField.setText("Go to:");
				}
				else {
					predictiveField.setText("Search:");
				}
				
			}
			
		});
		// Add it to the search-bar.
		searchBar.add(addressField);
		
	}
	
	/**
	 * This method fires off the text in the address field to display a new web-page-
	 * if the address is not an URL it will search for the string using the default search engine.
	 */
	public void fire() {
		
		// If the URL validates...
		if(Browser.getInstance().makeURL(addressField.getText()) != null) {
			
			// Get the currently open page and set it to the new web-page.
			window.getTabBar().getPages().get(
					window.getTabBar().getComponent().getSelectedIndex()).show(
							Browser.getInstance().makeURL(addressField.getText()));
			
		}
		// Else search for the string.
		else {
			
		}
		
	}
	
	/**
	 * Method returns the main component post-initialisation.
	 * 
	 * @return	JPanel component containing functioning browser navigation bar.
	 */
	public JPanel getComponent() {
		
		return searchBar;
		
	}
	
	/**
	 * Method returns the component which holds a user-entered web-address.
	 *
	 * @return	JTextField component containing URLs.
	 */
	public JTextField getAddressField() {
		
		return addressField;
				
	}
	
}
