package com.odcgroup.mdf.generation.translation;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.generation.provider.BaseTranslationkey;

/**
 * @author atr
 */
abstract class MdfTranslationKey extends BaseTranslationkey {

	@Override
	protected String getKeySuffix(ITranslationKind kind) {
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
	public MdfTranslationKey(ITranslation translation) {
		super(translation);
	}	
}
