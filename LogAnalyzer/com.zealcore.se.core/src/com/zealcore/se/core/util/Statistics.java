package com.zealcore.se.core.util;

public class Statistics {
	
	private double minimum;
	
	private double maximum;
	
	private double mean;
	
	private int hitCount;
	
	public Statistics() {
		minimum = Double.MAX_VALUE;
		maximum = - Double.MAX_VALUE;
		mean = 0.0;
		hitCount = 0;
	}
	
	public void update(double value) {
		minimum = (minimum > value) ? value : minimum;
		maximum = (maximum < value) ? value : maximum;
		mean += value;
		hitCount++;
	}

	public double getMinimum() {
		return minimum ;
	}

	public double getMaximum() {
		return maximum ;
	}

	public double getMean() {
		if (hitCount != 0) {
	        return (mean / hitCount ) ;
		}
		return 0.0;
	}
}
