package com.odcgroup.mdf.editor.ui.dialogs;

import org.eclipse.swt.widgets.Shell;

import com.odcgroup.mdf.editor.ui.WidgetFactory;

/**
 * TODO: DOCUMENT ME!
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public interface DialogPageContainer {

    public WidgetFactory getWidgetFactory();

	public DialogPage getCurrentPage();

	public void updateTitleBar();

	public Shell getShell();

	public void updateButtons();

	public void updateMessage();

	public void setDirty(boolean dirty);
	
    //OCS-26284 added dispose method
	public void dispose();
}
