package com.odcgroup.server.ui.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.UnableToStartServerException;
import com.odcgroup.server.ui.UnableToStopServerException;

/**
 * @author pkk
 */
public class DebugServerAction extends AbstractServerAction {

	private static Logger logger = LoggerFactory.getLogger(DebugServerAction.class);

	/**
	 * @param shell
	 * @param provider
	 * @param text
	 */
	public DebugServerAction(Shell shell, ISelectionProvider provider) {
		super(shell, provider, "Debug");
		setToolTipText("");
		setImage("icons/debugserver.gif");
	}

	@Override
	public void run(final IDSServer server) {
		try {
			if (server.getState() != IDSServer.STATE_STOPPED) {
				getContributions().stop(server);
			}
		} catch (UnableToStopServerException e) {
			logger.error("Unable to stop the server", e);
		}
		try {
			getContributions().start(server, true);
		} catch (UnableToStartServerException e) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Cannot launch the server", e.getMessage());
		}
	}
	
	protected boolean accept(IDSServer server) {
		return true;
	}
	
}
