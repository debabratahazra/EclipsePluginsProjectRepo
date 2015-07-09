package com.odcgroup.t24.version.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

public class FieldTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	@Override
	protected BaseTranslation newTranslation(IProject project, Object element) {
		if (element instanceof Field) {
			return new FieldTranslation(this, project, (Field) element);
		} else {
			throw new IllegalArgumentException("FieldTranslationProvider doesn't support " + element + " input");
		}
	}

}
