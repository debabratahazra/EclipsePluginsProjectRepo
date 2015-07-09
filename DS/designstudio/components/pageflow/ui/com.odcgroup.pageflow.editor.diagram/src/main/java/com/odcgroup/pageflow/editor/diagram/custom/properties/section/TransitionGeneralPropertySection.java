package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.CheckboxPropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

/**
 * 
 * @author pkk
 */
public class TransitionGeneralPropertySection extends AbstractPropertiesSection {
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.gmf.editors.properties.AbstractPropertiesSection#configureProperties()
	 */
	protected void configureProperties() {

		setUseThreeFourthLayout(true);
		SimpleGroupWidget group = new SimpleGroupWidget(null);	
		
		SimpleTextWidget transitionName = new SimpleTextWidget(PageflowPackage.eINSTANCE.getTransition_DisplayName(),
				Messages.TransitionGeneralPropertySectionNameLabel);
		transitionName.setFillHorizontal(true);
		group.addPropertyFeature(transitionName);
		
		CheckboxPropertyWidget transitionIsIdempotent = new CheckboxPropertyWidget(PageflowPackage.eINSTANCE.getTransition_IsIdempotent(),
				Messages.TransitionGeneralPropertySectionIsIdempotentLabel,Boolean.TRUE);
		group.addPropertyFeature(transitionIsIdempotent);
		
		SimpleTextWidget transitionDesc = new SimpleTextWidget(PageflowPackage.eINSTANCE.getTransition_Desc(),
				Messages.TransitionGeneralPropertySectionDescLabel);
		transitionDesc.setMultiline(true);
		group.addPropertyFeature(transitionDesc);
		

		
		this.addPropertyFeature(group);		
	}
	
}
