package com.odcgroup.t24.enquiry.translation;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;

public class EnquiryTranslation extends BaseVersionTranslation implements ITranslation {
	
	private Enquiry enquiry;

	protected final Enquiry getEnquiry() {
		return this.enquiry;
	}
	
	@Override
	public ITranslationKind[] getTranslationKinds() {
		return new ITranslationKind[] { ITranslationKind.NAME };
	}

	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String text = null;
		
		switch (kind) {

			case NAME: {
				LocalTranslation localTranslation = findTranslation(locale, (LocalTranslations)enquiry.getDescription());
				if (localTranslation != null) {
					text = localTranslation.getText();
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
				if (enquiry.getDescription() == null) {
					enquiry.setDescription(TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslations());
				}
				oldText = setTranslation((LocalTranslations)enquiry.getDescription(), locale, newText);
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
		return getEnquiry();
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
				localTranlations=(LocalTranslations)enquiry.getDescription();
				oldText = deleteTranslation(kind, locale, localTranlations);
				//set the description to null if no description translation.
				if(localTranlations != null)
				if(localTranlations.getTranslations().isEmpty()){
					enquiry.setDescription(null); 
				}
				break;
				
			}
	
			default: {
				break;
			}
		}

		return oldText;
	}

	public EnquiryTranslation(ITranslationProvider provider, IProject project, Enquiry enquiry) {
		super(provider, project);
		if (enquiry == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		this.enquiry = enquiry;	
	}

	@Override
	public boolean isReadOnly() throws TranslationException {
		return false;
	}
}
