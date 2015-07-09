package com.odcgroup.process.diagram.custom.properties.sections.legacy;

import com.odcgroup.process.diagram.custom.properties.ProcessBasicPropertySection;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionBooleanPropertyHelper;

public class ProcessTechnicalSection extends ProcessBasicPropertySection {	

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.diagram.custom.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {		
		addAttribute(new SectionAttributePropertyHelper("ID", ProcessPackage.eINSTANCE.getProcess_Name(), null));	
		addAttribute(new SectionAttributePropertyHelper("Target File Name", ProcessPackage.eINSTANCE.getProcess_Filename(), null));	
		addAttribute(new SectionBooleanPropertyHelper("Pageflow calls Workflow", "Workflow calls Pageflow", ProcessPackage.eINSTANCE.getProcess_Inverted()));
				
	}

}