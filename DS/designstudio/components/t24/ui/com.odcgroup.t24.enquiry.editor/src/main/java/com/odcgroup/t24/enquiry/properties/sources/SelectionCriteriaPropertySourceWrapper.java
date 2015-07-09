package com.odcgroup.t24.enquiry.properties.sources;

import org.apache.commons.lang.StringUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;

/**
 *
 * @author phanikumark
 *
 */
public class SelectionCriteriaPropertySourceWrapper implements IPropertySource {
	
	private IPropertySource source;
	private EnquiryPackage pkg = EnquiryPackage.eINSTANCE;
	private SelectionCriteria model;

	/**
	 * @param source
	 * @param selection
	 * @param sourceProvider
	 */
	public SelectionCriteriaPropertySourceWrapper(IPropertySource source, SelectionCriteria model) {
		this.source = source;
		this.model = model;
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
		if (id.equals(pkg.getSelectionCriteria_Values().getName())) {
			return StringUtils.join(model.getValues(), ',');
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

}
