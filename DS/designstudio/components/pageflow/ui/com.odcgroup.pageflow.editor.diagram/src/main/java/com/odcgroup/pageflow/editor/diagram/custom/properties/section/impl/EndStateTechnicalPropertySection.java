package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;

public class EndStateTechnicalPropertySection extends PageflowBasicPropertySection {
	
	private static final String exitUriToolTip = Messages.PropertiesActionURIToolTip;

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		addAttribute(new SectionAttributePropertyHelper(Messages.EndStateTechnicalPropertySectionIDLabel, true, true, PageflowPackage.eINSTANCE.getState_Name(), null));
		addAttribute(new SectionAttributePropertyHelper(Messages.EndStateTechnicalPropertySectionExitURILabel, PageflowPackage.eINSTANCE.getEndState_ExitUrl(), null, exitUriToolTip, true));
		addAttribute(new SectionAttributePropertyHelper(Messages.TechnicalPropertySectionDescLabel, PageflowPackage.eINSTANCE.getEndState_TechDesc(), null, true));
	}
	

}

