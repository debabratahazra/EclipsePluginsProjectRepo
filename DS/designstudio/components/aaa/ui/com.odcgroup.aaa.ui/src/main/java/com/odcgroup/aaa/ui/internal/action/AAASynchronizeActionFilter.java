package com.odcgroup.aaa.ui.internal.action;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IActionFilter;

import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author atr, Kai Kreuzer
 * @since 1.40.0
 */
public class AAASynchronizeActionFilter implements IActionFilter {

	/*
	 * @see org.eclipse.ui.IActionFilter#testAttribute(java.lang.Object, java.lang.String, java.lang.String)
	 */
	public boolean testAttribute(Object target, String name, String value) {
		if (target instanceof MdfClass && "format".equals(name)) {
			MdfClass clazz = (MdfClass) target;
			
			if (clazz instanceof EObject) {
				Resource resource = ((EObject)clazz).eResource();
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(resource);
				IOfsModelResource modelResource;
				try {
					modelResource = ofsProject.getOfsModelResource(resource.getURI());
					if(modelResource.hasScope(IOfsProject.SCOPE_PROJECT)) {
						Long id = AAAAspect.getTripleAFormatFunctionDictId(clazz);
						return id != null;
					}
				} catch (ModelNotFoundException e) {
					return false;
				}
			}
		}

		return false;
	}

}
