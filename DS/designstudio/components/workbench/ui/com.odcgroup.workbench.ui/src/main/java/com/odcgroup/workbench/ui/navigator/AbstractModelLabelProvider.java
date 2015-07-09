package com.odcgroup.workbench.ui.navigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.workbench.core.helper.StringHelper;

/**  
 * This class provides an easy means to implement a content extension for standard OFS
 * model resources. It automatically provides the EMF model images and names.
 * These can be changed by the implementing subclass.
 * 
 * @author Kai Kreuzer, Odyssey
 *
 */
abstract public class AbstractModelLabelProvider extends AdapterFactoryLabelProvider {

	/** The name of the model that this content provider should handle */
	  private final String MODEL;
	
	/**
	 * Create the ModelLabelProvider instance.
	 * 
	 * @param factory an EMF adapter factory that provides adapters for image providers, etc.
	 * @param model the name of the model that this content provider should handle
	 */
	  public AbstractModelLabelProvider(AdapterFactory factory, String model) {
		    super(factory);
		    this.MODEL = model;
	  }

	  /**
	   * Returns the platform icon for a file. You can replace with your own icon
	   * If not a IFile, then passes to the regular EMF.Edit providers 
	   */
	  public Image getImage(Object object) {
	    if (object instanceof IFile)
	      return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
	    return super.getImage(object);
	  }

	  /**
	   * If the object is a file with the right extension for this model,
	   * the file name is is capitalized and returned without its extension.
	   * If the object is an EMF model element 
	   */
	  public String getText(Object object) {
	    if (object instanceof IFile) {
	    	IFile file = (IFile) object;
	    	if(file.getFileExtension().equals(MODEL)) {
	    		return StringHelper.withoutExtension(file.getName());
	    	} else {
	    		return file.getName();
	    	}
	    }	    	
	    return super.getText(object);
	  }
}
