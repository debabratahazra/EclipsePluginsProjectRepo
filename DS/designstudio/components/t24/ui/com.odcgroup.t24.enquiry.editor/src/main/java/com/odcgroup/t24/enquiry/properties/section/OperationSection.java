package com.odcgroup.t24.enquiry.properties.section;

import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.t24.enquiry.editor.part.FieldEditPart;
import com.odcgroup.t24.enquiry.editor.part.OutputFieldEditPart;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.filter.IPropertyFilter;
import com.odcgroup.t24.enquiry.properties.filter.OperationSectionFilter;
import com.odcgroup.t24.enquiry.properties.sources.FieldPropertySourceWrapper;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public class OperationSection extends AbstractSection {

	@Override
	public IPropertyFilter getPropertyFilter() {
		return new OperationSectionFilter();
	}

	@Override
	public IPropertySource getPropertySource(Object object) {
		IPropertySource source = super.getPropertySource(object);
		if (object instanceof FieldEditPart) {
			Field field = (Field)((FieldEditPart) object).getModel();
			source = new FieldPropertySourceWrapper(source, modelPropertySourceProvider, field);
		} else if (object instanceof OutputFieldEditPart) {
			Field field = (Field)((OutputFieldEditPart) object).getModel();
			source = new FieldPropertySourceWrapper(source, modelPropertySourceProvider, field);			
		}
		return source;
	}

}
