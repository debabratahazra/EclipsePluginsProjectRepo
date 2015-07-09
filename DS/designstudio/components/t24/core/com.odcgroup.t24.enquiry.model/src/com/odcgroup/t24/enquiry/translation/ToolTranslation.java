package com.odcgroup.t24.enquiry.translation;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.Tool;
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
public class ToolTranslation extends BaseVersionTranslation {

	private Tool tool;
	/**
	 * @param provider
	 * @param project
	 */
	protected ToolTranslation(ITranslationProvider provider, IProject project,Tool tool) {
		super(provider, project);
		this.tool = tool;
	}

	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String text = null;

		switch (kind) {

		case NAME: {
			LocalTranslation localTranslation = findTranslation(locale, (LocalTranslations) tool.getLabel());
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
			if (tool.getLabel() == null) {
				tool.setLabel(TranslationDslPackage.eINSTANCE.getTranslationDslFactory().createLocalTranslations());
			}
			oldText = setTranslation((LocalTranslations) tool.getLabel(), locale, newText);
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
		return getTool();
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
			localTranlations = (LocalTranslations) tool.getLabel();
			oldText = deleteTranslation(kind, locale, localTranlations);
			// set the description to null if no header translation.
			if (localTranlations != null)
				if (localTranlations.getTranslations().isEmpty()) {
					tool.setLabel(null);
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

	public Tool getTool() {
		return tool;
	}
}
