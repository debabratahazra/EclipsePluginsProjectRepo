package com.odcgroup.translation.core.tests.provider;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.tests.model.SimpleObject;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class SimpleTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	@Override
	protected BaseTranslation newTranslation(IProject project, Object eObj) {
		return new SimpleTranslation(this, project, (SimpleObject) eObj);
	}

}
