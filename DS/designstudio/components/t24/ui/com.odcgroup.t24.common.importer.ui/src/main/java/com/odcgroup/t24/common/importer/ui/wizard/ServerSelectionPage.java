package com.odcgroup.t24.common.importer.ui.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.server.ui.ServerUICore;
import com.odcgroup.t24.common.importer.IServerSelector;
import com.odcgroup.t24.common.importer.ui.Messages;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * This wizard page allows the user to select the server from which models
 * will be imported.
 */
public class ServerSelectionPage extends WizardPage implements Listener {
	
	private IServerSelector serverSelector;
	
	private void validateControls() {
		setPageComplete(serverSelector.isServerSet());
		canFlipToNextPage();
		getWizard().getContainer().updateButtons();
	}
	
	/**
	 * @return
	 */
	protected List<IExternalServer> getExternalServers() {
		IServerContributions contributions = ServerUICore.getDefault().getContributions();
		List<IExternalServer> servers = new ArrayList<IExternalServer>();
		for (IDSServer server : contributions.getServers()) {
			if (server instanceof IExternalServer) {
				servers.add((IExternalServer)server);
			}
		}
		return servers; 
	}
	
	/**
	 * @return
	 */
	protected final IServerSelector getServerSelector() {
		return this.serverSelector;
	}
	
	/**
	 * @param parent
	 * @return
	 */
	protected ServerSelectionGroup createServerSelectionGroup(Composite parent) {
		return new ServerSelectionGroup(parent, this, serverSelector, Messages.ServerSelectionGroup_message);
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete();
	}
	
	@Override
	public void createControl(Composite parent) {

		// create page content
		Composite topLevel = new Composite(parent, SWT.NULL);
		topLevel.setLayout(new GridLayout(1, true));
		
		ServerSelectionGroup ssg = createServerSelectionGroup(topLevel); 
		
		// initialize
		ssg.setInput(getExternalServers());

		// Set the control for the receiver.
		setControl(topLevel);

		// Set help context
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), IOfsHelpContextIds.CONTEXT_PREFIX);
	}

	@Override
	public void handleEvent(Event event) {
		validateControls();
	}

	/**
	 * @param serverSelector
	 */
	public ServerSelectionPage(IServerSelector serverSelector) {
		super(""); //$NON-NLS-1$
		setTitle(Messages.ServerSelectionPage_title);
		setDescription(Messages.ServerSelectionPage_description);
		this.serverSelector = serverSelector;
		setPageComplete(false);
	}

}
