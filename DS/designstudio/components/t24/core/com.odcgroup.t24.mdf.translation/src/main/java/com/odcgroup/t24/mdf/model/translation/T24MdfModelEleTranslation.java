package com.odcgroup.t24.mdf.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.translation.MdfTranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;

/**
 * TODO: Document me!
 *
 * @author ramapriyamn
 *
 */
public class T24MdfModelEleTranslation extends MdfTranslation {

	private static final String DOCUMENTATION = "Documentation";

	/**
	 * @param provider
	 * @param project
	 * @param mdfModelElement
	 */
	public T24MdfModelEleTranslation(ITranslationProvider provider, IProject project, MdfModelElement mdfModelElement) {
		super(provider, project, mdfModelElement);
	}
	
	@Override
	protected ITranslationKind[] getTranslationKinds() {
		ITranslationKind[] kinds = {};
		if (mdfModelElement instanceof MdfDomain || 
			mdfModelElement instanceof MdfClass  ||
			mdfModelElement instanceof MdfEnumeration) {
			kinds = new ITranslationKind[] {ITranslationKind.NAME, ITranslationKind.DOCUMENTATION }; 
		} else {
			kinds = new ITranslationKind[] {ITranslationKind.NAME, ITranslationKind.TEXT, ITranslationKind.DOCUMENTATION };
		}
		return kinds;
	}
	
	@Override
	protected String getAnnotationFromKind(ITranslationKind kind) {
		String result = null;
		switch (kind) {
		case NAME:
			result = TRANSLATION_LABEL;
			break;
		case TEXT:
			result = TRANSLATION_TOOLTIP;
			break;
		case DOCUMENTATION:
			result = DOCUMENTATION;
			break;
		default:
			result = null;
			break;
		}
		return result;
	}
}
