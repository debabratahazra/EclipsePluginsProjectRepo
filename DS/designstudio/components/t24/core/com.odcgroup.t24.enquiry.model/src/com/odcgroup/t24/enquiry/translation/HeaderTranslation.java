package com.odcgroup.t24.enquiry.translation;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class HeaderTranslation extends BaseVersionTranslation {

	private EnquiryHeader header;
	/**
	 * @param provider
	 * @param project
	 */
	protected HeaderTranslation(ITranslationProvider provider, IProject project,EnquiryHeader header) {
		super(provider, project);
		this.header = header;
	}

	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String text = null;

		switch (kind) {

		case NAME: {
			LocalTranslation localTranslation = findTranslation(locale, (LocalTranslations) header.getLabel());
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
	public String setText(ITranslationKind kind, Locale locale, String newText) throws TranslationException {

		if (null == newText || null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String oldText = null;

		switch (kind) {
		case NAME: {
			if (header.getLabel() == null) {
				header.setLabel(TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslations());
			}
			oldText = setTranslation((LocalTranslations) header.getLabel(), locale, newText);
			fireChangeTranslation(kind, locale, oldText, newText);
			break;
		}
		default:
			break;
		}
		return oldText;
	}

	@Override
	public Object getOwner() {
		return getHeader();
	}

	@Override
	public String delete(ITranslationKind kind, Locale locale) throws TranslationException {
		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String oldText = null;
		LocalTranslations localTranlations = null;
		switch (kind) {

		case NAME: {
			localTranlations = (LocalTranslations) header.getLabel();
			oldText = deleteTranslation(kind, locale, localTranlations);
			// set the description to null if no header translation.
			if (localTranlations != null)
				if (localTranlations.getTranslations().isEmpty()) {
					header.setLabel(null);
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
	public boolean isReadOnly() throws TranslationException {
		return false;
	}

	@Override
	protected ITranslationKind[] getTranslationKinds() {
		return new ITranslationKind[] { ITranslationKind.NAME };
	}

	public EnquiryHeader getHeader() {
		return header;
	}
}
