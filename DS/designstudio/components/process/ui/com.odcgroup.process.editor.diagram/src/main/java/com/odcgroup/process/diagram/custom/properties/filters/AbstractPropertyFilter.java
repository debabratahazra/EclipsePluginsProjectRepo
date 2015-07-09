package com.odcgroup.process.diagram.custom.properties.filters;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.jface.viewers.IFilter;

import com.odcgroup.workbench.editors.ui.ResourceUtil;
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
		IFile processFile = ResourceUtil.getFile(eObject);
		if (processFile != null) {
			if(!processFile.exists()) return false; // process is packaged in a jar
			IProject project = processFile.getProject();	
			if(!GenerationCore.isTechnical(project)){ // project type is non technical
				return false;
			}else { // project type is technical
				return true;
			}	
		}
		return false;
	}

}
