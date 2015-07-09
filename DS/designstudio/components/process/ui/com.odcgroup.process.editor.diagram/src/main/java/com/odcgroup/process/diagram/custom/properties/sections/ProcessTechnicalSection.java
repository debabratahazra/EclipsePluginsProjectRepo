package com.odcgroup.process.diagram.custom.properties.sections;

import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.RadioGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

public class ProcessTechnicalSection extends AbstractPropertiesSection {	

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.diagram.custom.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		
		setUseThreeFourthLayout(true);
		SimpleGroupWidget group = new SimpleGroupWidget(null);
		
		SimpleTextWidget processName = new SimpleTextWidget(ProcessPackage.eINSTANCE.getProcess_Name(),
				"ID");
		processName.setFillHorizontal(true);
		group.addPropertyFeature(processName);
		
		SimpleTextWidget targetFileName = new SimpleTextWidget(ProcessPackage.eINSTANCE.getProcess_Filename(),
				"Target File Name");
		targetFileName.setFillHorizontal(true);
		group.addPropertyFeature(targetFileName);
		
		RadioGroupWidget problemMgt = new RadioGroupWidget(ProcessPackage.eINSTANCE.getProcess_Inverted(), "", false);
		problemMgt.addRadio("Pageflow calls Workflow", Boolean.TRUE.booleanValue());
		problemMgt.addRadio("Workflow calls Pageflow", Boolean.FALSE.booleanValue());
		group.addPropertyFeature(problemMgt);
		
		this.addPropertyFeature(group);					
	}

}