package com.odcgroup.mdf.editor.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class MdfModelEditorInput implements IEditorInput {
	private final MdfModelElement model;

    /**
     * Constructor for MdfEditorInput
     */
    public MdfModelEditorInput(MdfModelElement model) {
        super();
        this.model = model;
    }

    /**
     * @see org.eclipse.ui.IEditorInput#exists()
     */
    public boolean exists() {
        return (model != null);
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
     */
    public ImageDescriptor getImageDescriptor() {
        return MdfPlugin.getDefault().getImageRegistry().getDescriptor(MdfCore.ICON_DOMAIN);
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getName()
     */
    public String getName() {
        return (model == null ? null : model.getName());
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getPersistable()
     */
    public IPersistableElement getPersistable() {
        return null;
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getToolTipText()
     */
    public String getToolTipText() {
    	String tooltip = (model == null) ? null : model.getDocumentation();
    	
    	if (null == tooltip) {
    		// Never return null because of sanity checks of Eclipse
    		tooltip = "";
    	}
        
		return tooltip;
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class adapter) {
        return null;
    }

	public MdfModelElement getModel() {
		return model;
	}

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
    	if (obj instanceof MdfModelEditorInput) {
    		MdfModelEditorInput other = (MdfModelEditorInput) obj;
    		if (model != null) {
	    		return model.getQualifiedName().equals(other.model.getQualifiedName());
    		} else {
    			return (other.model == null);
    		}
    	}
    	
        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return (model == null ? 0 : model.hashCode());
    }

}
