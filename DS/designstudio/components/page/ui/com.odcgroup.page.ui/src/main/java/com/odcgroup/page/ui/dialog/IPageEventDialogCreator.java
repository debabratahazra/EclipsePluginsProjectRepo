package com.odcgroup.page.ui.dialog;

import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Widget;

/**
 * A factory object for <code>IPageEventDefinitionDialog</code>.
 * <p>
 * This interface is only required when creating a <code>IPageEventDefinitionDialog</code> from a plugin.xml file.
 * Since <code>IPageEventDefinitionDialog</code>s have no default constructor they cannot be
 * instantiated directly with <code>Class.forName</code>.
 *
 * @author pkk
 *
 */
public interface IPageEventDialogCreator {
	
	/**
	 * creates an instance of IPageEventDefinitionDialog implementation
	 * 
	 * @param parentShell
	 * @param widget
	 * @param event
	 * @param title
	 * @param message
	 * @return IPageEventDefinitionDialog
	 */
	public IPageEventDefinitionDialog createEventDialog(Shell parentShell, Widget widget, Event event, String title, String message);

}
