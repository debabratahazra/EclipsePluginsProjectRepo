package com.odcgroup.t24.enquiry.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

public class EnquiryTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	@Override
	protected BaseTranslation newTranslation(IProject project, Object element) {
		if (element instanceof Enquiry) {
			return new EnquiryTranslation(this, project, (Enquiry) element);
		} else {
			throw new IllegalArgumentException("EnquiryTranslationProvider doesn't support " + element + " input");
		}
	}

}
