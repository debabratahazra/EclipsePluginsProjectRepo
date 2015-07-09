package com.odcgroup.t24.version.translation;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.translation.BaseTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;

abstract class BaseVersionTranslation extends BaseTranslation implements ITranslation {
	
	protected final String deleteTranslation(ITranslationKind kind, Locale locale, LocalTranslations localTranslations) {
		String oldText = null;
		if(localTranslations != null){
			List<LocalTranslation> translationList = localTranslations.getTranslations();
		for (LocalTranslation localTranslation : translationList) {
			if (locale.equals(LocaleUtils.toLocale(localTranslation.getLocale()))) {
				oldText = localTranslation.getText();
				translationList.remove(localTranslation);
				fireChangeTranslation(kind, locale, oldText, null);
				break;
				}
			}
		}
		return oldText;	
	}
	
	
	protected final LocalTranslation findTranslation(Locale locale, LocalTranslations localTranslations) {
		LocalTranslation result = null;
		if (localTranslations != null) {
			for (LocalTranslation translation : localTranslations.getTranslations()) {
				if (locale.equals(LocaleUtils.toLocale(translation.getLocale()))) {
					result = translation;
					break;
				}
			}
		}
		return result;
	}

	protected final String setTranslation(LocalTranslations localTranslations, Locale locale, String newText) {
		String oldValue = null;
		LocalTranslation translation = findTranslation(locale, localTranslations);
		if (translation != null) {
			oldValue = translation.getText();
			translation.setText(newText);
		} else {
			translation = TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslation();
			String language = locale.toString();
			translation.setLocale(language);
			translation.setText(newText);
			localTranslations.getTranslations().add(translation);
		}
		return oldValue;	
	}


	@Override
	public boolean isReadOnly() throws TranslationException {
		if (isProtected())
			return true;
		
		Resource resource = ((EObject)getOwner()).eResource();
		if (resource != null) {
			URI uri = resource.getURI();
			String fileString = URI.decode(uri.path());
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileString));
			if (file != null) {
				return file.isReadOnly();
			}
		}
		return true;
		
	}

	@Override
	public boolean isProtected() throws TranslationException {
		return false;
	}

	@Override
	public boolean isInheritable() {
		return false;
	}

	@Override
	public boolean isInherited(ITranslationKind kind, Locale locale)
			throws TranslationException {
		return false;
	}

	@Override
	public String getInheritedText(ITranslationKind kind, Locale locale)
			throws TranslationException {
		return null;
	}


	protected BaseVersionTranslation(ITranslationProvider provider, IProject project) {
		super(provider, project);
	}

}
