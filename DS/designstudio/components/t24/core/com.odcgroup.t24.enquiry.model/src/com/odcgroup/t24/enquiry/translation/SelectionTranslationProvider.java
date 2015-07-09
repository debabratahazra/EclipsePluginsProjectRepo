package com.odcgroup.t24.enquiry.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class SelectionTranslationProvider extends BaseTranslationProvider implements ITranslationProvider{

	@Override
	protected BaseTranslation newTranslation(IProject project, Object element) {
		if (element instanceof Selection) {
			return new SelectionTranslation(this, project, (Selection) element);
		} else {
			throw new IllegalArgumentException("EnquiryTranslationProvider doesn't support " + element + " input");
		}
	}

}
