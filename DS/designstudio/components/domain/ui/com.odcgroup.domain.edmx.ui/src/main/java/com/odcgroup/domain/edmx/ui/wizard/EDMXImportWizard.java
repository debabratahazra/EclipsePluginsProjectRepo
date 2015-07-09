package com.odcgroup.domain.edmx.ui.wizard;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.xtext.resource.SaveOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.domain.edmx.EDMXImportException;
import com.odcgroup.domain.edmx.EDMXImporter;
import com.odcgroup.domain.edmx.ui.EDMXUICore;
import com.odcgroup.domain.edmx.ui.util.EDMXConstants;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;


/**
 * @author Ramya Veigas
 * @version 1.0
 */
public class EDMXImportWizard extends Wizard implements IImportWizard {	

	private static Logger LOGGER = LoggerFactory.getLogger(EDMXImportWizard.class);
	
	private EDMXPage edmxPage;
	
	private EDMXContainerSelectionPage selectionPage;
	
	private EDMXImporter importer;
	
	private String userName = "";
	
	private String password = "";
	
	private URL svcUrl;
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
	}

	public boolean performFinish() {

		final Shell shell = getShell();
		svcUrl = edmxPage.getSvcUrl();
		userName = edmxPage.getUsername().getText();
		password = edmxPage.getPassword().getText();

		final EDMXImporter importer = getImportModel();
		final boolean[] canceled = { false };
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			
			/* (non-Javadoc)
			 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
			 */
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				IWorkspaceRunnable wsop = new IWorkspaceRunnable() {
					public void run(IProgressMonitor monitor) throws CoreException {
						monitor.beginTask(EDMXUICore.getDefault().getString(EDMXConstants.EDMX_WIZARD_IMPORT_START), 10);
						monitor.worked(1);
						Collection<MdfDomainImpl> domainImpls;
						try {
							domainImpls = importer.fromURLImportEDMX(svcUrl, userName, password);
							saveEDMXFile(importer.getContainer(), domainImpls);
						} catch (EDMXImportException ex) {
							importer.getImportReport().error(ex);
						} catch (IOException e) {
							importer.getImportReport().error("Error Saving Domains:"+e.getMessage());
							LOGGER.error(e.getLocalizedMessage(), e);
						} finally {
							canceled[0] = monitor.isCanceled();
							monitor.done();
						}
					}
					
				};		

				try {
					ResourcesPlugin.getWorkspace().run(wsop, null, IWorkspace.AVOID_UPDATE, monitor);
				} catch (CoreException ex) {
					MessageDialog.openError(shell, "ERROR", ex.getMessage());
				}
				
			}

		};
		
		try {
			new ResizableProgressMonitorDialog(shell).run(true, true, op);
			if (!canceled[0]) {
				TitleAreaDialog dlg = new DomainImportReportDialog(getShell(), 
						importer.getImportReport(), 
						importer.getModelName());
				if (dlg != null) {
					dlg.open();
				}
			}
		} catch (InvocationTargetException ex) {
			MessageDialog.openError(shell, "ERROR", ex.getMessage());
		} catch (InterruptedException ex) {
			MessageDialog.openError(shell, "ERROR", ex.getMessage());
		} finally {
		}

		return true;
	}
	
	private void saveEDMXFile(IContainer container,
			Collection<MdfDomainImpl> domainImpls) throws IOException {
		IFile outputFile;
		for (MdfDomainImpl domain : domainImpls) {
			outputFile = container.getFile(new Path(domain.getName() + ".domain"));
			
			IOfsProject ofsProject = OfsCore.getOfsProject(outputFile.getProject());
	        if(!outputFile.exists()) {
	    		ofsProject.getModelResourceSet().createOfsModelResource(outputFile, IOfsProject.SCOPE_PROJECT);
	        }
	        Resource resource = ofsProject.getModelResourceSet().getResource(ModelURIConverter.createModelURI((IResource)outputFile), false);
	        resource.getContents().clear();
	        resource.getContents().add(domain);
	        
	        Map<Object, Object> options = SaveOptions.newBuilder().format().noValidation().getOptions().toOptionsMap();
	        resource.save(options);
		}
		
	}
	
	@Override
	public void addPages() {
		edmxPage = new EDMXPage();
		addPage(edmxPage);
		selectionPage = new EDMXContainerSelectionPage(importer.getContainerSelector());
		addPage(selectionPage);
	}
	
	/**
	 * 
	 */
	public EDMXImportWizard() {
		setModelImporter(createModelImporter());
		setWindowTitle(EDMXUICore.getDefault().getString(EDMXConstants.EDMX_WIZARD_WINDOW_TITLE));
	}
	
	protected EDMXImporter createModelImporter() {
		EDMXImporter edmxImporter = new EDMXImporter();
		edmxImporter.setModelName(EDMXUICore.getDefault().getString(EDMXConstants.EDMX_MODEL_NAME));
		return edmxImporter;
	}
	
	/**
	 * @param importer
	 */
	protected void setModelImporter(EDMXImporter importer) {
		this.importer = importer;
	}
	
	protected final EDMXImporter getImportModel() {
		return this.importer;
	}
	
	class ResizableProgressMonitorDialog extends ProgressMonitorDialog {
		protected boolean isResizable() {
			return true;
		}

		public ResizableProgressMonitorDialog(Shell parent) {
			super(parent);
		}
	}
}
