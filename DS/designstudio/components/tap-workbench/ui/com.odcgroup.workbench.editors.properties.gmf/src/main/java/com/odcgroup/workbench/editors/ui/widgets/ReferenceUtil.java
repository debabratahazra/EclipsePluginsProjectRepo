package com.odcgroup.workbench.editors.ui.widgets;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class ReferenceUtil {
	
	private String label;
	private EStructuralFeature feature = null;
	private EObject refObject = null;
	private boolean sorter = false;
	private boolean editable = true;

	/**
	 * @param label
	 * @param feature
	 * @param refObject
	 */
	public ReferenceUtil(String label, EStructuralFeature feature, EObject refObject, boolean sorter, boolean editable) {
		this.label = label;
		this.feature = feature;
		this.refObject = refObject;
		this.sorter = sorter;
		this.editable = editable;
	}

	/**
	 * @return
	 */
	public EStructuralFeature getFeature() {
		return feature;
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return
	 */
	public EObject getRefObject() {
		return refObject;
	}

	/**
	 * @return
	 */
	public boolean isSorter() {
		return sorter;
	}

	/**
	 * @return
	 */
	public boolean isEditable() {
		return editable;
	}


}
