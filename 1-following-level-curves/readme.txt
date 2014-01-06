LevelCurvesApplet
--------------------------------------------------------------------------------------

Compilation:
     javac -classpath lib/functionParser.jar *.java
Running:
     java -classpath .;lib/functionParser.jar LevelCurvesApplication

Source zip package includes:
   - All source .java files for the Level Curves program
   - The LevelCurvesApplication class, for launching the program as a standalone
     application instead of an an applet
   - The functionParser library

Features:
   - Click anywhere on the graph to set x(0) at the mouse position.

Parameters:
   - x-min, x-max, y-min, y-max
        Boundaries for the graph window
   - Use corrections
        Check this box to turn on using a correction vector at each step
   - epsilon
        The distance moved in each step
   - N
        The number of times to iterate the algorithm
   - X(0)
        The initial position
   - epsilon for gradients
        The value of epsilon which should be used for approximating partial
	derivatives.

Warning:
   - This program does not make use of threads or any such measure to control running
     time; if you enter a particularly large value for N, it will hang while the
     algorithm runs for a long time.

Libraries used:
   - functionParser .................................................. Eric Carlen
     http://www.math.gatech.edu/%7Ecarlen/applets/docs/Package-functionParser.html
