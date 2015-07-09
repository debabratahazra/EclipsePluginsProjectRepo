package com.odcgroup.process.model.translation;

import java.util.Arrays;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.process.model.Translation;
import com.odcgroup.process.model.TranslationKind;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.translation.BaseTranslation;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * @author yan
 */
public class ProcessTranslation extends BaseTranslation implements ITranslation {
	
	private static final ITranslationKind[] SUPPORTED_KIND = 
		new ITranslationKind[] {ITranslationKind.NAME, ITranslationKind.TEXT};

	protected IProcessElementAdapter processElement;

	protected static class ProcessTranslationHelper {

		public static String getText(IProcessElementAdapter processElement, ITranslationKind kind, Locale locale) throws TranslationException {
			Translation existingTranslation = findExistingTranslation(processElement, kind, locale);
			if (existingTranslation != null) {
				return existingTranslation.getMessage();
			} else {
				return null;
			}
		}
		
		/**
		 * @param kind
		 * @param locale
		 * @return
		 */
		public static Translation findExistingTranslation(IProcessElementAdapter processElement, ITranslationKind kind, Locale locale) {
			Translation existingTranslation = null;
			for (Object obj : processElement.getTranslations()) {
				Translation translation = (Translation)obj;
				if (match(kind, translation.getKind()) &&
						match(locale, translation.getLanguage())) {
					existingTranslation = translation;
				}
			}
			return existingTranslation;
		}

		/**
		 * @param kind1
		 * @param kind2
		 * @return <code>true</code> if the ITranlsation kind matches the
		 * TranslationKind, <code>false</code> otherwise.
		 */
		static boolean match(ITranslationKind kind1, TranslationKind kind2) {
			if ((kind1 == null) && (kind2 == null)) {
				return true;
			}
			if (kind1 == null || kind2 == null) {
				return false;
			}
			switch (kind1) {
			case NAME:
				return kind2.getValue() == 0;
			case TEXT:
				return kind2.getValue() == 1;
			}
			return false;
		}

		/**
		 * @param locale
		 * @param language
		 * @return <code>true</code> if the locale matches the language,
		 * <code>false</code> otherwise.
		 */
		static boolean match(Locale locale, String language) {
			if ((locale == null) && (language == null)) {
				return true;
			}
			if ((locale == null) || (language == null)) {
				return false;
			}
			return locale.getLanguage().equals(language);
		}

	}
	
	public ProcessTranslation(ITranslationProvider provider, IProject project, IProcessElementAdapter processElement) {
		super(provider, project);
		this.processElement = processElement;
	}

	@Override
	public final ITranslationKind[] getTranslationKinds() {
		return SUPPORTED_KIND;
	}

	@Override
	public final boolean isInheritable() {
		return false; 
	}
	
	@Override
	public final boolean isInherited(ITranslationKind kind, Locale locale) throws TranslationException {
		return false;
	}	

	@Override
	public final String getInheritedText(ITranslationKind kind, Locale locale) throws TranslationException {
		return null;
	}

	@Override
	public boolean isReadOnly() throws TranslationException {
		if (isProtected())
			return true;
		
		Resource resource = ((EObject)getOwner()).eResource();
		if (resource != null) {
			URI uri = ModelURIConverter.toResourceURI(resource.getURI());
			// Search the resource in the project only
			IOfsProject ofsProject = OfsCore.getOfsProject(getProject());
			IOfsModelResource ofsResource = ofsProject.getModelResourceSet()
					.getOfsModelResource(uri, IOfsProject.SCOPE_PROJECT);
			if (ofsResource != null) {
				return ofsResource.isReadOnly();
			} else {
				// The resource is not in the project, i.e. it is the standard model
				return true; 
			}
		} else {
			// If the resource is in a jar, it is not modifiable
			return true;
		}
	}

	@Override
	public boolean isProtected() throws TranslationException {
		return false;
	}
	
	@Override
	public final Object getOwner() {
		return processElement.getAdaptee();
	}

	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {
		return ProcessTranslationHelper.getText(processElement, kind, locale);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String setText(ITranslationKind kind, Locale locale, String newText) throws TranslationException {
		if (!Arrays.asList(SUPPORTED_KIND).contains(kind)) {
			return getText(kind, locale);
		}
		
		Translation existingTranslation = ProcessTranslationHelper.findExistingTranslation(processElement, kind, locale);
		String oldText = null;
		if (existingTranslation == null)  {
			Translation newTranslation = ProcessFactory.eINSTANCE.createTranslation();
			newTranslation.setKind(convert(kind));
			newTranslation.setLanguage(convert(locale));
			newTranslation.setMessage(newText);
			processElement.getTranslations().add(newTranslation);
		} else {
			oldText = existingTranslation.getMessage();
			existingTranslation.setMessage(newText);
		}
		fireChangeTranslation(kind, locale, oldText, newText);
		return oldText;
	}

	
	/**
	 * Convert a ITranslationKind in a TranslationKind
	 * @param kind
	 * @return the ITranslationKind equivalent to the TranslationKind
	 */
	protected final TranslationKind convert(ITranslationKind kind) {
		switch (kind) {
		case NAME:
			return TranslationKind.NAME_LITERAL;
		case TEXT:
			return TranslationKind.TEXT_LITERAL;
		}
		return null;
	}
	
	/**
	 * Convert the locale in a 2 letters language code 
	 * @param locale
	 * @return the 2 letters language code extracted from the locale
	 */
	protected String convert(Locale locale) {
		if (locale != null) {
			return locale.getLanguage();
		} else {
			return null;
		}
	}
	
	@Override
	public String delete(ITranslationKind kind, Locale locale) throws TranslationException {
		String oldText = null;
		Translation translation = ProcessTranslationHelper.findExistingTranslation(processElement, kind, locale);
		if (translation != null) {
			oldText = translation.getMessage();
			processElement.getTranslations().remove(translation);
			fireChangeTranslation(kind, locale, oldText, null);
		}
		return oldText;
	}

	/**
	 * @param kind
	 * @param locale
	 * @return
	 */
	protected Object findExistingTranslation(ITranslationKind kind, Locale locale) {
		return ProcessTranslationHelper.findExistingTranslation(processElement, kind, locale);
	}

}
