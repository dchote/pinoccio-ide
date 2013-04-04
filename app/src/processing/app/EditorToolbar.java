package processing.app;
import static processing.app.I18n._;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class EditorToolbar extends JToolBar {
	static final int BUTTON_COUNT = 6;
	
	static final int BUTTON_WIDTH  = 27;
	/** Height of each toolbar button. */
	static final int BUTTON_HEIGHT = 32;
	/** The amount of space between groups of buttons on the toolbar. */
	static final int BUTTON_GAP    = 8;
	/** Size of the button image being chopped up. */
	static final int BUTTON_IMAGE_SIZE = 33;
	
	static final int VERIFY   = 0;
	static final int UPLOAD   = 1;

	static final int NEW      = 2;
	static final int OPEN     = 3;
	static final int SAVE     = 4;

	static final int SERIAL   = 5;

	Editor editor;

	Image offscreen;
	int width, height;
  
	JPopupMenu popup;
	JMenu menu;
	
	static JButton[] buttons; 

	public EditorToolbar(Editor editor, JMenu menu) {
		this.editor = editor;
	    this.menu = menu;
	
	
		//setLayout(new BorderLayout());
		// Needed for unified toolbar look
		putClientProperty("Quaqua.ToolBar.style", "title");
		setFloatable(false);

		addButtons();
		//addSearchField();
	}


	private void addButtons() {
		Dimension size = getSize();
	    if ((offscreen == null) || (size.width != width) || (size.height != height)) {
			offscreen = createImage(size.width, size.height);
			width = size.width;
			height = size.height;
		}
	
		Image allButtonIcons = Base.getThemeImage("buttons.png", this);
	    Image buttonImages[][] = new Image[BUTTON_COUNT][3];
	    
		for (int i = 0; i < BUTTON_COUNT; i++) {
			for (int state = 0; state < 3; state++) {
				Image image = createImage(BUTTON_WIDTH, BUTTON_HEIGHT);
				Graphics g = image.getGraphics();
				//g.drawImage(allButtonIcons,  -(i * BUTTON_IMAGE_SIZE) - 3, (-2 + state) * BUTTON_IMAGE_SIZE, null);
				//buttonImages[i][state] = image;
			}
		}
		
		buttons = new JButton[BUTTON_COUNT];
		
		JButton button = new JButton("Verify");
		buttons[VERIFY] = button;
		
		
		
		this.add(buttons[VERIFY]);
		
		//this.add(buttons[VERIFY]);
		
		//JButton button = null;

		//button = makeToolbarButton("Verify");
		//add(button, BorderLayout.WEST);
		//button = makeToolbarButton("Upload");
		//add(button);
		//button = makeToolbarButton("Take a Shit");
		//add(button);
		
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