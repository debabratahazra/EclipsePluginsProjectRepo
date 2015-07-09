package com.odcgroup.process.diagram.custom.properties.sections.legacy;

import com.odcgroup.process.diagram.custom.properties.ProcessBasicPropertySection;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;

public class StartEventPropertySection extends ProcessBasicPropertySection {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {			
		addAttribute(new SectionAttributePropertyHelper("Description", ProcessPackage.eINSTANCE.getNamedElement_Description(), null, true));	
	}	
}
