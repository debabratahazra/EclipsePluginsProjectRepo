package com.odcgroup.pageflow.editor.diagram.custom.properties.filter;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.jface.viewers.IFilter;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.GenerationCore;

public abstract class AbstractPropertyFilter implements IFilter {

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public final boolean select(Object toTest) {
		EObject eObject = fetchEObject(toTest);
		if (eObject == null){
			return false;
		} else {
			return enableFor(eObject);
		}
	}
	
	/**
	 * @param toTest
	 * @return
	 */
	public abstract EObject fetchEObject(Object toTest);
	
	/**
	 * @param eObject
	 * @return
	 */
	private boolean enableFor(EObject eObject){
		if (eObject == null){
			return false;
		}
		if (eObject.eIsProxy()){			
			eObject = EMFCoreUtil.resolve(GMFEditingDomainFactory.INSTANCE.createEditingDomain(), eObject);
		}
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(eObject.eResource());
		//check for referenced resources
		if (ofsProject != null) {			
			IProject project = ofsProject.getProject();	
			if(!GenerationCore.isTechnical(project)){ // project type is non technical
				return false;
			}else { // project type is technical
				return true;
			}	
		}
		return false;
	}

}
