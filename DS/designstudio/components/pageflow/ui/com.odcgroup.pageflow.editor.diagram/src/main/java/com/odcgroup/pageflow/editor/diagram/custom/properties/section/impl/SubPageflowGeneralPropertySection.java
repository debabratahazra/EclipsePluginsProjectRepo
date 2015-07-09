package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;

public class SubPageflowGeneralPropertySection extends PageflowBasicPropertySection {

	@Override
	protected void configureProperties() {
		addAttribute(new SectionAttributePropertyHelper(Messages.SubPageflowGeneralPropertySectionNameLabel, false, PageflowPackage.eINSTANCE.getState_DisplayName(), null));
		addAttribute(new SectionAttributePropertyHelper(Messages.TechnicalPropertySectionDescLabel, PageflowPackage.eINSTANCE.getState_Desc(), null, true));
		
	}

	
	
}
