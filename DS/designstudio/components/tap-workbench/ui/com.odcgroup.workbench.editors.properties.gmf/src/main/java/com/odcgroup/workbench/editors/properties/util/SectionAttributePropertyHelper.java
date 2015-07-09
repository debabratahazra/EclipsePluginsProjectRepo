package com.odcgroup.workbench.editors.properties.util;

import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Widget;

/**
 * @author pkk
 * convenience class
 */
public class SectionAttributePropertyHelper implements SectionPropertyHelper {
	
	protected String label;
	protected Widget control;
	protected EStructuralFeature feature;
	protected EReference reference;
	protected boolean textarea = false;
	protected String tooltip;
	protected boolean unique = false;
	protected String tab;
	protected List enumeratedValues = null;
	protected boolean enumeration = false;
	protected boolean ID = false;
	
	protected boolean localizeable = false;
	protected AdapterFactory adapterFactory = null;
	protected int attributeIndex;
	protected boolean widthAsTextArea = false;
	
	
	/**
	 * @return
	 */
	public Widget getControl() {
		return control;
	}

	/**
	 * @param control
	 */
	public void setControl(Widget control) {
		this.control = control;
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
	public EReference getReference() {
		return reference;
	}
	
	public SectionAttributePropertyHelper(String label, EStructuralFeature feature, EReference reference, List values) {
		this(label, feature, reference);
		this.enumeratedValues = values;
		if (values != null && !values.isEmpty()){
			this.enumeration = true;
		}
		
	}
	
	public SectionAttributePropertyHelper(String label, EStructuralFeature feature, EReference reference, boolean textarea) {
		this(label, feature, reference);
		this.textarea = textarea;
	}
	
	/**
	 * @param label
	 * @param tabName
	 * @param feature
	 * @param reference
	 * @param textarea
	 */
	public SectionAttributePropertyHelper(String label, String tabName,EStructuralFeature feature, EReference reference, boolean textarea) {
		this(label, feature, reference);
		this.textarea = textarea;
		this.tab = tabName;
	}
	
	public SectionAttributePropertyHelper(String label, boolean unique, EStructuralFeature feature, EReference reference) {
		this(label, feature, reference);
		this.unique = unique;
	}
	
	public SectionAttributePropertyHelper(String label, boolean unique, boolean ID, EStructuralFeature feature, EReference reference) {
		this(label, feature, reference);
		this.unique = unique;
		this.ID = ID;
	}
	
	public SectionAttributePropertyHelper(String label, String tabName, boolean unique, EStructuralFeature feature, EReference reference) {
		this(label, feature, reference);
		this.unique = unique;
		this.tab = tabName;
	}

	/**
	 * @param label
	 * @param feature
	 * @param reference
	 */
	public SectionAttributePropertyHelper(String label, EStructuralFeature feature, EReference reference) {
		this.label = label;
		this.feature = feature;
		this.reference = reference;
	}
	
	/**
	 * @param label
	 * @param feature
	 * @param reference
	 */
	public SectionAttributePropertyHelper(String label, EStructuralFeature feature, EReference reference, String tooltip) {
		this.label = label;
		this.feature = feature;
		this.reference = reference;
		this.tooltip = tooltip;
	}
	
	/**
	 * @param label
	 * @param feature
	 * @param reference
	 * @param tooltip
	 * @param widthAsTextArea
	 */
	public SectionAttributePropertyHelper(String label, EStructuralFeature feature, EReference reference, String tooltip, boolean widthAsTextArea) {
		this(label, feature, reference, tooltip);
		this.widthAsTextArea = widthAsTextArea;
	}
	
	/**
	 * @param label
	 * @param tabName
	 * @param feature
	 * @param reference
	 * @param tooltip
	 */
	public SectionAttributePropertyHelper(String label, String tabName, EStructuralFeature feature, EReference reference, String tooltip) {
		this.label = label;
		this.feature = feature;
		this.reference = reference;
		this.tooltip = tooltip;
		this.tab = tabName;
	}
	
	/**
	 * @param label
	 * @param tabName
	 * @param feature
	 * @param reference
	 */
	public SectionAttributePropertyHelper(String label, String tabName, EStructuralFeature feature, EReference reference) {
		this.label = label;
		this.feature = feature;
		this.reference = reference;
		this.tab = tabName;
	}

	/**
	 * @return
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * @param tooltip
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public boolean isTextarea() {
		return textarea;
	}

	public boolean isUnique() {
		return unique;
	}

	public boolean isEnumeration() {
		return enumeration;
	}

	public List getEnumeratedValues() {
		return enumeratedValues;
	}

	public boolean isID() {
		return ID;
	}

	public void setID(boolean id) {
		ID = id;
	}
	
	
	public boolean isLocalizeable() {
		return localizeable;
	}

	public AdapterFactory getAdapterFactory() {
		return adapterFactory;
	}
	
	/**
	 * @param adapterFactory
	 * @param attributeIndex
	 */
//	public void setLocalizeableAdapterFactory(AdapterFactory adapterFactory, int attributeIndex) {
//		this.localizeable = true;
//		this.adapterFactory = adapterFactory;
//		this.attributeIndex = attributeIndex;
//	}

	public int getAttributeIndex() {
		return attributeIndex;
	}

	public boolean isWidthAsTextArea() {
		return widthAsTextArea;
	}

	public void setWidthAsTextArea(boolean widthAsTextArea) {
		this.widthAsTextArea = widthAsTextArea;
	}
}
