package com.odcgroup.workbench.editors.properties.util;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Widget;

public class SectionBooleanPropertyHelper implements SectionPropertyHelper {
	
	protected String label;
	protected Widget trueControl;
	protected Widget falseControl;
	protected EStructuralFeature feature;
	protected String trueLabel;
	protected String falseLabel;
	
	/**
	 * @param trueLabel
	 * @param falseLabel
	 * @param feature
	 */
	public SectionBooleanPropertyHelper(String trueLabel, String falseLabel, EStructuralFeature feature){
		this.trueLabel = trueLabel;
		this.falseLabel = falseLabel;
		this.feature = feature;
	}
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.util.SectionPropertyHelper#getLabel()
	 */
	public String getLabel() {
		return null;
	}


	/**
	 * @return
	 */
	public Widget getTrueControl() {
		return trueControl;
	}


	/**
	 * @param trueControl
	 */
	public void setTrueControl(Widget trueControl) {
		this.trueControl = trueControl;
	}


	/**
	 * @return
	 */
	public Widget getFalseControl() {
		return falseControl;
	}


	/**
	 * @param falseControl
	 */
	public void setFalseControl(Widget falseControl) {
		this.falseControl = falseControl;
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
	public String getTrueLabel() {
		return trueLabel;
	}


	/**
	 * @return
	 */
	public String getFalseLabel() {
		return falseLabel;
	}

}
