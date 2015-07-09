package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

/**
 *
 * @author pkk
 *
 */
public class ViewStateGeneralPropertySection extends AbstractPropertiesSection {
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.gmf.editors.properties.AbstractPropertiesSection#configureProperties()
	 */
	protected void configureProperties() {

		setUseThreeFourthLayout(true);
		SimpleGroupWidget group = new SimpleGroupWidget(null);	
		
		SimpleTextWidget viewStateName = new SimpleTextWidget(PageflowPackage.eINSTANCE.getState_DisplayName(),
				Messages.ViewStateGeneralPropertySectionNameLabel);
		viewStateName.setFillHorizontal(true);
		group.addPropertyFeature(viewStateName);
		
		SimpleTextWidget viewStateDesc = new SimpleTextWidget(PageflowPackage.eINSTANCE.getState_Desc(),
				Messages.ViewStateGeneralPropertySectionDescLabel);
		viewStateDesc.setMultiline(true);
		group.addPropertyFeature(viewStateDesc);
		
		this.addPropertyFeature(group);		
	}	
	

}

