package com.odcgroup.workbench.compare.ui.team;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.ResourceNode;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.ui.team.AbstractTeamHandler;
import org.eclipse.emf.compare.util.EclipseModelUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.team.svn.core.operation.AbstractGetFileContentOperation;
import org.eclipse.team.svn.core.resource.ILocalResource;
import org.eclipse.team.svn.ui.compare.ResourceCompareInput.ResourceElement;

import com.odcgroup.workbench.compare.util.CompareUtils;
import com.odcgroup.workbench.compare.util.OfsModelCompareEditorInput;
import com.odcgroup.workbench.compare.util.OfsModelCompareEditorInput.StreamedModelElement;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.ui.OfsUIResourceHelper;

/**
 * Team Handler for Local revisions & SVN resources
 * 
 * @author pkk
 */
public class OfsTeamHandler extends AbstractTeamHandler {
	
	private boolean isLeftRemote = false;
	private boolean isRightRemote = false;

	public OfsTeamHandler() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.team.AbstractTeamHandler#loadResources(org.eclipse.compare.structuremergeviewer.ICompareInput)
	 */
	public boolean loadResources(ICompareInput input) throws IOException, CoreException {		

		final ITypedElement left = input.getLeft();
		final ITypedElement right = input.getRight();
		final ITypedElement ancestor = input.getAncestor();
		
		IOfsProject ofsProject = null;
		if (input instanceof OfsModelCompareEditorInput) {
			ofsProject = ((OfsModelCompareEditorInput) input).getOfsProject();
		} else if (left instanceof ResourceNode){
			IProject project = ((ResourceNode) left).getResource().getProject();
			ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
		} else if (left instanceof ResourceElement) {
			ResourceElement res = (ResourceElement) left;
			ILocalResource localResource = res.getLocalResource();
			IProject project = (localResource != null)? localResource.getResource().getProject() : null;			
			ofsProject =  (project != null) ? OfsCore.getOfsProjectManager().getOfsProject(project) : null;
			if (ofsProject == null) {
				ofsProject = OfsUIResourceHelper.getOfsProject(res.getRepositoryResource().getUrl());
			}
		}

		ResourceSet leftResourceSet = new ResourceSetImpl();
		ResourceSet rightResourceSet = new ResourceSetImpl();
		ResourceSet ancestorResourceSet = new ResourceSetImpl();
		
		if (ofsProject != null) {
			URIConverter uriConverter = new ModelURIConverter(ofsProject);
			leftResourceSet.setURIConverter(uriConverter);
			rightResourceSet.setURIConverter(uriConverter);
		}
				
		if (left instanceof ResourceNode && right instanceof IStreamContentAccessor) {
			// File Revision / History Element, compare with local history
			leftResource = CompareUtils.fetchModelRoot(((ResourceNode) left).getContents(),
					left.getName(), leftResourceSet).eResource();
			rightResource = CompareUtils.fetchModelRoot(((IStreamContentAccessor) right).getContents(),
					right.getName(), rightResourceSet).eResource();
			isRightRemote = true;
			if (ancestor != null) {
				ancestorResource = getAncestorResource(ancestor, ancestorResourceSet);
			}
			return true;
		} else if (left instanceof ResourceElement && right instanceof ResourceElement) {	
			// SVN compare
			ResourceElement leftElement = (ResourceElement) left;
			ResourceElement rightElement = (ResourceElement) right;
			
			// DS-4726: we must fetch the contents first, before we can access it
			InputStream leftContents = fetchContents(leftElement);
			InputStream rightContents = fetchContents(rightElement);
			
			if(leftContents!=null) {
				EObject leftObj = CompareUtils.fetchModelRoot(leftContents, left.getName(), leftResourceSet);
				leftResource = leftObj.eResource();
			} else {
				ResourceSet rs = new ResourceSetImpl();
				leftResource = new ResourceImpl(URI.createURI("n/a"));
				rs.getResources().add(leftResource);
			}
			if(rightContents!=null) {
				EObject rightObj = CompareUtils.fetchModelRoot(rightContents, right.getName(), rightResourceSet);
				rightResource = rightObj.eResource();
				isRightRemote = true;
			} else {
				ResourceSet rs = new ResourceSetImpl();
				rightResource = new ResourceImpl(URI.createURI("n/a"));
				rs.getResources().add(rightResource);
			}
			if (ancestor != null) {
				ancestorResource = getAncestorResource(ancestor, ancestorResourceSet);
			}
			return true;
		} else if (left instanceof StreamedModelElement && right instanceof StreamedModelElement) {
			// comparison of two standard resources
			leftResource = CompareUtils.fetchModelRoot(((StreamedModelElement)left).getContents(), left.getName(),
					leftResourceSet).eResource();			
			rightResource = CompareUtils.fetchModelRoot(((StreamedModelElement)right).getContents(), right.getName(),
					rightResourceSet).eResource();
			isRightRemote = true;
			if (ancestor != null) {
				ancestorResource = getAncestorResource(ancestor, ancestorResourceSet);
			}
			return true;
		} 
		return false;
	}
	
	private InputStream fetchContents(ResourceElement element) throws CoreException {
		AbstractGetFileContentOperation fetcher = element.getFetcher();
		if(fetcher!=null) {
			fetcher.run(new NullProgressMonitor());
			if(!fetcher.getStatus().isOK()) throw new CoreException(fetcher.getStatus());
		}
		return element.getContents();
	}


	/**
	 * @param ancestor
	 * @return
	 * @throws IOException
	 * @throws CoreException
	 */
	private Resource getAncestorResource(ITypedElement ancestor, ResourceSet resourceSet) throws IOException, CoreException {
		if (ancestor == null) {
			return null;
		}
		Resource ancestorRes = null;
		if (ancestor instanceof ResourceNode) {
			ancestorRes = EclipseModelUtils.load(
					((ResourceNode)ancestor).getResource().getFullPath(), resourceSet)
					.eResource();
		} else if (ancestor instanceof StreamedModelElement) {
			ancestorRes = CompareUtils.fetchModelRoot(((StreamedModelElement)ancestor).getContents(), ancestor.getName(),
					resourceSet).eResource();	
		}
		return ancestorRes;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.team.AbstractTeamHandler#isLeftRemote()
	 */
	public boolean isLeftRemote() {
		return this.isLeftRemote;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.team.AbstractTeamHandler#isRightRemote()
	 */
	public boolean isRightRemote() {
		return this.isRightRemote;
	}


}
