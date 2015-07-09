package com.odcgroup.mdf.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.provider.BaseTranslationModelVisitorProvider;

/**
 * @author atr
 */
public class MdfFTranslationModelVisitorProvider extends BaseTranslationModelVisitorProvider {

	@Override
	protected ITranslationModelVisitor newModelVisitor(IProject project, Object element) {
		return new MdfTranslationModelVisitor(project, (MdfDomain)element);
	}

}
