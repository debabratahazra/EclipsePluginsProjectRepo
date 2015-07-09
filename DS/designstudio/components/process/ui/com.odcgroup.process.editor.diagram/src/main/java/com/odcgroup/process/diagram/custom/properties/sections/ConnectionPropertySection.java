package com.odcgroup.process.diagram.custom.properties.sections;

import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class ConnectionPropertySection extends AbstractPropertiesSection {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.odcgroup.workflow.tools.processDesigner.ui.properties.sections.
	 * AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {

		setUseThreeFourthLayout(true);
		SimpleGroupWidget group = new SimpleGroupWidget(null);

		SimpleTextWidget name = new SimpleTextWidget(ProcessPackage.eINSTANCE.getFlow_Name(), "Name");
		name.setFillHorizontal(true);
		group.addPropertyFeature(name);

		this.addPropertyFeature(group);
	}

}
