package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;

public class PageflowTechnicalPropertySection extends PageflowBasicPropertySection {
	
private static final String errorViewToolTip = Messages.PropertiesActionURIToolTip;

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		addAttribute(new SectionAttributePropertyHelper(Messages.PageflowTechnicalPropertySectionErrorURILabel, PageflowPackage.eINSTANCE.getView_Url(), PageflowPackage.eINSTANCE.getPageflow_ErrorView(), errorViewToolTip));
		addAttribute(new SectionAttributePropertyHelper(Messages.PageflowTechnicalPropertySectionFileNameLabel, PageflowPackage.eINSTANCE.getPageflow_FileName(), null));
		addAttribute(new SectionAttributePropertyHelper(Messages.TechnicalPropertySectionDescLabel, PageflowPackage.eINSTANCE.getPageflow_TechDesc(), null, true));		
	}

}
