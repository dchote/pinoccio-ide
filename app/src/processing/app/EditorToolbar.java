package processing.app;
import static processing.app.I18n._;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class EditorToolbar extends JToolBar implements ActionListener {
	static final int TOOLBAR_HEIGHT  = 40;
	
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
	}


	private void addButtons() {
		buttons = new JButton[BUTTON_COUNT];
		
		buttons[VERIFY] = makeToolbarButton("VERIFY", "Verify", "icons/Gear.png", "Verify Sketch");
		this.add(buttons[VERIFY], BorderLayout.WEST);
		
		buttons[UPLOAD] = makeToolbarButton("UPLOAD", "Upload", "icons/Box » This Side Up.png", "Upload Sketch");
		this.add(buttons[UPLOAD]);
		
		
		this.addSeparator();
		
		buttons[NEW] = makeToolbarButton("NEW", "New", "icons/Filetype.png", "New Sketch");
		this.add(buttons[NEW]);
		
		buttons[OPEN] = makeToolbarButton("OPEN", "Open", "icons/Folder » Blue.png", "Open Existing Sketch");
		this.add(buttons[OPEN]);
		
		buttons[SAVE] = makeToolbarButton("SAVE", "Save", "icons/Disk » Hard Disk.png", "Save Sketch");
		this.add(buttons[SAVE]);
		
		this.add(Box.createHorizontalGlue());
		
		buttons[SERIAL] = makeToolbarButton("SERIAL", "Serial", "icons/Magnifying Glass.png", "Serial Terminal");
		this.add(buttons[SERIAL], BorderLayout.EAST);
	}

	protected JButton makeToolbarButton(String actionCommand, String buttonText, String imagePath, String toolTipText) {
	
		JButton button = new JButton(new ImageIcon(Base.getThemeImage(imagePath, this)));
		//button.setVerticalTextPosition(SwingConstants.BOTTOM);
		//button.setHorizontalTextPosition(SwingConstants.CENTER);
		//button.setText(buttonText);
	    button.setToolTipText(toolTipText);
		button.setActionCommand(actionCommand);
		button.addActionListener(this);

	    return button;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		String actionCommand = actionEvent.getActionCommand();
		boolean shiftPressed = (actionEvent.getModifiers() & ActionEvent.SHIFT_MASK) == ActionEvent.SHIFT_MASK;
		
		if (actionCommand == "VERIFY") {
			editor.handleRun(false);
		} else if (actionCommand == "UPLOAD") {
			editor.handleExport(shiftPressed);
		} else if (actionCommand == "NEW") {
			if (shiftPressed) {
				editor.base.handleNew();
			} else {
				editor.base.handleNewReplace();
			}
		} else if (actionCommand == "OPEN") {
			final int x = buttons[OPEN].getX();
			final int y = buttons[OPEN].getY() + (TOOLBAR_HEIGHT - 4);
			
			popup = menu.getPopupMenu();
		    popup.show(EditorToolbar.this, x, y);
		} else if (actionCommand == "SAVE") {
			editor.handleSave(false);
		} else if (actionCommand == "SERIAL") {
			editor.handleSerial();			
		}
	}
	
	public void activate(int buttonIndex) {
		buttons[buttonIndex].setEnabled(false);
	}
	
	public void deactivate(int buttonIndex) {
		buttons[buttonIndex].setEnabled(true);
	}
	
	public Dimension getPreferredSize() {
		return getMinimumSize();
	}

	public Dimension getMinimumSize() {
		return new Dimension(400, TOOLBAR_HEIGHT);
	}

	public Dimension getMaximumSize() {
		return new Dimension(3000, TOOLBAR_HEIGHT);
	}
}