// JacobiMethod.java

package edu.gatech.gth773s.math;

public class JacobiMethod {
	
	public static final int LARGEST_OFFDIAG = 0;
	public static final int UNSORTED = 1;
	
	Matrix matrix;
	int size;
	int iterations = 0;
	int selectionType;
	
	public JacobiMethod (Matrix m, int selectionType) {
		size = m.getRows();
		if (size != m.getCols())
			throw new RuntimeException("Matrix is not square");
		if (!m.isSymmetric())
			throw new RuntimeException("Matrix is not symmetric");
		if (selectionType<0 || selectionType>1)
			throw new IllegalArgumentException("selectionType out of range");
		matrix = m;
		this.selectionType = selectionType;
	}
	
	public Matrix getMatrix() {
		return matrix;
	}
	
	public int iterations() {
		return iterations;
	}
	
	public double off() {
		return matrix.offDiag();
	}

	public double[] diagonals() {
		return matrix.getDiagonalArray();
	}
	
	/**
	 * Find the location of the largest off-diagonal
	 * entry where i < j (in the top-right).
	 */
	private int[] largestOffDiag() {
		int[] ret = new int[2];
		double v = 0;
		for (int i=size-1; i>=0; i--)
			for (int j=i-1; j>=0; j--)
				if (i != j) {
					double abs = Math.abs(matrix.getValue(i, j));
					if (abs > v) {
						v = abs;
						ret[0] = j;
						ret[1] = i;
					}
				}
		return ret;
	}
	
	private int[] nextOffDiag() {
		int[] ret = new int[2];
		int offDiags = ((size*size)-size)/2;
		int offset = offDiags-1-((iterations-1)%offDiags);
		//ret[0] = (int)quadratic(1,-1,-2*offset)-1;
		ret[0] = (int) ((Math.sqrt(8*offset+1)-1)/2);
		ret[1] = offset - (ret[0]*(ret[0]+1))/2 + 1;
		ret[0] = matrix.rows - 2 - ret[0];
		ret[1] = matrix.cols - ret[1];
		return ret;
	}
	
	public void jacobiStep() {
		iterations++;
		
		int[] entry = (selectionType==LARGEST_OFFDIAG)? largestOffDiag(): nextOffDiag();
		
		/* Form 2x2 matrix B */
		double a = matrix.getValue(entry[0], entry[0]);
		double b = matrix.getValue(entry[0], entry[1]);
		double d = matrix.getValue(entry[1], entry[1]);
		
		/* Solve for larger eigenvalue */
		double eig = ((a+d)/2) + Math.sqrt( Math.pow(b,2) + Math.pow((a-d)/2,2) );
		
		/* Find matrix U which diagonalizes B */
			/* [-b, a-eig]
			 * [a-eig, b] */
		
		/* Form Givens matrix */
		Matrix G = Matrix.identity(size);
		double aMinusEig = a-eig;
		double eigenvectorMagnitude = new Vector2d(b, aMinusEig).magnitude();
		aMinusEig /= eigenvectorMagnitude;
		b /= eigenvectorMagnitude;
		G.entries[entry[0]][entry[0]] = -b;
		G.entries[entry[0]][entry[1]] = G.entries[entry[1]][entry[0]] = aMinusEig;
		G.entries[entry[1]][entry[1]] = b;
		
		matrix = G.transpose().times(matrix).times(G);
		
	}
	
}
