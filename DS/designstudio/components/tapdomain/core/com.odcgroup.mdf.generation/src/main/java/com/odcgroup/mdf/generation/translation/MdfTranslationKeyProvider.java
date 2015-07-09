package com.odcgroup.mdf.generation.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.provider.BaseTranslationKeyProvider;

/**
 * @author atr
 */
public class MdfTranslationKeyProvider extends BaseTranslationKeyProvider {

	@Override
	protected ITranslationKey newTranslationKey(IProject project, ITranslation translation) {
		ITranslationKey key = null;
		MdfModelElement element = (MdfModelElement)translation.getOwner();
		if (element instanceof MdfEnumValue) {
			key = new MdfEnumValueTranslationKey(translation);
		} else if (element instanceof MdfReverseAssociation) {
			key = new MdfReverseAssociationTranslationKey(translation);
		} else {
			key = new MdfElementTranslationKey(translation);
		}
		return key;
	}

}
