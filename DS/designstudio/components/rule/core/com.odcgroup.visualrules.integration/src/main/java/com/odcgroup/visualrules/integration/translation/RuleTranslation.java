package com.odcgroup.visualrules.integration.translation;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.translation.BaseTranslation;
import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationFactory;
import com.odcgroup.visualrules.integration.model.ruletranslation.Translation;
import com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * @author atr, kkr
 */
public class RuleTranslation extends BaseTranslation {
	
	private static final Logger logger = LoggerFactory.getLogger(RuleTranslation.class);
	
	private static final ITranslationKind[] SUPPORTED_KIND = 
		new ITranslationKind[] {ITranslationKind.NAME};

	protected RuleMessageProxy owner;

	protected static class RuleTranslationHelper {

		public static String getText(RuleMessageProxy ruleElement, ITranslationKind kind, Locale locale) throws TranslationException {
			Translation existingTranslation = findExistingTranslation(ruleElement, kind, locale);
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
		public static Translation findExistingTranslation(RuleMessageProxy ruleElement, ITranslationKind kind, Locale locale) {
			Translation existingTranslation = null;
			for (Object obj : ruleElement.getTranslations()) {
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
		 * @return <code>true</code> if the ITranslation kind matches the
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
			return locale.toString().equals(language);
		}

	}

	@Override
	public String delete(ITranslationKind kind, Locale locale) throws TranslationException {
		RuleMessageProxy o = (RuleMessageProxy) getOwner();
		Resource res = o.eResource();
		String oldText = null;
		Translation translation = RuleTranslationHelper.findExistingTranslation(o, kind, locale);
		if (translation != null) {
			oldText = translation.getMessage();
			o.getTranslations().remove(translation);
			if(o.getTranslations().size()==0) {
				RulesIntegrationUtils.getRulesTranslationModel(getProject()).getCodeMap().removeKey(o.getCode());
				owner = RuletranslationFactory.eINSTANCE.createRuleMessageProxy();
				owner.setCode(o.getCode());
			}
			try {
				res.save(null);
				fireChangeTranslation(kind, locale, oldText, null);
			} catch (IOException e) {
				logger.error("Cannot modify rule translation '{}'", o.getCode(), e);
			}
		}
		return oldText;	
	}

	@Override
	public ITranslationKind[] getTranslationKinds() {
		return new ITranslationKind[]{ITranslationKind.NAME};
	}

	@Override
	public String getInheritedText(ITranslationKind kind, Locale locale) throws TranslationException {
		// not applicable
		return null;
	}

	@Override
	public final Object getOwner() {
		// out of some reason, the eResource is often null, so we need to retrieve the correct owner again.
		// TODO: find a solution for this workaround
		if(owner.eResource()==null) {
			final RuleTranslationRepo rulesTranslationModel = RulesIntegrationUtils.getRulesTranslationModel(getProject());
			final EMap<String, RuleMessageProxy> codeMap = rulesTranslationModel.getCodeMap();
			final String code = owner.getCode();
			RuleMessageProxy updatedOwner = codeMap.get(code);
			if(updatedOwner==null) {
				updatedOwner = RuletranslationFactory.eINSTANCE.createRuleMessageProxy();
				updatedOwner.setCode(code);
				codeMap.put(code, updatedOwner);
				updatedOwner = codeMap.get(code);				
			}
			owner = updatedOwner;
		}
		return owner;
	}

	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {
		return RuleTranslationHelper.getText(owner, kind, locale);
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
	public boolean isReadOnly() throws TranslationException {
		if (isProtected())
			return true;
		
		RuleMessageProxy o = (RuleMessageProxy) getOwner();
		Resource resource = o.eResource();
		if (resource != null) {
			URI uri = ModelURIConverter.toResourceURI(resource.getURI());
			// Search the resource in the project only
			IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(getProject());
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
	public String setText(ITranslationKind kind, Locale locale, String newText) throws TranslationException {
		if (!Arrays.asList(SUPPORTED_KIND).contains(kind)) {
			return getText(kind, locale);
		}
		RuleMessageProxy o = (RuleMessageProxy) getOwner();
		Translation existingTranslation = RuleTranslationHelper.findExistingTranslation(o, kind, locale);
		String oldText = null;
		if (existingTranslation == null)  {
			Translation newTranslation = RuletranslationFactory.eINSTANCE.createTranslation();
			newTranslation.setKind(convert(kind));
			newTranslation.setLanguage(locale == null ? null : locale.toString());
			newTranslation.setMessage(newText);
			o.getTranslations().add(newTranslation);
		} else {
			oldText = existingTranslation.getMessage();
			existingTranslation.setMessage(newText);
		}
		try {
			o.eResource().save(null);
			fireChangeTranslation(kind, locale, oldText, newText);
		} catch (IOException e) {
			logger.error("Cannot modify rule translation '{}'", owner.getCode(), e);
		}
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
			return TranslationKind.NAME;
		}
		return null;
	}
		
	/**
	 * @param provider
	 * @param project
	 */
	public RuleTranslation(ITranslationProvider provider, IProject project, RuleMessageProxy owner) {
		super(provider, project);
		this.owner = owner;
	}
}
