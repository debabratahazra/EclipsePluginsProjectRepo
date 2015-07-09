package com.odcgroup.page.ui.properties;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.command.UpdateIncludePropertyCommand;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;

/**
 * Specialized PropertySourceAdapter intrinsic Model property.
 * 
 * @author atr
 */
public class IncludePropertySourceAdapter extends AbstractPropertySourceAdapter {
	
    /**
     * Creates a new DomainAttributePropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public IncludePropertySourceAdapter(Property property, CommandStack commandStack) {
        super(property, commandStack);
    }
    
    /**
     * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#getPropertyValue()
     */
    public Object getPropertyValue() {
    	Model model = getProperty().getModel();
    	String uri = "";
    	if (model != null) {
    		Resource res = model.eResource();
    		if (res != null) {
    			uri = res.getURI().trimFragment().toString();
    		} else {
    			// The included resource is not found. Check it has not been deleted
    			uri = EcoreUtil.getURI(model).trimFragment().toString();
    		}
    	}
    	return uri;
    }
    
    /**
     * Sets the value of the property.
     * 
     * @param newValue
     *            The value of the property
     *            
     * @see IPropertySource#setPropertyValue(Object, Object)
     */
    public void setPropertyValue(Object newValue) {
    	if (newValue instanceof IOfsModelResource) {
    		IOfsModelResource mResource = (IOfsModelResource) newValue;
    		try {
				List<EObject> objs = mResource.getEMFModel();
				if (!objs.isEmpty()) {
					EObject obj = objs.get(0);
					if (obj instanceof Model) {
						setPropertyValue((Model) obj);
					}
				}
			} catch (IOException e) {
	    		PageUIPlugin.getDefault().logError(e.getLocalizedMessage(), e);
			} catch (InvalidMetamodelVersionException e) {
	    		PageUIPlugin.getDefault().logError(e.getLocalizedMessage(), e);
			}
    	} else	if (newValue instanceof Model) {
    		// set the model
    		Model newModel = (Model)newValue;
    		setPropertyValue(newModel);
	    	
    	} else if (newValue instanceof String ){
    		throw new IllegalArgumentException("The new value must be a model instance");
    	}
    	
    }
    
    /**
     * @param newModel
     */
    private void setPropertyValue(Model newModel) {
    	Property p = getProperty();
    	if (newModel != p.getModel()) {
        	getCommandStack().execute(new UpdateIncludePropertyCommand(getProperty(), newModel));
    	}
    }
    
    /** 
     * @see com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter#isPropertySet()
     */
    public boolean isPropertySet() {
    	boolean isSet = getProperty().getModel() != null 
    				 && getProperty().getModel().eResource() != null; 
        return isSet;
    }

    /**
     * @see com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter#resetPropertyValue()
     */
    public void resetPropertyValue() {
    	setPropertyValue((Model)null);
    }        
}