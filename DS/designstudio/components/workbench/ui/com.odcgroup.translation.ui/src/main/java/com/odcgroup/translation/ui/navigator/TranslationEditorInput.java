package com.odcgroup.translation.ui.navigator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.odcgroup.workbench.core.IOfsProject;

/**
 * editorInput for the TranslationEditor
 * 
 * @author pkk
 */
public class TranslationEditorInput implements IEditorInput {
	
	private IOfsProject ofsProject;

	public TranslationEditorInput(IOfsProject ofsProject) {
		this.ofsProject = ofsProject;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		if (ofsProject == null) {
			return false;
		}
		return ofsProject.getProject().exists();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		String name = "Translations ["+ofsProject.getName()+"]";
		return name;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		String toolTip = "Translations for project \'"+ofsProject.getName()+"\'";
		return toolTip;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings({ "rawtypes" })
	public Object getAdapter(Class adapter) {
		return null;
	}
	
	/**
	 * @return the ofsProject
	 */
	public IOfsProject getOfsProject() {
		return ofsProject;
	}
	
}