package com.odcgroup.mdf.generation.translation;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.translation.core.ITranslation;

/**
 * @author atr
 */
class MdfElementTranslationKey extends MdfTranslationKey {

	/** */
	private String prefix;

	@Override
	protected String getKeyPrefix() {
		if (prefix == null) {
			MdfModelElement element = (MdfModelElement)getTranslation().getOwner();
			String tmp = element.getQualifiedName().toString();
			tmp = tmp.replace("#", ".");
			tmp = tmp.replace(":", ".");
			prefix = tmp.toLowerCase();		
		}
		return prefix;
	}

	/**
	 * @param translation
	 */
	public MdfElementTranslationKey(ITranslation translation) {
		super(translation);
	}

}
