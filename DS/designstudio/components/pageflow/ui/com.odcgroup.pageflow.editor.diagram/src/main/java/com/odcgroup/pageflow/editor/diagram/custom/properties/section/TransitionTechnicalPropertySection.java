package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class TransitionTechnicalPropertySection extends AbstractPropertiesSection {
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.gmf.editors.properties.AbstractPropertiesSection#configureProperties()
	 */
	protected void configureProperties() {

		setUseThreeFourthLayout(true);
		SimpleGroupWidget group = new SimpleGroupWidget(null);	
		
		SimpleTextWidget transitionId = new SimpleTextWidget(PageflowPackage.eINSTANCE.getTransition_Name(),
				Messages.TransitionTechnicalPropertySectionIDLabel);
		transitionId.setFillHorizontal(true);
		group.addPropertyFeature(transitionId);
		
		SimpleTextWidget transitionTechDesc = new SimpleTextWidget(PageflowPackage.eINSTANCE.getTransition_TechDesc(),
				Messages.TransitionTechnicalPropertySectionDescLabel);
		transitionTechDesc.setMultiline(true);
		group.addPropertyFeature(transitionTechDesc);
		
		this.addPropertyFeature(group);		
	}
	

}
