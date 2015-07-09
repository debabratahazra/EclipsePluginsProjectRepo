package com.odcgroup.menu.generation.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.provider.BaseTranslationKeyProvider;

/**
 * @author pkk
 *
 */
public class MenuItemTranslationKeyProvider extends BaseTranslationKeyProvider{

	@Override
	protected ITranslationKey newTranslationKey(IProject project,
			ITranslation translation) {
		return new MenuItemTranslationKey(translation);
	}

}
