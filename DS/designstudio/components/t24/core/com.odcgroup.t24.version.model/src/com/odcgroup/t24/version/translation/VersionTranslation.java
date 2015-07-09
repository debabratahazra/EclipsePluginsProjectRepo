package com.odcgroup.t24.version.translation;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;

public class VersionTranslation extends BaseVersionTranslation implements ITranslation {
	
	private Version version;

	protected final Version getVersion() {
		return this.version;
	}
	
	@Override
	public ITranslationKind[] getTranslationKinds() {
	    return new ITranslationKind[] { ITranslationKind.NAME,ITranslationKind.HEADER1, ITranslationKind.HEADER2,
		                            ITranslationKind.HEADER, ITranslationKind.FOOTER };
	}

	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String text = null;
		
		switch (kind) {

			case NAME: {
				LocalTranslation localTranslation = findTranslation(locale, (LocalTranslations)version.getDescription());
				if (localTranslation != null) {
					text = localTranslation.getText();
				} 
				break;
			}
			case HEADER1: {
				LocalTranslation translation = findTranslation(locale, (LocalTranslations)version.getHeader1());
				if (translation != null) {
					text = translation.getText();
				} 
				break;
			}
	
			case HEADER2: {
				LocalTranslation translation = findTranslation(locale, (LocalTranslations)version.getHeader2());
				if (translation != null) {
					text = translation.getText();
				} 
				break;
			}
			case HEADER: {
				LocalTranslation translation = findTranslation(locale, (LocalTranslations)version.getHeader());
				if (translation != null) {
					text = translation.getText();
				} 
				break;
			}
	
			case FOOTER: {
				LocalTranslation translation = findTranslation(locale, (LocalTranslations)version.getFooter());
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
	public String setText(ITranslationKind kind, Locale locale, String newText)
			throws TranslationException {

		if (null == newText || null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String oldText = null;

		switch (kind) {

			case NAME: {
				if (version.getDescription() == null) {
					version.setDescription(TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslations());
				}
				oldText = setTranslation((LocalTranslations)version.getDescription(), locale, newText);
				fireChangeTranslation(kind, locale, oldText, newText);
				break;
			}
	
			case HEADER1: {
				if (version.getHeader1() == null) {
					version.setHeader1(TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslations());
				}
				oldText = setTranslation((LocalTranslations)version.getHeader1(), locale, newText);
				fireChangeTranslation(kind, locale, oldText, newText);
				break;
			}

			case HEADER2: {
				if (version.getHeader2() == null) {
					version.setHeader2(TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslations());
				}
				oldText = setTranslation((LocalTranslations)version.getHeader2(), locale, newText);
				fireChangeTranslation(kind, locale, oldText, newText);
				break;
			}
			case HEADER: {
				if (version.getHeader() == null) {
				    version.setHeader(TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslations());
				}
				oldText = setTranslation((LocalTranslations)version.getHeader(), locale, newText);
				fireChangeTranslation(kind, locale, oldText, newText);
				break;
			}
			case FOOTER: {
				if (version.getFooter() == null) {
				    version.setFooter(TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslations());
				}
				oldText = setTranslation((LocalTranslations)version.getFooter(), locale, newText);
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
		return getVersion();
	}

	@Override
	public String delete(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String oldText = null;
		LocalTranslations localTranlations =null;  
		switch (kind) {

			case NAME: {
			        localTranlations=(LocalTranslations)version.getDescription();
				oldText = deleteTranslation(kind, locale, localTranlations);
				//set the description to null if no description translation.
				if(localTranlations != null)
				if(localTranlations.getTranslations().isEmpty()){
				    version.setDescription(null); 
				}
				break;
			}
	
			case HEADER1: {
			        localTranlations=(LocalTranslations)version.getHeader1();
				oldText = deleteTranslation(kind, locale,localTranlations );
				//set the header1 to null if no header1 translations.
				if(localTranlations != null){
				if (localTranlations.getTranslations().isEmpty()) {
					version.setHeader1(null);
				}
				}
				break;
			}
	
			case HEADER2: {
			        localTranlations=(LocalTranslations)version.getHeader2();
				oldText = deleteTranslation(kind, locale, localTranlations);
				//set the header2 to null if no header2 translations.
				if(localTranlations.getTranslations().isEmpty()){
				    version.setHeader2(null); 
				}
				break;
			}
			case HEADER: {
				localTranlations =(LocalTranslations)version.getHeader();
				oldText = deleteTranslation(kind, locale, localTranlations);
				//set the header to null if no translation.
				if(localTranlations.getTranslations().isEmpty()){
				    version.setHeader(null); 
				}
				break;
			}
			case FOOTER: {
				localTranlations =(LocalTranslations)version.getFooter();
				oldText = deleteTranslation(kind, locale, localTranlations);
				//set the footer to null if no translation.
				if(localTranlations.getTranslations().isEmpty()){
				    version.setFooter(null); 
				}
				break;
			}
			default: {
				break;
			}
		}

		return oldText;
	}

	public VersionTranslation(ITranslationProvider provider, IProject project, Version version) {
		super(provider, project);
		if (version == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		this.version = version;	}

}
