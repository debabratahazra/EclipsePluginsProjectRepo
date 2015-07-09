package com.odcgroup.t24.enquiry.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.provider.BaseTranslationModelVisitorProvider;

/**
 * @author atr
 */
public class EnquiryTranslationModelVisitorProvider extends BaseTranslationModelVisitorProvider {

	@Override
	protected ITranslationModelVisitor newModelVisitor(IProject project, Object element) {
		if (element instanceof Enquiry) {
			return new EnquiryTranslationModelVisitor(project, (Enquiry)element);
		} else {
			throw new IllegalArgumentException("EnquiryTranslationProvider doesn't support " + element + " input");
		}
	}

	
}
