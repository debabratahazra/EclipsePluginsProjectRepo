package com.odcgroup.ocs.server.embedded.ui;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.BundleContext;

import com.odcgroup.ocs.server.embedded.model.IEmbeddedServer;
import com.odcgroup.ocs.server.embedded.util.EmbeddedServerClasspathHelper;
import com.odcgroup.ocs.server.embedded.util.EmbeddedServerManager;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.workbench.ui.AbstractUIActivator;

public class OCSServerUIEmbeddedCore extends AbstractUIActivator {

	M2EJobListener m2eJobListener;

	public static String PLUGIN_ID = "com.odcgroup.ocs.server.embedded.ui";

	public static OCSServerUIEmbeddedCore getDefault() {
		return (OCSServerUIEmbeddedCore) getDefault(OCSServerUIEmbeddedCore.class);
	}

	public IServerContributions getContributions() {
		return new EmbeddedServerContributions();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		m2eJobListener = new M2EJobListener();
		// Listen to m2eclipse update dependency job (to update the embedded server displayed in the Server View)
		Job.getJobManager().addJobChangeListener(m2eJobListener);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.AbstractUIActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		Job.getJobManager().removeJobChangeListener(m2eJobListener);
		m2eJobListener = null;
		super.stop(context);
	}

	private static final class M2EJobListener implements IJobChangeListener {
		@SuppressWarnings("restriction")
		@Override
		public void done(IJobChangeEvent event) {
			// We watch for the m2eclipse ProjectRegistryRefreshJob to finish.
			if (event.getJob() instanceof org.eclipse.m2e.core.internal.project.registry.ProjectRegistryRefreshJob){
				Job updateProjectListJob = new Job("Update project list of embedded servers") {
					@Override
					public IStatus run(
							IProgressMonitor monitor) {
						for (IEmbeddedServer server: EmbeddedServerManager.getInstance().getEmbeddedServers()) {
							// Project deployed to embedded server closed
							Set<IProject> referencedProjectsByContainer = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(server.getServerProject(), true, false);
							Set<IProject> referencedProjectsByBuildPath = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(server.getServerProject(), false, true);
							for (IProject project : referencedProjectsByContainer) {
								DSProject ocsProject = new DSProject(project);
								ocsProject.setLocked(referencedProjectsByContainer.contains(project));
								server.addDsProject(ocsProject);
							}
							for (IProject project : referencedProjectsByBuildPath) {
								DSProject ocsProject = new DSProject(project);
								ocsProject.setLocked(referencedProjectsByContainer.contains(project));
							}
						}
						return Status.OK_STATUS;
					}

				};
				updateProjectListJob.setPriority(Job.SHORT);
				updateProjectListJob.schedule();
			}
		}

		public void aboutToRun(IJobChangeEvent event) { }

		public void awake(IJobChangeEvent event) { }

		public void running(IJobChangeEvent event) { }

		public void scheduled(IJobChangeEvent event) { }

		public void sleeping(IJobChangeEvent event) { }
	}

}
