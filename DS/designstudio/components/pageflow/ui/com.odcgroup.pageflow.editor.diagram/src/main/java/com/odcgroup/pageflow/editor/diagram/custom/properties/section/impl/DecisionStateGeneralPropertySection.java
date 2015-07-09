package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import org.eclipse.swt.events.SelectionEvent;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;


public class DecisionStateGeneralPropertySection extends PageflowBasicPropertySection {
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */	
	protected void configureProperties() {
		addAttribute(new SectionAttributePropertyHelper(Messages.DecisionStateGeneralPropertySectionNameLabel, false, PageflowPackage.eINSTANCE.getState_DisplayName(), null));
		//addAttribute(new SectionAttributePropertyHelper("Name", PageflowPackage.eINSTANCE.getState_Name(), null));
		addAttribute(new SectionAttributePropertyHelper(Messages.DecisionStateGeneralPropertySectionDescLabel, PageflowPackage.eINSTANCE.getState_Desc(), null, true));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}

