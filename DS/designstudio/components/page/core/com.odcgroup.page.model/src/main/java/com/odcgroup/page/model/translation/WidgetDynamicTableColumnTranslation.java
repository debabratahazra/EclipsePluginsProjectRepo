package com.odcgroup.page.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.page.model.Widget;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;

/**
 * Dynamic Table Column supports only toolTip.
 *
 * @author atr
 *
 */
public class WidgetDynamicTableColumnTranslation extends WidgetTranslation {

	protected ITranslationKind[] getSupportedTranslationKinds() {
		return new ITranslationKind[] { ITranslationKind.TEXT };		
	}
	
	/**
	 * @param provider
	 * @param project
	 * @param widget
	 */
	public WidgetDynamicTableColumnTranslation(ITranslationProvider provider, IProject project, Widget widget) {
		super(provider, project, widget);
	}

}
