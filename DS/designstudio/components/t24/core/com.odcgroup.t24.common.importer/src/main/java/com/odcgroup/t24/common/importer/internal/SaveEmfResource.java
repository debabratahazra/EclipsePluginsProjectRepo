package com.odcgroup.t24.common.importer.internal;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.workbench.dsl.xml.EIO;

public class SaveEmfResource<T extends IModelDetail> implements IImportingStep<T> {

	private IImportModelReport report;
	private EIO eio;
	private ResourceSet resourceSet;
	private IFolder rootFolder;
	
	private String getMessage(T model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Saving Resource"); //$NON-NLS-1$
		return b.toString();
	}
	
    private void createParent(IContainer parent) throws CoreException {
        if (!parent.exists()) {
            createParent(parent.getParent());
            if (parent instanceof IFolder) {
                ((IFolder) parent).create(true, false, new NullProgressMonitor());
            }
        }
    }

	
	protected final IFolder getRootFolder() {
		return this.rootFolder;
	}
	
	
	// default implementation: the model is save in the root folder.
	protected IFolder getDestinationFolder(T model) {
		return getRootFolder();
	}

	@Override
	public boolean execute(T model, IProgressMonitor monitor) {
		boolean success = true;
		if (monitor != null) {
			monitor.subTask(getMessage(model));
		}
		EObject eObj = model.getModel();
		if (eObj != null) {
			
			IFolder destinationFolder = getDestinationFolder(model);
			try {
				createParent(destinationFolder);
			} catch (CoreException ex) {
				report.error(model, ex);
				return false;
			}
			
			String destinationPathName = model.getFullModelName(destinationFolder);
			URI uri = null;
			try {
				uri = URI.createPlatformResourceURI(destinationPathName, true);
				eio.cloneAndSave(uri, eObj);
			} catch (Exception ex) {
				success = false;
				report.error(model, ex);
				if (uri != null) {
					// delete the resource when its size is zero.
					try {
						Resource resource = resourceSet.getResource(uri, true);
						if (resource !=null && resource.getContents().isEmpty()) {
							resource.delete(null);
							uri = null;
						}
					} catch (Exception ex2) {
						report.error(model, ex2); //$NON-NLS-1$
						// ignore 
					}
				}
			}
			if(uri != null){
				model.setResource(resourceSet.getResource(uri, true));
			}
		} else {
			success = false;
			report.error(model,"Unable to save the model."); //$NON-NLS-1$
		}
		return success;
	}

	public SaveEmfResource(IImportModelReport report, EIO eio, ResourceSet resourceSet, IFolder rootFolder) {
		this.report = report;
		this.eio = eio;
		this.resourceSet = resourceSet;
		this.rootFolder = rootFolder;
	}

}
