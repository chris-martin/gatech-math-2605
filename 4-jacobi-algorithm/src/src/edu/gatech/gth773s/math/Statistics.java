// Statistics.java

package edu.gatech.gth773s.math;

import java.util.ArrayList;

/**
 * A set of numbers upon which statistical analysis
 * can be performed.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class Statistics {
	
	private ArrayList<Number> data;
	
	/**
	 * Create a Statistics object with an empty dataset.
	 */
	public Statistics() {
		data = new ArrayList<Number>();
	}
	
	/**
	 * Create a Statistics object with numbers in it.
	 * @param list numbers to add to the dataset
	 */
	public Statistics(Number[] list) {
		this();
		add(list);
	}
	
	/**
	 * Add a single number to the dataset.
	 * @param number to be added
	 */
	public void add(Number n) {
		data.add(n);
	}
	
	/**
	 * Add an array of numbers to the dataset.
	 * @param list array containing the numbers to add
	 */
	public void add(Number[] list) {
		for (Number n : list)
			add(n);
	}
	
	/**
	 * Calculate the sum of all the numbers.
	 */
	public double sum() {
		double sum = 0;
		for (Number n : data)
			sum += n.doubleValue();
		return sum;
	}
	
	/**
	 * Calculate the average (arithmetic mean).
	 */
	public double mean() {
		return sum()/data.size();
	}
	
	/**
	 * Calculate the standard deviation of the data.
	 */
	public double standardDeviation() {
		double mean = mean();
		double squaredError = 0;
		for (Number n : data)
			squaredError += Math.pow( (n.doubleValue()-mean), 2 );
		return Math.sqrt(squaredError/data.size());
	}
	
}
