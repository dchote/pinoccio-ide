Pinoccio IDE, forked from Arduino 1.5.x

The goal of this fork is to provide a more unified UI experience. My current focus is solely on OSX, but I will change focus to Windows once I am done with my initial UI cleanup.

Things I have done thus far:
	- Native main window. I have moved the toolbar and status bar to native widgets.
	- Workspaces.  Workspaces allow different sketchbook locations to be maintained, without having to restart the app. This makes it easeir to maintain separate libraries trees etc. Especially helpful on codebases that dont use standard Arduino libs.
	- ArduPilot HAL layer.  ArduPilot HAL doesnt use the Arduino core, so I have merged some of the changes from the Ardupilot Arduino IDE fork in to this, I have also updated them to work with 1.5.x platforms.
	- Serial monitor cleanup. Making it a little more terminal like, auto launch on successful compilation, etc.

For upcoming stuff, please check out todo.txt

Latest binary build for OSX is 0.0.4 at http://cloud.chote.me/3B2r360Z3Y1r