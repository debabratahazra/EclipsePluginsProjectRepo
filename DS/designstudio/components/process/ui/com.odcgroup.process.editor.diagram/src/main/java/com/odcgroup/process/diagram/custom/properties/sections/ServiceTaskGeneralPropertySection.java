package com.odcgroup.process.diagram.custom.properties.sections;

import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class ServiceTaskGeneralPropertySection extends AbstractPropertiesSection {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {				
		
		setUseThreeFourthLayout(true);
		SimpleGroupWidget group = new SimpleGroupWidget(null);
		
		SimpleTextWidget name = new SimpleTextWidget(ProcessPackage.eINSTANCE.getNamedElement_Name(),
				"Name");
		name.setFillHorizontal(true);
		group.addPropertyFeature(name);
		
		SimpleTextWidget pageflowDesc = new SimpleTextWidget(ProcessPackage.eINSTANCE.getNamedElement_Description(),
				"Description");
		pageflowDesc.setMultiline(true);
		group.addPropertyFeature(pageflowDesc);
		
		this.addPropertyFeature(group);	
	}	

}
