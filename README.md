Signal_Lost

Signal Lost is a work in progress game engine. I have been steadily working on it during my
study towards a bachelors of Computer and Mathematical Science. Because of this many of the
currently implemented features are poorly constructed and need revisiting. I would consider
much of the project to be of poor quality, however this was exactly the point. This project
was intended as a learning experience for myself, and an experimentation into how games are
constructed within Java specifically.

The project is aproximately 30% finished. However much of what exists, as previously stated
, must be revisited and optimised. As it currently stands the game does not run well on anything 
short of a high-spec modern computer.

Signal Lost can be broken down into four main parts as follows:
	-Game Logic Componants: There are many parts within the project that serve only to
		provide logic for various other systems implemented within the project.
		These are likely the cause of much of the failing of the project and I intend
		to revist much of them in an attempt to optimise run speed.
	-Console, and font Componants: The game features a console that can be opened with the f12
		key. The font used to print text within the console is generated dynamically
		at run time, and is another candidate for slowdown. In general the font system
		is lacking and I would not recommend using it as an example in any case. The
		console itself exists to provide commands to enable and disable varous debugging
		systems. There were plans to futher expand upon the console system and include
		additional commands and gameplay functions.
	-Map Editor Componants: Signal Lost includes a build in map editor accessible through console
		commands. The map editor allows a user to place and delete objects within a game map,
		save and load game maps, and switch the current game map. In future projects I would
		advise to avoid including a map editor within the engine itself and instead create
		a second program for such work.
	-Sprite Loading Componants: Signal Lost makes use of CBA files creted with the tool CBA-Draw
		as graphical objects. Because of this much custom sprite loading and storing was
		required. In future I would like to expand upon this library of loading protocals
		and improve sprite caching, as it stands a very high usage of ram is required when
		running the game, and this is mostly contributed to by the sprite loading system.

Playing the game is relatively simple, WASD to move, SPACE to jump, and LEFT MOUSE BUTTON to shoot.
Players may notice that recticle for the weapon changes depending on where the player is aiming,
and how accurate their weapon is. Many statistical aspects of the weapons may vary based on which
parts the weapon uses when being generated.

Signal Lost features the foundations of a weapon creating and customization mechanic. Weapons are made
from individual weapon parts, which can be interchanged. The intention for this system was to allow
players to create weapons specific to their style of gameplay. This system is rudementary and is intended
to be expaned upon.

Console Commands:

The console is opened by pressing the "F12" key. A window will be opened provide a text log of
both previous commands and command output, as well as a text edit section at the bottom which
a player can use to input commands. The available commands are as follows:

	-"overlay collision": shows the collision maps of objects within the game level.
	-"overlay fps": displays the current fps (Frames per second) and tps (Ticks per second) in the
		top left hand side of the screen.
	-"overlay weaponcones": displays weapons cones of fire.
	-"overlay hitboxes": displays each characters hitbox.
	-"overlay grid": overlays a grid pattern onto the game, useful when editing levels.
	-"overlay grid set <integer number>": sets the size of the grid pattern being overlayed to the
		specified integer number.

	-"grid set <integer number>": sets the grid snap to the specified integer number, objects
		being placed in build mode snap to this grid.
	
	-"build mode": enables building mode, when enabled a seperate window will appear allowing for
		the selection of objects to be placed in the map, the object window includes a search
		function that will be outlined below.
	-"build layer <integer number [between 0 and 4]>": Sets the layer for objects to be played on
		while in build mode. There are four layers, the greater the number the futher into the
		background the object will appear. Only objects on layer 0 will collide with characters.

	-"drawn": Prints the number of drawn objects into the console output window.

	-"force save": saves the level to file.
	-"force load": loads the level from file.
	-"force clear": clears all objects from the game map.

	-"player weapon random": randomizes the players current weapon.

Object Window Libraries:

While in Build Mode, a new window will appear. The purpose of this window is to select an object to
place onto the game level. At the bottom of the selector window is a text box, where a player may enter
the name of an object shelf. All items from the selected shelf will appear in the selector window.
The available object shelfs are as follows:

	-"ruins"	-"wood"		-"decoration"
	-"store"	-"plant"	-"ground"
	-"building"	-"moss"
