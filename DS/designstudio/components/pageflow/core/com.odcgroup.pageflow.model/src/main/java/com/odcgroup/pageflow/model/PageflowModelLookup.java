package com.odcgroup.pageflow.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;


/**
 *
 * @author pkk
 *
 */
public class PageflowModelLookup extends ModelResourceLookup {

    public static final String[] EXTENSIONS = { Activator.MODEL_NAME };

    /**
     * @param ofsProject
     */
    public PageflowModelLookup(IOfsProject ofsProject) {
        super(ofsProject, EXTENSIONS);
    }

	/**
	 * @return
	 */
	public List<Pageflow> getAllPageflows() {
		EClass eClass = PageflowPackage.eINSTANCE.getPageflow();
    	Set<IOfsModelResource> mResources = getAllOfsModelResources();
    	List<Pageflow> pageflows = new ArrayList<Pageflow>();
    	try {
	    	for (IOfsModelResource mResource : mResources) {    		
	    		List<EObject> contents = mResource.getEMFModel();
	    		for (EObject eObject : contents) {
					if (eClass.isInstance(eObject)) {
						pageflows.add((Pageflow) eObject); 
					}
				}
			}
    	} catch (Exception e) {
			Activator.getDefault(Activator.class).logError(e.getLocalizedMessage(), e);
		}
        return pageflows;
    }
}
