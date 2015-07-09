package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;

public class ViewStateTechnicalPropertySection extends PageflowBasicPropertySection {
	
		
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */	
	protected void configureProperties() {
		addAttribute(new SectionAttributePropertyHelper(Messages.ViewStateTechnicalPropertySectionIDLabel, true, true, PageflowPackage.eINSTANCE.getState_Name(), null));
		//addAttribute(new SectionAttributePropertyHelper(Messages.ViewStateTechnicalPropertySectionURILabel, PageflowPackage.eINSTANCE.getView_Url(), PageflowPackage.eINSTANCE.getViewState_View(), errorViewToolTip));
		addAttribute(new SectionAttributePropertyHelper(Messages.TechnicalPropertySectionDescLabel, PageflowPackage.eINSTANCE.getViewState_TechDesc(), null, true));
	}
	

}
