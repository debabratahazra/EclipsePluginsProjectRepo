package com.odcgroup.server.ui.actions;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.SelectionProviderAction;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.server.IDSServerStateChangeListener;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.server.ui.ServerUICore;

/**
 * @author pkk
 */
public abstract class AbstractServerAction extends SelectionProviderAction
		implements IDSServerStateChangeListener {
	
	protected Shell shell;
	
	/**
	 * @param provider
	 * @param text
	 */
	public AbstractServerAction(Shell shell, ISelectionProvider provider, String text) {
		super(provider, text);
		this.shell = shell;
		setEnabled(false);
	}
	
	/**
	 * @param server
	 * @return
	 */
	protected boolean accept(IDSServer server) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		Object obj = getStructuredSelection().getFirstElement();
		IDSServer server = null;
		if (obj instanceof IDSServer) {
			server = (IDSServer)obj;
		} else if (obj instanceof IDSProject) {
			server = ((IDSProject)obj).getServer();
		}
		if (server != null) {
			if (accept(server)) {
				run(server);
			}
			selectionChanged(getStructuredSelection());
		}
	}	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.SelectionProviderAction#selectionChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void selectionChanged(IStructuredSelection selection) {
		if (selection.isEmpty()) {
			setEnabled(false);
			return;
		}
		Object obj = selection.getFirstElement();
		if (obj instanceof IDSServer) {
			setEnabled(accept((IDSServer) obj));
		} 
	}
	
	public void serverStateChanged(IDSServer server) {
		setEnabled(accept(server));
	}

	/**
	 * @return the shell
	 */
	public Shell getShell() {
		return shell;
	}

	/**
	 * @param server
	 */
	public abstract void run(IDSServer server);
	
	/**
	 * @param path
	 */
	public void setImage(String path) {
		setImageDescriptor(ServerUICore.getImageDescriptor(path));
	}

	/**
	 * Needed for testing
	 */
	protected IServerContributions getContributions() {
		return ServerUICore.getDefault().getContributions();
	}

}
