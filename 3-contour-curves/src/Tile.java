// Tile.java

import edu.gatech.gth773s.math.Vector2d;
import functionParser.Evaluator;
import Jama.*;

public class Tile {

	public double xmin, ymin, xmax, ymax;
	
	public Tile(double x, double y, double width, double height) {
		xmin = x;
		ymin = y;
		xmax = x+width;
		ymax = y+height;
	}
	
	public Tile[] split() {
		double tileWidth = (xmax-xmin)/2.;
		double tileHeight = (ymax-ymin)/2.;
		Tile[] ret = new Tile[4];
		ret[0] = new Tile(xmin, ymin, tileWidth, tileHeight);
		ret[1] = new Tile(xmin+tileWidth, ymin, tileWidth, tileHeight);
		ret[2] = new Tile(xmin, ymin+tileHeight, tileWidth, tileHeight);
		ret[3] = new Tile(xmin+tileWidth, ymin+tileHeight, tileWidth, tileHeight);
		return ret;
	}

	public double area() {
		return (xmax-xmin)*(ymax-ymin);
	}
	
	public boolean isDegenerate(Evaluator function, double epsilon) {
		
		Vector2d[] points = new Vector2d[9];
		points[0] = new Vector2d(xmin, ymin);
		points[1] = new Vector2d(xmin, ymax);
		points[2] = new Vector2d(xmax, ymin);
		points[3] = new Vector2d(xmax, ymax);
		points[4] = new Vector2d(xmin+(xmax-xmin)/2., ymin);
		points[5] = new Vector2d(xmin+(xmax-xmin)/2., ymax);
		points[6] = new Vector2d(xmin, ymin+(ymax-ymin)/2.);
		points[7] = new Vector2d(xmax, ymin+(ymax-ymin)/2.);
		points[8] = new Vector2d(xmin+(xmax-xmin)/2., ymin+(ymax-ymin)/2.);
		
		double[][] Mvalues = new double[9][6];
		double[] bValues = new double[9];
		
		for (int r=0; r<9; r++) {
			Mvalues[r][0] = 1;
			Mvalues[r][1] = points[r].getX() - points[8].getX();
			Mvalues[r][2] = points[r].getY() - points[8].getY();
			Mvalues[r][3] = Math.pow(points[r].getX() - points[8].getX(), 2) / 2.;
			Mvalues[r][4] = (points[r].getX() - points[8].getX()) * (points[r].getY() - points[8].getY());
			Mvalues[r][5] = Math.pow(points[r].getY() - points[8].getY() , 2) / 2;
			
			bValues[r] = function.evaluate(points[r].getX(), points[r].getY());
		}
		
		Matrix M = new Matrix(Mvalues);
					
		Matrix b = new Matrix(bValues, 9);
		Matrix a = M.solve(b);
		Matrix r = M.times(a).minus(b);
		
		double num = Math.max((xmax-xmin),(ymax-ymin)) * Math.sqrt(
						Math.pow(r.normInf(), 2) / (
								9 * ( Math.pow(a.get(0,0), 2) + Math.pow(a.get(1,0), 2) )
						)
					);
		if (num > epsilon)
			return true;
		return false;
	}
	
	public String toString() {
		return "Rectangle: ("+xmin+","+ymin+") ("+xmax+","+ymax+")";
	}
	
}
