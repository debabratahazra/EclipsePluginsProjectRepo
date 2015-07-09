package com.odcgroup.t24.enquiry.translation;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.Field;
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
public class FieldTranslation extends BaseVersionTranslation {

	private Field field;

	/**
	 * @param provider
	 * @param project
	 */
	protected FieldTranslation(ITranslationProvider provider, IProject project, Field element) {
		super(provider, project);
		if (element == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		this.field = element;
	}

	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String text = null;

		switch (kind) {

		case NAME: {
			LocalTranslation localTranslation = findTranslation(locale, (LocalTranslations) field.getLabel());
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
			if (field.getLabel() == null) {
				field.setLabel(TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslations());
			}
			oldText = setTranslation((LocalTranslations) field.getLabel(), locale, newText);
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
		// TODO Auto-generated method stub
		return getField();
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
			localTranlations = (LocalTranslations) field.getLabel();
			oldText = deleteTranslation(kind, locale, localTranlations);
			// set the description to null if no description translation.
			if (localTranlations != null)
				if (localTranlations.getTranslations().isEmpty()) {
					field.setLabel(null);
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
		// TODO Auto-generated method stub
		return new ITranslationKind[] { ITranslationKind.NAME };
	}

	public Field getField() {
		return field;
	}
}
