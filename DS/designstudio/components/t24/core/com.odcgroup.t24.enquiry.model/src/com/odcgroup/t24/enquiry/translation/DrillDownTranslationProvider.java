package com.odcgroup.t24.enquiry.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 *
 * @author phanikumark
 *
 */
public class DrillDownTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	@Override
	protected BaseTranslation newTranslation(IProject project, Object element) {
		if (element instanceof DrillDown) {
			return new DrillDownTranslation(this, project, (DrillDown) element);
		} else {
			throw new IllegalArgumentException("EnquiryTranslationProvider doesn't support " + element + " input");
		}
	}

}
