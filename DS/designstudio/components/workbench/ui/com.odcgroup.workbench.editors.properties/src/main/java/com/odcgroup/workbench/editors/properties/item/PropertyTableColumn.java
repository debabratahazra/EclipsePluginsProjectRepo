package com.odcgroup.workbench.editors.properties.item;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 *
 * @author pkk
 *
 */
public class PropertyTableColumn {
	
	private String name;
	private EStructuralFeature feature;
	private int weight;
	
	/**
	 * @param name
	 * @param feature
	 */
	public PropertyTableColumn(String name, EStructuralFeature feature, int weight) {
		this.name = name;
		this.feature = feature;
		this.weight = weight;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the feature
	 */
	public EStructuralFeature getFeature() {
		return feature;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}	

}
