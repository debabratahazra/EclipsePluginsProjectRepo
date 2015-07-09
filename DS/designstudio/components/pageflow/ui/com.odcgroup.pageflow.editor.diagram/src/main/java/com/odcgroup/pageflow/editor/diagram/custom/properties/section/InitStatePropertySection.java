package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class InitStatePropertySection extends AbstractPropertiesSection {
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.gmf.editors.properties.AbstractPropertiesSection#configureProperties()
	 */
	protected void configureProperties() {

		setUseThreeFourthLayout(true);
		SimpleGroupWidget group = new SimpleGroupWidget(null);		
		
		SimpleTextWidget desc = new SimpleTextWidget(PageflowPackage.eINSTANCE.getState_Desc(),
				Messages.InitStatePropertySectionDescLabel);
		desc.setMultiline(true);
		group.addPropertyFeature(desc);
		
		this.addPropertyFeature(group);		
	}
	

}
