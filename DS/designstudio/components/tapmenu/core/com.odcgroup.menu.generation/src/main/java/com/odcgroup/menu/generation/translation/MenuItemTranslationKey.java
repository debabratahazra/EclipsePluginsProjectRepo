package com.odcgroup.menu.generation.translation;

import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.generation.provider.BaseTranslationkey;

/**
 * @author pkk
 *
 */
public class MenuItemTranslationKey extends BaseTranslationkey {
	
	/**
	 * 
	 */
	String prefix = null;

	/**
	 * @param translation
	 */
	protected MenuItemTranslationKey(ITranslation translation) {
		super(translation);
	}

	@Override
	protected String getKeyPrefix() {
		return "";
				}

	@Override
	protected String getKeySuffix(ITranslationKind kind) {
		MenuItem item = (MenuItem) getTranslation().getOwner();
		return item.getName();
	}
	
	
	@Override
	public String getKey(ITranslationKind kind) {
		String key = getKeyPrefix();
		if (key != null) {
			key += getKeySuffix(kind);
		} 
		return key;
	}

}
