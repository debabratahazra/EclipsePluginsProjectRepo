package com.odcgroup.t24.version.translation;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.UnexpectedTranslationException;
import com.odcgroup.translation.core.translation.InheritableTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

public class FieldTranslation extends InheritableTranslation implements ITranslation {
	
	private static final String STAR_NAME = "*";
	
	private Field field;

	protected final Field getField() {
		return this.field;
	}
	
	@Override
	public ITranslationKind[] getTranslationKinds() {
		return new ITranslationKind[] 
				{ ITranslationKind.NAME, ITranslationKind.TEXT };
	}
	
	
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


	protected String doGetText(Field field, ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String text = null;
		
		switch (kind) {

			case NAME: {
				LocalTranslation localTranslation = findTranslation(locale, (LocalTranslations)field.getLabel());
				if (localTranslation != null) {
					text = localTranslation.getText();
				} 
				break;
			}
	
			case TEXT: {
				LocalTranslation translation = findTranslation(locale, (LocalTranslations)field.getToolTip());
				if (translation != null) {
					text = translation.getText();
				} 
				break;
			}

			default: {
				break;
			}
		}
		
		return text;

	}

	@Override
	public String doSetText(ITranslationKind kind, Locale locale, String newText)
			throws TranslationException {

		if (null == newText || null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String oldText = null;

		switch (kind) {

			case NAME: {
				if (field.getLabel() == null) {
					field.setLabel(TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslations());
				}
				oldText = setTranslation((LocalTranslations)field.getLabel(), locale, newText);
				fireChangeTranslation(kind, locale, oldText, newText);
				break;
			}
	
			case TEXT: {
				if (field.getToolTip() == null) {
					field.setToolTip(TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslations());
				}
				oldText = setTranslation((LocalTranslations)field.getToolTip(), locale, newText);
				fireChangeTranslation(kind, locale, oldText, newText);
				break;
			}
			default:
				break;

		}
		return oldText;
	}

	@Override
	public final Object getOwner() {
		return getField();
	}

	@Override
	public String doDelete(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String oldText = null;
		LocalTranslations localTranlations =null;  
		switch (kind) {

			case NAME: {
				localTranlations =(LocalTranslations)field.getLabel();     
				oldText = deleteTranslation(kind, locale,localTranlations);
				//set the label to null if no translation.
				if(localTranlations != null){
					if(localTranlations.getTranslations().isEmpty()){
				    field.setLabel(null); 
					}
				}
				break;
			}
	
			case TEXT: {
				localTranlations =(LocalTranslations)field.getToolTip();
				oldText = deleteTranslation(kind, locale, localTranlations);
				//set the tooltip to null if no translation.
				if(localTranlations.getTranslations().isEmpty()){
				    field.setToolTip(null); 
				}
				break;
			}
			
			default: {
				break;
			}
		}

		return oldText;
	}
	
	@Override
	public boolean isInheritable() {
		return ! STAR_NAME.equals(getField().getName());
	}

	public FieldTranslation(ITranslationProvider provider, IProject project, Field field) {
		super(provider, project);
		if (field == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		this.field = field;	}

	@Override
	public boolean isReadOnly() throws TranslationException {
		return false;
	}

	@Override
	public boolean isProtected() throws TranslationException {
		return false;
	}

	@Override
	protected ITranslation doGetDelegate() throws TranslationException {
		ITranslation delegate = null;
		EObject container = EcoreUtil.getRootContainer(field);
		if (container instanceof Version) {
			Version version = (Version) container; 
			MdfEntity forApp = version.getForApplication();
			MdfProperty property = null;
			if(forApp !=null && forApp instanceof MdfClass) {
			    property = VersionUtils.getMdfProperty(field, (MdfClass) forApp);
			}
			if (property != null) {
				IOfsProject ofsProject = OfsCore.getOfsProject(getProject());
				if (ofsProject != null) {
					ITranslationManager manager = TranslationCore.getTranslationManager(getProject());
					delegate = manager.getTranslation(property);
				}
			}
		}
		if (delegate == null) {
			String fieldName = getField().getName();
			Resource resource = getField().eResource();
			String uri = resource != null ? resource.getURI().toString() : "???";
			throw new UnexpectedTranslationException("Cannot load inherited translation field=["+fieldName+"] uri=["+uri+"]");
		}
		return delegate;
	}

	@Override
	protected String doGetText(ITranslationKind kind, Locale locale)
			throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String text = doGetText(getField(), kind, locale);
		
		if (STAR_NAME.equals(getField().getName())) {
			// special case: star field cannot inherit the translation from the domain
			// so we simply return the existing local translation or 
			// (important) an empty string in order to avoid to retrieve the translation from the domain
			if (text == null) {
				text = "";
			}
		}
		return text;	
	}
	
	


}
