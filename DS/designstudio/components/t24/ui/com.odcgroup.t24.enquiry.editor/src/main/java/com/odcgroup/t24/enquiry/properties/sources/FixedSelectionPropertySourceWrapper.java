package com.odcgroup.t24.enquiry.properties.sources;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.google.common.collect.Lists;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;

/**
 *
 * @author phanikumark
 *
 */
public class FixedSelectionPropertySourceWrapper implements IPropertySource {
	
	private IPropertySource source;
	private FixedSelection model;
	private EnquiryPackage pkg = EnquiryPackage.eINSTANCE;
	
	public FixedSelectionPropertySourceWrapper(IPropertySource source, FixedSelection model) {
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
		if (id.equals(pkg.getFixedSelection_Values().getName())) {
			List<String> list = model.getValues();
			return StringUtils.join(list, ',');				
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
		if (id.equals(pkg.getFixedSelection_Values().getName())) {
			if (value instanceof List) {
				source.setPropertyValue(id, value);
			} else {
				String val = (String) value;
				List<String> list = Lists.newArrayList(val.split(","));
				source.setPropertyValue(id, list);
			}
		} else if(id.equals(pkg.getFixedSelection_Operand().getName())) {
			source.setPropertyValue(id, value);
			if (model.getValues().isEmpty()) {
				model.getValues().add("value");
			}
		} else {
			source.setPropertyValue(id, value);
		}

	}

}
