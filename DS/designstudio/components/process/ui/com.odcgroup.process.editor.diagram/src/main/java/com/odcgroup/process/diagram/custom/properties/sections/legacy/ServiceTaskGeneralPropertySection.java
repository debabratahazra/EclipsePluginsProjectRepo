package com.odcgroup.process.diagram.custom.properties.sections.legacy;

import com.odcgroup.process.diagram.custom.properties.ProcessBasicPropertySection;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;

public class ServiceTaskGeneralPropertySection extends ProcessBasicPropertySection {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {	
		
		SectionAttributePropertyHelper name = new SectionAttributePropertyHelper("Name", ProcessPackage.eINSTANCE.getNamedElement_Name(), null);
		//name.setLocalizeableAdapterFactory(ProcessLocalizableAdapterFactory.INSTANCE, 0);		
		addAttribute(name);	
		
		SectionAttributePropertyHelper desc = new SectionAttributePropertyHelper("Description", ProcessPackage.eINSTANCE.getNamedElement_Description(), null, true);
		//desc.setLocalizeableAdapterFactory(ProcessLocalizableAdapterFactory.INSTANCE, 1);		
		addAttribute(desc);	
	}	

}
