package com.odcgroup.server.ui.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.ui.UnableToStopServerException;

/**
 *
 *
 */
public class StopServerAction extends AbstractServerAction {
	
	/**
	 * @param shell
	 * @param provider
	 * @param text
	 */
	public StopServerAction(Shell shell, ISelectionProvider provider) {
		super(shell, provider, "Stop");
		setToolTipText("Stop Server");
		setImage("icons/stopserver.gif");
	}

	@Override
	public void run(final IDSServer server) {
		try {
			if (server.getState() != IDSServerStates.STATE_STOPPED) {
				getContributions().stop(server);
			}
		} catch (UnableToStopServerException e) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Cannot launch the server", e.getMessage());
		}
	}
	
	protected boolean accept(IDSServer server) {
		if (server.getState() == IDSServer.STATE_STARTED 
				|| server.getState() == IDSServer.STATE_STARTING
				|| server.getState() == IDSServer.STATE_STARTED_DEBUG
				|| server.getState() == IDSServer.STATE_STOPPING
				|| server.getState() == IDSServer.STATE_ACTIVE
				|| server.getState() == IDSServer.STATE_ACTIVE_IN_DEBUG) {
			return true;
		}
		return false;
	}

}
