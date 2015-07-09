package com.odcgroup.t24.mdf.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.translation.MdfTranslation;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;

public class T24MdfTranslation extends MdfTranslation implements ITranslation {

	@Override
	protected ITranslationKind[] getTranslationKinds() {
		
		// retrieve the default translation
		ITranslationKind[] kinds = super.getTranslationKinds();
		
		final int N = kinds.length; 
		ITranslationKind[] results = new ITranslationKind[N+5];
		for (int kx=0; kx < N; kx++) {
			results[kx] = kinds[kx];
		}
		
		// add the additional translation kinds
		results[N  ] = ITranslationKind.TEXT;
		results[N+1] = ITranslationKind.HEADER1;
		results[N+2] = ITranslationKind.HEADER2;
		results[N+3] = ITranslationKind.DESCRIPTION;
		results[N+4] = ITranslationKind.DOCUMENTATION;
		return results;
	}

	@Override
	protected String getAnnotationFromKind(ITranslationKind kind) {
		String result = null;
		switch (kind) {
		case TEXT:
			result = "Tooltip";
			break;
		case HEADER1:
			result = "Header1";
			break;
		case HEADER2:
			result = "Header2";
			break;
		case DESCRIPTION:
			result = "Description";
			break;
		case DOCUMENTATION:
			result = "Documentation";
			break;
		default:
			result = super.getAnnotationFromKind(kind);
			break;
		}
		return result;
	}

	/**
	 * Create a translation for MDF domain element
	 * 
	 * @param project
	 * @param mdfModelElement
	 */
	public T24MdfTranslation(ITranslationProvider provider, IProject project,
			MdfModelElement mdfModelElement) {
		super(provider, project, mdfModelElement);
	}

}
