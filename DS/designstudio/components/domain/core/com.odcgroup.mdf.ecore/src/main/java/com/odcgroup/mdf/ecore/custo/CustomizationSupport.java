package com.odcgroup.mdf.ecore.custo;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * Only CDM generators relies on Custom annotations
 * @author yan
 */
public class CustomizationSupport {

    private static final String CDM_WUI_MODELS = "cdm-wui-models";

	public static void setCustomElement(MdfModelElement model) {
        if (isCdmModel(model)) {
            CustoAspect.setCustomElement(model);
        }
    }
    
    public static void setCustomElement(MdfModelElement model, IProject project) {
        if (isCdmModel(project)) {
            CustoAspect.setCustomElement(model);
        }
    }

    public static boolean isCdmModel(MdfModelElement model) {
    	try {
    		// avoid the new child descriptors needed for contextual menu 
    		//(MdfModelElementItemProvider#collectNewChildDescriptors(Collection,Object))
    		if (model instanceof EObject && ((EObject)model).eContainer() != null) {
        		Resource resource = ((EObject)model).eResource();
    	        URIConverter converter = resource.getResourceSet().getURIConverter();
    	        if(converter instanceof ModelURIConverter) {
    	        	ModelURIConverter modelConverter = (ModelURIConverter) converter;
    	        	IProject project = modelConverter.getOfsProject().getProject();
    	        	if(project != null && isCdmModel(project)) {
    	        		return true;
    	        	}
    	        }    			
    		}
    	} catch(Exception e) {
    		OfsCore.getDefault().logWarning("Cannot determine project for MdfModelElement " + model.getName(), null);
    	}
        return false;
    }
    
    /**
     * @param project
     * @return
     */
    private static boolean isCdmModel(IProject project) {
    	if(project != null && project.getName().equals(CDM_WUI_MODELS)) {
    		return true;
    	}
    	return false;
    }
}
