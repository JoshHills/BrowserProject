import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * @author Josh Hills
 *
 * This is a test class!
 * It has likely had many functions.
 */
public class Driver {

	public static void main(String[] args) throws MalformedURLException {
		
		/* Test bookmarks. */
		
		// Create window.
		JFrame window = new JFrame("Test Bookmarks");
		FlowLayout fl = new FlowLayout();
		window.setLayout(fl);
		window.setSize(500, 500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Load resources into an array.
				
		File directory = new File("./Assets/BookmarkIcons");
		File[] bmIcons = directory.listFiles();
		
		Icon[] icons = new Icon[bmIcons.length];
		
		for(int i = 0; i < icons.length; i++) {
			icons[i] = new ImageIcon(bmIcons[i].toString());
		}
		
		JComboBox<Icon> iconSelect = new JComboBox<Icon>(icons);

		iconSelect.setBorder(null);
		iconSelect.setBackground(null);
		
		JTextField nameOf = new JTextField("Name of bookmark.");
		
		window.add(nameOf);
		
		window.add(iconSelect);
		/*
		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					Bookmark bm = new Bookmark((Icon) iconSelect.getSelectedItem(),
							nameOf.getText(), new URL("http://www.google.com/"));
					window.add(bm.getJComponent());			
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
			}
			
		});
		
		window.add(create);
		*/
			
		
	}

}
