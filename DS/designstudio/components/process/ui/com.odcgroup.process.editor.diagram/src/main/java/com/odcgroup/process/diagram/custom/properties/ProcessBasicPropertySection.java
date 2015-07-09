package com.odcgroup.process.diagram.custom.properties;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.workbench.editors.properties.AbstractBasicPropertiesSection;

public abstract class ProcessBasicPropertySection extends
		AbstractBasicPropertiesSection {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.ofs.workbench.editors.propeties.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected abstract void configureProperties();

	
	/* (non-Javadoc)
	 * @see com.odcgroup.ofs.workbench.editors.propeties.AbstractBasicPropertiesSection#getReferenceObject(org.eclipse.emf.ecore.EClass)
	 */
	public EFactory getEFactory() {
		return ProcessFactory.eINSTANCE;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.propeties.AbstractBasicPropertiesSection#checkIsTaken(java.lang.String)
	 */
	public boolean checkIsTaken(Object key, EStructuralFeature feature){
		return false;
	}

}
