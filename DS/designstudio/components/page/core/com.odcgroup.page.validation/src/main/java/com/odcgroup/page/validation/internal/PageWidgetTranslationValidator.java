package com.odcgroup.page.validation.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource;
import org.xml.sax.SAXException;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.validation.Activator;
import com.odcgroup.page.validation.internal.constraint.PageValidationListener;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.richtext.RichTextUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This class defines specific constraints for events
 * 
 * <p>
 * This first implementation just check a subset of the properties of each
 * event, in particular all properties that might contain either a reference to
 * a model (module, pageflow) or to a domain element.
 * 
 * @author atr
 */
public class PageWidgetTranslationValidator {

	/** This listener is notify whenever an error is found */
	private PageValidationListener listener = null;

	/** The widget to be validated */
	private Widget widget = null;

	/** The OFS project */
	private IOfsProject ofsProject = null;
	
	/** used to avoid to notify twice the same error message */
	private Set<String> statusSent = new HashSet<String>();

	/**
	 * Notifies the Listener of a new error message
	 * 
	 * @param message
	 *            the error message
	 */
	private void notifyError(String message) {
		if (!statusSent.contains(message)) {
			IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			listener.onValidation(status);
			statusSent.add(message);
		}
	}
	
	private void checkRichTextTranslations(Widget widget) {		
		if (!widget.getType().isRichtext()) {
			return;
		}
		
		ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
		
		ITranslation translation = manager.getTranslation(widget);
		for (ITranslationKind kind : translation.getAllKinds()) {
			if (translation.acceptRichText(kind)) {
				List<Locale> locales = new ArrayList<Locale>();
				locales.add(manager.getPreferences().getDefaultLocale());
				locales.addAll(manager.getPreferences().getAdditionalLocales());
				String text;
				for (Locale locale : locales) {				
					try {
						text = translation.getText(kind, locale);
						if (RichTextUtils.isRichRext(text)) {
							RichTextUtils.validateRichText(text);
						}
					} catch (TranslationException ex) {
						// ignore this one.
					} catch (SAXException ex) {
						notifyError("Error in translation["+translation.getDisplayName(kind)+"/"+locale.getDisplayName(Locale.ENGLISH)+"] " + ex.getMessage());
					} catch (IOException ex) {
						notifyError("Error in translation["+translation.getDisplayName(kind)+"/"+locale.getDisplayName(Locale.ENGLISH)+"] " + ex.getMessage());
					}
				}
			}
		}
		
	}
	
	/**
	 * Checks all the event defined for the given widget
	 */
	public void checkConstraints() {
		checkRichTextTranslations(widget);
	}

	/**
	 * Constructs a new event validator
	 * 
	 * @param listener
	 *            will be notified whenever an error is found
	 * @param widget
	 *            the widget to be verified
	 */
	public PageWidgetTranslationValidator(PageValidationListener listener, Widget widget) {
		this.listener = listener;
		this.widget = widget;
		Resource res = widget.eResource();
		if (res != null) {
			ofsProject = OfsResourceHelper.getOfsProject(res);
		}
	}
	
	/**
	 * Constructs a new event validator
	 * 
	 * @param listener
	 *            will be notified whenever an error is found
	 * @param widget
	 *            the widget to be verified
	 */
	public PageWidgetTranslationValidator(PageValidationListener listener, Widget widget, IOfsProject ofsProject) {
		this.listener = listener;
		this.widget = widget;
		this.ofsProject = ofsProject;
	}

}
