package com.odcgroup.page.translation.generation;

import com.odcgroup.page.model.Widget;
import com.odcgroup.translation.core.ITranslation;

/**
 * @author atr
 */
public class WidgetTranslationKey extends PageTranslationKey {
	
	private String prefix = null;

	@Override
	protected final String getKeyPrefix() {
		return prefix;
	}

	/**
	 * @param translation
	 */
	public WidgetTranslationKey(ITranslation translation) {
		super(translation);
		long tid = ((Widget)getTranslation().getOwner()).getTranslationId();
		if (tid > 0) {
			prefix = tid+"";
		}
	}
	
}
