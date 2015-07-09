package com.odcgroup.t24.version.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.provider.BaseTranslationModelVisitorProvider;

/**
 * @author atr
 */
public class VersionTranslationModelVisitorProvider extends BaseTranslationModelVisitorProvider {

	@Override
	protected ITranslationModelVisitor newModelVisitor(IProject project, Object element) {
		if (element instanceof Version) {
			return new VersionTranslationModelVisitor(project, (Version)element);
		} else {
			throw new IllegalArgumentException("VersionTranslationProvider doesn't support " + element + " input");
		}
	}

	
}
