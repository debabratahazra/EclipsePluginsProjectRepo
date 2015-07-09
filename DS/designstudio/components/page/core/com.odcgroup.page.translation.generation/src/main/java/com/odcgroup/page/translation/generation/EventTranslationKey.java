package com.odcgroup.page.translation.generation;

import com.odcgroup.page.model.Event;
import com.odcgroup.translation.core.ITranslation;

/**
 * @author atr
 */
public class EventTranslationKey extends PageTranslationKey  {
	
	private String prefix = null;

	@Override
	protected final String getKeyPrefix() {
		return prefix;
	}

	/**
	 * @param translation
	 */
	public EventTranslationKey(ITranslation translation) {
		super(translation);
		long tid = ((Event)getTranslation().getOwner()).getTranslationId();
		if (tid > 0) {
			prefix = tid+"";
		}
	}
	
}
