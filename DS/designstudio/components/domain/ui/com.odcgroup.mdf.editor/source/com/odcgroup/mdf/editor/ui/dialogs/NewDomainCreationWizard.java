package com.odcgroup.mdf.editor.ui.dialogs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ContainerGenerator;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.XtextProjectHelper;

import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelChangedEvent;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.AbstractDomainCreationPage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.DomainCreationPageFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.resources.OfsModelResource;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard;

/**
 * @author pkk
 * 
 * a simplified domain creation wizard
 */
public class NewDomainCreationWizard extends AbstractNewModelResourceCreationWizard {
	
	protected AbstractDomainCreationPage filePage = null;
	protected IFile file;
    private static final Logger LOGGER = Logger.getLogger(NewDomainCreationWizard.class);
    private MdfDomain domain;
    private boolean copyWizard=false;
    private OfsModelResource resource=null;
    
	
    public NewDomainCreationWizard(){
    	
    }
    public NewDomainCreationWizard(OfsModelResource resource,boolean copy ){
    	this.copyWizard=copy;
    	this.resource=resource;
    }
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		model = "domain";
		super.init(workbench, currentSelection);
		if(!copyWizard){
		domain = MdfFactory.eINSTANCE.createMdfDomain();
		((MdfDomainImpl)domain).setName("NewDomain");
		((MdfDomainImpl)domain).setMetamodelVersion(OfsCore.getCurrentMetaModelVersion("domain"));
	    }else{
	      EList<EObject> modellist=null;
			try{
				modellist=(resource).getEMFModel();
			}catch(Exception e){
			}
		  if(modellist!=null && !(modellist.isEmpty())){
			  domain=(MdfDomain)EcoreUtil.copy( modellist.get(0));	  
			  setWindowTitle("Copy Domain");
	  }
	    } 
	}

	/**
	 * @generated
	 */
	public void addPages() {
		super.addPages();
		if (filePage == null) {
			if (containerFullPath == null) {
				containerFullPath = getSelectionFullPath();
			}
			filePage = DomainCreationPageFactory.getCreationPage("domainNewFileCreation", getWorkbench(),
					containerFullPath, domain,copyWizard,resource);
		}
		addPage(filePage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		if (canFinish()) {
	        try {
	            IWorkspace workspace = MdfPlugin.getWorkspace();
	            final IFile file = createNewFile(filePage.getFileName());

	            workspace.run(new IWorkspaceRunnable() {

	                public void run(IProgressMonitor monitor) throws CoreException {
	                    try {
	                        monitor.beginTask("Creating model", 3);
	                        
	                        XtextResourceSet resourceSet = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, file.getProject());
							URI uri = URI.createPlatformResourceURI(file.getFullPath().toOSString());
							Resource resource = resourceSet.getResource(uri, true);
							
	                        resource.getContents().add((EObject) domain);
	                        monitor.worked(1);

	                        MdfUtility.doSave(resource, file,
	                                new SubProgressMonitor(monitor, 1));
	                        monitor.worked(1);

	                        MdfProjectRepository.fireModelChangedEvent(this, domain,
	                                ModelChangedEvent.ELEMENT_ADDED,
	                                new SubProgressMonitor(monitor, 1));
	                        monitor.worked(1);
	                    } finally {
	                        monitor.done();
	                    }
	                }
	            }, file, IWorkspace.AVOID_UPDATE, null);
	            
	            addXtextProjectNature(file);
	            openEditor(file);
	            return true;
	        } catch (CoreException e) {
	            LOGGER.error(e, e);
	            MdfCore.openError(getShell(), e);
	        }
		}

        return false;
    }
	
	private void addXtextProjectNature(IFile iFile) {
		try {
			//Adding XText nature in Project
			IProject project = iFile.getProject();
			IProjectDescription desc = project.getDescription();
			String[] oldNatureIds = desc.getNatureIds();
			for (String nature : oldNatureIds) {
				if(nature.equals(XtextProjectHelper.NATURE_ID))
					return; // Ignore to add xtext nature
			}
			String [] natureIds = new String[oldNatureIds.length + 1];
			System.arraycopy(oldNatureIds, 0, natureIds, 0, oldNatureIds.length);
			natureIds[oldNatureIds.length] = XtextProjectHelper.NATURE_ID;
			desc.setNatureIds(natureIds);
			project.setDescription(desc, null);
		} catch (Exception e) {
			LOGGER.warn(e, e);
		}
	}
	/**
	 * @param fileName
	 * @return
	 */
	public IFile createNewFile(String fileName) {
		if (file != null) {
			return file;
		}

		// create the new file and cache it if successful

		final IPath containerPath = containerFullPath;
		IPath newFilePath = containerPath.append(fileName);
		final IFile newFileHandle = createFileHandle(newFilePath);
		final InputStream initialContents = null;

		WorkspaceModifyOperation op = new WorkspaceModifyOperation(createRule(newFileHandle)) {
			protected void execute(IProgressMonitor monitor) throws CoreException {
				try {
					monitor.beginTask("Creating New Item", 2000);
					ContainerGenerator generator = new ContainerGenerator(containerPath);
					generator.generateContainer(new SubProgressMonitor(monitor, 1000));
					createFile(newFileHandle, initialContents, new SubProgressMonitor(monitor, 1000));
				} finally {
					monitor.done();
				}
			}
		};

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(getContainer().getShell(), // Was Utilities.getFocusShell()
						"New Item Error", null, // no special message
						((CoreException) e.getTargetException()).getStatus());
			} else {
				// CoreExceptions are handled above, but unexpected runtime exceptions and errors may still occur.
				MessageDialog.openError(getContainer().getShell(), "New Item Error",
						"Error creating / opening the file: " + e.getTargetException().getMessage());
			}
			return null;
		}

		file = newFileHandle;

		return file;
	}
	
	/**
	 * @param fileHandle
	 * @param contents
	 * @param monitor
	 * @throws CoreException
	 */
	protected void createFile(IFile fileHandle, InputStream contents, IProgressMonitor monitor) throws CoreException {
		if (contents == null) {
			contents = new ByteArrayInputStream(new byte[0]);
		}

		try {
			IPath path = fileHandle.getFullPath();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			int numSegments = path.segmentCount();
			if (numSegments > 2 && !root.getFolder(path.removeLastSegments(1)).exists()) {
				// If the direct parent of the path doesn't exist, try to create the
				// necessary directories.
				for (int i = numSegments - 2; i > 0; i--) {
					IFolder folder = root.getFolder(path.removeLastSegments(i));
					if (!folder.exists()) {
						folder.create(false, true, monitor);
					}
				}
			}
			fileHandle.create(contents, false, monitor);
		} catch (CoreException e) {
			// If the file already existed locally, just refresh to get contents
			if (e.getStatus().getCode() == IResourceStatus.PATH_OCCUPIED) {
				fileHandle.refreshLocal(IResource.DEPTH_ZERO, null);
			} else {
				throw e;
			}
		}

		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}
	
	/**
	 * @param filePath
	 * @return
	 */
	protected IFile createFileHandle(IPath filePath) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
	}
	
	/**
	 * @param resource
	 * @return
	 */
	protected ISchedulingRule createRule(IResource resource) {
		IResource parent = resource.getParent();
		while (parent != null) {
			if (parent.exists()) {
				return resource.getWorkspace().getRuleFactory().createRule(resource);
			}
			resource = parent;
			parent = parent.getParent();
		}
		return resource.getWorkspace().getRoot();
	}

    /**
     * @param file
     * @throws PartInitException
     */
    private void openEditor(IFile file) throws PartInitException {
        IWorkbenchPage p = MdfPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();

        if (p != null) {
            p.openEditor(new FileEditorInput(file),
                    MdfCore.DOMAIN_MODEL_EDITOR_ID, true);
        }
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	public boolean canFinish() {
		return filePage.isPageComplete();
	}
	
	

}
