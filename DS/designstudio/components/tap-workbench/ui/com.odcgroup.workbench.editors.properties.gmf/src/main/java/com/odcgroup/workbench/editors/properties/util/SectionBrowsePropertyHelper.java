package com.odcgroup.workbench.editors.properties.util;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.odcgroup.workbench.editors.properties.controls.BrowsePropertyControl;

/**
 * @author pkk convenience class
 */
public class SectionBrowsePropertyHelper implements SectionPropertyHelper {

	protected String label;
	protected BrowsePropertyControl control;
	protected EStructuralFeature feature;
	protected EReference reference;
	protected AdapterFactory adapterFactory;
	protected String browseLabel = "Browse";
	protected String[] extensions;
	protected EClass modelClass;

	/**
	 * @param label
	 * @param feature
	 * @param reference
	 * @param modelRepository
	 * @param modelLookup
	 * @param selectionDialog
	 */
	public SectionBrowsePropertyHelper(String label,
			EStructuralFeature feature, EReference reference,
			AdapterFactory adapterFactory, EClass modelClass, String[] extensions) {
		this.label = label;
		this.feature = feature;
		this.reference = reference;
		this.adapterFactory = adapterFactory;
		this.extensions = extensions;
		this.modelClass = modelClass;
	}

	/**
	 * @return
	 */
	public BrowsePropertyControl getControl() {
		return control;
	}

	/**
	 * @param control
	 */
	public void setControl(BrowsePropertyControl control) {
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

	public String getBrowseLabel() {
		return browseLabel;
	}

	public void setBrowseLabel(String browseLabel) {
		this.browseLabel = browseLabel;
	}

	public String[] getExtensions() {
		return extensions;
	}

	public AdapterFactory getAdapterFactory() {
		return adapterFactory;
	}

	public EClass getModelClass() {
		return modelClass;
	}

}
