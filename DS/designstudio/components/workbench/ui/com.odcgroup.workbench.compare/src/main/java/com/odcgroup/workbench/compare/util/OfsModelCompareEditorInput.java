package com.odcgroup.workbench.compare.util;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.ResourceNode;
import org.eclipse.compare.internal.CompareUIPlugin;
import org.eclipse.compare.internal.Utilities;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * editorinput to compare custo models with standard (streamed) models
 * 
 * @author pkk
 *
 */
public class OfsModelCompareEditorInput extends CompareEditorInput {
	
	private final IOfsModelResource leftResource;
	private final IOfsModelResource rightResource;
	
	private CompareConfiguration config;
	
	private IOfsProject ofsProject = null;

	/**
	 * @param customResource
	 * @param standardResource
	 */
	public OfsModelCompareEditorInput(IOfsProject ofsProject, IOfsModelResource leftResource, IOfsModelResource rightResource) {
		super(new CompareConfiguration());
		this.ofsProject = ofsProject;
		this.leftResource = leftResource;
		this.rightResource = rightResource;
		initializeCompareConfiguration();
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.compare.CompareEditorInput#prepareInput(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings("restriction")
	protected Object prepareInput(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		String format= Utilities.getString("ResourceCompare.twoWay.title"); //$NON-NLS-1$
		String leftLabel = getLabel(leftResource);
		String rightLabel = getLabel(rightResource);
		Object[] labels = new String[] {leftLabel, rightLabel};
		setTitle(MessageFormat.format(format, labels));		
		ICompareInput input = fetchCompareInput();		
		return input;
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("restriction")
	void initializeCompareConfiguration() {
		config = super.getCompareConfiguration();		
		config.setLeftLabel(getLabel(leftResource));
		config.setRightLabel(getLabel(rightResource));
		IResource resource = null;
		if (leftResource.getResource() != null) {
			resource = leftResource.getResource();
		} else if (rightResource.getResource() != null) {
			resource = rightResource.getResource();
		}
		config.setLeftImage(CompareUIPlugin.getImage(resource));
		config.setRightImage(CompareUIPlugin.getImage(resource));		
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.compare.CompareEditorInput#getCompareConfiguration()
	 */
	public CompareConfiguration getCompareConfiguration() {
		return config;
	}

	/**
	 * @return
	 */
	public IOfsProject getOfsProject() {
		return ofsProject;
	}


	/**
	 * @return
	 */
	private ICompareInput fetchCompareInput() {
		ITypedElement left = null;
		ITypedElement right = null;
		if (leftResource.getResource() == null && rightResource.getResource() == null) {
			left = new StreamedModelElement(leftResource);
			right = new StreamedModelElement(rightResource);
		} else if (leftResource.getResource() != null && rightResource.getResource() == null){
			left = new ResourceNode(leftResource.getResource());
			right = new StreamedModelElement(rightResource);
		} else if (leftResource.getResource() == null && rightResource.getResource() != null) {
			left = new ResourceNode(rightResource.getResource());
			right = new StreamedModelElement(leftResource);			
		}
		if (left == null || right == null) {
			return null;
		}
		return new OfsModelCompareInput(ofsProject, left, right);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.compare.CompareEditorInput#getToolTipText()
	 */
	@SuppressWarnings("restriction")
	public String getToolTipText() {
		if (leftResource != null && rightResource != null) {
			String leftLabel = getLabel(leftResource);
			String rightLabel = getLabel(rightResource);				
			String format = Utilities.getString("ResourceCompare.twoWay.tooltip"); //$NON-NLS-1$
			Object[] labels = new String[] {leftLabel, rightLabel};
			return MessageFormat.format(format, labels);
		}
		// fall back
		return super.getToolTipText();
	}	
	
	private static String getLabel(IOfsModelResource modelResource) {
		String name = modelResource.getName();
		if(modelResource.isReadOnly()) name += " (r/o)";
		return name;
	}
	
	/**
	 * TODO: Document me!
	 *
	 * @author pkk
	 *
	 */
	public class StreamedModelElement implements ITypedElement, IStreamContentAccessor {
		
		final IOfsModelResource mResource;
		
		public StreamedModelElement(IOfsModelResource mResource) {
			this.mResource = mResource;
		}

		
		@SuppressWarnings("restriction")
		public Image getImage() {
			return CompareUIPlugin.getImage(mResource);
		}

		
		public String getName() {
			return mResource.getName();
		}

		
		public String getType() {
			return mResource.getFullPath().getFileExtension();
		}

		
		public InputStream getContents() throws CoreException {
			return mResource.getContents();
		}
		
	}
	
}
