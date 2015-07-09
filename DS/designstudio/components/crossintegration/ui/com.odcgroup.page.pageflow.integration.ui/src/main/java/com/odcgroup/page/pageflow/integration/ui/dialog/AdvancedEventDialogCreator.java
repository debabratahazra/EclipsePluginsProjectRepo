package com.odcgroup.page.pageflow.integration.ui.dialog;

import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.dialog.IPageEventDefinitionDialog;
import com.odcgroup.page.ui.dialog.IPageEventDialogCreator;

/**
 *
 * @author pkk
 *
 */
public class AdvancedEventDialogCreator implements IPageEventDialogCreator {

	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.dialog.IPageEventDialogCreator#createEventDialog(org.eclipse.swt.widgets.Shell, com.odcgroup.page.model.Widget, com.odcgroup.page.model.Event, java.lang.String, java.lang.String)
	 */
	@Override
	public IPageEventDefinitionDialog createEventDialog(Shell parentShell, Widget widget, Event event, String title,
			String message) {
		return new EventDefinitionDialog(parentShell, widget, event, title, message);
	}

}
