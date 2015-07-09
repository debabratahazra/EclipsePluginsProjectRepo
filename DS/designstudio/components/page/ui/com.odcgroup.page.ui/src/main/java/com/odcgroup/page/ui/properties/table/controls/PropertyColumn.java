package com.odcgroup.page.ui.properties.table.controls;

/**
 *
 * @author pkk
 *
 */
public class PropertyColumn {
	/**      */
	private String name;
	/**      */
	private int minimumWidth;
	/**      */
	private int weight;
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**      */
	private boolean resizeable = false;
	
	/**
	 * @param name
	 * @param minimumWidth
	 * @param weight
	 * @param resizeable
	 */
	public PropertyColumn(String name, int minimumWidth, int weight, boolean resizeable) {
		this.name = name;
		this.minimumWidth = minimumWidth;
		this.weight = weight;
		this.resizeable = resizeable;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the minimumWidth
	 */
	public int getMinimumWidth() {
		return minimumWidth;
	}
	/**
	 * @return the resizeable
	 */
	public boolean isResizeable() {
		return resizeable;
	}
	
	

}
