package com.odcgroup.t24.application.importer.ui.listener;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Injector;
import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.domain.ui.internal.DomainActivator;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.IServerContributions;
import com.odcgroup.server.ui.ServerUICore;
import com.odcgroup.t24.application.importer.internal.ApplicationImporter;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.common.importer.ui.Messages;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.ui.builder.listener.IT24DeployListener;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class LocalRefDeployReImportListener implements IT24DeployListener {

	@Override
	public void onSuccessfulDeploy(File deployedXmlFile) {
		MdfClassImpl baseClass = getBaseDomain(deployedXmlFile);
		ApplicationDetail application = getApplicationDetail(baseClass);
		IFile file = OfsResourceHelper.getFile(baseClass.eResource());
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IContainer destinationContainer = workspace.getRoot().getFolder(file.getFullPath());
		reimportApplication(application, destinationContainer.getParent());
	}

	private void reimportApplication(final ApplicationDetail application, final IContainer container) {		
		
		final WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			
			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException,
					InterruptedException {
				List<IExternalServer> servers = getExternalServers();
				if (servers.isEmpty()) {
					return;
				}
				Injector injector = DomainActivator.getInstance().getInjector(DomainActivator.COM_ODCGROUP_DOMAIN_DOMAIN);
				Shell shell = Display.getDefault().getActiveShell();
				final ApplicationImporter importer = new ApplicationImporter(injector);
				IWorkspace ws = ResourcesPlugin.getWorkspace();
				IWorkspaceDescription desc = ws.getDescription();
				try {
					// turn off auto-building
					desc.setAutoBuilding(false);
					ws.setDescription(desc);
					// import models
					importer.setContainer(container);
					importer.setServer((IExternalServer) servers.get(0));
					importer.setModelName(com.odcgroup.t24.application.importer.ui.Messages.ApplicationModel_name);
					importer.getSelectedModels().add(application);
					importer.importModels(monitor);					
										
				} catch (CoreException ex) {
					MessageDialog.openError(shell, Messages.ModelImportWizard_error, ex.getMessage());
				} catch (T24ServerException ex) {
					importer.getImportReport().error(ex);
				} finally {
					// turn on auto-building					
					desc.setAutoBuilding(true);
					ws.setDescription(desc);
					monitor.done();
				}				
			}
		};
		
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = Display.getDefault().getActiveShell();
				try {
					ISchedulingRule rule = null;
			        Job job = Job.getJobManager().currentJob();
			        if (job != null) {
			            rule = job.getRule();
			        } else {
			            rule = ResourcesPlugin.getWorkspace().getRoot();
			        }
					IRunnableContext context = new ProgressMonitorDialog(shell);
					PlatformUI.getWorkbench().getProgressService().runInUI(context, op, rule);
				} catch (InvocationTargetException ex) {
					Throwable realException = ex.getTargetException();
					MessageDialog.openError(shell, Messages.ModelImportWizard_error, realException.getMessage());
				} catch (InterruptedException ex) {
					MessageDialog.openInformation(shell, Messages.ModelImportWizard_cancelled, ex.getMessage());
				}
			}
		});

	}

	private ApplicationDetail getApplicationDetail(MdfClassImpl baseClass) {
		return new ApplicationDetail(T24Aspect.getT24Name(baseClass), baseClass.getParentDomain().getName(),
				T24Aspect.getModule(baseClass.getParentDomain()));
	}

	@SuppressWarnings("unchecked")
	private MdfClassImpl getBaseDomain(File deployedXmlFile) {
		MdfClassImpl baseClass = null;
		String name = deployedXmlFile.getName();
		if (name.startsWith("X_")) {
			String localRefApplicationName = name.replace(".xml", "");
			MdfDomainImpl localRefDomain = getMdfDomain(localRefApplicationName);
			if (localRefDomain != null) {
				List<MdfClass> klasses = localRefDomain.getClasses();
				for (MdfClass mdfClass : klasses) {
					if (mdfClass.getBaseClass() != null) {
						baseClass = (MdfClassImpl) mdfClass.getBaseClass();
					}
				}
			}
		}
		return baseClass;
	}

	private List<IExternalServer> getExternalServers() {
		IServerContributions contributions = ServerUICore.getDefault().getContributions();
		List<IExternalServer> servers = new ArrayList<IExternalServer>();
		for (IDSServer server : contributions.getServers()) {
			if (server instanceof IExternalServer) {
				servers.add((IExternalServer) server);
			}
		}
		return servers;
	}
	
	private MdfDomainImpl getMdfDomain(String mdfDomainName) {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		MdfDomainImpl mdfDomain = null;
		for (IProject project : projects) {
			if (project.getName().contains("-gen") || project.getName().contains("-server")) {
				continue;
			}
			if (project.getName().contains("models")) {
				MdfDomainImpl domain = (MdfDomainImpl) DomainRepositoryUtil.getMdfDomain(
						QualifiedName.create(mdfDomainName), project);
				if (domain != null) {
					mdfDomain = domain;
					break;
				}
			}
		}
		return mdfDomain;
	}
}
