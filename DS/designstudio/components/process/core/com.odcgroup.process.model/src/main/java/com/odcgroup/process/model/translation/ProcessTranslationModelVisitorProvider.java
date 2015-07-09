package com.odcgroup.process.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.process.model.Process;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.provider.BaseTranslationModelVisitorProvider;

/**
 * @author atr
 */
public class ProcessTranslationModelVisitorProvider extends BaseTranslationModelVisitorProvider {

	@Override
	protected ITranslationModelVisitor newModelVisitor(IProject project, Object element) {
		return new ProcessModelVisitor(project, (Process)element);
	}

	
}
