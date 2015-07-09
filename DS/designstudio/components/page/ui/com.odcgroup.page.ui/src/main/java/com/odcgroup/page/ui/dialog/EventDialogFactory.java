package com.odcgroup.page.ui.dialog;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.log.Logger;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Widget;

/**
 * Factory that creates a user preferred event definition dialog
 *
 * @author pkk
 *
 */
public class EventDialogFactory {
	
	/**  */
	public static final String EXTENSION_POINT = "com.odcgroup.page.ui.eventDefinitionDialog";
	/**  */
	public static final String CONFIG_ELEMENT_NAME = "eventDialog";
	/**  */
	public static final String CLASS_ATTRIBUTE = "class";
	/**  */	
	private IPageEventDialogCreator dialogCreator = null;
	/**  */	
	private static EventDialogFactory INSTANCE = null;
	
	/**
	 * private constructor
	 */
	private EventDialogFactory() {		
	}
	
	/**
	 * @return EventDialogFactory
	 */
	public static EventDialogFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new EventDialogFactory();
		}
		return INSTANCE;
	}
	
	/**
	 * Create Event Definition Dialog
	 * 
	 * @param shell
	 * @param widget
	 * @param event
	 * @return IPageEventDefinitionDialog
	 */
	public IPageEventDefinitionDialog createEventDialog(Shell shell, Widget widget, Event event) {
		IPageEventDefinitionDialog dialog = null;
		if (dialogCreator == null) {
			dialogCreator = getEventDialogCreatorFromRegistry();
		}
		String title = "Event";
		String message = "Modify Event";
		if (event == null) {
			message = "Create a new Event";
		}
		if (dialogCreator == null) {
			dialog = new EventDialog(shell, widget, event, title, message);
		} else {
			dialog = dialogCreator.createEventDialog(shell, widget, event, title, message);
		}
		return dialog;
	}
	
	
	/**
	 * fetches the EventDialogCreator from registry
	 * 
	 * @return IPageEventDialogCreator
	 * 
	 */
	private IPageEventDialogCreator getEventDialogCreatorFromRegistry() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT);
        IPageEventDialogCreator eventDialogCreator = null;
		IExtension[] extensions = point.getExtensions();
		if (extensions.length > 0) {
			if (extensions.length > 1) {
				Logger.warning("More than one extensions are defined for \'"+EXTENSION_POINT+"\'");
			}
			IConfigurationElement[] elements = extensions[0].getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				if (CONFIG_ELEMENT_NAME.equals(elements[j].getName())) {					
					try {
						eventDialogCreator = (IPageEventDialogCreator) elements[j].createExecutableExtension(CLASS_ATTRIBUTE);
					} catch (CoreException e) {
						Logger.error("Error loading \'"+EXTENSION_POINT+"\'", e);
					}
				}
			}
		}
		return eventDialogCreator;
	}

}
