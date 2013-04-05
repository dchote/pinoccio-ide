/* -*- mode: java; c-basic-offset: 2; indent-tabs-mode: nil -*- */

/*
Part of the Processing project - http://processing.org

Copyright (c) 2005-07 Ben Fry and Casey Reas

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 59 Temple Place, Suite 330, Boston, MA	02111-1307	USA
*/

package processing.app;

import processing.app.syntax.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import javax.swing.*;


/**
* Li'l status bar fella that shows the line number.
*/
public class SerialMonitorLineStatus extends JToolBar {
	static final int TOOLBAR_HEIGHT	 = 20;

	JLabel boardName = new JLabel();
	JLabel serialPort = new JLabel();
	JLabel serialRate = new JLabel();


	public SerialMonitorLineStatus() {		
		putClientProperty("Quaqua.ToolBar.style", "bottom");
		setFloatable(false);
		
		addLabels();
	}
	
	private void addLabels() {
		if (boardName.getText() == "" && serialPort.getText() == "") {
			Map<String, String> boardPreferences =	Base.getBoardPreferences();
		
			if (boardPreferences != null) {
				setBoardName(boardPreferences.get("name"));
			} else {
				setBoardName("-");
			}
			
			setSerialPort(Preferences.get("serial.port"));
			setSerialRate(Preferences.getInteger("serial.debug_rate"));
		}
		
		this.add(serialRate, BorderLayout.WEST);
		
		this.add(Box.createHorizontalGlue());
		
		this.add(boardName, BorderLayout.EAST);
		this.addSeparator();
		this.add(serialPort, BorderLayout.EAST);
		this.add(Box.createRigidArea(new Dimension(16,16)));
	}

	public void setBoardName(String boardName) { 
		this.boardName.setText(boardName); 
	}
	
	public void setSerialPort(String serialPort) { 
		this.serialPort.setText(serialPort); 
	}

	public void setSerialRate(int serialRate) { 
		this.serialRate.setText("Baud: " + serialRate); 
	}


	public Dimension getPreferredSize() {
		return getMinimumSize();
	}

	public Dimension getMinimumSize() {
		return new Dimension(300, TOOLBAR_HEIGHT);
	}

	public Dimension getMaximumSize() {
		return new Dimension(3000, TOOLBAR_HEIGHT);
	}
}
