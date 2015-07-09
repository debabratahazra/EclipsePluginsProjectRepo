package com.odcgroup.t24.server.external.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.ui.builder.T24DeployConsole;
import com.odcgroup.t24.server.external.ui.builder.listener.IT24DeployListener;
import com.odcgroup.t24.server.external.util.T24ExternalServerManager;
import com.odcgroup.workbench.ui.AbstractUIActivator;

public class T24ServerUIExternalCore extends AbstractUIActivator {

	// The console access for deployment related activities
	private T24DeployConsole deployBuilderConsole;

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.server";  

	public static final String NATURE_ID = "com.odcgroup.t24.server.deploynature";
	
	private final static String DEPLOY_LISTENER_ID = "com.odcgroup.t24.server.external.ui.deploylistener";
	
	private static Logger logger = LoggerFactory.getLogger(T24ServerUIExternalCore.class);
	
	/** The shared instance */
	private static T24ServerUIExternalCore plugin;
	
	private ExternalT24ServerContributions contributions;
	
	private List<IT24DeployListener> deployListeners = new ArrayList<IT24DeployListener>();
	
	public T24ServerUIExternalCore() {
		plugin = this;
	}

	public IServerContributions getContributions() {
		return contributions;
	}
	
	public List<IT24DeployListener> getDeployListeners() {
		if (deployListeners.isEmpty()) {
			IConfigurationElement[] configs = Platform.getExtensionRegistry().getConfigurationElementsFor(DEPLOY_LISTENER_ID);

			if (configs.length == 0) {
				logger.error("The extension point " + DEPLOY_LISTENER_ID + " is not properly configured");
				throw new IllegalStateException("The extension point " + DEPLOY_LISTENER_ID + " is not properly configured");
			}

			try {
				for (IConfigurationElement config : configs) {
					Object deployListener = config.createExecutableExtension("class");
					if (deployListener instanceof IT24DeployListener) {
						deployListeners.add((IT24DeployListener) deployListener);
					} else {
						logger.error("The extension point " + DEPLOY_LISTENER_ID + " is not properly configured (wrong class)");
						throw new IllegalStateException("The extension point " + DEPLOY_LISTENER_ID + " is not properly configured (wrong class)");
					}
				}
				logger.info("The T24 Deploy Listeners are configured");
			} catch (CoreException e) {
				throw new RuntimeException(e);
			}
		}
		return deployListeners;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		deployBuilderConsole = new T24DeployConsole();
		contributions = new ExternalT24ServerContributions();
		T24ExternalServerManager.initiliazeExternalServer();
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
		plugin = null;
		super.stop(context);
	}
	
	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static T24ServerUIExternalCore getDefault() {
		return plugin;
	}

	/**
	 * @return the console used by the deploy builder
	 */
	public T24DeployConsole getDeployBuilderConsole() {
		return deployBuilderConsole;
	}

	public List<IExternalServer> getDisplayableExternalServers() {
		return T24ExternalServerManager.getInstance().getExternalServers();
	}

	/**
	 * @return
	 */
	public IExternalServer getExternalServer() {
		return T24ExternalServerManager.getExternalServer();
	}
	/**
	 * this setter is only for deploy external test  purpose to set the customised console
	 * @param deployConsole
	 */
	public void setDeployBuilderConsole(T24DeployConsole deployConsole){
		deployBuilderConsole = deployConsole;
	}
}
