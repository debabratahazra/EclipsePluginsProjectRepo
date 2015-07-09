package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;

public class SubPageflowPropertySection extends PageflowBasicPropertySection {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		addAttribute(new SectionAttributePropertyHelper(Messages.SubPageflowPropertySectionIDLabel, true, true, PageflowPackage.eINSTANCE.getState_Name(), null));
		addAttribute(new SectionAttributePropertyHelper(Messages.TechnicalPropertySectionDescLabel, PageflowPackage.eINSTANCE.getSubPageflowState_TechDesc(), null, true));
	}

}
