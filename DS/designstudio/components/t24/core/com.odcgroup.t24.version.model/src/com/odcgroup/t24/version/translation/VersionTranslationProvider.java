package com.odcgroup.t24.version.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

public class VersionTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	@Override
	protected BaseTranslation newTranslation(IProject project, Object element) {
		if (element instanceof Version) {
			return new VersionTranslation(this, project, (Version) element);
		} else {
			throw new IllegalArgumentException("VersionTranslationProvider doesn't support " + element + " input");
		}
	}

}
