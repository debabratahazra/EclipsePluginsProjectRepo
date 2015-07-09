package com.odcgroup.t24.enquiry.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.Tool;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class ToolTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	@Override
	protected BaseTranslation newTranslation(IProject project, Object element) {
		if (element instanceof Tool) {
			return new ToolTranslation(this, project, (Tool) element);
		} else {
			throw new IllegalArgumentException("ToolTranslationProvider doesn't support " + element + " input");
		}
	}

}
