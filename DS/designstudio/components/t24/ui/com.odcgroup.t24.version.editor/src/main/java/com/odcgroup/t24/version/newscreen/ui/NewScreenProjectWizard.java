package com.odcgroup.t24.version.newscreen.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ContainerGenerator;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.t24.version.repository.VersionRepository;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard;


public class NewScreenProjectWizard extends AbstractNewModelResourceCreationWizard implements INewWizard {
	
	Version version;
	private NewScreenWizardPage page;
	private IFile file;
	
	public NewScreenProjectWizard() {
		setWindowTitle("New Design Studio T24 Screen");
		
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		setNeedsProgressMonitor(true);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard#addPages()
	 */
	public void addPages() {
		version = VersionDSLFactoryImpl.eINSTANCE.createVersion();
		
		page = new NewScreenWizardPage(version, getSelectionFullPath(), getProject());
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		if (canFinish()) {
	        try {
	            IWorkspace workspace = ResourcesPlugin.getWorkspace();
	            String scrName = page.getScreenName();
	            if (scrName.isEmpty()) {
	            	scrName = "New Screen";
	            }
	            final IFile file = createNewFile(scrName+ ".version");        
	            
	            version.setShortName(page.getShortNametext());
	            VersionUtils.setVersionMetaModelVersion(version);
	            version.setRecordsPerPage("1");
	            version.setFieldsPerLine("MULTI");
	            version.setNumberOfAuthorisers(1);
				String t24Name = version.getT24Name();
				version.setT24Name(t24Name.concat(version.getShortName()).replace("_", "."));
                final XtextResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, file.getProject());
                
				associateAuditVersion(t24Name);

	            workspace.run(new IWorkspaceRunnable() {

	                public void run(IProgressMonitor monitor) throws CoreException {
	                    try {
	                        monitor.beginTask("Creating model", 3);
	                        URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
	                        Resource resource = rs.getResource(uri, true);
	                        resource.getContents().add((EObject) version);
	                        monitor.worked(1);
	                        doSave(resource, file, new SubProgressMonitor(monitor, 1));
	                        monitor.worked(1);
	                    } finally {
	                        monitor.done();
	                    }
	                }
	            }, file, IWorkspace.AVOID_UPDATE, null);
	            
	            file.refreshLocal(0, null);
	            if(com.odcgroup.t24.version.editor.utils.VersionUtils.isMoreVersionEditorOpen()){
					return true;
				}
	            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(file),"com.odcgroup.t24.version.VersionDSL");
	            
	            return true;
	        } catch (CoreException e) {
	        	// LOGGER
	        }
		}

        return false;
    }

	
	/**
	 * Select respective application audit version and associate with newly created version.
	 * @author manilkapoor
	 * @param rs
	 */
	private void associateAuditVersion(final String t24Name) {
		VersionRepository versionRepo = new VersionRepository();
		for (IEObjectDescription description : versionRepo.getAllVersions()) {
			if (description.getName().toString().equals(t24Name + "AUDIT")) {
				setAssociateVersion(description);
				break;
			} else if (description.getName().toString().contains("AUDIT")
					&& description.getName().toString().equals(t24Name.replace(".", "_").concat("AUDIT"))) {
				setAssociateVersion(description);
				break;
			}
		}
	}

	/**
	 * @param description
	 */
	private void setAssociateVersion(IEObjectDescription description) {
		Version associatedVersion = (Version) description.getEObjectOrProxy();
		version.getAssociatedVersions().add(associatedVersion);
	}
	
	/**
	 * @param resource
	 * @param file
	 * @param monitor
	 * @throws CoreException
	 */
	public static void doSave(Resource resource, IFile file,
			IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Saving model", 2);

		try {
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			resource.save(os, Collections.EMPTY_MAP);
			monitor.worked(1);

			if (file.exists()) {
				file.setContents(new ByteArrayInputStream(os.toByteArray()),
						false, true, new SubProgressMonitor(monitor, 1));
				monitor.worked(1);
			} else {
				file.create(new ByteArrayInputStream(os.toByteArray()), false,
						new SubProgressMonitor(monitor, 1));
				monitor.worked(1);
			}
		} catch (IOException e) {
		} finally {
			monitor.done();
		}
	}

	/**
	 * @param fileName
	 * @return
	 */
	private IFile createNewFile(String fileName) {

		if (file != null) {
			return file;
		}

		// create the new file 

		final IPath containerPath = new Path(getSelectionFullPath().toString());
		IPath newFilePath = containerPath.append(fileName);
		final IFile newFileHandle = createFileHandle(newFilePath);

		WorkspaceModifyOperation op = new WorkspaceModifyOperation(createRule(newFileHandle)) {
			protected void execute(IProgressMonitor monitor) throws CoreException {
				try {
					monitor.beginTask("Creating New Item", 2000);
					ContainerGenerator generator = new ContainerGenerator(containerPath);
					generator.generateContainer(new SubProgressMonitor(monitor, 1000));
					createFile(newFileHandle, new SubProgressMonitor(monitor, 1000));
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
	protected void createFile(IFile fileHandle, IProgressMonitor monitor) throws CoreException {
		InputStream contents = new ByteArrayInputStream(new byte[0]);

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

	private IFile createFileHandle(IPath filePath) {
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
}
