package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;

public class PageflowPropertyReferenceSection extends PageflowBasicPropertySection {
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		addListReference(new SectionListReferencePropertyHelper(null, PageflowPackage.eINSTANCE.getPageflow_Property(),null, PageflowFactory.eINSTANCE,PageflowPackage.eINSTANCE.getProperty(), false));
	}

}
