/* -*- mode: java; c-basic-offset: 2; indent-tabs-mode: nil -*- */

/*
  Copyright (c) 2012 Pat Hickey <pat@moreproductive.org>
  All Rights Reserved.

  Sorry if this code smells - I have no idea how to write Java. - 20 Dec 2012

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License version 2
  as published by the Free Software Foundation.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software Foundation,
  Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package processing.app.ardupilot;

import java.util.*;
import processing.app.ardupilot.APHal;
import processing.app.Preferences;

public class APConfig {
    public HashMap<String, APHal> halBoardsTable;
    public String halName;

    public APConfig () {
        /* We initialize the board table the same every time, I
         * just don't really know how to write these in place. */
        createBoardTable();
        /* Load the hal board from preferences, except when the preference
         * doesn't exist or doesn't match a known board. Default to none. */
        String prefHalBoard = Preferences.get("ardupilot.hal");
        if (prefHalBoard == null) {
            setBoard("none");
        } else if (!halBoardsTable.containsKey(prefHalBoard)) {
            setBoard("none");
        } else {
            setBoard(prefHalBoard);
        }
    }

    private void createBoardTable() {
        halBoardsTable = new HashMap<String, APHal>();
        /** none is a special case - pass null as configFlag and halName.
         * I need a placholder to just show "None" for a menuitem without
         * getting too crazy.
         */
        halBoardsTable.put("none",
                new APHal(null,
                          "None",
                          null));
        /**
         * APM1 and APM2 are what you'd expect.
         * I'm not making any effort to support APM1/1280 as we don't support
         * that for developers - tridge keeps it alive for the remaining
         * ArduPlane users who haven't upgraded.
         */
        halBoardsTable.put("apm1",
                new APHal("HAL_BOARD_APM1",
                          "ArduPilot Mega 1",
                          "apm1"));
        halBoardsTable.put("apm2",
                new APHal("HAL_BOARD_APM2",
                          "ArduPilot Mega 2.x",
                          "apm2"));
    }

    public APHal getBoard() {
        return halBoardsTable.get(halName);
    }

    public void setBoard(String name) {
        if (halBoardsTable.containsKey(name)) {
            APHal hal = halBoardsTable.get(name);

            Preferences.set("ardupilot.hal", name);
			halName = name;

            if (hal.boardName != null) {
                Preferences.set("board", hal.boardName);
				Preferences.set("target_package", "ardupilot");
				Preferences.set("target_platform", "avr");
            }   
        }
    }

    public Boolean excludeCore() {
        /* hack */
        APHal board = getBoard();

		if (board.configFlag != null) {
			return true;
		} else {
			return false;
		}
    }   

    public String getFlags() {
        APHal board = getBoard();
		String flags = "";
		
        if (board.configFlag != null) {
            flags = "-mcall-prologues -DCONFIG_HAL_BOARD=" + board.configFlag + " -DEXCLUDECORE";
        }

        return flags;
    }
}
