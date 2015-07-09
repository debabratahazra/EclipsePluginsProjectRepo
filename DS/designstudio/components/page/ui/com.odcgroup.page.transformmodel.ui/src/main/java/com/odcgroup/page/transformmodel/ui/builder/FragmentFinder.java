package com.odcgroup.page.transformmodel.ui.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelPackage;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.ui.PageTransformModelUICore;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;

/**
 * Finds the fragment for the spec
 * 
 * @author atr
 */
public class FragmentFinder {
	
	/** The OFS project. */
	private IOfsProject ofsProject;

	/**
	 * Creates the FragmentRegistry for the specified project.
	 * 
	 * @param ofsProject
	 *            The IOfsProject to create the registry for
	 */
	public FragmentFinder(IOfsProject ofsProject) {
		Assert.isNotNull(ofsProject);
		
		this.ofsProject = ofsProject;
	}
	
	/**
	 * Finds the default fragment for the specified Dataset and Cardinality.
	 * 
	 * @param qName The domain qualified name of a Dataset
	 * @param cardinality The Cardinality (see PropertyTypeConstants.CARDINALITY_ONE and PropertyTypeConstants.CARDINALITY_MANY)
	 * @return Model The model
	 */
	public Model findFragment(MdfName qName, String cardinality) {
    	List<Model> models = getAllFragments();
    	for (Model m : models) {
    		if (isDefaultFragment(m, qName, cardinality)) {
    			return m;
    		}
    	}
    	
    	// Not found
    	return null;
	}
	
	/**
	 * @return
	 */
	private List<Model> getAllFragments() {
		EClass eClass = ModelPackage.eINSTANCE.getModel();
    	ModelResourceLookup mml = new ModelResourceLookup(ofsProject, new String[]{PageConstants.FRAGMENT_FILE_EXTENSION});
    	Set<IOfsModelResource> mResources = mml.getAllOfsModelResources();
    	List<Model> pagemodels = new ArrayList<Model>();
    	try {
	    	for (IOfsModelResource mResource : mResources) {    		
	    		List<EObject> contents = mResource.getEMFModel();
	    		if (!contents.isEmpty() && eClass.isInstance(contents.get(0))){
					pagemodels.add((Model) contents.get(0)); 
				}
			}
    	} catch (Exception e) {
    		PageTransformModelUICore.getDefault().logError(e.getLocalizedMessage(), e);
		}
        return pagemodels;
	}

	/**
	 * Creates the mapping between the Domain Entities / Datasets and the
	 * default fragments which they are associated with.
	 * 
	 * @param model The Model
	 * @param qName The qualified name of a Dataset 
	 * @param cardinality The Cardinality (see PropertyTypeConstants.CARDINALITY_ONE and PropertyTypeConstants.CARDINALITY_MANY)
	 * @return boolean True if this is the default fragment
	 */
	private boolean isDefaultFragment(Model model, MdfName qName, String cardinality) {

		boolean isDefault = false;
		
		if (model != null) {

			Widget rw = model.getWidget();
	
			String ev = rw.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);	
			if (qName.getQualifiedName().equals(ev)) {
	
				String dv = rw.getPropertyValue(PropertyTypeConstants.DEFAULT_FRAGMENT);	
				if (Boolean.valueOf(dv)) {
				
					String cv = rw.getPropertyValue(PropertyTypeConstants.CARDINALITY);
					isDefault = cardinality.equals(cv);
				}
			}
		}
		
		return isDefault;
	}
}