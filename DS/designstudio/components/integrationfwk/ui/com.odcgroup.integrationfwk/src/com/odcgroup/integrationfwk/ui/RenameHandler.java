package com.odcgroup.integrationfwk.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

/**
 * Class which handles the Rename command.
 * <p>
 * Whenever the rename command is executed, this class is getting invoked
 * through plug-in.xml with a special contribution as Handler extension point.
 * </p>
 * 
 * @author sbharathraja
 * 
 */
public class RenameHandler extends AbstractHandler {

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		MessageDialog.openError(Display.getCurrent().getActiveShell(),
				"Rename Integration Project",
				"Renaming is not allowed for Integration Project!");
		return null;
	}

}
