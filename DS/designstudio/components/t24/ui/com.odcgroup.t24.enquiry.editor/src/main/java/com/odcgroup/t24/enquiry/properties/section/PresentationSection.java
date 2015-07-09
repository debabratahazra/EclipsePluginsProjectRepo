package com.odcgroup.t24.enquiry.properties.section;

import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.editor.part.FieldEditPart;
import com.odcgroup.t24.enquiry.editor.part.OutputFieldEditPart;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.filter.IPropertyFilter;
import com.odcgroup.t24.enquiry.properties.sources.EnquiryPropertySourceWrapper;
import com.odcgroup.t24.enquiry.properties.sources.FieldPropertySourceWrapper;



public class PresentationSection extends AbstractSection {


	@Override
	public IPropertyFilter getPropertyFilter() {
		return new EnquiryPresentationSectionFilter();
	}

	@Override
	public IPropertySource getPropertySource(Object object) {
		IPropertySource source = super.getPropertySource(object);
		if(object instanceof EnquiryDiagramEditPart ){
			Enquiry enquiry = (Enquiry)((EnquiryDiagramEditPart)object).getModel();
			source=new EnquiryPropertySourceWrapper(source,modelPropertySourceProvider,enquiry );
		}
		if(object instanceof FieldEditPart ){
			Field field = (Field)((FieldEditPart)object).getModel();
			source= new FieldPropertySourceWrapper(source,modelPropertySourceProvider, field);
		} else if (object instanceof OutputFieldEditPart) {
			Field field = (Field)((OutputFieldEditPart)object).getModel();
			source= new FieldPropertySourceWrapper(source,modelPropertySourceProvider, field);			
		}
		return source;
	}	

}
