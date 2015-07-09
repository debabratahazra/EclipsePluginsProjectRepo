package com.odcgroup.t24.mdf.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.translation.MdfTranslation;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;


public class T24MdfDomainTranslation extends MdfTranslation implements ITranslation {

	public T24MdfDomainTranslation(ITranslationProvider provider, IProject project, MdfModelElement mdfModelElement) {
		super(provider, project, mdfModelElement);
	}

	@Override
	protected ITranslationKind[] getTranslationKinds() {
		// retrieve the default translation
		ITranslationKind[] kinds = super.getTranslationKinds();
		final int N = kinds.length;
		ITranslationKind[] results = new ITranslationKind[2];
		for (int kx = 0; kx < N; kx++) {
			results[kx] = kinds[kx];
		}
		results[N] = ITranslationKind.DESCRIPTION;
		return results;
	}
	
	@Override
	protected String getAnnotationFromKind(ITranslationKind kind) {
		String result = null;
		switch (kind) {
		case DESCRIPTION:
			result = "Description";
			break;
		default:
			result = super.getAnnotationFromKind(kind);
			break;
		}
		return result;
	}
}
