package com.odcgroup.server.ui.actions;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.dialogs.ManageProjectsDialog;
import com.odcgroup.server.ui.views.ServerView;

/**
 *
 */
public class AddRemoveProjectsAction extends AbstractServerAction {
	
	private ServerView serverView;

	/**
	 * @param shell
	 * @param provider
	 * @param serverView 
	 * @param text
	 */
	public AddRemoveProjectsAction(Shell shell, ISelectionProvider provider, ServerView serverView) {
		super(shell, provider, "Add/Remove Project(s)");
		setImage("icons/addproj.gif");
		this.serverView = serverView;
	}

	@Override
	public void run(IDSServer server) {		
		ManageProjectsDialog dialog = new ManageProjectsDialog(shell, server);
		dialog.open();
		serverView.expand(server);
	}

}
