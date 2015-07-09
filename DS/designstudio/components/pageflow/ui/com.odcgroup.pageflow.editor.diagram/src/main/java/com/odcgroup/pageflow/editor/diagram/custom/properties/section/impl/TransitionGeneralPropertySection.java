package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;

/**
 * @author pkk
 *
 */
public class TransitionGeneralPropertySection extends PageflowBasicPropertySection {
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		addAttribute(new SectionAttributePropertyHelper(Messages.TransitionGeneralPropertySectionNameLabel, true, PageflowPackage.eINSTANCE.getTransition_DisplayName(), null));
		addAttribute(new SectionAttributePropertyHelper(Messages.TransitionGeneralPropertySectionDescLabel, PageflowPackage.eINSTANCE.getTransition_Desc(), null, true));
		addAttribute(new SectionAttributePropertyHelper(Messages.TransitionGeneralPropertySectionIsIdempotentLabel, PageflowPackage.eINSTANCE.getTransition_IsIdempotent(), null, false));
	}
	
	
	
}
