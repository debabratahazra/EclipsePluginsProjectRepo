package com.odcgroup.ocs.server.embedded.ui;

public class BlockInfo {
	
	private String id;
	
	private int weight;

	public BlockInfo(String id, int weight) {
		this.id = id;
		this.weight = weight;
	}
	
	/**
	 * @return the id
	 */
	String getId() {
		return id;
	}

	/**
	 * @return the weight
	 */
	int getWeight() {
		return weight;
	}

}
