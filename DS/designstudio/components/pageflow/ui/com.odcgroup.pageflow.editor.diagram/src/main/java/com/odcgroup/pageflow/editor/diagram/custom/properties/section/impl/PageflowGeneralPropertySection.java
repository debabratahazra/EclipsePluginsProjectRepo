package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;

public class PageflowGeneralPropertySection extends PageflowBasicPropertySection {
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		addAttribute(new SectionAttributePropertyHelper(Messages.PageflowGeneralPropertySectionNameLabel, PageflowPackage.eINSTANCE.getPageflow_Name(), null));
		addAttribute(new SectionAttributePropertyHelper(Messages.PageflowGeneralPropertySectionDescLabel, PageflowPackage.eINSTANCE.getPageflow_Desc(), null,true));
	}

}
