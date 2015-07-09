package com.odcgroup.page.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.page.model.Event;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * TODO: Document me!
 * 
 * @author atr
 * 
 */
public class EventTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	@Override
	protected BaseTranslation newTranslation(IProject project, Object obj) {
		return new EventTranslation(this, project, (Event)obj);
	}

}
