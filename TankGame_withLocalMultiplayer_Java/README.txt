Tank game with local multiplayer created by Kareem Khalidi and John Gauchi for CSC 335 "Object Oriented Programming" at the University of Arizona.

Design Goals:
    1. The client-server interactions are founded upon socket interactions, where Packet objects
    carrying data and a command are serialized and either sent or received using RobustSockets. Each
    RobustSocket both send packets and listening for incoming packets. Back and forth communication
    proceeds where the client sends a command from the server upon user keyboard input, the server
    then executes that command and any other commands in the tick, and sends back the data to the
    clients in a class called GameData that has the objects to be drawn to the screen. The
    client then receives that data and adds the map data to be drawn, as that was omitted to reduce
    packet size and hence improve performance, along with executing any commands, whether sent from
    the server or itself. The observers of the ClientGameHandler are then notified, causing the data
    to be drawn. The system does not use any client-based game state processing and hence
    uses not interpolation. Packets containing GameData were centered around what out to be drawn to
    the screen, including a list of entities, the map name, and the player stats for the scoreboard.

    2. All tanks are built upon the abstract tank class. When creating a new tank, you can specify
    many parameters such as its width and height, its position, its health and armor, what weapon it
    wields, and more. You can also either use the default movement method, or you can overload it
    and define a custom movement script for this specific tank to use instead. You can also use the
    abstract weapon class to create custom weapons with custom ways of firing, which you can then
    give the tanks, and the abstract projectile class to create custom projectiles with custom
    movement and properties. This allows a developer to add new tanks and customize almost anything
    about them without having to alter any code except for the new tank and/or weapon classes.

    3. The maze that constrains the players is loaded through a custom map file system, where a 0
    represents blank space, a 1 represents a wall, and a 2 represents a spawn point. The map class
    reads in the file and initializes the map class with all the necessary spawn point and wall
    objects. This means that adding new maps is as simple as copying an old map file, switching
    around the numbers until you are happy with the new map, and then adding a new option on the
    main menu drop down to select this new map. When a player tank is moving around the map, it
    checks to see if it has collided with a wall. If it has, the tank moves back to the last
    position before it collided with the wall.

    4. Tank movements and the firing of a tank are animated with the swt class and the command
    design pattern. When moving your tank in a direction, the MoveTank command calls the tanks move
    method, which then changes the tanks position in the direction it is facing. So when the tank is
    redrawn once a tick, it looks like it is moving. The bullets use a similar method, except once
    the space bar key is pressed, the MoveTankShell command will keep adding new MoveTankShell
    commands to the Server Game Handler command queue until the tank shell collides with something.

    5. Players are represented by the Player class. This class stores much useful information about
    the players, such as their name, their tank, their ID, and their stats (kills, death, etc). The
    players are all saved in an ArrayList of players on the Server Game Handler.

    6. The game play starts by having one player select a tank, map, and game mode, and host a game.
    The other players then enter in the host players IP Address, select a tank, and join the game.
    If the game mode is one life, then the game ends when only one tank is left. If it is death
    match, then the game ends once a player reaches 10 kills. Upon ending of the game, all players
    are taken to a game over screen, which has a button to return them to a main menu where they can
    then join/host another game. Players can also manually leave the game early by pausing with the
    esc key and returning to the main menu.

Extensibility:
    The code is extensible in multiple areas. The base package contains many classes to be
    subclassed to add additional functionality, such as Communicator, RobustThread, and Observer.
    Game elements too can be subclassed, Tank, Weapon, or TankShell to provide new tanks, weapons,
    or special tank projectiles. GameMode can also be subclassed to provide new ways to see if games
    have ended, and map information encoding system could provide opportunities for game modes like
    capture the flag, domination, etc. Weapon can also be subclassed to provide new weapons that
    different tank subclasses could hold, including the use different projectiles through
    subclassing.


Flexibility:
    The code sees flexibility through the polymorphism in the Command system, where new commands can
    be added with custom implementations of an abstract execute function to provide new
    functionality that could either be sent between client and server and executed on either one.
    Such flexibility also ties into the Tank and Projectile classes, as they provide functions such
    as fire() and move() which can be overridden in subclasses whilst still using the same MoveTank,
    Shoot, and MoveTankShell commands through polymorphism. Flexibility is also seen in the
    wrappers involving KeyInput and Window, which allow for a different GUI system to be easily
    interface with certain actions through subclassing and using polymorphism.

Reuse:
    Much of the code can be easily reused like that found in the base package, such as RobustSocket,
    which can allow for socket communication of packets which hold any sort of data or command, as
    well as their  corresponding communicators which can function as a tick-based network client or
    server processor. Code that is found in entity or player can also be reused with minimal
    modification, as well as Client and Server Game Handlers used for a different game along with
    some of the commands. Classes such as weapon and projectile can also be reused and extended in
    similar games.

Design Pattern Usage:
    - Singleton: ClientGameHandler, ServerGameHandler, GameUI, GameController

    - Composite: Entities, each entity has positional and drawing functionality and can be treated
    uniformly

    - Command: Commands can be sent to and from the server by the client, functionality handled
    abstract execution function that utilizes the resources of the client or server, there is a
    little bit of visitor-like work done here as the Communicator is passed in the execute function,
    extending the work that a particular Communicator can do without needing to modify Communicator
    or a subclass (although it was appropriate to do this at times).

    - Iterator: Java iterators are used throughout

    - Observer: The ClientGameHandler is a subject to the GameView observer, the ServerGameHandler
    can  be likened to a subject that is updating ClientGameHandlers through RobustSocket

    - Strategy: The Weapon class hsa an abstract function that enables firing mechanisms of
    different weapons while all being handled through the Shoot command calling such function and
    somewhat of the controllers

    - Adapter: Threads, Sockets, and SWT Window drawing and Input Events have wrapper classes either
    to make the functionality more abstract and flexible for other systems, like in the case of the
    SWT wrapper classes, or to provide an easier interface for commonly used functionality like
    Sockets that send and receive objects through serialization/deserialization or threads to
    provide smoother start/stop/looped run behavior.

    - Facade: The Main Menu class in some way functions like a facade, with an open function being
    able to start and lead to subsequent management of the game, simplifying the main function.