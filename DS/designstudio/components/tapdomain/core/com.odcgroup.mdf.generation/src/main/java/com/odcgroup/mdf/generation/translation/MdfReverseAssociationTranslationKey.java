package com.odcgroup.mdf.generation.translation;

import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.translation.core.ITranslation;

/**
 * @author atr
 */
class MdfReverseAssociationTranslationKey extends MdfTranslationKey {

	/** */
	private String prefix;

	@Override
	protected String getKeyPrefix() {
		if (prefix == null) {
			MdfReverseAssociation ra = (MdfReverseAssociation)getTranslation().getOwner();
			String tmp = ra.getParentClass().getQualifiedName().toString();
	        tmp = tmp.replace("#", ".");
	        tmp = tmp.replace(":", ".");
	        StringBuilder builder = new StringBuilder();
	        builder.append(tmp);
	        builder.append(".");
	        builder.append(ra.getName());
	        builder.append(".");
	        prefix = builder.toString().toLowerCase();			}
		return prefix;
	}

	/**
	 * @param translation
	 */
	public MdfReverseAssociationTranslationKey(ITranslation translation) {
		super(translation);
	}

}
