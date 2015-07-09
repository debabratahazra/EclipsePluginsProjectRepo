package com.odcgroup.page.translation.generation;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.generation.provider.BaseTranslationkey;

/**
 * @author atr
 */
public abstract class PageTranslationKey extends BaseTranslationkey {

	@Override
	public String getKeySuffix(ITranslationKind kind) {
		String suffix = "";
		switch (kind) {
			case NAME:
				suffix = "text";
				break;
			case TEXT:
				suffix = "tooltip";
				break;
		}
		return suffix;		
	}
	
	/**
	 * @param translation
	 */
	public PageTranslationKey(ITranslation translation) {
		super(translation);
	}
	
}
