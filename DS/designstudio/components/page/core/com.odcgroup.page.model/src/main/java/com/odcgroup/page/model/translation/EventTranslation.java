package com.odcgroup.page.model.translation;

import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelPackage;
import com.odcgroup.page.model.Translation;
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
 * This class help to read/write messages for an <code>Event</code>
 * 
 * @author atr
 */
public class EventTranslation extends BaseTranslation implements ITranslation {

	/** The wrapped event */
	private Event event;

	/**
	 * Find the message given the language
	 * 
	 * @param event
	 * @param locale
	 * @return Translation
	 */
	private final Translation lookupMessage(Event event, Locale locale) {
		Translation result = null;
		for (Translation translation : event.getMessages()) {
			if (locale.toString().equals(translation.getLanguage())) {
				result = translation;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Update the translation identifier
	 */
	private void updateTranslationId() {
		synchronized (event) {
			if (event.getMessages().size() == 0) {
				event.setTranslationId(0);
			} else if (event.getTranslationId() == 0) {
				event.setTranslationId(System.nanoTime());
			}
		}
	}	

	/**
	 * @param locale
	 * @param newValue
	 * @return the old value of the message
	 */
	private final String setMessage(Locale locale, String newValue) {
		String oldValue = null;
		Translation message = lookupMessage(this.event, locale);
		if (message != null) {
			oldValue = message.getMessage();
			message.setMessage(newValue);
		} else {
			String language = locale.toString();
			message = ModelPackage.eINSTANCE.getModelFactory().createTranslation();
			message.setLanguage(language);
			message.setMessage(newValue);
			this.event.getMessages().add(message);
			updateTranslationId();
		}
		return oldValue;
	}

	/**
	 * @param kind the translation kind
	 * @param locale the language
	 * @return the text of the deleted message
	 */
	private final String deleteMessage(ITranslationKind kind, Locale locale) {
		String oldText = null;
		String language = locale.toString();
		List<Translation> messages = event.getMessages();
		for (Translation message : messages) {
			if (language.equals(message.getLanguage())) {
				oldText = message.getMessage();
				messages.remove(message);
				updateTranslationId();
				fireChangeTranslation(kind, locale, oldText, null);
				break;
			}
		}
		return oldText;
	}

	/**
	 * @return the wrapped event
	 */
	protected final Event getEvent() {
		return this.event;
	}

	/**
	 * @param event
	 * @param kind
	 * @param locale
	 * @return the text of the designated translation
	 */
	protected String getText(Event event, ITranslationKind kind, Locale locale) {
		String text = null;
		Translation translation = lookupMessage(event, locale);
		if (translation != null) {
			text = translation.getMessage();
		}
		return text;
	}

	@Override
	public String delete(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		if (!kind.equals(ITranslationKind.NAME)) {
			throw new IllegalArgumentException("Illegal translation kind");
		}

		return deleteMessage(kind, locale);
	}

	@Override
	public final ITranslationKind[] getTranslationKinds() {
		return new ITranslationKind[] { ITranslationKind.NAME };
	}

	@Override
	public final Object getOwner() {
		return this.event;
	}

	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		if (!kind.equals(ITranslationKind.NAME)) {
			throw new IllegalArgumentException("Illegal translation kind");
		}

		return getText(getEvent(), kind, locale);
	}

	@Override
	public final boolean isInheritable() {
		return false;
	}

	@Override
	public final boolean isInherited(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		if (!kind.equals(ITranslationKind.NAME)) {
			throw new IllegalArgumentException("Illegal translation kind");
		}

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
		
		Resource resource = getEvent().eResource();
		if (resource != null) {
			URI uri = ModelURIConverter.toResourceURI(resource.getURI());
			IOfsProject ofsProject = OfsCore.getOfsProject(getProject());
			IOfsModelResource ofsResource = 
				ofsProject.getModelResourceSet().getOfsModelResource(uri, IOfsProject.SCOPE_PROJECT);
			if (ofsResource != null) {
				return ofsResource.isReadOnly();
			} else {
				// the widget is in a standard project.
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean isProtected() throws TranslationException {
		return false;
	}
	
	@Override
	public String setText(ITranslationKind kind, Locale locale, String newText) throws TranslationException {

		if (null == kind || null == locale || null == newText) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		if (!kind.equals(ITranslationKind.NAME)) {
			throw new IllegalArgumentException("Illegal translation kind");
		}

		String oldText = setMessage(locale, newText);
		fireChangeTranslation(kind, locale, oldText, newText);
		return oldText;
	}

	/**
	 * Constructor
	 * 
	 * @param project
	 *            the project
	 * @param event
	 *            the wrapped event
	 */
	public EventTranslation(ITranslationProvider provider, IProject project, Event event) {
		super(provider, project);

		if (null == event) {
			throw new IllegalArgumentException("Argument cannot be null");
		}

		this.event = event;
	}

}
