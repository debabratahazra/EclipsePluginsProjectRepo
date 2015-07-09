package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;

public class TransitionTechnicalPropertySection extends PageflowBasicPropertySection {


	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		addAttribute(new SectionAttributePropertyHelper(Messages.TransitionTechnicalPropertySectionIDLabel, PageflowPackage.eINSTANCE.getTransition_Name(), null));
		addAttribute(new SectionAttributePropertyHelper(Messages.TransitionTechnicalPropertySectionDescLabel, PageflowPackage.eINSTANCE.getTransition_TechDesc(), null, true));		
	}
	

}
