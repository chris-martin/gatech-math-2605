// Matrix.java

package edu.gatech.gth773s.math;

public class Matrix {

	public static Matrix identity(int size) {
		Matrix I = new Matrix(size, size);
		for (int i=size-1; i>=0; i--)
			I.entries[i][i] = 1;
		return I;
	}
	
	protected int rows, cols;
	protected double[][] entries;

	/**
	 * Construct a zero-filled matrix
	 */
	public Matrix(int rows, int cols) {
		if (rows<=0 || cols<=0)
			throw new IllegalArgumentException("Row and column counts must be greater than zero");
		this.rows = rows;
		this.cols = cols;
		entries = new double[rows][cols];
	}
	
	/**
	 * Construct a new Matrix from a rectangular
	 * array of entries.
	 */
	public Matrix(double[][] entries) {
		cols = entries[0].length;
		rows = entries.length;
		for (int i=1; i<entries.length; i++)
			if (entries[i].length != cols)
				throw new IllegalArgumentException("entries array must be rectangular");
		this.entries = entries;
	}
	
	/**
	 * Construct a Matrix from an array of entries
	 * and a number of columns.
	 */
	public Matrix(double[] entries, int cols) {
		if (entries.length % cols != 0)
			throw new IllegalArgumentException();
		this.cols = cols;
		rows = entries.length / cols;
		this.entries = new double[rows][cols];
		int n=0;
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
				this.entries[i][j] = entries[n++];
	}
	
	public Matrix(Matrix m, int rows, int cols) {
		if (m.entries.length!=rows || m.entries[0].length!=cols)
			throw new DimensionMismatchException("Matrix does not have given dimensions");
		entries = m.entries;
		rows = m.rows;
		cols = m.cols;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public double getValue(int row, int col) {
		return entries[row][col];
	}
	
	public double[] getValueArray() {
		double[] ret = new double[rows*cols];
		int n = ret.length;
		for (int i=rows-1; i>=0; i--)
			for (int j=cols-1; j>=0; i--)
				ret[--n] = entries[i][j];
		return ret;
	}
	
	public double[] getRowArray(int row) {
		return entries[row];
	}
	
	public Vector getRowVector(int row) {
		return new Vector(getRowArray(row));
	}
	
	public double[] getColArray(int col) {
		double[] arr = new double[rows];
		for (int j=rows-1; j>=0; j--)
			arr[j] = entries[j][col];
		return arr;
	}
	
	public Vector getColVector(int col) {
		return new Vector(getColArray(col));
	}
	
	public double[] getDiagonalArray() {
		if (rows != cols)
			throw new DimensionMismatchException("Matrix is not square");
		double[] ret = new double[rows];
		for (int i=rows-1; i>=0; i--)
			ret[i] = entries[i][i];
		return ret;
	}
	
	public boolean isSymmetric(double epsilon) {
		for (int i=rows-1; i>=0; i--)
			for (int j=i-1; j>=0; j--)
				if (Math.abs(entries[i][j]-entries[j][i]) > epsilon)
					return false;
		return true;
	}
	
	public boolean isSymmetric() {
		return isSymmetric(0);
	}
	
	public void fillRandom(double min, double max) {
		double scale = max-min;
		for (int i=rows-1; i>=0; i--)
			for (int j=cols-1; j>=0; j--)
				entries[i][j] = min + (Math.random() * scale);
	}
	
	public void fillRandom() {
		fillRandom(-10, 10);
	}
	
	public void fillRandomSymmetric(double min, double max) {
		if (rows != cols)
			throw new DimensionMismatchException("Matrix is not square");
		double scale = max-min;
		for (int i=rows-1; i>=0; i--) {
			entries[i][i] = min + (Math.random() * scale);
			for (int j=i-1; j>=0; j--)
				entries[i][j] = entries[j][i] = min + (Math.random() * scale);
		}
	}
	
	public void fillRandomSymmetric() {
		fillRandomSymmetric(-10, 10);
	}
	
	public double offDiag() {
		if (rows != cols)
			throw new DimensionMismatchException("Matrix is not square");
		double off = 0;
		for (int i=rows-1; i>=0; i--)
			for (int j=cols-1; j>=0; j--)
				if (i != j)
					off += Math.pow(entries[i][j], 2);
		return off;
	}
	
	public double onDiag() {
		if (rows != cols)
			throw new DimensionMismatchException("Matrix is not square");
		double on = 0;
		for (int i=rows; i>=0; i--)
			on += Math.pow(entries[i][i], 2);
		return on;
	}
	
	public Matrix transpose() {
		Matrix t = new Matrix(cols, rows);
		for (int i=rows-1; i>=0; i--) {
			t.entries[i][i] = entries[i][i];
			for (int j=i-1; j>=0; j--) {
				t.entries[i][j] = entries[j][i];
				t.entries[j][i] = entries[i][j];
			}
		}
		return t;
	}
	
	public Matrix times(Matrix B) {
		if (rows!=B.cols || cols!=B.rows)
			throw new DimensionMismatchException("Dimension mismatch for matrix multiplication");
		Matrix m = new Matrix(rows, rows);
		for (int i=rows-1; i>=0; i--) // row of A
			for (int j=rows-1; j>=0; j--) // col of B
				m.entries[i][j] = getRowVector(i).dot(B.getColVector(j));
		return m;
	}
	
	public Matrix times(double s) {
		Matrix m = new Matrix(rows, cols);
		for (int i=rows-1; i>=0; i--)
			for (int j=cols-1; j>=0; j--)
				m.entries[i][j] = entries[i][j]*s;
		return m;
	}
	
	public void timesEquals(double s) {
		for (int i=rows-1; i>=0; i--)
			for (int j=cols-1; j>=0; j--)
				entries[i][j] *= s;
	}
	
	public Matrix plus(Matrix B) {
		if (B.rows!=rows || B.cols!=cols)
			throw new DimensionMismatchException("Dimension mismatch for matrix addition");
		Matrix m = new Matrix(rows, cols);
		for (int i=rows-1; i>=0; i--)
			for (int j=cols-1; j>=0; j--)
				m.entries[i][j] = entries[i][j] + B.entries[i][j];
		return m;
	}
	
	public void plusEquals(Matrix B) {
		if (B.rows!=rows || B.cols!=cols)
			throw new DimensionMismatchException("Dimension mismatch for matrix addition");
		for (int i=rows-1; i>=0; i--)
			for (int j=cols-1; j>=0; j--)
				entries[i][j] += B.entries[i][j];
	}
	
	public Matrix minus(Matrix B) {
		if (B.rows!=rows || B.cols!=cols)
			throw new DimensionMismatchException("Dimension mismatch for matrix subtraction");
		Matrix m = new Matrix(rows, cols);
		for (int i=rows-1; i>=0; i--)
			for (int j=cols-1; j>=0; j--)
				m.entries[i][j] = entries[i][j] - B.entries[i][j];
		return m;
	}
	
	public void minusEquals(Matrix B) {
		if (B.rows!=rows || B.cols!=cols)
			throw new DimensionMismatchException("Dimension mismatch for matrix subtraction");
		for (int i=rows-1; i>=0; i--)
			for (int j=cols-1; j>=0; j--)
				entries[i][j] -= B.entries[i][j];
	}
	
	/**
	 * Apply the matrix to a vector.
	 */
	public Vector applyTo(Vector v) {
		if (cols!=v.values.length)
			throw new DimensionMismatchException("Dimension mismatch for matrix-vector application");
		Vector ret = new Vector(cols);
		for (int i=cols-1; i>=0; i--)
			ret.values[i] = getRowVector(i).dot(v);
		return ret;
	}
	
	public String toString() {
		java.text.DecimalFormat fmt = new java.text.DecimalFormat("#.##");
		StringBuffer str = new StringBuffer();
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++)
				str.append(fmt.format(entries[i][j])+"\t");
			str.append("\n");
		}
		str.deleteCharAt(str.length()-1);
		return str.toString();
	}
	
}
