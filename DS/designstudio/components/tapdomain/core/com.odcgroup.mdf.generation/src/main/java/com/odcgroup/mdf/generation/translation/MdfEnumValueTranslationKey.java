package com.odcgroup.mdf.generation.translation;

import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.translation.core.ITranslation;

/**
 * @author atr
 */
class MdfEnumValueTranslationKey extends MdfTranslationKey {

	/** */
	private String prefix;

	@Override
	protected String getKeyPrefix() {
		if (prefix == null) {
			MdfEnumValue value = (MdfEnumValue) getTranslation().getOwner();
			MdfEnumeration mdfEnum = value.getParentEnumeration();
	        StringBuilder builder = new StringBuilder();
	        builder.append(mdfEnum.getParentDomain().getName().toLowerCase());
	        builder.append(".");
	        builder.append(mdfEnum.getName().toLowerCase());
	        builder.append(".");
	        builder.append(value.getValue());
	        prefix = builder.toString();	
		}
		return prefix;
	}
	
	/**
	 * @param translation
	 */
	public MdfEnumValueTranslationKey(ITranslation translation) {
		super(translation);
	}

}
