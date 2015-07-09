package com.odcgroup.t24.enquiry.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.LabelOperation;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class LabelOpTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	@Override
	protected BaseTranslation newTranslation(IProject project, Object element) {
		if(element instanceof LabelOperation){
			return new LabelOperationTranslation(this, project, (LabelOperation) element); 
		} else {
			throw new IllegalArgumentException("LabelOpTranslationProvider doesn't support " + element + " input");
		}
	}

}
