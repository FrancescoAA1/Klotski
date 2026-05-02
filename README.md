# KLOTSKI

## **The Game**

Klotski is a puzzle consisting of a 4x5 grid box containing colored blocks of various sizes, including a red square block of size 2x2. The goal of the game is to move the red block toward the exit located at the bottom of the grid, using the fewest possible moves. To achieve this, the user can move blocks vertically or horizontally, ensuring that a moved block does not end up in occupied cells.

The game, originally created in the 20th century, has been released in many different configurations, each varying in block types and difficulty level. In this desktop version, the user will be able to challenge their logical skills with some of the most well-known configurations, supported by additional features designed to help solve the puzzle more efficiently.

__________________________________________________________________________

At startup, the game displays an intuitive main menu with the following four options (presented clockwise starting from the top-left button):

#### 1. **New Game**:
This option allows the user to start a new game. The user can choose one of the predefined configurations and begin playing immediately. Alternatively, the user can return to the main menu by clicking the back arrow in the top-left corner.

#### 2. **Load Game**:
The "Load Game" option allows the user to resume a previously saved game. For each saved game, the configuration number, number of moves already made, start date, and current status are displayed. This mode allows the user to return to a specific point in the game without losing progress. Again, the user can return to the main menu by clicking the back arrow in the top-left corner.

#### 3. **Exit**:
The "Exit" option allows the user to close the game.

#### 4. **Credits**:
In the "Credits" section, the user can view the names of the Klotski game developers. By clicking on each icon, it is possible to view each developer’s LinkedIn profile.

_________________________________________________________________________

When selecting either **New Game** or **Load Game**, after choosing a configuration or a saved game, the user is presented with the classic game board, representing the puzzle field. Here, the user can drag blocks with the mouse, undo the previous move, save the current game, request hints for the next best move, and return to the list of available configurations. The buttons enabling these features are listed clockwise starting from the top-left corner:

- **Home** button, to return to the main menu

- **Level** button, to return to the configuration selection screen

- **Hint** button, to receive suggestions; as specified in the project requirements, the game is capable of solving each configuration from the start without user intervention. However, if the player performs moves that do not match the optimal solution, the Hint function resets the configuration to the initial state (decreasing the number of moves) and then executes the optimal solution.

> Important Note: by right-clicking the button, it is possible to view a fast-forwarded solution of the configuration.

- **Save** button, to save the current game. The saved game is immediately available in the "Load Game" section, allowing the user to restore previously executed moves.

- **Undo** button, to undo the current move. This feature is not available at the start of a new game or if the history of a saved game has been deleted.

- **Home** button, to return to the main menu.

As in the physical version of the game, a match ends when the red block is positioned at the opening in the bottom-center of the blue frame. In this case, the player will see a victory banner displaying the number of moves performed and the hints used. Additionally, the window provides the option to return to the main menu (**Home** button) or to return to the move prior to victory (**Continue** button).

_________________________________________________________________________

# **User Instructions**

1. To download the application, first access the following link:  
https://github.com/FrancescoAA1/Klotski/releases/  
There you will find two versions of the game, available respectively for Windows and macOS operating systems.

2. Select the version corresponding to your operating system and click on the file **Klotski.jar** to start downloading the program.

3. Once the download is complete, you will find the file in your computer’s Downloads folder.

4. Before running the executable, make sure you have Java installed on your machine. You can check whether Java is already installed by following these steps:

• Open the Windows Command Prompt or macOS Terminal.

• Type the command `java -version` and press Enter.

• If the command returns information about the installed Java version, Java is already present on your system. In that case, you can proceed to the next step.

• If the command is not recognized or Java is not installed, you can download it from the official website:  
https://www.java.com/it/download/manual.jsp

• Make sure you have also installed the JDK (version 17 or later), which is required to run the libraries used by Klotski.jar. If you do not have a JDK installed, you can download it directly from the official website:  
https://www.oracle.com/java/technologies/downloads/

5. Once Java installation is complete (if necessary), return to your Downloads folder and double-click the Klotski .jar file.

The Klotski executable will open and you can start playing.

**Note:** From the link provided in step 1, it is also possible to download the zip files of the game in order to inspect the source code.

**Note for macOS users:** to properly run KlotskiMacOS.jar, the following steps are required:

• Allow execution of applications from unidentified developers. To do this, open "System Settings", go to "Privacy & Security", and authorize the execution of KlotskiMacOS.jar by clicking the appropriate button and entering the current user’s credentials (which must have root privileges).

• To enable double-click execution of KlotskiMacOS.jar, the file must be placed inside a folder with root privileges for all users. To do this, create a folder, right-click it, and select "Get Info". In the "Sharing & Permissions" section, set "read and write" privileges for every user who has access to that folder.

• If you want to run KlotskiMacOS.jar in any other folder that does not meet the above requirements, you must run it from the terminal. To do so, open a terminal in the folder containing KlotskiMacOS.jar and execute the following command:

sudo java -jar KlotskiMacOS.jar

(In this case, a folder named "target" will be created by the game in the same directory, containing log files of the matches)
