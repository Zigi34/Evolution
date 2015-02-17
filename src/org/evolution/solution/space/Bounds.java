package org.evolution.solution.space;
import java.util.Random;


public class Bounds {
	private Double minValue = Double.MIN_VALUE;
	private Double maxValue = Double.MAX_VALUE;
	
	private static Random random = new Random();
	
	public Bounds(Double minValue, Double maxValue) {
		if(minValue != null)
			this.minValue = minValue;
		if(maxValue != null)
			this.maxValue = maxValue;
	}
	
	public Double getMinValue() {
		return minValue;
	}
	
	public Double getMaxValue() {
		return maxValue;
	}
	
	public void setMinValue(Double minValue) {
		if(minValue != null)
			this.minValue = minValue;
	}

	public void setMaxValue(Double maxValue) {
		if(maxValue != null)
			this.maxValue = maxValue;
	}

	public Double getRandomValue() {
		return random.nextDouble() * (maxValue - minValue) + minValue;
	}
}
