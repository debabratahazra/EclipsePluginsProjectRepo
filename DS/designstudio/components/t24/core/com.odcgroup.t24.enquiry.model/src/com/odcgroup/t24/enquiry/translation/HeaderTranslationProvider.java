package com.odcgroup.t24.enquiry.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class HeaderTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	@Override
	protected BaseTranslation newTranslation(IProject project, Object element) {
		if (element instanceof EnquiryHeader) {
			return new HeaderTranslation(this, project, (EnquiryHeader) element);
		} else {
			throw new IllegalArgumentException("HeaderTranslationProvider doesn't support " + element + " input");
		}
	}

}
