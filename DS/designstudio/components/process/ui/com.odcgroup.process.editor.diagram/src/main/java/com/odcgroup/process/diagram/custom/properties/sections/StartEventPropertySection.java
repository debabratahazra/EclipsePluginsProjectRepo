package com.odcgroup.process.diagram.custom.properties.sections;

import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class StartEventPropertySection extends AbstractPropertiesSection {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {	

		setUseThreeFourthLayout(true);
		SimpleGroupWidget group = new SimpleGroupWidget(null);

		SimpleTextWidget desc = new SimpleTextWidget(ProcessPackage.eINSTANCE.getNamedElement_Description(), "Description");
		desc.setFillHorizontal(true);
		desc.setMultiline(true);
		group.addPropertyFeature(desc);

		this.addPropertyFeature(group);
	
	}	
}
