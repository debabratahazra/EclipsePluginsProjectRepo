package com.odcgroup.workbench.ui.xtext.search;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.search.IXtextEObjectSearch.Default;

import com.odcgroup.workbench.core.repository.ILanguageRepository;

/**
 *
 * @author mumesh
 *
 */
public class LanguageXtextEObjectSearch extends Default {

	private EObject context;
	private ILanguageRepository languageRepository;
	private EReference reference;
	
	/**
	 * @param context, cannot be null
	 */
	public LanguageXtextEObjectSearch() {
		super();
	}


	/**
	 * @param reference the reference to set
	 */
	public void setReference(EReference reference) {
		this.reference = reference;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(EObject context) {
		this.context = context;
	}

	/**
	 * @param languageRepository the languageRepository to set
	 */
	public void setLanguageRepository(ILanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}
	
	public ILanguageRepository getLanguageRepository() {
		return this.languageRepository;
	}

	@Override
	protected Iterable<IEObjectDescription> getSearchScope() {
		return languageRepository.getExportedObjectsByType(context, reference);
	}
	
}