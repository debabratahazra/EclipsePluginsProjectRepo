package com.odcgroup.server.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.util.DSProjectUtil;

/**
 * The activator class controls the plug-in life cycle
 */
public class ServerUICore extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.server.ui";

	// Server contribution
	public static final String SERVER_CONTRIBUTION_EXTENSION_POINT = PLUGIN_ID + ".contribution";

	// Contribution (made by embedded-server-ui, external-server-ui, ...)
	private List<IServerContributions> contributions;

	private static Logger logger = LoggerFactory.getLogger(ServerUICore.class);

	// The shared instance
	private static ServerUICore plugin;
	
	private static ProjectOpeningListener projectOpeningListener;
	
	private static final class ProjectOpeningListener implements
			IResourceChangeListener {
		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getDelta() != null) {
				IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
					public boolean visit(IResourceDelta delta)
							throws CoreException {
						IResource resource = delta.getResource();
						if (resource instanceof IWorkspaceRoot)
							return true;
						if (resource instanceof IProject) {
							final IProject project = (IProject) resource;
							if ((delta.getFlags() & IResourceDelta.OPEN) != 0) {
								if (project.exists() && project.isOpen()) {
									if (project.getName().endsWith("-models-gen")) {
										for (IDSServer server : ServerUICore.getDefault().getContributions()
												.getServers()) {
											// Add deployable project to each
											// servers
											List<IDSProject> newProjectList = DSProjectUtil.addProjectByName(project,
													server.getDsProjects());
											ServerUICore
													.getDefault()
													.getContributions()
													.updateDeployedProjects(server,
															newProjectList.toArray(new IDSProject[0]));

										}
									}
									getDefault().getContributions().notifyProjectChanged(project,
											IServerContributions.ChangeKind.ADDED);
								}
							} else if ((delta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
								// Project description change (nature, builder, ...)
								getDefault().getContributions().notifyProjectChanged(project, IServerContributions.ChangeKind.CHANGED);
							}
						}
						return false;
					}
				};
				try {
					event.getDelta().accept(visitor);
				} catch (CoreException e) {
					logger.warn("Error while checking for closed projects", e);
				}
			}

			if (event.getResource() instanceof IProject) {
				IProject project = (IProject) event.getResource();
				boolean projectDeleted = (event.getType() & IResourceChangeEvent.PRE_DELETE) != 0;
				boolean projectClosed = (event.getType() & IResourceChangeEvent.PRE_CLOSE) != 0;
				if (projectDeleted || projectClosed) {
					getDefault().getContributions().notifyProjectChanged(project, IServerContributions.ChangeKind.REMOVED);
				}
			}
		}
	}
	
	/**
	 * The constructor
	 */
	public ServerUICore() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		// Avoid SWTError at shutdown
		getWorkbench().addWorkbenchListener(new IWorkbenchListener() {
			public boolean preShutdown( IWorkbench workbench, boolean forced ) {
				// Remove consoles manually in time. Otherwise they are removed, when the display is
				// already disposed and this causes exceptions.
				ConsolePlugin.getDefault().getConsoleManager().removeConsoles(
				ConsolePlugin.getDefault().getConsoleManager().getConsoles() );
				return true;
			}


			public void postShutdown( IWorkbench workbench ) {
				// Do nothing
			}
		});

		// TODO remove listener in the stop
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {
			private Set<String> removedProjects = new HashSet<String>();
			public void resourceChanged(IResourceChangeEvent event) {
				boolean projectDeleted = (event.getType() & IResourceChangeEvent.PRE_DELETE) != 0;
				boolean projectClosed = (event.getType() & IResourceChangeEvent.PRE_CLOSE) != 0;
				
				if (event.getResource() instanceof IProject &&
						(projectDeleted || projectClosed)) {
					final IProject project = (IProject)event.getResource();
					for (IDSServer server: ServerUICore.getDefault().getContributions().getServers()) {
						for (IDSProject deployedProject : server.getDsProjects()) {
							String projectName = project.getName();
							if (deployedProject.getName().equals(projectName)) {
								removedProjects.add(projectName);
								List<IDSProject> newProjectList = DSProjectUtil.removeProjectByName(projectName, server.getDsProjects());
								ServerUICore.getDefault().getContributions().updateDeployedProjects(server, newProjectList.toArray(new IDSProject[0]));
							}
						}
					}
				} else if (!removedProjects.isEmpty()) {
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							final StringBuffer warningMessage = new StringBuffer();
							warningMessage.append("The following project(s) have been removed from the Server View:\n");
							for (String removedProject : removedProjects) {
								warningMessage.append("  - " + removedProject + "\n");
							}

							MessageDialog.openWarning(Display.getDefault().getActiveShell(),
									"Project(s) removed from the Server View",
									warningMessage.toString());

							removedProjects.clear();
						}
					});
				}
				
				
			}
		});
		
		projectOpeningListener = new ProjectOpeningListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(projectOpeningListener);
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(projectOpeningListener);
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ServerUICore getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public IServerContributions getContributions() {
		if (contributions == null) {
			contributions = new ArrayList<IServerContributions>();

			// Retrieve the server contributions
			IConfigurationElement[] configs = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(SERVER_CONTRIBUTION_EXTENSION_POINT);

			if (configs.length == 0) {
				logger.error("The extension point " + SERVER_CONTRIBUTION_EXTENSION_POINT + " is not properly configured");
				throw new IllegalStateException("The extension point " + SERVER_CONTRIBUTION_EXTENSION_POINT + " is not properly configured");
			}

			try {
				for (IConfigurationElement config : configs) {
					Object serverContribution = config.createExecutableExtension("type");
					if (serverContribution instanceof IServerContributions) {
						contributions.add((IServerContributions)serverContribution);
					} else {
						logger.error("The extension point " + SERVER_CONTRIBUTION_EXTENSION_POINT + " is not properly configured (wrong type)");
						throw new IllegalStateException("The extension point " + SERVER_CONTRIBUTION_EXTENSION_POINT + " is not properly configured (wrong type)");
					}
				}
				logger.info("The server contributions are configured");
			} catch (CoreException e) {
				throw new RuntimeException(e);
			}
		}

		return new AggregatorContribution(contributions);
	}

}
