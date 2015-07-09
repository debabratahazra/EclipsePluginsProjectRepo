package com.odcgroup.process.diagram.custom.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.workbench.editors.properties.AbstractTabbedPropertiesSection;

public abstract class ProcessTabbedPropertySection extends
		AbstractTabbedPropertiesSection {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.ofs.workbench.editors.propeties.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected abstract void configureProperties();

	
	/* (non-Javadoc)
	 * @see com.odcgroup.ofs.workbench.editors.propeties.AbstractBasicPropertiesSection#getReferenceObject(org.eclipse.emf.ecore.EClass)
	 */
	public EObject getReferenceObject(EReference reference) {
		return ProcessFactory.eINSTANCE.create(reference.getEReferenceType());
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.propeties.AbstractBasicPropertiesSection#checkIsTaken(java.lang.String)
	 */
	public boolean checkIsTaken(Object key, EStructuralFeature feature){
		return false;
	}

}
