package com.odcgroup.ocs.server.external.ui;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

import com.odcgroup.ocs.server.external.model.IExternalServer;
import com.odcgroup.ocs.server.external.ui.builder.DeployConsole;
import com.odcgroup.ocs.server.external.ui.builder.PrepareDeploymentFacade;
import com.odcgroup.ocs.server.external.ui.util.ExternalServerManager;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.workbench.ui.AbstractUIActivator;

public class OCSServerUIExternalCore extends AbstractUIActivator {
	
	public static final String PLUGIN_ID = "com.odcgroup.ocs.server.external.ui";
	
	public static final String NATURE_ID = "com.odcgroup.ocs.server.deploynature";


	// The console access for deployment related activities
	private DeployConsole deployBuilderConsole;

	private ExternalServerContributions contributions;

	public static OCSServerUIExternalCore getDefault() {
		return (OCSServerUIExternalCore) getDefault(OCSServerUIExternalCore.class);
	}

	public IServerContributions getContributions() {
		return contributions;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		deployBuilderConsole = new DeployConsole();
		ExternalServerManager.initiliazeExternalServer();
		contributions = new ExternalServerContributions();
		PrepareDeploymentFacade.updateServerViewAndFixConfig();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		if (deployBuilderConsole != null) {
			deployBuilderConsole.dispose();
			deployBuilderConsole = null;
		}
		super.stop(context);
	}

	/**
	 * @return the console used by the deploy builder
	 */
	public DeployConsole getDeployBuilderConsole() {
		return deployBuilderConsole;
	}

	public List<IExternalServer> getDisplayableExternalServers() {
		ArrayList<IExternalServer> externalServers = new ArrayList<IExternalServer>();
		IExternalServer externalServer = getExternalServer();
		if(externalServer.getState() != IDSServer.STATE_NOT_CONFIGURED) {
			externalServers.add(externalServer);
		}
		return externalServers;
	}

	/**
	 * @return
	 */
	public IExternalServer getExternalServer() {
		return ExternalServerManager.getExternalServer();
	}

}
