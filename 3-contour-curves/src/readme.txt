Contour Curves
--------------------------------------------------------------------------------------

Compilation:
     javac *.java
Running:
     java ContourApplication

Source zip package includes:
   - All source .java files for the Contour Curves program
   - The ContourApplication class, for launching the program as a standalone
     application instead of an an applet
   - The functionParser library
   - The Jama library

Gui parameters:
   - x-min, x-max, y-min, y-max
        Boundaries for the graph window
   - Adaptive tile sizing
        Toggles use of curvature analysis to break up troublesome tiles
   - Show gridlines
        Toggles display of the grid tiles used in the graphing algorithm
   - Default grid size
        The number of rows and columns initially used for graphing

Libraries used:
   - functionParser .................................................. Eric Carlen
     http://www.math.gatech.edu/%7Ecarlen/applets/docs/Package-functionParser.html
   - Jama .................................................... The MathWorks, NIST
     http://math.nist.gov/javanumerics/jama/