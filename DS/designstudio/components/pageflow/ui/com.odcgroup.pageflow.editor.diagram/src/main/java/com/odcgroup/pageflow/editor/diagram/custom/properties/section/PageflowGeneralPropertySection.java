package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class PageflowGeneralPropertySection  extends AbstractPropertiesSection {
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.gmf.editors.properties.AbstractPropertiesSection#configureProperties()
	 */
	protected void configureProperties() {
		
		setUseThreeFourthLayout(true);
		SimpleGroupWidget group = new SimpleGroupWidget(null);
		
		SimpleTextWidget pageflowName = new SimpleTextWidget(PageflowPackage.eINSTANCE.getPageflow_Name(),
				Messages.PageflowGeneralPropertySectionNameLabel);
		pageflowName.setFillHorizontal(true);
		group.addPropertyFeature(pageflowName);
		
		SimpleTextWidget pageflowDesc = new SimpleTextWidget(PageflowPackage.eINSTANCE.getPageflow_Desc(),
				Messages.PageflowGeneralPropertySectionDescLabel);
		pageflowDesc.setMultiline(true);
		group.addPropertyFeature(pageflowDesc);
		
		this.addPropertyFeature(group);		
	}
}

