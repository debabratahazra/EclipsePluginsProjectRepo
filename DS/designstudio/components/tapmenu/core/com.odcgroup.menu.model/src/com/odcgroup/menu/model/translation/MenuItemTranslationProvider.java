package com.odcgroup.menu.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

public class MenuItemTranslationProvider extends BaseTranslationProvider
		implements ITranslationProvider {

	public MenuItemTranslationProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BaseTranslation newTranslation(IProject project, Object element) {

		BaseTranslation translation = null;

		if (element instanceof MenuItem) {

			MenuItem menuItem = (MenuItem) element;
			translation = newMenuItemTranslation(project, menuItem);

		} else {
			throw new IllegalArgumentException(
					"MenuTranslationProvider doesn't support " + element
							+ " input");
		}
		return translation;
	}

	private BaseTranslation newMenuItemTranslation(IProject project,
			MenuItem menuItem) {
		return new MenuItemTranslation(this, project, menuItem);
	}

}
