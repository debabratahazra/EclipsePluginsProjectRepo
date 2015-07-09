package com.odcgroup.t24.enquiry.properties.sources;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class SelectionPropertySourceWrapper implements IPropertySource {

	private IPropertySource source;

	/**
	 * @param source
	 * @param selection
	 * @param sourceProvider
	 */
	public SelectionPropertySourceWrapper(IPropertySource source) {
		super();
		this.source = source;
	}

	@Override
	public Object getEditableValue() {
		return source.getEditableValue();
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return source.getPropertyDescriptors();
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (isBooleanType(id)) {
			if (source.getPropertyValue(id) == null)
				return false;
		}
		return source.getPropertyValue(id);
	}

	@Override
	public boolean isPropertySet(Object id) {
		return source.isPropertySet(id);
	}

	@Override
	public void resetPropertyValue(Object id) {
		source.resetPropertyValue(id);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		source.setPropertyValue(id, value);
	}

	/**
	 * This method is used to identify the properties which are of type Boolean
	 * and used to set a default value of false if tag is missing in the DSL
	 * 
	 * @param id
	 * @return
	 */
	private boolean isBooleanType(Object id) {
		return (id.equals(EnquiryPackage.eINSTANCE.getSelection_Mandatory().getName()) || id
				.equals(EnquiryPackage.eINSTANCE.getSelection_PopupDropDown().getName()));
	}

}
