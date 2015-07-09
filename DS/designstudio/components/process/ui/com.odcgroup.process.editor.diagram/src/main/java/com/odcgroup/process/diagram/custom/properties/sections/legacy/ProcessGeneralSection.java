package com.odcgroup.process.diagram.custom.properties.sections.legacy;

import com.odcgroup.process.diagram.custom.properties.ProcessBasicPropertySection;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;

public class ProcessGeneralSection extends ProcessBasicPropertySection {	

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.diagram.custom.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		/*
		SectionAttributePropertyHelper processName = new SectionAttributePropertyHelper("Name", ProcessPackage.eINSTANCE.getProcess_DisplayName(), null);
		processName.setLocalizeableAdapterFactory(ProcessLocalizableAdapterFactory.INSTANCE, 0);		
		addAttribute(processName);
		*/
		
		addAttribute(new SectionAttributePropertyHelper("Display Name", ProcessPackage.eINSTANCE.getProcess_DisplayName(), null));
		/*
		SectionAttributePropertyHelper desc = new SectionAttributePropertyHelper("Description", ProcessPackage.eINSTANCE.getProcess_Description(), null, true);
		desc.setLocalizeableAdapterFactory(ProcessLocalizableAdapterFactory.INSTANCE, 1);
		addAttribute(desc);
		*/
		addAttribute(new SectionAttributePropertyHelper("Comment", ProcessPackage.eINSTANCE.getProcess_Comment(), null, true));		
	}

}
