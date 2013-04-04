package processing.app;
import static processing.app.I18n._;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class ApplicationToolbar extends JToolBar {

	public ApplicationToolbar() {
		//setLayout(new BorderLayout());
		// Needed for unified toolbar look
		putClientProperty("Quaqua.ToolBar.style", "title");
		setFloatable(false);

		JButton button = null;

		button = makeToolbarButton("Verify");
		add(button, BorderLayout.WEST);
		button = makeToolbarButton("Upload");
		add(button);
		button = makeToolbarButton("Take a Shit");
		add(button);
		//addSearchField();
	}


	private void addButtons() {

		    //first button
		
	}

	protected JButton makeToolbarButton(String buttonName) {
	
	    //Create and initialize the button.
	    JButton button = new JButton();
		button.setText(buttonName);
	    //button.setActionCommand(actionCommand);
	    button.setToolTipText(buttonName);
	    //button.addActionListener(this);

	    return button;
	}

	private void addSearchField() {
		JPanel panel = new JPanel();
		this.add(panel, BorderLayout.EAST);

		JTextField search = new JTextField();
		search.putClientProperty("Quaqua.TextField.style","search");
		search.setPreferredSize(new Dimension(150, 25));
		search.setMaximumSize(new Dimension(150, 25));
		panel.add(search);
	}
	
	
	public Dimension getPreferredSize() {
		return getMinimumSize();
	}

	public Dimension getMinimumSize() {
		return new Dimension(300, 36);
	}

	public Dimension getMaximumSize() {
		return new Dimension(3000, 36);
	}
}